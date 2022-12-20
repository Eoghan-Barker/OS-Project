import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum Platform {
    Windows, Mac, Unix
};

enum Status {
    Open, Assigned, Closed
};

public class Bug {
    private String appName;
    private String date;
    private String description;
    private Platform platform;
    private Status status;
    private Employee employee = null;

    private LocalDateTime dateObj;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Bug(String n, String p, String desc) {
        appName = n;
        description = desc;

        dateObj = LocalDateTime.now();
        date = dateObj.format(dateFormatter);

        status = Status.Open;

        switch (p.toUpperCase()) {
            case "WINDOWS":
                platform = Platform.Windows;
                break;

            case "MAC":
                platform = Platform.Mac;
                break;

            case "UNIX":
                platform = Platform.Unix;
                break;

            default:
                platform = Platform.Windows;
                break;
        }
    }

    public String getName() {
        return appName;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Status getStatus() {
        return status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setStatus(int s) {
        switch (s) {
            case 1:
                status = Status.Open;
                employee = null;
                break;

            case 2:
                status = Status.Closed;
                break;

            case 3:
                status = Status.Assigned;
                break;

            default:
                status = Status.Open;
                break;

        }
    }

    public void assignEmployee(Employee emp) {
        employee = emp;
        status = Status.Assigned;
    }
}
