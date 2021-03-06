##Task

Should implement a console CRUD application that has the following entities:

* User (id, firstName, lastName, List <Post> posts, Region region)
* Post (id, content, created, updated)
* Region (id, name)
* Role (enum ADMIN, MODERATOR, USER)


 As a data warehouse, you must use text files:

 * users.txt
 * posts.txt
 * regions.txt

 The user in the console should be able to create, retrieve, edit and delete data.

 Layers:

 * model - POJO classes
 * repository - classes that implement access to text files
 * controller - processing requests from the user
 * view - all data necessary for working with the console

For example: User, UserRepository, UserController, UserView, etc.

For the repository layer, it is desirable to use the basic interface:
```interface GenericRepository <T, ID>```

```interface UserRepository extends GenericRepository <User, Long>```

```class JavaIOUserRepositoryImpl implements UserRepository```

The result of the task should be a separate repository with a README.md file that contains a description of the task, project and instructions for launching the application through the command line.

##Project

###Directory layout

     .
     ├── src
          ├── main
               ├── java
                    ├── command          # Actions with database
                    ├── controller       # Controllers layout
                    ├── factory          # DI container
                    ├── model            # Models layout
                    ├── repository       # Repositories layout
                         ├── csv         # CSV implementations of repositories
                         └── io          # IO implementations of repositories
                    ├── runner           # Class with main method
                    └── view             # Views layout
                         └── impl        # Views implementations
          ├── resources                  # Resources
               └── files                 # Data warehouse
                    ├── csv              # CSV data warehouses
                    └── io               # TXT data warehouses
     ├── .gitignore
     └── README.md

###Repository

https://github.com/Wismut/crud_project

###Compiling

1. ```git clone https://github.com/Wismut/crud_project```
2. ```cd crud_project```
3. for Windows 
```dir /s /B *.java > sources.txt```
   for Linux/MacOS 
```find -name "*.java" > sources.txt```
4. ```javac @sources.txt```

###Running

```java -cp src\main\java\ runner.Runner```

And follow the instructions in the console
