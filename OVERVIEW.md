##Overview SirixDB
SirixDB is a versioned database focused on storing temporal data in XML or JSON form. The purpose is to have a way of checking past information in your system and to be able to see when the information was added, deleted, and changed. The thought is for SirixDB to store small size snapshots (commits) to save both space and performance, thus competing in efficiency with non-temporal database systems. 

Compared to a temporal database, which is storing data related to time instances, how long the value was true in the real world, at what time the value was stored in the database and how long it was valid in the database, SirixDB is storing the changes in a snapshot. This makes it more space and performance efficient.

The reason to use databases with past data storage is to be able to look at the evolution of data and analyze trends, a feature that is very important in these times of artificial intelligence. By having past data, it is possible to predict future values of data as well.

SirixDB also allows time-travel queries to be able to get historical information about the data that is stored, which makes it possible to ask questions such as “What was the salary of James Smith on December 16th, 2018?” or “Who was the CEO for this company in 2006?”, if the system was in use at that time. 

SirixDB is using Gradle as a built tool. The tools for testing are jUnit 3 and TestNG. Jmh is used for benchmarking.

The architecture of SirixDB has modules (core, GUI, benchmark, XQuery) that contain their own packages and tests. The module most used is sirix-core which contains packages, for example, for the direct access layer for interactions with the system, for the interfaces when using the API of SirixDB, for caching and transaction logs, and for making the settings of the system, such as defining constant variables. The system also has an example module with tutorials and examples on, for example, how to create an XML database or how to use XQuery. 

The most important class is the ResourceConfiguration. This class defines how the system should be built and what storage backend to use if it will be a file or memory-mapped storage for the database. It contains attributes like paths to the storage files and methods to serialize and deserialize JSON-files. 

The org.sirix.io package is the one containing classes on how to read from and write to files. The system uses interfaces for reader, writer, and storage, which are used by many classes in other places of the codebase to separate the implementation from the interface to help with testing. This makes the system easier to use and fast to program when creating new classes that use these interfaces.

SirixDB is based on a science project (treetank.org) that has been expanded to a stand-alone software product. The science project aimed to create the algorithm that SirixDB is using. 
