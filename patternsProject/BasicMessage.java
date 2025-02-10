import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BasicMessage implements Message {
    @Override
    public String getMessage() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd");
        return now.format(formatter);
    }
}