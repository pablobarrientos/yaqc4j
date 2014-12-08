yaqc4j
======

Yaqc4j is an specification-based testing framework, based on QuickCheck for Haskell by John Hughes. It creates test data automatically through user-defined and built-in generators.

## What is this tool about?

YetAnother QuickCheck for Java, or YAQC4J for short, is a non-intrusive testing tool based on the ideas behind Clasessen’s QuickCheck tool. The original QuickCheck was written in a functional setting, in the pure functional language Haskell. YAQC4J is a translation of those ideas to the OO paradigm. It also attacks new problems generated from the migration of those ideas to OOP (like singletons, interfaces, abstract classes, etc).

## What is the philosophy behind QuickCheck?
In the original QuickCheck tool, specifications or tests are the form in which properties for functions are proven. All tests are specifically generated to attempt to falsify assertions. The method consists on running many times the given specification. Different randomly generated values are used on each run. That is the philosophy we took from QuickCheck for our tool. We recommend to take a look at the Haskell library or read the corresponding paper, because we won’t explain it here in depth and it is not neccessary for using our tool.

JUnit is the well-known framework used in Java for unit testing. In JUnit, tests are written by subclassifying the class TestCase and writing methods that represent particular tests in recent versions of JUnit annotations can be used for the same purpose. All test methods begin with test or have a @Test annotation. There are also two inherited (empty) methods to set a context for every test, setUp and tearDown, that can be overidden, if necessary. The framework runs the test class, method by method, executing setUp and tearDown each time. It also provides the possibility to run just one of the methods or a test suite.

Every method should represent a special configuration (a special state in which the object is tested). Generally, each test method sends a single message to the object under testing. But before the object receives the message, the object itself must be created and initizalized with certain collaborators. The developer has two options in JUnit: to initialize the object in the setUp method or to hardcode the creation and initialization of the object.

YAQC4J enters at this point to provide the creation of randomic object. The developer doesn’t need to write as many different tests as different states an object could be when the method isinvoked. In many cases, all possible states are infinite! Our tool runs each test method a given number of times, and every time different randomly generated objects are provided.

## How do I write and run my test cases with YAQC4J?
In order to run a YAQC4J test, you have to specify the name of the Yaqc4j’s runner, which is QCCheckRunner. Write the @RunWith annotation in your test class in this way:
```
@RunWith(ar.edu.unq.yaqc4j.runners.QCCheckRunner.class)
public class MyTest {...}
```
Another important difference between JUnit tests and YAQC4J ’s tests, is the use of parameters in test methods. Parameters in YAQC4J represent the random generated values. Here is a simple example, which tests the following property: if we reverse twice a string (any string!), the resulting string must be equivalent to the original one:
```
@RunWith(ar.edu.unq.yaqc4j.runners.QCCheckRunner.class)
public class SimpleTest {
  @Test
  public void testReverse(final String str) {
    StringBuffer buff = new StringBuffer(str);
    assertEquals(str, buff.reverse().reverse().toString());
  }
}
```
YAQC4J allows to configure the conditions of the test by providing the @Configuration annotation. The parameters for this annotation are:
* minsize / maxsize: the minimum (and/or maximum) size of the object generated. The idea of size is relative to the kind of object. For example, for strings the size can be the length, and for numbers it can be the lower and upper bounds. Also, if you define your own generators (see Secci´on 0.5), you can give a special meaning to the minsize/maxsize, and use them as you prefer.
* tests: number of times YAQC4Jmust run the test(s).
* maxArgumentFails: sometimes certain object could not be useful for our tests (they don’t satisfy some properties). We can discard those objects, using the Implication feature (see Sectionimply). maxArgumentFails indicates how many times the tool could fail generating a good object for the test. When the test is ran, and the tool is unable to generate an object, the test will fail.
* distribution: represents the random distribution used to generate the object. YAQC4Jprovides uniform (default), normal, inverted normal, negative and positive normal distributions. The developer could also define his own distribution by subclassifying the class Distribution.

