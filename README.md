# ReactChat-backend

## About.
I created this project strictly for learning purposes so code quality might not be on production level but 
It might serve as a good example how to combine Websockets with REST.

## Features.
- JWT Authentication
- Creating/Removing/Editing of ChatRoom
- Security
- Broadcasting ChatMessages through WebSockets
- Any changes to ChatRoom are also broadcasted through WebSockets
- There is also more features inside the code but they aren't supported by FrontEnd yet.

## Plans for future.
At this point I got fed up with this project so there won't be any features related updates 
although I will add UnitTests in near future.

## Build.
To build this project you will require to setup mysql database which you can configure inside  
> 'src/main/resources/application.properties'.  
* Schema for 'general' database is placed in 'resources/schema/react_chat.sql'.  
* 'rooms' database needs a user with permission to create/edit/delete tables.

This project uses [Lombok](https://projectlombok.org/) so make sure that your IDE supports it.  


## Live demo.
See [frontend](https://github.com/abbl/ReactChat-front) for more details.
