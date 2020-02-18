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
    toBytes: 12

    getDivisionBits: 9

    compareUAsPrefix: 8

    compareAsPrefix: 8

    stringToNumber: 18

    getType: 18

    getBytes: 8

    setDivisionBitArray: 29

    [StringValue.escape (before)](https://github.com/soffan20/sirix/blob/f42c0fa64378cc8dac55e2f1a1d50e2c0007a329/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L4-L52): 18

    [StringValue.escape (after)](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L6-L43): 2

    [Nodekind.deserialize (before)](https://github.com/soffan20/sirix/blob/513d23f929e28b3d333ce784e55f248700af452e/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062-L1091): 8

    [NodeKind.deserialize (after)](https://github.com/soffan20/sirix/blob/c2d7f24060b3f92c42f7cac52d9f57b08709b813/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1071-L1103): 2


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

    [StringValue.escape](https://github.com/soffan20/sirix/blob/f42c0fa64378cc8dac55e2f1a1d50e2c0007a329/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L4-L52): 46 lines long, not very complex.

    [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/513d23f929e28b3d333ce784e55f248700af452e/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062-L1091): Roughly 30 lines of code but longer than needed, but not too complex.

3. What is the purpose of the functions?

    toBytes: Convert int array into byte array.

    getDivisionBits: Calculates the number of bits needed to store the chosen value.

    compareUAsPrefix: Compare 2 unsigned values to see which is the largest.

    compareAsPrefix: Compare 2 prefixes to see which is the largest.

    stringToNumber: Convert a String into other types depending on the value.

    getType: Return the most common type of 2 atomic operands form an XML schema.

    getBytes: Convert String into a byte array.

    setDivisionBitArray: Divide the bits of a byte array by a given divisor.

    [StringValue.escape](https://github.com/soffan20/sirix/blob/f42c0fa64378cc8dac55e2f1a1d50e2c0007a329/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L4-L52): The function escapes control characters and specific unicode characters in a string.

    [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/513d23f929e28b3d333ce784e55f248700af452e/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062-L1091): Deserialize JSON strings into a node type for the internal Abstract Syntax Tree
4. Are exceptions taken into account in the given measurements?

    toBytes: No exceptions exist.

    getDivisionBits: No exceptions exist.

    compareUAsPrefix: No exceptions exist.

    compareAsPrefix: No exceptions exist.

    stringToNumber: Yes, there are two exceptions.

    getType: No exceptions exist.

    getBytes: Yes, one exception exists.

    setDivisionBitArray: No exceptions exist.

    [StringValue.escape](https://github.com/soffan20/sirix/blob/f42c0fa64378cc8dac55e2f1a1d50e2c0007a329/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L4-L52): No exceptions exist.

    [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/513d23f929e28b3d333ce784e55f248700af452e/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062-L1091): AssertionError is thrown when the assumed type of the string to be parsed is not one of the specified number formats.

5. Is the documentation clear w.r.t. all the possible outcomes?

    toBytes: No documentation at all.

    getDivisionBits: The exists some comments, but very high-level.

    compareUAsPrefix: No documentation at all.

    compareAsPrefix: No documentation at all.

    stringToNumber: No documentation at all.

    getType: There exists some documentation, but is not very detailed.

    getBytes: There exists some documentation, but is not very detailed.

    setDivisionBitArray: There exists some documentation, but is not very detailed.

    [StringValue.escape](https://github.com/soffan20/sirix/blob/f42c0fa64378cc8dac55e2f1a1d50e2c0007a329/bundles/sirix-core/src/main/java/org/sirix/service/json/serialize/StringValue.java#L4-L52): No documentation at all but one reference to the Unicode standard.

    [Nodekind.deserialize](https://github.com/soffan20/sirix/blob/513d23f929e28b3d333ce784e55f248700af452e/bundles/sirix-core/src/main/java/org/sirix/node/NodeKind.java#L1062-L1091): No documentation at all.

## Coverage

### Tools

We used the built-in tool in IntelliJ. It was very easy to use.

Document your experience in using a "new"/different coverage tool.

How well was the tool documented? Was it possible/easy/difficult to
integrate it with your build environment?

### DYI

Show a patch (or link to a branch) that shows the instrumented code to
gather coverage measurements.

The patch is probably too long to be copied here, so please add
the git command that is used to obtain the patch instead:

git diff ...

What kinds of constructs does your tool support, and how accurate is
its output?

### Evaluation

1. How detailed is your coverage measurement?

2. What are the limitations of your own tool?

3. Are the results of your tool consistent with existing coverage tools?

### Coverage improvement

Show the comments that describe the requirements for the coverage.

Report of old coverage: [link]

Report of new coverage: [link]

Test cases added:

git diff ...

## Refactoring

Plan for refactoring complex code:

Estimated impact of refactoring (lower CC, but other drawbacks?).

Carried out refactoring (optional)

git diff ...

## Overall experience

What are your main take-aways from this project? What did you learn?

Is there something special you want to mention here?