The following example shows the use of the annotation @Configuration:
```
@RunWith(ar.edu.unq.yaqc4j.runners.QCCheckRunner.class)
@Configuration(maxsize = 50, minsize = -50, tests = 100)
public class SimpleTest {
  @Test
  @Configuration(distribution=InvertedNormalDistribution.class)
  public void classConfigurationTest(final int i) {
    assertTrue("@Configuration (or int generator) failed",
      i >= -50 && i <= 50);
  }

  @Test
  @Configuration(maxsize = 5, minsize = -5)
  public void methodConfigurationTest(final int i) {
     assertTrue("@Configuration (or in generator) failed", i >= -5 && i <= 5);
  }
}
```
Notice that the annotation @Configuration and all its parameters can be used at class level and at method(single test) level. Method-level configurations will overwrite class-level configurations.
When we run this test and if everything went well, we receive the message:
```OK. Passed 300 tests.```

## Which types and classes have a predefined generator?
Our tool provides several generators for many standard types: String, Long, Byte, Boolean, Character, Float, Short, StringBuilder, StringBuffer, Void, BigDecimal, BigInteger, Date, Calendar, primitives, positive integers, negative integers, arrays, List, Map, Queue, Set, enumeratives, etc.
We also include many classes equivalent to the combinators written in the original QuickCheck: oneof, element, frequencyGen, and percentageGen. Moreover, we added more generators for features present only in OO programming: NullGen, IdentityGen, CloneGen. Finally, a functor-like generator, TransformerGenerator, allows mapping from one type to another. That is the way in which we created a generator of Calendar instances by using the generator of Date instances, which had been already defined.

## Can I create custom generators?
You can define your own generator for any class you like. All generators must implement the Gen<T> interface, being T the class of the object to be generated.
```
public interface Gen<T> {
  T arbitrary(Distribution random, long minsize, long maxsize);
}
```
As you can see, arbitrary(...) is the only method that is actually required to create a generator. The parameters (random, minsize and maxsize) have the same meaning than in the anotation @Configuration.
We copy an example of a predefined generator below:
```
public final class CharacterGen implements Gen<Character> {
  private static final int MIN_CHAR = 32;
  private static final int MAX_CHAR = 355;
  public Character arbitrary(final Distribution random, final long minsize, final long maxsize) {
   return Character.valueOf((char) Arbitrary.choose(random, Math.max(MIN_CHAR, minsize), Math.min(maxsize, MAX_CHAR)));
  }
}
```
## How do I specify the generators to be used in my tests?
YAQC4Jprovides the annotation @Generator and @UseGenerators to specify (at method-level or class-level) the generator to be used according to the parameters defined in out tests.
@Generator takes two parameters: generator, which is the class name of the generator; and klass, which is the class name of the generated object/s. When YAQC4J ’s runner executes the tests, it uses this generator for generating the objects used by the test.
@UseGenerators takes an array of generators. The runner will choose randomly one generator of this array for generating the objects used by the test.
```
@RunWith(ar.edu.unq.yaqc4j.runners.QCCheckRunner.class)
public class MyTest {
  @Test
  @Generator(klass = MyClass.class, generator = MyGenerator.class)
  public void testSomething(final Myclass instance) {
    ...
  }
}
```
## Special conditions
Sometimes you need some contraint or precondition to be hold by the objects used in your test. The best option is to create a custom generator which considers the constraint(s). However, you can use the Implication.imply method, which receives a boolean representing the predicate the generated object must hold. In case the object generated is not suitable (i.e. it doesn’t hold the property/assertion) the test is discarded. A limit in the number of failures (object that doesn’t hold the property) before the test is declared failed could be specified as a metadata with the annotation @Configuration (parameter maxArgumentsFails).

## How do I specify the number of times a test should run?
Developers can specify the number of times a test runs with the configuration param in the @Configuration annotation.

