# Mapper
This is a mapper to copy properties between two objects. 

# How use 

```java
MyObject source = new MyObjet(...);
MyObject target = new MyObjet(...);
//my object target will get all source field value except field1 and field2.
BeanUtils.copyProperties(source,target,"field1", "field2");
```

With this methode i can ignore fields in complexe type like that :


```java
MyObject source = new MyObjet(...);
MyObject target = new MyObjet(...);
//my object target will get all source field value except id in field1 and label in field2.
BeanUtils.copyProperties(source,target,"field1.id", "field2.label");
```
