
# Assignment
Implement asynchronous calculation of PI number up to a given number of correct digits.

For calculation of PI please use Gregory-Leibniz formula:
https://en.wikipedia.org/wiki/Leibniz_formula_for_%CF%80

Please create two different solutions described below.

## Task 1. Single-process solution
Create `StandaloneSolution` application (implements `foo.bar.baz.Solution`) that calculates PI number in a single JVM.
The calculations should be performed concurrently using Producer-Consumer pattern.
A Producer is a thread that generates jobs (messages) which are then executed by Consumer threads (workers).
When all the jobs are finished, the main task's result should be gathered and outputted.

## Task 2. Distributed solution
Create `DistributedSolution` application (implements `foo.bar.baz.Solution`) that uses a cluster of three `Worker` applications to calculate the PI number in a distributed manner.
All requests to Workers should be asynchronous, meaning the caller side doesn't wait for a completion of a job. You are free to introduce any intermediary applications on need.


Application should work on Spring Boot 2, save a calculation result and a request statistic (to Master) into database, and should have Rest API for requests to get the statistic. Also preferable to use Docker container platform.