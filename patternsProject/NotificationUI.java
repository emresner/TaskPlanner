import java.util.List;

public class NotificationUI implements Observer {
    private TaskManager taskManager;

    public NotificationUI(TaskManager taskManager) {
        this.taskManager = taskManager;
        taskManager.registerObserver(this);
    }

    @Override
    public void update() {
        List<Task> tasks = taskManager.getTasks();
        System.out.println("Notifications Updated:");
        for (Task task : tasks) {
            System.out.println("Task '" + task.getName() + "' is nearing its deadline!");
        }
    }
}