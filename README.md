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
 interface GenericRepository <T, ID>

 interface UserRepository extends GenericRepository <User, Long>

 class JavaIOUserRepositoryImpl implements UserRepository

 The result of the task should be a separate repository with a README.md file that contains a description of the task, project and instructions for launching the application through the command line.
 
 ## Project
 
 ### Directory layout
 
     .
     ├── src
          ├── main
               ├── java
                    ├── command          # Actions with database
                    ├── controller       # Controllers layout
                    ├── model            # Models layout
                    ├── repository       # Repositories layout
                         └── io         # IO implementations of repositories
                    ├── runner           # Class with main method
                    └── view             # Views layout
          ├── resources                    # Resources
               └── files                  # Data warehouse
     ├── .gitignore
     └── README.md

