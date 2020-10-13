# Project Name
> On-Her-Majesty-s-Secret-Service-system

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Launch](#Launch)
* [Inspiration](#inspiration)
* [Goals](#Goals)
* [Contact](#contact)

## General info
Java Multithreading  based Publish-Subscribe program that simulates one of the movies on James Bond series,
Each message is delivered through the Message broker to the subscribers for each topic.



## Technologies
* Java - 8 Or 11
* Maven - version 3.6.3

## Launch
Open cmd\terminal in the project directory and run the next commend:</br>
#### Compile
> mvn compile </br>
#### Run
> mvn exec:java -Dexec.mainClass="bgu.spl.mics.application.MI6Runner" -Dexec.args="inputFile.json inventoryOutputFile.json diaryOutputFile.json"

## Run Examples
The inputFile.json located in the repository as an example input for the program, we've also added a folder with an example output file.
for every run of the program you must include an inputFile.json.

#### Input explanation
##### Inventory:
> Contains the gadget available for the squed.
##### Services:
> Contains the number of instances of each service in our program.
##### Intelligence:
> Part of the services, contains the missions and their details.
##### Squad:
> Contains our agents.
##### Time:
> The program will start it's shuttdown proccess at the time given here.

#### Output explanation
##### diaryOutputFile
> Contains a diary log of the missions that the squad executed in the program time frame.
##### inventoryOutputFile:
> Contains the gadgets which the squad didn't use during their missions.

## Inspiration
As part of our System Programing course we received this project as an assignment
##Goals
* Programing an asynchronous project.
* Managing concurrency issues.
	* Deadlocks.
	* Keeping the code as efficient as possible by making it as concurrent as possible without compromising the code correctness.
 

## Contact
Created by [Itamar Lederman](https://github.com/Itamarled/) & [Shimi Nagar](https://github.com/Shimonna394)