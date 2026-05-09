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
**•** The CPU will be assigned based on the assigned priority numbers, with processes like \
P2 (Priority 1) potentially finishing earlier than they would in a standard queue.
# RR Scheduling 
**•** The scheduler will cycle through all processes in order of arrival, giving each the \
fixed time quantum of 3 units until completion.
# output 
# Priority Scheduling
<img width="726" height="92" alt="Screenshot 2026-05-09 232321" src="https://github.com/user-attachments/assets/5d8f798e-5f36-4128-be53-c82ce3f9b986" />

# RR Scheduling 
<img width="735" height="90" alt="image" src="https://github.com/user-attachments/assets/536284e7-1c5f-47be-be7f-f0212781cbeb" />

# Comparison Summary
<img width="590" height="230" alt="image" src="https://github.com/user-attachments/assets/62ba9fe9-c6fa-41f8-936c-12cc5bc12f47" />
# Scenario B: Urgency case
**•** Include one or more processes with clearly higher priority so the policy difference is visible.
# The purpose
The purpose of this scenario is to demonstrate the efficiency of the Priority algorithm in handling time-critical tasks. \
By assigning a high priority to certain processes, you can observe how they preempt others to achieve a lower Response Time compared to the "fair" Round Robin approach.






