public class Task {
    private String name;
    private String description;
    private String category;
    private String deadline;

    public Task(String name, String description, String category, String deadline) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDeadline() {
        return deadline;
    }
}