## Classifying the input
YAQC4Jprovides two ways to classify the input randomly generated for your tests.
* @Classify: it is an annotation which can be used at parameter level to indicate that YAQC4Jmust report the distribution of the generated input. This annotation receives a name and an array of classifiers. All classifier classes are subclasses of the abstract class Classifier, which defines a simple method for indicating which condition the object must hold to be classified into that category.
```public abstract boolean classify(T object);```
Notice that you can include as many classifiers as you want or need in your test parameter, and they can overlap each other. It is, a given random object can fall in more than one category.
For instance, in the following case, a list with one element is included in NonEmptyListClassifier and SingletonListClassifier.
```
@Test
@Generator(generator=SimpleCollectionGen.class, klass=List.class)
@Configuration(maxsize=200)
public void sample2(@Classify(name="sampleLists",
                              classifiers = {EmptyListClassifier.class,NonEmptyListClassifier.class,SingletonListClassifier.class })
                    final List<Integer> list) {
  ...
}
```
When we run this example, we obtain:
```
Results for collector "sampleIntegers":
even: 142 occurences (47.333332%)
odd: 158 occurences (52.666668%)
OK. Passed 300 tests.
```
* @Collect: it is another annotation which can also be used at parameter level to indicate that YAQC4Jmust report the distribution of the generated input. However, this annotation captures partitions of the type. It is, the categories are disjoint, so any object falls in one and only one category. For instance, if we want to see the distribution of the identity partition for the following test, we can write:
```
@Configuration(tests = 300)
public class SampleCollector {
  @Test
  @Configuration(maxsize=10, minsize=-10)
  @Generator(klass = Integer.class, generator = IntegerGen.class)
  public void sample1(@Classify(name="sampleIntegers",
                                classifiers = {EvenClassifier.class, OddClassifier.class })
                      final Integer a,
                      @Collect(name="partitionInt",
                               collector=IntegerPartititionCollector.class)
                      final Integer b) {
    ...
  }
```
The annotation @Collect receives the name of the collector, and the subclass of Collector for collecting the results. In this case, we created the class IdentityPartititionCollector, which reports the identity partition distribution. The class Collector (superclass of IdentityPartititionCollector), implements all logic required to collect the data. All its subclasses must implement the method: public abstract K getCategoryFor(T t).
When we run this example, we obtain:
```
Results for collector "partitionInt":
0: 37 occurences (12.333333%)
1: 11 occurences (3.6666667%)
2: 8 occurences (2.6666667%)
3: 19 occurences (6.3333335%)
4: 12 occurences (4.0%)
5: 14 occurences (4.6666665%)
6: 13 occurences (4.3333335%)
7: 16 occurences (5.3333335%)
8: 15 occurences (5.0%)
9: 18 occurences (6.0%)
-9: 18 occurences (6.0%)
-8: 15 occurences (5.0%)
-7: 16 occurences (5.3333335%)
-6: 13 occurences (4.3333335%)
-5: 14 occurences (4.6666665%)
-4: 11 occurences (3.6666667%)
-3: 19 occurences (6.3333335%)
-2: 13 occurences (4.3333335%)
-1: 18 occurences (6.0%)
```
These features allow us to verify whether the distribution and generators we use in our test are accurate. We than can change them in order to get better results or even better test coverage.

## Examples
* An abstract parameterized test for testing properties that should be met in every class that overwrites equals and/or hashCode methods.
```
@RunWith(ar.edu.unq.yaqc4j.runners.QCCheckRunner.class)
@Configuration(tests = 1)
public class EqualsHashProperties<T> {
   public EqualsHashProperties() {
      Arbitrary.registerConstructorGeneratorFor((Class<?>) Arbitrary.getSuperclassTypeParameter(this.getClass()));
   }

  @Test
  public void reflexiveEquals(final T a) {
    imply(a != null);
    assertEquals(a, a);
  }

  @Test
  public void simetricEquals(final T a, final T b) {
    imply(a != null && b != null);
    imply(a.equals(b));
    assertEquals(b, a);
  }

  @Test
  public void transitiveEquals(final T a, final T b, final T c) {
    imply(a != null && b != null && c != null);
    imply(a.equals(b));
    imply(b.equals(c));
    assertEquals(a, c);
  }

  @Test
  public void equalsHaveSameHashcode(final T a, final T b) {
    imply(a != null && b != null && a.equals(b));
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  public void hashcodeIsTheSame(final T a, final int times) {
    imply(a != null);
    int hc = a.hashCode();
    for (int i = 0; i < times; i++) {
      assertTrue(hc == a.hashCode());
    }
  }
}
```
Now, we can define our test for the type Integer as:
```
@Configuration(maxsize = 25, tests = 1, maxArgumentsFails = 10000)
@Generator(klass = int.class, generator = IntegerGen.class)
public class EqualsHashPropertiesInteger extends EqualsHashProperties<Integer> {
}
```

