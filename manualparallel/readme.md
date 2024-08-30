To create a new Maven project with the structure and files similar to the` ParallelInsertApplication.java` file you provided, you can use the `mvn archetype:generate` command. Here are the steps:

1. Open a terminal or command prompt and navigate to the directory where you want to create your new project.

2. Run the following command to generate a new Maven project using the `maven-archetype-quickstart` archetype:

```bash
mvn archetype:generate -DgroupId=com.example \
-DartifactId=manual-parallel \
-DarchetypeArtifactId=maven-archetype-quickstart \
-DinteractiveMode=false
```
This command will create a new directory named parallel-insert with the basic Maven project structure.

3. Relace files:

- Put file `DataSourceConfig.java` into  `src/main/java/com/example/datasource/` folder
- Put file `ParallelInsertApplication.java` into  `src/main/java/com/example/parallelinsert/` folder
- Put file `ParallelCopyApplication.java` into  `src/main/java/com/example/parallelcopy/` folder
- Put file `application.properties` into  `src/main/resources/` folder

4. Update the pom.xml file to include the necessary dependencies for your project. For example, you might need to add the following dependencies:

```XML
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.5.4</version>
    </dependency>
</dependencies>
```

5. Optionally, you can also add the Spring Boot Maven plugin to the pom.xml file to make it easier to run your application:

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>

6. After making these changes, you should have a new Maven project with the ParallelInsertApplication.java file and the necessary dependencies.

7. To build and run the application, you can use the following Maven commands:

- `mvn compile` to build the project
- `mvn exec:java -Dexec.mainClass="com.example.parallelinsert.ParallelInsertApplication" -Dexec.cleanupDaemonThreads=false` to execute parallel-insert
- `mvn exec:java -Dexec.mainClass="com.example.parallelcopy.ParallelCopyApplication" -Dexec.cleanupDaemonThreads=false` to execute parallel-copy



Note that you might need to adjust the code and dependencies based on your specific requirements and the versions of the libraries you're using.


Sample output

