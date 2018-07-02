/**
 * 
 */
package utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author damien
 *
 */
public class BeanUtilsTest {

	class TestSimple {
		private String id;
		private String test;

		public TestSimple(String id, String test) {
			super();
			this.id = id;
			this.test = test;
		}

	}

	class TestComplex {
		private String id;
		private TestSimple testSimple;

		public TestComplex(String id, TestSimple testSimple) {
			super();
			this.id = id;
			this.testSimple = testSimple;
		}

	}

	class TestSimpleChildren extends TestSimple {
		private String newProperty;

		public TestSimpleChildren(String id, String test, String newProperty) {
			super(id, test);
			this.newProperty = newProperty;
		}
	}

	@Test
	public void test() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		TestSimple sources = new TestSimple("idtest", "testtu");
		TestSimple target = new TestSimple("id1", "test1");
		BeanUtils.copyProperties(sources, target);
		Assertions.assertThat(target).isEqualToComparingFieldByFieldRecursively(new TestSimple("idtest", "testtu"));
	}

	@Test
	public void test1() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		TestSimple sources = new TestSimple(null, "testtu");
		TestSimple target = new TestSimple("id1", "test1");
		BeanUtils.copyProperties(sources, target);
		Assertions.assertThat(target).isEqualToComparingFieldByFieldRecursively(new TestSimple(null, "testtu"));
	}

	@Test
	public void test2() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		TestSimple sources = new TestSimple("idtest", "testtu");
		TestSimple target = new TestSimple("id1", "test1");
		BeanUtils.copyProperties(sources, target, "test");
		Assertions.assertThat(target).isEqualToComparingFieldByFieldRecursively(new TestSimple("idtest", "test1"));
	}

	@Test
	public void test3() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		TestSimple sources = new TestSimple(null, "testtu");
		TestSimple target = new TestSimple("id1", "test1");
		BeanUtils.copyProperties(sources, target, "test");
		Assertions.assertThat(target).isEqualToComparingFieldByFieldRecursively(new TestSimple(null, "test1"));
	}

	@Test
	public void test4() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		TestComplex sources = new TestComplex("id", new TestSimple("id1", "testtu"));
		TestComplex target = new TestComplex("id1", new TestSimple("id", "test1"));
		BeanUtils.copyProperties(sources, target);
		Assertions.assertThat(target)
				.isEqualToComparingFieldByFieldRecursively(new TestComplex("id", new TestSimple("id1", "testtu")));
	}

	@Test
	public void test5() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		TestComplex sources = new TestComplex("id", new TestSimple("id1", "testtu"));
		TestComplex target = new TestComplex("id1", new TestSimple("id", "test1"));
		BeanUtils.copyProperties(sources, target, "testSimple.id");
		Assertions.assertThat(target)
				.isEqualToComparingFieldByFieldRecursively(new TestComplex("id", new TestSimple("id", "testtu")));
	}

	@Test
	public void test6() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		TestComplex sources = new TestComplex("id", new TestSimpleChildren("id1", "testtu", "children"));
		TestComplex target = new TestComplex("id1", new TestSimple("id", "test1"));
		BeanUtils.copyProperties(sources, target, "id");
		Assertions.assertThat(target).isEqualToComparingFieldByFieldRecursively(
				new TestComplex("id1", new TestSimpleChildren("id1", "testtu", "children")));
	}

	@Test
	public void test7() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		TestComplex sources = new TestComplex("id", new TestSimpleChildren("id1", "testtu", "children"));
		TestComplex target = new TestComplex("id1", new TestSimple("id", "test1"));
		BeanUtils.copyProperties(sources, target, "testSimple.test");
		Assertions.assertThat(target)
				.isEqualToComparingFieldByFieldRecursively(new TestComplex("id", new TestSimple("id1", "test1")));
	}

	@Test
	public void test8() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		TestComplex sources = new TestComplex("id1", new TestSimple("id", "test1"));
		TestComplex target = new TestComplex("id", new TestSimpleChildren("id1", "testtu", "children"));
		BeanUtils.copyProperties(sources, target, "testSimple.test");
		Assertions.assertThat(target).isEqualToComparingFieldByFieldRecursively(
				new TestComplex("id1", new TestSimpleChildren("id", "testtu", "children")));
	}
}
