import java.time.LocalDate;

public class BirthdayMessageDecorator extends MessageDecorator {
    private LocalDate birthday;

    public BirthdayMessageDecorator(Message message, LocalDate birthday) {
        super(message);
        this.birthday = birthday;
    }

    @Override
    public String getMessage() {
        String baseMessage = message.getMessage();
        LocalDate today = LocalDate.now();

        if (today.equals(birthday)) {
            return baseMessage + " - Happy Birthday!";
        }
        return baseMessage;
    }
}