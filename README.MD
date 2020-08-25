# Task tracking system

Task tracking system is a Spring-Boot service for managing developers, tasks and planning sprints.

## Prerequisits

To run current project, you need to have Java 11 or above installed.
Clone project from git repository. For this, open folder, where you wish to keep sources. Open any command line tool in project root folder and run:

```bash
git clone https://github.com/Nidum/task-tracker
```

Also, ensure that your gradle version is above 6.3. Run this command to verify this:
```bash
./gradlew --version
```

To install newer version of gradle run:
```bash
./gradlew wrapper --gradle-version=6.5.1 --distribution-type=bin
```


## Running
Execute following command in any command line tool in the root folder of the project:
```bash
 ./gradlew bootJar bootRun
```
Now, as the app is up and running you can access it via Postman.

Available endpoints are:
* /api/developer
* /api/issue/bug
* /api/planning 

## Executing tests
To run all the unit and integration tests enter following command in any command line tool in the root folder of the project:
```bash
./gradlew clean test
```

## Additional configurations
App allows specifying max story points that a developer could complete withing sprint. For this change value of developer.maxstorypoints in file *application.properties*.

Also, app provides pre-configured test data set. If you want to change it, you can do it in *data.sql* file.