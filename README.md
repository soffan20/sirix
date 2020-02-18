# Report for assignment 3

## Project

Name: SirixDB

URL: https://github.com/sirixdb/sirix

A database with revision control.

## Onboarding experience

Did it build and run as documented?

Yes, but it was a bit hard to know how to run the tests. Windows had some issues running
the tests.

Not documented.

See the assignment for details; if everything works out of the box,
there is no need to write much here. If the first project(s) you picked
ended up being unsuitable, you can describe the "onboarding experience"
for each project, along with reason(s) why you changed to a different one.


## Complexity

1. What were the cyclomatic complexity for the 10 chosen functions?

CHANGE LINK TO BEFORE

 [SirixDeweyID.toBytes](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L625-L664): 12

 [SirixDeweyID.getDivisionBits](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L462-L490): 9

 [compareUAsPrefix](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L371): 8

 [compareAsPrefix](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L288): 8

 [JsonNumber.stringToNumber](https://github.com/soffan20/sirix/blob/31c4f23eb5037c8976270a143a680ac97e1ce25b/bundles/sirix-core/src/main/java/org/sirix/service/json/JsonNumber.java#L10-L58): 18

 [GeneralComp.getType](https://github.com/soffan20/sirix/blob/31c4f23eb5037c8976270a143a680ac97e1ce25b/bundles/sirix-core/src/main/java/org/sirix/service/xml/xpath/comparators/GeneralComp.java#L115-L173): 18

 [TypedValues.getBytes](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/utils/TypedValue.java#L233-L269): 8

 [SirixDeweyID.setDivisionBitArray](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L501-L651): 29


 [StringValue.escape](https://github.com/soffan20/sirix/blob/f42c0fa64378cc8dac55e2f1a1d50e2c0007a329/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L4-L52): 18

 [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/513d23f929e28b3d333ce784e55f248700af452e/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062-L1091): 8


* Did all tools/methods get the same result?
* Are the results clear?

2. Are the functions just complex, or also long?

    toBytes: 30 lines, so quite long.

    getDivisionBits: 18 lines, so not very long.

    compareUAsPrefix: About 20 lines, so not very long.

    compareAsPrefix: About 20 lines, so not very long.

    stringToNumber: 24 lines, so not very long.

    getType: 4 lines, so not long.

    getBytes: 16 lines, so medium.

    setDivisionBitArray: 79 lines, so very long.

    escape: 46 lines long, not very complex.

    deserialize: Roughly 30 lines of code but longer than needed, but not too complex.

3. What is the purpose of the functions?

    toBytes: Convert int array into byte array.

    getDivisionBits: Calculates the number of bits needed to store the chosen value.

    compareUAsPrefix: Compare 2 unsigned values to see which is the largest.

    compareAsPrefix: Compare 2 prefixes to see which is the largest.

    stringToNumber: Convert a String into other types depending on the value.

    getType: Return the most common type of 2 atomic operands form an XML schema.

    getBytes: Convert String into a byte array.

    setDivisionBitArray: Divide the bits of a byte array by a given divisor.

    escape: The function escapes control characters and specific unicode characters in a string.

    deserialize: Deserialize JSON strings into a node type for the internal Abstract Syntax Tree

4. Are exceptions taken into account in the given measurements?

    toBytes: No exceptions exist.

    getDivisionBits: No exceptions exist.

    compareUAsPrefix: No exceptions exist.

    compareAsPrefix: No exceptions exist.

    stringToNumber: Yes, there are two exceptions.

    getType: No exceptions exist.

    getBytes: Yes, one exception exists.

    setDivisionBitArray: No exceptions exist.

    escape: No exceptions exist.

    deserialize: AssertionError is thrown when the assumed type of the string to be parsed is not one of the specified number formats.

5. Is the documentation clear w.r.t. all the possible outcomes?

    toBytes: No documentation at all.

    getDivisionBits: The exists some comments, but very high-level.

    compareUAsPrefix: No documentation at all.

    compareAsPrefix: No documentation at all.

    stringToNumber: No documentation at all.

    getType: There exists some documentation, but is not very detailed.

    getBytes: There exists some documentation, but is not very detailed.

    setDivisionBitArray: There exists some documentation, but is not very detailed.

    escape: No documentation at all but one reference to the Unicode standard.

    deserialize: No documentation at all.

## Coverage

### Tools

We used the built-in tool in IntelliJ. It was very easy to use.

### DIY

 [SirixDeweyID.toBytes](https://github.com/soffan20/sirix/blob/b0ad26f30c1ba59dc361434599762dd235a3d49a/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L629-L681)

 [SirixDeweyID.getDivisionBits](https://github.com/soffan20/sirix/blob/b0ad26f30c1ba59dc361434599762dd235a3d49a/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L465-L494)

 [compareUAsPrefix](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L371)

 [compareAsPrefix](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L288)

 [JsonNumber.stringToNumber](https://github.com/soffan20/sirix/blob/31c4f23eb5037c8976270a143a680ac97e1ce25b/bundles/sirix-core/src/main/java/org/sirix/service/json/JsonNumber.java#L10-L58)

 [GeneralComp.getType](https://github.com/soffan20/sirix/blob/31c4f23eb5037c8976270a143a680ac97e1ce25b/bundles/sirix-core/src/main/java/org/sirix/service/xml/xpath/comparators/GeneralComp.java#L115-L173)

 [TypedValues.getBytes](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/utils/TypedValue.java#L233-L269)

 [SirixDeweyID.setDivisionBitArray](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L501-L651)

 [StringValue.escape](https://github.com/soffan20/sirix/blob/af439874b3359f4e50724f4467e3a1b21b94ace4/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L8-L68)

 [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/513d23f929e28b3d333ce784e55f248700af452e/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062-L1091)

What kinds of constructs does your tool support, and how accurate is
its output?

### Evaluation

1. How detailed is your coverage measurement?

Our coverage tool is very crude and has only measure line coverage in the non-exceptional path.
It simply accumulates a counter passed into it through a function call. The
total statistics are store in static variables in the instrumented classes to
accumulate measurements cross multiple objects during the total run time of the
tests.

2. What are the limitations of your own tool?

Our tool is very limited and does no exception handling or instrumenting of
code using reflection. This means ternaries are counted as one line of code and
not a branch.

3. Are the results of your tool consistent with existing coverage tools?

The tool successfully counted the line coverage of all the chosen functions and
it matched the measurement values of Intellij's built in coverage tool. Given
how short the coverage tool we found our approach to be good enough given the
time limit.

### Coverage improvement

Show the comments that describe the requirements for the coverage.

Table of old coverage:
* Calc                1 %

* StringValue.escape: 86%

* JsonNumber.StringToNumber: 46% 

Table of new coverage:
* Calc                36 %

* StringValue.escape: 100%

* JsonNumber.StringToNumber: 90% 

Test cases added:

* [Calc refactoring](https://github.com/soffan20/sirix/blob/master/bundles/sirix-core/src/test/java/org/sirix/utils/CalcTest.java)

* [StringValue.escape](https://github.com/soffan20/sirix/blob/734bea335d246aa44507ff88e3409b2a53eb3bf6/bundles/sirix-core/src/test/java/org/sirix/service/json/serializer/StringValueTest.java)

* [JsonNumber.StringToNumber](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/test/java/org/sirix/service/json/shredder/JsonNumberTest.java#L14-L63)

## Refactoring

Plan for refactoring complex code:

Estimated impact of refactoring (lower CC, but other drawbacks?).

Carried out refactoring

CHANGE LINK TO REFACTORED FUNCTIONS

 [SirixDeweyID.toBytes](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L557-L590)

 [SirixDeweyID.getDivisionBits](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L462-L468)

 [compareUAsPrefix](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L327)

 [compareAsPrefix](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L289)

 [JsonNumber.stringToNumber](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/service/json/JsonNumber.java#L8-L46)

 [GeneralComp.getType](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/service/xml/xpath/comparators/GeneralComp.java#L112-L163)

 [TypedValues.getBytes](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/utils/TypedValue.java#L233-L269)

 [SirixDeweyID.setDivisionBitArray](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L501-L651)

 [StringValue.escape](https://github.com/soffan20/sirix/blob/f42c0fa64378cc8dac55e2f1a1d50e2c0007a329/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L4-L52)

 [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/513d23f929e28b3d333ce784e55f248700af452e/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062-L1091)


## Pull requests

* [Emil Gedda: Reduce cyclomatic complexity in service.json.serialize.StringValue.escape](https://github.com/sirixdb/sirix/pull/196)
* [Mikael Karlsson: Bugfix/JsonNumber. stringToNumber](https://github.com/sirixdb/sirix/pull/201)
* [Andreas Rohlén: Refactor/SirixDeweyID.setDivisionBitArray: Reduced the cyclomatic complexity from 29 to 8](https://github.com/sirixdb/sirix/pull/202)
* [Daniel Schmekel: refactor: Removed code repetion in the calc class and created tests ...] (https://github.com/sirixdb/sirix/pull/203)

These merged pull requests substitutes the P+ requirement of writing 4 tests, except for Louise Zetterlund wrote 4 tests instead of a submitting a pull request upstream.

## Overall experience

What are your main take-aways from this project? What did you learn?

Is there something special you want to mention here?