```java
2024-08-30T16:32:04.159Z  INFO 8298 --- [demo] [ool-2-thread-19] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 11300001 to 11400000 in 33583 ms by Thread: 40
2024-08-30T16:32:04.159Z  INFO 8298 --- [demo] [ool-2-thread-19] c.e.p.ParallelInsertApplication          : Start to Process data range 14500001 to 14600000 by Thread: 40
2024-08-30T16:32:04.459Z  INFO 8298 --- [demo] [ool-2-thread-24] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 11500001 to 11600000 in 33583 ms by Thread: 45
2024-08-30T16:32:04.460Z  INFO 8298 --- [demo] [ool-2-thread-24] c.e.p.ParallelInsertApplication          : Start to Process data range 14600001 to 14700000 by Thread: 45
2024-08-30T16:32:04.581Z  INFO 8298 --- [demo] [ool-2-thread-26] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 11400001 to 11500000 in 33975 ms by Thread: 47
2024-08-30T16:32:04.581Z  INFO 8298 --- [demo] [ool-2-thread-26] c.e.p.ParallelInsertApplication          : Start to Process data range 14700001 to 14800000 by Thread: 47
2024-08-30T16:32:04.616Z  INFO 8298 --- [demo] [ool-2-thread-22] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 12700001 to 12800000 in 33571 ms by Thread: 43
2024-08-30T16:32:04.617Z  INFO 8298 --- [demo] [ool-2-thread-22] c.e.p.ParallelInsertApplication          : Start to Process data range 14800001 to 14900000 by Thread: 43
2024-08-30T16:32:04.646Z  INFO 8298 --- [demo] [ool-2-thread-20] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 12300001 to 12400000 in 33602 ms by Thread: 41
2024-08-30T16:32:04.647Z  INFO 8298 --- [demo] [ool-2-thread-20] c.e.p.ParallelInsertApplication          : Start to Process data range 14900001 to 15000000 by Thread: 41
2024-08-30T16:32:04.647Z  INFO 8298 --- [demo] [ool-2-thread-30] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 12200001 to 12300000 in 33634 ms by Thread: 51
2024-08-30T16:32:04.647Z  INFO 8298 --- [demo] [ool-2-thread-30] c.e.p.ParallelInsertApplication          : Start to Process data range 15000001 to 15100000 by Thread: 51
2024-08-30T16:32:04.649Z  INFO 8298 --- [demo] [ool-2-thread-31] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 11700001 to 11800000 in 33746 ms by Thread: 52
2024-08-30T16:32:04.649Z  INFO 8298 --- [demo] [ool-2-thread-31] c.e.p.ParallelInsertApplication          : Start to Process data range 15100001 to 15200000 by Thread: 52
2024-08-30T16:32:04.649Z  INFO 8298 --- [demo] [ool-2-thread-14] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 12400001 to 12500000 in 33605 ms by Thread: 35
2024-08-30T16:32:04.649Z  INFO 8298 --- [demo] [ool-2-thread-14] c.e.p.ParallelInsertApplication          : Start to Process data range 15200001 to 15300000 by Thread: 35
2024-08-30T16:32:04.653Z  INFO 8298 --- [demo] [ool-2-thread-15] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 11900001 to 12000000 in 33720 ms by Thread: 36
2024-08-30T16:32:04.653Z  INFO 8298 --- [demo] [ool-2-thread-15] c.e.p.ParallelInsertApplication          : Start to Process data range 15300001 to 15400000 by Thread: 36
2024-08-30T16:32:04.665Z  INFO 8298 --- [demo] [ool-2-thread-18] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 12600001 to 12700000 in 33620 ms by Thread: 39
2024-08-30T16:32:04.665Z  INFO 8298 --- [demo] [ool-2-thread-18] c.e.p.ParallelInsertApplication          : Start to Process data range 15400001 to 15500000 by Thread: 39
2024-08-30T16:32:04.671Z  INFO 8298 --- [demo] [ool-2-thread-29] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 12500001 to 12600000 in 33627 ms by Thread: 50
2024-08-30T16:32:04.671Z  INFO 8298 --- [demo] [ool-2-thread-29] c.e.p.ParallelInsertApplication          : Start to Process data range 15500001 to 15600000 by Thread: 50
2024-08-30T16:32:04.700Z  INFO 8298 --- [demo] [ool-2-thread-21] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 11600001 to 11700000 in 33800 ms by Thread: 42
2024-08-30T16:32:04.700Z  INFO 8298 --- [demo] [ool-2-thread-21] c.e.p.ParallelInsertApplication          : Start to Process data range 15600001 to 15700000 by Thread: 42
2024-08-30T16:32:04.770Z  INFO 8298 --- [demo] [ool-2-thread-23] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 11800001 to 11900000 in 33848 ms by Thread: 44
2024-08-30T16:32:04.770Z  INFO 8298 --- [demo] [ool-2-thread-23] c.e.p.ParallelInsertApplication          : Start to Process data range 15700001 to 15800000 by Thread: 44
2024-08-30T16:32:04.887Z  INFO 8298 --- [demo] [ool-2-thread-28] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 12000001 to 12100000 in 33901 ms by Thread: 49
2024-08-30T16:32:04.888Z  INFO 8298 --- [demo] [ool-2-thread-28] c.e.p.ParallelInsertApplication          : Start to Process data range 15800001 to 15900000 by Thread: 49
2024-08-30T16:32:04.940Z  INFO 8298 --- [demo] [ool-2-thread-25] c.e.p.ParallelInsertApplication          : Inserted 100000 rows in range 12100001 to 12200000 in 33927 ms by Thread: 46
2024-08-30T16:32:04.940Z  INFO 8298 --- [demo] [ool-2-thread-25] c.e.p.ParallelInsertApplication          : Start to Process data range 15900001 to 16000000 by Thread: 46
```
