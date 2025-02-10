import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TaskPlannerGUI extends JFrame {
    private JLabel lblDayDate;
    private JTextArea txtNotifications;
    private JList<String> taskList;
    private DefaultListModel<String> listModel;
    private JButton btnAddTask, btnEditTask, btnDeleteTask, btnSort;
    private JComboBox<String> sortCriteriaDropdown;
    private TaskDAO taskDAO;
    private TaskSorter taskSorter;

    public TaskPlannerGUI() {
        taskDAO = new TaskDAO();
        taskSorter = new TaskSorter();

        setTitle("Task Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        initializeUI();

        btnAddTask.addActionListener(e -> addTask());
        btnEditTask.addActionListener(e -> editTask());
        btnDeleteTask.addActionListener(e -> deleteTask());
        btnSort.addActionListener(e -> sortTasks());

        loadTasksFromDatabase();

        startDayDateTimer();
    }

    private void initializeUI() {

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel dayDatePanel = new JPanel(new GridLayout(1, 1));
        lblDayDate = new JLabel("", JLabel.CENTER);
        dayDatePanel.add(lblDayDate);

        txtNotifications = new JTextArea(3, 20);
        txtNotifications.setEditable(false);
        txtNotifications.setBorder(BorderFactory.createTitledBorder("Notifications"));
        JScrollPane notificationScroll = new JScrollPane(txtNotifications);

        topPanel.add(dayDatePanel, BorderLayout.NORTH);
        topPanel.add(notificationScroll, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);


        JPanel taskListPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setBorder(BorderFactory.createTitledBorder("Task List"));
        JScrollPane taskListScroll = new JScrollPane(taskList);
        taskListPanel.add(taskListScroll, BorderLayout.CENTER);
        add(taskListPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout());
        btnAddTask = new JButton("Add Task");
        btnEditTask = new JButton("Edit Task");
        btnDeleteTask = new JButton("Delete Task");

        sortCriteriaDropdown = new JComboBox<>(new String[]{"Name", "Category", "Deadline"});
        btnSort = new JButton("Sort");

        bottomPanel.add(btnAddTask);
        bottomPanel.add(btnEditTask);
        bottomPanel.add(btnDeleteTask);
        bottomPanel.add(sortCriteriaDropdown);
        bottomPanel.add(btnSort);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadTasksFromDatabase() {
        listModel.clear();
        List<Task> tasks = taskDAO.getAllTasks();
        for (Task task : tasks) {
            listModel.addElement(task.getName());
        }
    }

    private void addTask() {
        String taskName = JOptionPane.showInputDialog("Enter Task Name:");
        String taskDescription = JOptionPane.showInputDialog("Enter Task Description:");
        String taskCategory = JOptionPane.showInputDialog("Enter Task Category:");
        String taskDeadline = JOptionPane.showInputDialog("Enter Task Deadline (YYYY-MM-DD):");

        if (taskName != null && !taskName.trim().isEmpty()) {
            Task task = new Task(taskName, taskDescription, taskCategory, taskDeadline);
            taskDAO.addTask(task);
            listModel.addElement(taskName);
        }
    }


    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String taskName = listModel.getElementAt(selectedIndex);
            String updatedTaskName = JOptionPane.showInputDialog("Edit Task Name:", taskName);

            if (updatedTaskName != null && !updatedTaskName.trim().isEmpty()) {
                Task selectedTask = taskDAO.getAllTasks().get(selectedIndex);
                selectedTask = new Task(updatedTaskName, selectedTask.getDescription(),
                        selectedTask.getCategory(), selectedTask.getDeadline());

                taskDAO.deleteTask(taskName);
                taskDAO.addTask(selectedTask);

                listModel.set(selectedIndex, updatedTaskName);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a task to edit.");
        }
    }


    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String taskName = listModel.getElementAt(selectedIndex);
            taskDAO.deleteTask(taskName);
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a task to delete.");
        }
    }


    private void sortTasks() {
        String selectedCriteria = (String) sortCriteriaDropdown.getSelectedItem();
        List<Task> tasks = taskDAO.getAllTasks();


        if ("Name".equals(selectedCriteria)) {
            taskSorter.setStrategy(new SortByNameStrategy());
        } else if ("Category".equals(selectedCriteria)) {
            taskSorter.setStrategy(new SortByCategoryStrategy());
        } else if ("Deadline".equals(selectedCriteria)) {
            taskSorter.setStrategy(new SortByDeadlineStrategy());
        }


        taskSorter.sortTasks(tasks);
        listModel.clear();
        for (Task task : tasks) {
            listModel.addElement(task.getName());
        }
    }


    private void startDayDateTimer() {
        Timer timer = new Timer(1000, e -> updateDayDateAndNotifications());
        timer.start();
    }


    private void updateDayDateAndNotifications() {

        Message message = new BasicMessage();


        message = new BirthdayMessageDecorator(message, LocalDate.of(2025, 1, 10));


        message = new NotificationMessageDecorator(message, taskDAO.getAllTasks());


        lblDayDate.setText(message.getMessage());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskPlannerGUI gui = new TaskPlannerGUI();
            gui.setVisible(true);
        });
    }
}