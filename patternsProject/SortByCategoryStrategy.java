import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByCategoryStrategy implements TaskSortingStrategy {
    @Override
    public void sort(List<Task> tasks) {
        Collections.sort(tasks, Comparator.comparing(Task::getCategory));
    }
}