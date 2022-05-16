# Scheduling-Algorithms-Project
This project simulates and demonstrates the functionality of the round robin, shortest remaining time, and feedback scheduling algorithms. It reads a file named jobs.txt to determine the name, start time, and duration of the jobs that it will be scheduling, and outputs these jobs to the terminal. For each unit of time that a job is running, it then outputs a new line containing a single X which is vertically aligned with the job that is running at the current time.
A sample jobs.txt has been included in this repository.

To run the project, call Project3Driver and pass in the type of scheduling algorithm you would like to use as an argument.
Type RR for round robin, SRT for shortest remaining time, FF for feedback, and ALL for all algorithms.

Example call from a linux command line after compilation is complete: java Project3Driver ALL
