import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

enum Platform {
    Windows, Mac, Unix
};

enum Status {
    Open, Assigned, Closed
};

public class Bug {
    private String bugID;
    private String appName;
    private String date;
    private String description;
    private Platform platform;
    private Status status;

    private LocalDateTime dateObj;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static AtomicInteger idCounter = new AtomicInteger();

    // cosntructor for user created bug
    public Bug(String n, String p, String desc) {
        bugID = createBugID();
        appName = n;
        description = desc;

        dateObj = LocalDateTime.now();
        date = dateObj.format(dateFormatter);

        status = Status.Open;

        setPlatform(p);
    }

    // constructor for bug created from file
    public Bug(String i, String n, String d, String desc, String p, String s) {
        bugID = i;
        appName = n;
        date = d;
        description = desc;
        setPlatform(p);
        
        switch (s.toUpperCase()) {
            case "OPEN":
                status = Status.Open;
                break;

            case "CLOSED":
                status = Status.Closed;
                break;

            case "ASSIGNED":
                status = Status.Assigned;
                break;

            default:
                status = Status.Open;
                break;
        }
    }

    public String getBugID() {
        return bugID;
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

    // BugID is assigned to the next value in the counter
    // The counter is an AtomicInteger for thread synchronization
    public static String createBugID() {
        return String.valueOf(idCounter.getAndIncrement());
    }

    public void setStatus(int s) {
        switch (s) {
            case 1:
                status = Status.Open;
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

    public void setPlatform(String p) {
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

    public static void setIdCounter(int n) {
        idCounter.set(n);
    }
}
