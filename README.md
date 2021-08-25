<img align="center" src ="https://ibb.co/d6CLfYb">

![](https://img.shields.io/badge/Frontend-informational?style=flat&log=data:image/svg;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA0OCA0OCI+PGRlZnM+PHN0eWxlPi5he2ZpbGw6IzQxOGZkZTt9PC9zdHlsZT48L2RlZnM+PHBhdGggY2xhc3M9ImEiIGQ9Ik00Ni41LDE5QTEuNDk5NzcsMS40OTk3NywwLDAsMCw0NSwyMC41VjIySDQwLjg3MjI1YTE2LjksMTYuOSwwLDAsMC0zLjUzLTguNTEzNjdsMi45MjEyMS0yLjkyMTM5LDEuMTc1ODIuOTk1NjFhMS40OTk5MywxLjQ5OTkzLDAsMSwwLDIuMTIxMzQtMi4xMjExbC00Ljk5OTkxLTVhMS40OTk5LDEuNDk5OSwwLDAsMC0yLjEyMTI3LDIuMTIxMWwuOTk1NjUsMS4xNzU3OC0yLjkyMTM5LDIuOTIxMzhBMTYuOTAyMDUsMTYuOTAyMDUsMCwwLDAsMjYsNy4xMjc5M1YzaDEuNWExLjUsMS41LDAsMCwwLDAtM2gtN2ExLjUsMS41LDAsMCwwLDAsM0gyMlY3LjEyNzkzYTE2LjkwMjA1LDE2LjkwMjA1LDAsMCwwLTguNTEzNjcsMy41Mjk3OEwxMC41NjQ5NCw3LjczNjMzbC45OTU2NS0xLjE3NTc4YTEuNDk5OSwxLjQ5OTksMCwwLDAtMi4xMjEyNy0yLjEyMTFsLTQuODg0NzUsNWExLjQ5OTkzLDEuNDk5OTMsMCwwLDAsMi4xMjEzMywyLjEyMTFsMS4wNjA2Ny0uOTk1NjEsMi45MjEyMSwyLjkyMTM5QTE2LjksMTYuOSwwLDAsMCw3LjEyNzc1LDIySDNWMjAuNWExLjUsMS41LDAsMCwwLTMsMHY3YTEuNSwxLjUsMCwwLDAsMywwVjI2SDcuMTI3NzVhMTYuOSwxNi45LDAsMCwwLDMuNTMsOC41MTM2N0w3LjczNjU3LDM3LjQzNTA2bC0xLjE3NTgyLS45OTU2MWExLjQ5OTkzLDEuNDk5OTMsMCwwLDAtMi4xMjEzNCwyLjEyMTFsNC45OTk5MSw1YTEuNDk5OSwxLjQ5OTksMCwxLDAsMi4xMjEyNy0yLjEyMTFsLS45OTU2NS0xLjE3NTc4LDIuOTIxMjctMi45MjEzOEExNi45MDIsMTYuOTAyLDAsMCwwLDIyLDQwLjg3MjA3VjQ1SDIwLjVhMS41LDEuNSwwLDAsMCwwLDNoN2ExLjUsMS41LDAsMCwwLDAtM0gyNlY0MC44NzIwN2ExNi45MDIsMTYuOTAyLDAsMCwwLDguNTEzNzktMy41Mjk3OGwyLjkyMTI3LDIuOTIxMzgtLjk5NTY1LDEuMTc1NzhhMS40OTk5LDEuNDk5OSwwLDAsMCwyLjEyMTI3LDIuMTIxMWw0Ljk5OTkxLTVhMS40OTk5MywxLjQ5OTkzLDAsMSwwLTIuMTIxMzQtMi4xMjExbC0xLjE3NTgyLjk5NTYxLTIuOTIxMjEtMi45MjEzOUExNi45LDE2LjksMCwwLDAsNDAuODcyMjUsMjZINDV2MS41YTEuNSwxLjUsMCwwLDAsMywwdi03QTEuNDk5NzcsMS40OTk3NywwLDAsMCw0Ni41LDE5Wm0tMjgsMUEzLjUsMy41LDAsMSwxLDIyLDE2LjUsMy40OTk5NCwzLjQ5OTk0LDAsMCwxLDE4LjUsMjBaTTMwLDMzYTIsMiwwLDEsMSwyLTJBMi4wMDAwNiwyLjAwMDA2LDAsMCwxLDMwLDMzWiIvPjwvc3ZnPg==)
![](https://img.shields.io/badge/Java-11-informational?style=flat&logo=java)
![](https://img.shields.io/badge/Spring_Boot-informational?style=flat&logo=springboot)
[![CircleCI](https://circleci.com/gh/ChrisHilborne/Madrid-Covid-Radar.svg?style=shield&circle-token=efaef2f4e13a7303cc8bba9824c7a92398397433)](<LINK>)
[![codecov](https://codecov.io/gh/ChrisHilborne/Madrid-Covid-Radar/branch/production/graph/badge.svg?token=EGNR5GNVW4)](https://codecov.io/gh/ChrisHilborne/Madrid-Covid-Radar)

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This project is a Spring Boot web server that fetches Covid-19 data from The Ayunamiento de Madrid, processes it, and serves it as a REST API.
	
## Technologies
Project is created with:
* Java 11
* Spring Boot
* Spring JPA/MongoDB 
	
## Setup
Certain secrets are encrypted by [Jasypt](https://github.com/ulisesbocchio/jasypt-spring-boot) with a key stored in an enviroment variable. You therefore can not run this project without making changes to the follwing files in `/src/main/resources/`:

* `application.properties`:
Remove or comment out `jasypt.encryptor.password=...` if you do not wish to encrypt any properties, or include your own key. 

* `application-dev.properties`:
To connect to your own [MongoDB](https://www.mongodb.com/) instance you must edit the `spring.data.mongodb` properties to your requirements.


Once you have changed the `.properties` files according to your setup, use the following command to install the program:

```
$ mvn install -Pdev
```

Now that it is installed navigate to the `./target` folder and run the program with the following command:

```
$ java -jar covidradar-madrid-1.0.jar
```

## Licence
[MIT](https://github.com/ChrisHilborne/Madrid-Covid-Radar/blob/production/LICENCE)
