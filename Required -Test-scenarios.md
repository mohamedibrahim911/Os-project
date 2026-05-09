# Scenario A: Basic mixed workload
**•** Use a normal workload with several processes.
# The purpose
The purpose of this scenario is to establish a performance baseline for both algorithms under standard conditions where processes have varying 
arrival and burst times. It allows you to verify that the simulator correctly calculates \
core metrics like Completion Time and Waiting Time for a general set of data.
# Input 
<img width="926" height="97" alt="Screenshot 2026-05-09 224619" src="https://github.com/user-attachments/assets/17ef3fd1-f78d-4088-8351-4604753cd9f8" /> 

# Expected behavior
# Priority Scheduling
**•**The CPU will be assigned based on the assigned priority numbers, with processes like \
P2 (Priority 1) potentially finishing earlier than they would in a standard queue.
# RR Scheduling 
**•** The scheduler will cycle through all processes in order of arrival, giving each the \
fixed time quantum of 3 units until completion.