* A test with no parameters. In this case, the class Arbitrary is used to retrieve any available java.util.Date generator to be used by the ListGen lists generator. The list generator gives an instance of a list with some random size between 0 and 10. Arbitrary has a lot of useful methods to look for defined/registered generators for clases, creation of random objects from a randomly selected class, etc. We recommend to take a look at those methods as well as the predefined generators if you plan to define your own generator(s).
```pas
@Test
public void testDateList() {
  ListGen<Date> listGen = new ListGen<Date>((Gen<Date>) Arbitrary.getGeneratorFor(Arbitrary.defaultDistribution(), Date.class));
  List<Date> list = listGen.arbitrary(Arbitrary.defaultDistribution(), 0, 10);
  assertTrue(list.size() < 11);
}
```
## OOP issues
Functional and OO programming have strong differences. Moreover, Java and Haskell are absolutely different. Java has side effects, class hierarchies, interfaces, abstract classes, developers can define singletons, etc. Let’s see how YAQC4Jdeals with them.

### Singletons
The singleton pattern is a design pattern used to implement the mathematical concept of a singleton, and implies that one class has only one object in the whole system. That means that the instance is shared by all objects that reference and use it. The problem with this kind of pattern arises when the instance is stateful. In the context of unit testing, a singleton is shared among all test method that have it as aparameter. This, of course, could take to unexpected results if one test modifies the state of the singleton. For solving this issue, we created three different generators described below:
* **SingletonGenerator**, a simple abstract generator that access the singleton class method in run-time in order to get the unique instance of the class. A new object is returned every time because our framework resets the singleton class variable every time a test is runned, and forces the creation of a new instance. The singleton method and the class variable are assumed to be getInstance and instance respectively, but they can be specified when subclasifying or creating generator instances.
* **StatefulSerializableSingletonGen**, when the unique instance is stateful and implements the interface java.io.Serializable, we can provide a generator that gives a different instance every time a test request it. This generator makes use of this feature from the object to get another class instance. The same principles were used to define a generator for enumerative types, EnumGen.

### Abstract classes and hierarchies
Our idea for developers is to simplify the amount of code they have to write in order to use the tool. For that purpose, when a developer does not specifies or does not have a generator for a class, we try to create an instance by looking all generators defined for subclasses in its hierarchy and selecting randomly one of those generators. We proceed in the same way when the class is abstract. If no generator can be found, we use a constructor based generator, which is explained later.

### Interfaces
When an argument for a test is an interface, you have two options: the first one is to look for classes that implement the interface and find generators for those classes to choose one randomly; the other option we provide is the generation of a proxy object that implements that interface. The proxy will receive method calls and will try to return objects of the method return type. If the type doesn’t have an associated generator, the proxy will return null.

### Create objects based on defined constructors
As mentioned before, we have defined a special generic generator which creates instances of any class based on the public constructors the class has. The generator looks up for public constructor of the class, selects one of them randomly, and creates an instance by generating (also randomly) the arguments needed by the constructor. Of course, this approach is nice, but it relies in reflection capabilities of the Java language. Reflection should be use carefully and be avoided as much as possible. This is why we recommend defining as many specific generators as possible if the developer wants efficient tests.
