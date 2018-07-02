/**
 * 
 */
package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author damien
 *
 */
public class BeanUtils {

	public static <T> void copyProperties(T source, T target) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = source.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().contains("$")) {
				continue;
			}
			field.setAccessible(true);
			field.set(target, field.get(source));
			field.setAccessible(false);
		}
	}

	public static <T> void copyProperties(T source, T target, String... ignoreProperties)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = source.getClass().getSuperclass() == Object.class ? source.getClass().getDeclaredFields()
				: source.getClass().getSuperclass().getDeclaredFields();
		List<String> propertiesToIgnore = new ArrayList<String>(Arrays.asList(ignoreProperties));
		for (Field field : fields) {
			if (field.getName().contains("$") || propertiesToIgnore.contains(field.getName())) {
				continue;
			}
			field.setAccessible(true);
			if (existInPropertiesToIgnore(field, propertiesToIgnore)) {
				Object fieldSource = field.get(source);
				Object fieldTarget = field.get(target);
				String[] propertiesForField = propertiesToIgnore.stream()
						.filter((string) -> string.startsWith(field.getName() + "."))
						.map((string) -> string.replaceFirst(field.getName() + ".", "")).toArray(String[]::new);
				BeanUtils.copyProperties(fieldSource, fieldTarget, propertiesForField);
				field.set(target, fieldTarget);
				field.setAccessible(false);
				continue;
			}

			field.set(target, field.get(source));
			field.setAccessible(false);

		}
	}

	private static boolean existInPropertiesToIgnore(final Field field, final Collection<String> propertiesToIgnore) {
		return propertiesToIgnore.stream().anyMatch((string) -> string.startsWith(field.getName() + "."));
	}

}
