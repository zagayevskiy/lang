# What's lang

Lang is dynamically typed interpreted programming language.
The lang project is written just for fun, author is not going to take over the world.

# Examples

[Simple programs](../master/src/test/java/com/zagayevskiy/lang/Programs.java)

[More complex programs](../master/src/test/resources/sources/):

* Hash table;
* 99 bottles of beer;
* Fibonacci numbers computation;
* ...and some more tests.


# Description

## Types

* Primitive
    * [Boolean](#boolean)
    * [Integer](#integer)
    * [String](#string)
    * [Undefined](#undefined-type)
* [Arrays](#array)
* [Structures](#structures)
* [Functions](#functions)


### Boolean ###
    
Just boolean with only two instances: `true` &`false`.

Operators: `&&`, `||`, `!`.

Can be obtained from any value by using `toBoolean` method.

Conversion contracts:

* `toBoolean` same instance;
* `toInteger` 1 if true, 0 otherwise;
* `toString` "true" or "false".

### Integer ###

32-bit integer. NaN supported. Decimal constants.

Can be obtained from any value by using `toInteger` method. If conversion not supported, `NaN` will be returned.

Operators: `+`, `-`, `*`, `/`, `%`, `>>`, `<<`, `|`, `&`, `^`, `~`.

Conversion contract:

* `toBoolean` false when `0` or `NaN`, true otherwise;
* `toInteger` same instance;
* `toString` [String](#string) representing this integer in decimal system.

### String ###

Literals (may be multiline) in double (`"`) or single (`'`) quotes.

Can be obtained from any value by using `toString` method.

Operators:  `+`.

Methods:

* `subString(fromInclusive, toExclusive)` 

### Undefined type ###

Type with only one instance, named `undefined`. Conversion contract:

* `toBoolean` == false;
* `toInteger` == NaN;
* `toString` == "undefined".

### Array ###

Container stored contiguously in memory. Can store elements of any other type at the same time.

New instance creation:
```javascript
[] // empty array
[1, 2, 3] //array which contains 1, 2 and 3
[1, [2, true, ["s"]]] //array which contains 1 and array of 2, true and array of "s"   
```

Element access: 
```javascript
var array = [1, 2, 3];
array[1]; //2
array[1] = 0; //[1, 0, 3];
array[4[ = true; //[1, 0, 3, undefined, true] - autoexpansion
```


Methods:

* `size()` returns size of given array 
```javascript
array->size(); //5
```

Auto expansion:

### Structures ###

Container with named-fields. Like C-struct or POJO.

Definition:
```c
struct Point { //name
    x, y //fields
}
```

Instance creation:
```javascript
var point = new Point(10, 20);
```

Fields access:
```
point->x + point->y; //30
```

### Functions ###

Function is first-class object.

Definition(named):
``` javascript
function compute(x, y, z) { //name and arguments
    x + y + z; //return can be ommitted at the end of function
}
```

Definition(anonymous):
```javascript
var lambda = \(x, y) x + y; // \(arguments) expression
```

Partial applying (arguments binding):
```javascript
var computeX5 = compute(5);
```

Bound method:
```
var x = 123;
var toStrX = x->toString; // Important! No "()"! 

```

Call:
```javascript
compute(1, 2, 3);  //6
computeX5(true, 'qwerty'); //compute(5, true, "qwerty") => (5 + 1) + "qwerty" => "6qwerty";
lambda(10, 20); // 30
x = 456;
toStrX() //"123" Important! Bound to object, contained in variable x, not to x! 
x->toString() //"456"
```