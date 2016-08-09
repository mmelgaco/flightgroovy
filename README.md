# flightgroovy

This is a web service that will connect to an airline service, that provides availability results in XML format, and convert the results into a different JSON model.

## Requirements

- Java JDK 8
- Maven

## Download

```
$ git clone https://github.com/mmelgaco/flightgroovy.git
```

## Test and build

```
$ cd flightgroovy
$ mvn package
```

## Run

```
$ java -jar target/flightgroovy-0.0.1-SNAPSHOT.jar
```

## Usage

Open your favorite browser and point it to:  
http://localhost:8080/{ORIGIN_AIRPORT}/{DESTINATION_AIRPORT}/{DEPARTURE}/{RETURN}/{PAX}

- Replace:  
{ORIGIN_AIRPORT} with the origin airport code ( like DUB )  
{DESTINATION_AIRPORT} with the destination airport code ( like DUB )  
{DEPARTURE} with the departure data  
{RETURN} with the return date  
{PAX} with the number of passengers  
  

## Explanations

- It was my first time with groovy, so I probably did not use the best practices, patterns and resources.
- I think groovy is very powerfull.
- Given more time i could do more tests, i just made 2 basic tests.

