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
# Input
<img width="753" height="81" alt="image" src="https://github.com/user-attachments/assets/e9d6cdd8-5a06-498e-a7b4-61108ff19aaa" />

# Expected behavior
# Priority Scheduling
**•** High-priority processes will preempt others immediately upon arrival, resulting in a \
significantly lower response time (RT) for those specific tasks.
# RR Scheduling 
**•** Critical tasks will wait their turn just like any other process, leading to a \
higher average response time for "urgent" jobs.
# output 
# Priority Scheduling
<img width="690" height="78" alt="image" src="https://github.com/user-attachments/assets/3bb65eae-ace3-4f5b-9d9e-67009b641954" />

# RR Scheduling 
<img width="692" height="80" alt="image" src="https://github.com/user-attachments/assets/c5339621-741a-4545-a10a-e96cb7140588" />

# Comparison Summary
<img width="560" height="237" alt="image" src="https://github.com/user-attachments/assets/f629264e-43dd-4d54-b49d-e24375902606" />

# Scenario C: Fairness case
**•** Prepare a workload that shows whether Round Robin distributes service more evenly. 
# The purpose
The purpose of this scenario is to highlight the Round Robin algorithm's ability to distribute CPU time equally among all active processes.
It proves that no single process can hog the CPU, ensuring a more balanced and predictable execution environment for all tasks.
# Input 
<img width="903" height="77" alt="image" src="https://github.com/user-attachments/assets/e7f4de71-8384-47d1-b484-f484161d7556" />

# Expected behavior
# Priority Scheduling
**•** Service is naturally uneven, as the scheduler explicitly favors processes with lower priority \
numbers regardless of how long others have been waiting.
# RR Scheduling 
**•** This policy ensures a balanced distribution of service, where the difference \ between the minimum and maximum waiting times is minimized.
# output 
# Priority Scheduling
<img width="771" height="80" alt="image" src="https://github.com/user-attachments/assets/b8f7e764-593b-4d4e-b97f-9438dd198a71" />

# RR Scheduling
<img width="781" height="80" alt="image" src="https://github.com/user-attachments/assets/bebd168f-2e82-44f0-8428-5d110b7d8757" />

# Comparison Summary
<img width="657" height="220" alt="image" src="https://github.com/user-attachments/assets/6e93def9-eae0-4707-b3c1-ad4430cd8742" />

# Scenario D: Possible starvation case 
**•** Prepare a workload in which low-priority processes may wait much longer.
# The purpose
The purpose of this scenario is to illustrate the primary weakness of Priority scheduling, where low-priority \
tasks are repeatedly delayed by incoming high-priority ones. It provides a visual contrast to Round Robin, where every process is guaranteed to finish regardless of its status.
# Input 
<img width="843" height="102" alt="image" src="https://github.com/user-attachments/assets/9bfc73a5-24db-4188-b0d8-b4d046e86d67" />

# Expected behavior
# Priority Scheduling
**•** Low-priority tasks, such as P3 in the simulation, may experience very high waiting times (WT = 15) \
if higher-priority processes keep arriving.
# RR Scheduling 
**•** Starvation is impossible here because the circular nature of the queue guarantees that every process eventually gets its time slice.
# output 
# Priority Scheduling
<img width="752" height="106" alt="image" src="https://github.com/user-attachments/assets/b13d61cd-2ab3-4419-abb3-f9fb79a17942" />
# RR Scheduling
<img width="755" height="108" alt="image" src="https://github.com/user-attachments/assets/3a80f710-af89-4274-8e26-664566e44a8a" />

# Comparison Summary
<img width="605" height="254" alt="image" src="https://github.com/user-attachments/assets/211b46ad-4867-4f49-abac-5bae7820df27" />

# Scenario E: Validation case
**•** Include at least one invalid input example and show how the simulator handles it.
# The purpose
The purpose of this scenario is to test the robustness and error-handling capabilities of the simulator's GUI. \ It ensures the program can gracefully handle incorrect inputs, such as negative numbers or invalid  characters, without crashing or producing inaccurate simulation results.
# Expected behavior
# Priority Scheduling
**•** If a user enters an invalid priority level (e.g., a letter instead of a number), the "Add Process" function should trigger an error message or clear the invalid field.
# RR Scheduling 
**•** The simulator should detect invalid inputs like a negative Time Quantum or non-numeric burst times and halt the simulation to prevent logical errors.
# output 
<img width="1135" height="581" alt="image" src="https://github.com/user-attachments/assets/9963019a-5cdd-473a-95c3-7dfac774f030" />

