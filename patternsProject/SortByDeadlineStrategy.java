import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByDeadlineStrategy implements TaskSortingStrategy {
    @Override
    public void sort(List<Task> tasks) {
        Collections.sort(tasks, Comparator.comparing(Task::getDeadline));
    }
}