public class Process {
    String pid;
    int arrival, burst, priority;
    int remaining;
    int completion = 0;
    int waiting = 0;
    int turnaround = 0;
    int response = -1;

    public Process(String pid, int arrival, int burst, int priority) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
        this.priority = priority;
        this.remaining = burst;
    }
}