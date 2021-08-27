[![Header](https://i.ibb.co/6nb6M4T/Covid-Radar-Madird.png "Header")](https://covidradarmadrid.es)

[![CircleCI](https://circleci.com/gh/ChrisHilborne/Madrid-Covid-Radar.svg?style=shield&circle-token=efaef2f4e13a7303cc8bba9824c7a92398397433)](https://app.circleci.com/pipelines/github/ChrisHilborne/Madrid-Covid-Radar)
[![codecov](https://codecov.io/gh/ChrisHilborne/Madrid-Covid-Radar/branch/production/graph/badge.svg?token=EGNR5GNVW4)](https://codecov.io/gh/ChrisHilborne/Madrid-Covid-Radar)
![](https://img.shields.io/badge/Java_11-informational?style=flat&logo=java&logoColor=white&color=49464d)
![](https://img.shields.io/badge/Spring_Boot-informational?style=flat&logo=springboot&logoColor=white&color=49464d)
<a href="https://covidradarmadrid.es">
    <img src="https://img.shields.io/badge/Frontend-informational?style=flat&logo=react&logoColor=white&color=49464d">
</a>    



## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Consuming the API](#consuming-the-api)
* [Dataflow](#dataflow)
* [Licence](#licence)

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

## Consuming the API

Once you have started the program, you will be able to make GET requests to fetch Covid-19 data for Healthwards (zona basic de salud) in Madrid:

* To get all Healthward data:
```
curl http:localhost:8080/api/all
```

* To get a list of all Healthward names and geocodes:
```
curl http:localhost:8080/api/names&geocodes
```

* To get data for only one Healthward:
```
curl http:localhost:8080/api/geocode/{geocode}
``` 

## Dataflow

The below diagram illustrates the flow of data through the program and to the client.

<img src = https://i.ibb.co/6mDx0Z3/Madrid-Covid-Radar-Data-Flow-1.png>

## Licence
[MIT](https://github.com/ChrisHilborne/Madrid-Covid-Radar/blob/production/LICENCE)
