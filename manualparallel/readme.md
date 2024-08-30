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

`mvn clean install` to build the project

`mvn spring-boot:run` to run the Spring Boot application

Note that you might need to adjust the code and dependencies based on your specific requirements and the versions of the libraries you're using.
