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

## Complexity

1. What were the cyclomatic complexity for the 10 chosen functions?

 * [SirixDeweyID.toBytes](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L625-L664): 12
 * [SirixDeweyID.getDivisionBits](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L462-L490): 9
 * [compareUAsPrefix](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L371): 8
 * [compareAsPrefix](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L288): 8
 * [JsonNumber.stringToNumber](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/service/json/JsonNumber.java#L6-L53): 10
 * [GeneralComp.getType](https://github.com/soffan20/sirix/blob/31c4f23eb5037c8976270a143a680ac97e1ce25b/bundles/sirix-core/src/main/java/org/sirix/service/xml/xpath/comparators/GeneralComp.java#L115-L173): 18
 * [TypedValues.getBytes](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/utils/TypedValue.java#L233-L269): 8
 * [SirixDeweyID.setDivisionBitArray](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L501-L651): 29
 * [StringValue.escape](https://github.com/soffan20/sirix/blob/f42c0fa64378cc8dac55e2f1a1d50e2c0007a329/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L4-L52): 18
 * [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/3becfd7760fbfb7af5b635c7d373340a3fd1979f/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062): 8


Did all tools/methods get the same result?

Yes.

Are the results clear?

If you want to see coverage for a class, you need to run all tests since the method could be covered from all possible tests. The intellij coverage tool will only show coverage for the whole class, it is not possible to check coverage for individual methods. To see the coverage for an individual method you have to manually calculate it.

2. Are the functions just complex, or also long?

 * **toBytes**: 30 lines, so quite long.
 * **getDivisionBits**: 18 lines, so not very long.
 * **compareUAsPrefix**: About 20 lines, so not very long.
 * **compareAsPrefix**: About 20 lines, so not very long.
 * **stringToNumber**: 24 lines, so not very long.
 * **getType**: 4 lines, so not long.
 * **getBytes**: 16 lines, so medium.
 * **setDivisionBitArray**: 79 lines, so very long.
 * **escape**: 46 lines long, not very complex.
 * **deserialize**: Roughly 30 lines of code but longer than needed, but not too complex.

3. What is the purpose of the functions and why should the complexity be high (or not)?

* **toBytes**: Convert int array into byte array. The outcome is always the same, the branches depend on the size of the input. Should not be high, but is since they used many if-statements.

* **getDivisionBits**: Calculates the number of bits needed to store the chosen value. The outcome depend of the size of the input. Should not be high, but is since they used many if-statements

* **compareUAsPrefix**: Compare 2 unsigned values to see which is the largest.

* **compareAsPrefix**: Compare 2 prefixes to see which is the largest.

* **stringToNumber**: Convert a String into other types depending on the value.

* **getType**: Return the most common type of 2 atomic operands form an XML schema.

* **getBytes**: The purpose of this function is to convert a String into a byte array. The branches in this function represent possible special characters that can be present in the string. These special characters are “&” and “<”. There are additional branches for when the string is empty and for when the conversion fails, for which an exception is thrown.

* **setDivisionBitArray**: The purpose of this function is to divide the bits of a byte array by a given divisor. The different branches are dependent on what the values in the maxDivisionValue array in the class are. Depending on if the division value is less than or equal to a certain value in the maxDivisionValue array, a different branch is going to be executed. These different branches determine what the prefix and suffix of of the byte array is. This then affects the later calculations of the division.

* **escape**: The function escapes control characters and specific unicode
  characters in a string. The main complexity comes from the use of a switch
  statement and aswell as manual loops and checks instead of using the standard
  library to reduce boilerplate.

* **deserialize**: Deserialize JSON strings into a node type for the internal
  Abstract Syntax Tree. Just like **escape** the main complexity was a switch
  case statement which could be refactored into a array indexing operation.


4. Are exceptions taken into account in the given measurements?

 * **toBytes**: No exceptions exist.
 * **getDivisionBits**: No exceptions exist.
 * **compareUAsPrefix**: No exceptions exist.
 * **compareAsPrefix**: No exceptions exist.
 * **stringToNumber**: Yes, there are two exceptions.
 * **getType**: No exceptions exist.
 * **getBytes**: Yes, one exception exists.
 * **setDivisionBitArray**: No exceptions exist.
 * **escape**: No exceptions exist.
 * **deserialize**: AssertionError is thrown when the assumed type of the string to be parsed is not one of the specified number formats.

5. Is the documentation clear w.r.t. all the possible outcomes?

 * **toBytes**: No documentation at all.
 * **getDivisionBits**: The exists some comments, but very high-level.
 * **compareUAsPrefix**: No documentation at all.
 * **compareAsPrefix**: No documentation at all.
 * **stringToNumber**: No documentation at all.
 * **getType**: There exists some documentation, but is not very detailed.
 * **getBytes**: There exists some documentation, but is not very detailed.
 * **setDivisionBitArray**: There exists some documentation, but is not very detailed.
 * **escape**: No documentation at all but one reference to the Unicode standard.
 * **deserialize**: No documentation at all.

## Coverage

### Tools

A small tool for manually instrumenting code was developed and used alongside
the built-in tool in IntelliJ. It was very easy to use, except that the
built-in tool was not granular enough.
The tool consists of
[Coverage.java](https://github.com/soffan20/sirix/blob/coverage-tool/bundles/sirix-core/src/main/java/org/sirix/utils/Coverage.java)
and
[FunctionCoverage.java](https://github.com/soffan20/sirix/blob/coverage-tool/bundles/sirix-core/src/main/java/org/sirix/utils/FunctionCoverage.java).

### DIY

 * [SirixDeweyID.toBytes](https://github.com/soffan20/sirix/blob/b0ad26f30c1ba59dc361434599762dd235a3d49a/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L629-L681)
 * [SirixDeweyID.getDivisionBits](https://github.com/soffan20/sirix/blob/b0ad26f30c1ba59dc361434599762dd235a3d49a/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L465-L494)
 * [compareUAsPrefix](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L371)
 * [compareAsPrefix](https://github.com/soffan20/sirix/blob/25fcab99903affa033cac90c325cb0a19435e507/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L288)
 * [JsonNumber.stringToNumber](https://github.com/soffan20/sirix/blob/31c4f23eb5037c8976270a143a680ac97e1ce25b/bundles/sirix-core/src/main/java/org/sirix/service/json/JsonNumber.java#L10-L58)
 * [GeneralComp.getType](https://github.com/soffan20/sirix/blob/31c4f23eb5037c8976270a143a680ac97e1ce25b/bundles/sirix-core/src/main/java/org/sirix/service/xml/xpath/comparators/GeneralComp.java#L115-L173)
 * [TypedValues.getBytes](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/utils/TypedValue.java#L233-L269)
 * [SirixDeweyID.setDivisionBitArray](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L501-L651)
 * [StringValue.escape](https://github.com/soffan20/sirix/blob/af439874b3359f4e50724f4467e3a1b21b94ace4/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L8-L68)
 * [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/dba9769cb2f168a2df9ee98fcfa7358ed1535818/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062)

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
* TypedValue: 20%
* SirixDeweyID: 39%


Table of new coverage:
* Calc                36 %
* StringValue.escape: 100%
* JsonNumber.StringToNumber: 90%
* TypedValue: 24%
* SirixDeweyID: 40%

Test cases added:

* [Calc refactoring](https://github.com/soffan20/sirix/blob/master/bundles/sirix-core/src/test/java/org/sirix/utils/CalcTest.java)
* [StringValue.escape](https://github.com/soffan20/sirix/blob/734bea335d246aa44507ff88e3409b2a53eb3bf6/bundles/sirix-core/src/test/java/org/sirix/service/json/serializer/StringValueTest.java)
* [JsonNumber.StringToNumber](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/test/java/org/sirix/service/json/shredder/JsonNumberTest.java#L14-L63)
* [TypedValue.getBytes](https://github.com/soffan20/sirix/blob/master/bundles/sirix-core/src/test/java/org/sirix/utils/TypedValueTest.java)

## Refactoring

Plan for refactoring complex code:

Replace if-statements with for-loop. Replace switches with hash maps. Split code
into several functions. Remove code duplication.

Estimated impact of refactoring (lower CC, but other drawbacks?).

No, since we literally replace code with the same functions, only less lines or more
efficient.

Carried out refactoring

 * [SirixDeweyID.toBytes](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L557-L590)
 * [SirixDeweyID.getDivisionBits](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L462-L468)
 * [compareUAsPrefix](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L327)
 * [compareAsPrefix](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/utils/Calc.java#L289)
 * [JsonNumber.stringToNumber](https://github.com/soffan20/sirix/blob/31c4f23eb5037c8976270a143a680ac97e1ce25b/bundles/sirix-core/src/main/java/org/sirix/service/json/JsonNumber.java#L10-L58)
 * [GeneralComp.getType](https://github.com/soffan20/sirix/blob/31c4f23eb5037c8976270a143a680ac97e1ce25b/bundles/sirix-core/src/main/java/org/sirix/service/xml/xpath/comparators/GeneralComp.java#L115-L173)
 * [TypedValues.getBytes](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/utils/TypedValue.java#L233-L269)
 * [SirixDeweyID.setDivisionBitArray](https://github.com/soffan20/sirix/blob/ceff1cbc847298e91d64f291eb6651161e34ff76/bundles/sirix-core/src/main/java/org/sirix/node/SirixDeweyID.java#L501-L651)
 * [StringValue.escape](https://github.com/soffan20/sirix/blob/92aad315305016d56f7dbf8aae469ac80c8e8ee3/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java)
 * [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1071-L1103)

## Pull requests

* [Emil Gedda: Reduce cyclomatic complexity in service.json.serialize.StringValue.escape](https://github.com/sirixdb/sirix/pull/196)
* [Mikael Karlsson: Bugfix/JsonNumber. stringToNumber](https://github.com/sirixdb/sirix/pull/201)
* [Andreas Rohlén: Refactor/SirixDeweyID.setDivisionBitArray: Reduced the cyclomatic complexity from 29 to 8](https://github.com/sirixdb/sirix/pull/202)
* [Daniel Schmekel: refactor: Removed code repetion in the calc class and created tests ...](https://github.com/sirixdb/sirix/pull/203)

These merged pull requests substitutes the P+ requirement of writing 4 tests, except for Louise Zetterlund wrote 4 tests instead of a submitting a pull request upstream.

## Overall experience

What are your main take-aways from this project? What did you learn?

We were surprised that the quality of the code was so bad. We learned to use some tools to check
the quality of the code and what to focus on.
