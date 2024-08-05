1. Create database in the docker using command: "docker run -p 3306:3306 --name newsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=newsdb -d mysql"
2. Run the project by command: mvn spring-boot:run

There are no other actions to do. You can also change db configurations in docker command or in application.yml file.

If you don`t want to wait 20 minutes for db update change the cron expression from "0 0/20 * * * ?" to "0 0/1 * * * ?" (each 1 minute) or "0 0/2 * * * ?" (each 2 minutes). 
Cron expression is in NewsCollector.java file in package com.example.server.schedule. 
