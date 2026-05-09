import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SchedulingSimulator extends JFrame {

    private List<Process> processes = new ArrayList<>();
    
    private DefaultTableModel inputModel, rrTableModel, prTableModel;

    private JTextArea rrGantt, prGantt, comparisonArea;
    private JTextField pidF, arrF, burstF, priF, qF;

    public SchedulingSimulator() {
        setTitle("Scheduling Simulator - RR vs Priority Scheduling");
        setSize(1600, 950);
        setMinimumSize(new Dimension(1300, 780));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = createInputPanel();

        inputModel = new DefaultTableModel(new String[]{"PID", "Arrival", "Burst", "Priority"}, 0);
        rrTableModel = new DefaultTableModel(new String[]{"PID","Arrival","Burst","Priority","Completion","WT","TAT","RT"}, 0);
        prTableModel = new DefaultTableModel(new String[]{"PID","Arrival","Burst","Priority","Completion","WT","TAT","RT"}, 0);

        rrGantt = createGanttArea();
        prGantt = createGanttArea();
        comparisonArea = createComparisonArea();

        JSplitPane masterSplit = createLayout();

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.add(inputPanel, BorderLayout.NORTH);
        main.add(masterSplit, BorderLayout.CENTER);

        add(main);
        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
        
        pidF = new JTextField(8);
        arrF = new JTextField(8);
        burstF = new JTextField(8);
        priF = new JTextField(8);
        qF = new JTextField("3", 8);

        JButton addBtn = new JButton("Add Process");
        JButton runBtn = new JButton("Run Simulation");
        JButton clearBtn = new JButton("Clear All");

        panel.add(new JLabel("PID:")); panel.add(pidF);
        panel.add(new JLabel("Arrival:")); panel.add(arrF);
        panel.add(new JLabel("Burst:")); panel.add(burstF);
        panel.add(new JLabel("Priority:")); panel.add(priF);
        panel.add(new JLabel("Quantum:")); panel.add(qF);
        panel.add(addBtn); panel.add(runBtn); panel.add(clearBtn);

        addBtn.addActionListener(e -> addProcess());
        runBtn.addActionListener(e -> runSimulation());
        clearBtn.addActionListener(e -> clearAll());

        return panel;
    }

    private JTextArea createGanttArea() {
        JTextArea area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setEditable(false);
        return area;
    }

    private JTextArea createComparisonArea() {
        JTextArea area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setBackground(new Color(245, 245, 245));
        area.setEditable(false);
        return area;
    }

    private JSplitPane createLayout() {
        JPanel ganttPanel = new JPanel(new GridLayout(2, 1, 5, 12));
        ganttPanel.add(new JScrollPane(rrGantt));
        ganttPanel.add(new JScrollPane(prGantt));

        JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(new JTable(inputModel)), ganttPanel);
        topSplit.setResizeWeight(0.35);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        tablesPanel.add(new JScrollPane(new JTable(rrTableModel)));
        tablesPanel.add(new JScrollPane(new JTable(prTableModel)));

        JSplitPane bottomSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                tablesPanel, new JScrollPane(comparisonArea));
        bottomSplit.setResizeWeight(0.7);

        JSplitPane masterSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplit, bottomSplit);
        masterSplit.setResizeWeight(0.4);

        return masterSplit;
    }

    private void addProcess() {
        try {
            String pid = pidF.getText().trim();
            int arrival = Integer.parseInt(arrF.getText().trim());
            int burst = Integer.parseInt(burstF.getText().trim());
            int priority = Integer.parseInt(priF.getText().trim());

            if (pid.isEmpty() || arrival < 0 || burst <= 0 || priority <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid input! All values must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (processes.stream().anyMatch(p -> p.pid.equalsIgnoreCase(pid))) {
                JOptionPane.showMessageDialog(this, "PID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            processes.add(new Process(pid, arrival, burst, priority));
            inputModel.addRow(new Object[]{pid, arrival, burst, priority});

            pidF.setText(""); arrF.setText(""); burstF.setText(""); priF.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runSimulation() {
        if (processes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add at least one process!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int quantum = Integer.parseInt(qF.getText().trim());

        List<Process> rrList = RoundRobinScheduler.copyProcesses(processes);
        List<Process> prList = PriorityScheduler.copyProcesses(processes);

        String rrGanttText = RoundRobinScheduler.run(rrList, quantum);
        String prGanttText = PriorityScheduler.runPreemptive(prList);

        rrGantt.setText(rrGanttText);
        prGantt.setText(prGanttText);

        updateTable(rrTableModel, rrList);
        updateTable(prTableModel, prList);

        showComparison(rrList, prList, quantum);
    }

    private void updateTable(DefaultTableModel model, List<Process> list) {
        model.setRowCount(0);
        for (Process p : list) {
            model.addRow(new Object[]{
                p.pid, p.arrival, p.burst, p.priority,
                p.completion, p.waiting, p.turnaround, p.response
            });
        }
    }

    private void showComparison(List<Process> rr, List<Process> pr, int quantum) {
        int n = rr.size();
        double rrWT = rr.stream().mapToDouble(p -> p.waiting).sum();
        double rrTAT = rr.stream().mapToDouble(p -> p.turnaround).sum();
        double rrRT = rr.stream().mapToDouble(p -> p.response).sum();
        double prWT = pr.stream().mapToDouble(p -> p.waiting).sum();
        double prTAT = pr.stream().mapToDouble(p -> p.turnaround).sum();
        double prRT = pr.stream().mapToDouble(p -> p.response).sum();

        StringBuilder sb = new StringBuilder();
        sb.append("=================== COMPARISON SUMMARY ===================\n\n");
        sb.append("Quantum Used: ").append(quantum).append("\n");
        sb.append("Priority Rule: Lower number = Higher Priority (Preemptive)\n\n");
        sb.append(String.format("Round Robin → Avg WT: %.2f | Avg TAT: %.2f | Avg RT: %.2f\n",
                rrWT/n, rrTAT/n, rrRT/n));
        sb.append(String.format("Priority   → Avg WT: %.2f | Avg TAT: %.2f | Avg RT: %.2f\n\n",
                prWT/n, prTAT/n, prRT/n));
        sb.append("Analysis:\n");
        sb.append("• Round Robin is more fair and balanced.\n");
        sb.append("• Priority favors urgent processes but risks starvation.\n");
        sb.append("• Higher priority processes get significantly better response time.");

        comparisonArea.setText(sb.toString());
    }

    private void clearAll() {
        processes.clear();
        inputModel.setRowCount(0);
        rrTableModel.setRowCount(0);
        prTableModel.setRowCount(0);
        rrGantt.setText("");
        prGantt.setText("");
        comparisonArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SchedulingSimulator::new);
    }
}