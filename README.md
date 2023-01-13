# Scheduler

##Introduction

There are multiple algorith for computer scheduling. This program contain few of them including First Come First Serve (FCFS), Shortest-Job-First (SJF), Ranked Shortest-Job-First (SJF) and Round Robin. This program will also calculate the average waiting time based on the user input.

---

##How to use

The program need to be modified in the main as how the user want to use it.
\
\
First the user need to initialize the Scheduler class by either using the available static method or by passing multiple parameter required to the constructor.
Few available static method includes `getInput()`, `randomData()` and `roundRobinData()`, where each of the method description can be seen in the code.
Then the user canmethod chain with the preferred scheduling algorithm.
\
\
For example:
`Scheduler.getinput().roundRobinData()`
\
\
The above code will get the input from the user and print the output for Round Robin Scheduling
