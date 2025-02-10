import java.util.List;

public class TaskListUI implements Observer {
    private TaskManager taskManager;

    public TaskListUI(TaskManager taskManager) {
        this.taskManager = taskManager;
        taskManager.registerObserver(this);
    }

    @Override
    public void update() {
        List<Task> tasks = taskManager.getTasks();
        System.out.println("Task List Updated:");
        for (Task task : tasks) {
            System.out.println("- " + task.getName() + " (" + task.getDeadline() + ")");
        }
    }
}