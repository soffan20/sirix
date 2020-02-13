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

1. What are your results for ten complex functions?
    toBytes: 12
    
    getDivisionBits: 9
    
    compareUAsPrefix: 8
    
    compareAsPrefix: 8
    
    stringDecimal: 18
    
    getType: 18
    
    getBytes: 8
    
    setDivisionBitArray: 29 
    
   * Did all tools/methods get the same result?
   * Are the results clear?
2. Are the functions just complex, or also long?

    toBytes: 38 lines, so quite long.
    
    getDivisionBits: 19 lines, so not very long.
    
    compareUAsPrefix: About 20 lines, so not very long.
    
    compareAsPrefix: About 20 lines, so not very long.
    
    stringDecimal: 36 lines, so quite long as well.
    
    getType: 45 lines, so quite long as well.
    
    getBytes: 28 lines, so medium.
    
    setDivisionBitArray: 118 lines, so very long.
    
3. What is the purpose of the functions?

    toBytes: Convert int array into byte array.
    
    getDivisionBits: Calculates the number of bits needed to store the chosen value.
    
    compareUAsPrefix: Compare 2 unsigned values to see which is the largest.
    
    compareAsPrefix: Compare 2 prefixes to see which is the largest.
    
    stringDecimal: Convert a String into other types depending on the value.
    
    getType: Return the most common type of 2 atomic operands form an XML schema.
    
    getBytes: Convert String into a byte array.
    
    setDivisionBitArray: Divide the bits of a byte array by a given divisor. 

4. Are exceptions taken into account in the given measurements?

    toBytes: No exceptions exist.
    
    getDivisionBits: No exceptions exist.
    
    compareUAsPrefix: No exceptions exist.
    
    compareAsPrefix: No exceptions exist.
    
    stringDecimal: Yes, there are two exceptions.
    
    getType: No exceptions exist.
    
    getBytes: Yes, one exception exists.
    
    setDivisionBitArray: No exceptions exist.
    
5. Is the documentation clear w.r.t. all the possible outcomes?

    toBytes: No documentation at all.
    
    getDivisionBits: The exists some comments, but very high-level.
    
    compareUAsPrefix: No documentation at all.
    
    compareAsPrefix: No documentation at all.
    
    stringDecimal: No documentation at all.
    
    getType: There exists some documentation, but is not very detailed.
    
    getBytes: There exists some documentation, but is not very detailed.
    
    setDivisionBitArray: There exists some documentation, but is not very detailed.

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
