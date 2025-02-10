import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotificationMessageDecorator extends MessageDecorator {
    private List<Task> tasks;

    public NotificationMessageDecorator(Message message, List<Task> tasks) {
        super(message);
        this.tasks = tasks;
    }

    @Override
    public String getMessage() {
        String baseMessage = message.getMessage();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        StringBuilder notifications = new StringBuilder();

        for (Task task : tasks) {
            LocalDate deadline = LocalDate.parse(task.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (deadline.equals(tomorrow)) {
                notifications.append("\nReminder: Task '").append(task.getName())
                        .append("' is due tomorrow!");
            }
        }

        return notifications.length() > 0 ? baseMessage + notifications : baseMessage;
    }
}