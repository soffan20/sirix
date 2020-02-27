## Project

Name: SirixDB

URL: https://github.com/sirixdb/sirix

A database with revision control.

## Onboarding experience

Did it build and run as documented?
    
Yes, but it was a bit hard to know how to run the tests. Windows had some issues running
the tests. Building and running the project and tests were not documented and took a while to figure out

## UML class diagram and its description

[UML diagram before changes](https://drive.google.com/open?id=1aRD_uANkDXxkefOC7EtGK1fH02ZmWJDf)

[UML diagram after changes](https://drive.google.com/open?id=1gbGhfqkQB65t8VKQCfqqriHqFJ0KPteQ)

Optional (point 1): Architectural overview.

[Overview document](https://github.com/soffan20/sirix/tree/overview)

## Selected issue(s)

Title: Provide a memory-mapped file backend

URL: https://github.com/sirixdb/sirix/issues/115

The maintainer described the issue like this: 

> In addition to the RandomAccessFile backend, we could use a memory-mapped file backend, preferably using a framework or something like that which is proven and stable.

In short, the current storage backend system uses direct files as the underlying backing storage, which are inherently slow when subjected to lots of small writes and read due to excessive copying inside the OS. By switching to memory-mapped files, we can reduce the time spent writing changes to disk. Memory-mapped files allow the OS to decide for itself when it want to flush the changes to disk. Allowing the OS to do batch disk operations instead we can gain a significant performance boost. All existing major relational database systems use memory-mapped files.
[Functional requirements](https://docs.google.com/spreadsheets/d/18Y5qv2kYvP9o51C7uxbBEtsGyPz5dqG4tnvo5QNrNkQ/edit?usp=sharing)

### Requirements affected by functionality being refactored

Optional (point 3): trace tests to requirements.

[Tests](https://github.com/soffan20/sirix/commit/acab3ea6d6f9697920d6d2831ba65b99dbe36e44)

### Existing test cases relating to refactored code

Our new feature only adds code and does not change the existing code, but it is hidden behind an interface that is tested throughout the test code.
The interfaces themselves (Reader, Writer, Storage) are not directly tested in the
code but instead indirectly in the integration tests. The integration tests
are not extensive but only tests the most basic Database operartions;
inserting, updating, reading, and deleting.

[Some of the integration tests can be found
here](https://github.com/sirixdb/sirix/tree/master/bundles/sirix-xquery/src/test/java/org/sirix/xquery/function/jn/io)


### Test results
The existing coverage for the already existing related code is ~80%. The
existing tests does not directly test the storage interface but instead tested
everywhere else indirectly. Since the interface was not tested for anything
other than the basic usage, we quikcly found a few bugs that appeared in special case
scenarios.

### Patch/fix

Here is our path of our implementation: [Patch](https://github.com/soffan20/sirix/pull/74)

Optional (point 4): the patch is clean.

The bugfixes and the extending the test suite with tests covering cases where
we found bugs were sent upstream in a pull request: [Patch](https://github.com/sirixdb/sirix/pull/220)

### Documentation MemoryMap Implementation 
The current implementation will do all read/writes directly to disc when managing the database. The main idea with this implementation is to use MappedByteBuffer which will load a file into memory that can be accessed faster.


MemoryMap

MemoryMap is the equivalent of FileStorage with methods createReader and createWriter. The MemoryMap works as a virtual file which is instead stored in the RAM> These methods return a MemoryMapReader and MemoryMapWriter respectively, instead of the FileReader and FileWriter that FileStorage returns. MemoryMap uses composition with FileStorage in order to use the functions that don’t need to be modified.

MemoryMapWriter

MemoryMapWriter is the equivalent of FileWriter. It has the same functionality as FileWriter except for writing to RAM instead of a file on the harddrive. A MappedByteBuffer is used to read and write from RAM instead of using the seek, get and put methods of RandomAccessFile. 

MemoryMapReader

MemoryMapReader is the equivalent of FilerReader. It has the same functionality as FileReader except for reading from RAM instead of a file on the harddrive. A MappedByteBuffer is used to read from RAM instead of using the seek and get methods of RandomAccessFile. 

MappedByteBufferHandler 

MappedByteBufferHandler is used to handle the writing and reading to and from files to make sure that the offset and size of the buffer are correctly handled. It implements all the necessary methods that are needed for reading and writing. It also makes sure that the the file which is held in memory is synchronized so that several threads accessing the file will see the same thing.


## Effort spent

For each team member, how much time was spent in

1. plenary discussions/meetings;
* Andreas: 4
* Daniel: 4
* Emil: 4
* Louise: 4
* Mikael: 4


2. discussions within parts of the group;

* Andreas: 3
* Daniel: 3
* Emil: 3
* Louise:
* Mikael: 3

3. reading documentation;

* Andreas:
* Daniel:
* Emil: 5
* Louise: 10
* Mikael:

4. configuration and setup;

* Andreas: 1
* Daniel: 1
* Emil: 4
* Louise: 1
* Mikael: 1

5. analyzing code/output;

* Andreas: 2
* Daniel: 2
* Emil: 1
* Louise:
* Mikael: 2

6. writing documentation;

* Andreas:
* Daniel:
* Emil: 5
* Louise: 8
* Mikael:

7. writing code;

* Andreas: 18
* Daniel: 18
* Emil: 5
* Louise: 2
* Mikael: 18

8. running code?

* Andreas: 2
* Daniel: 2
* Emil: 4
* Louise:
* Mikael: 2

When setting up the tools and settings we had some issues making the project to build, so this took some extra time. 

## Overall experience

What are your main take-aways from this project? What did you learn?

The issues seemed doable from the start, but since there were many existing bugs in the open-source project, we didn’t manage to finish on time. So we learned that adding extra hours to a time estimation really makes sense.

### Extraordinary work (P+ 3.2.1.7)

Since this issue at hand is at its core related to performance, we decided to implement a benchmark suite to properly verify the performance benefits (or losses) of the different storage backends. The benchmark suite is written generically for the Storage interface and then run for each implementor. The results are then aggregated and displayed in a table format at the end of benchmarking all backends.

[Benchmark](https://github.com/soffan20/sirix/commit/6d32d6e16eda51985b3c8e78996cd28205e35baa)

### Contributions 

Louise: Documentation and tests.

Daniel, Andreas and Mikael: Memory Mapped Storage

Emil: Tests, benchmarks, and bug fixing	
