import java.util.ArrayList;
import java.util.List;

public class TaskManager implements Subject {
    private List<Observer> observers;
    private List<Task> tasks;

    public TaskManager() {
        observers = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    public List<Task> getTasks() {
        return tasks;
    }
}