import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobinScheduler {

    public static String run(List<Process> processes, int quantum) {
        List<Process> list = copyProcesses(processes);
        list.sort((p1, p2) -> Integer.compare(p1.arrival, p2.arrival));

        StringBuilder gantt = new StringBuilder("ROUND ROBIN (Quantum = " + quantum + ")\n\n");

        Queue<Process> queue = new LinkedList<>();
        int time = 0, done = 0, i = 0;
        Process last = null;
        int start = 0;

        while (done < list.size()) {
            // Add arrived processes
            while (i < list.size() && list.get(i).arrival <= time) {
                queue.add(list.get(i++));
            }

            if (queue.isEmpty()) {
                if (i < list.size()) {
                    time = list.get(i).arrival;
                } else {
                    time++;
                }
                continue;
            }

            Process p = queue.poll();

            // Set response time
            if (p.response == -1) {
                p.response = time - p.arrival;
            }

            // Gantt chart
            if (last != p) {
                if (last != null) {
                    gantt.append(start).append(" - ").append(last.pid)
                         .append(" - ").append(time).append(" → ");
                }
                start = time;
                last = p;
            }

            int exec = Math.min(quantum, p.remaining);
            p.remaining -= exec;
            time += exec;

            // Add newly arrived processes
            while (i < list.size() && list.get(i).arrival <= time) {
                queue.add(list.get(i++));
            }

            if (p.remaining > 0) {
                queue.add(p);
            } else {
                p.completion = time;
                p.turnaround = time - p.arrival;
                p.waiting = p.turnaround - p.burst;
                done++;
            }
        }

        if (last != null) {
            gantt.append(start).append(" - ").append(last.pid).append(" - ").append(time);
        }

        // IMPORTANT: Copy results back to original list (for table)
        for (int j = 0; j < processes.size(); j++) {
            Process orig = processes.get(j);
            Process simulated = list.get(j);
            orig.completion = simulated.completion;
            orig.waiting = simulated.waiting;
            orig.turnaround = simulated.turnaround;
            orig.response = simulated.response;
        }

        return gantt.toString();
    }

    public static List<Process> copyProcesses(List<Process> original) {
        List<Process> copy = new ArrayList<>();
        for (Process p : original) {
            copy.add(new Process(p.pid, p.arrival, p.burst, p.priority));
        }
        return copy;
    }
}