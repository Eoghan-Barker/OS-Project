import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BugList {

    private LinkedList<Bug> bugList;

    private File bugFile = new File("buglist.txt");

    public BugList() {
        bugList = new LinkedList<Bug>();
    }

    // Create a new Bug and add it to the list
    public synchronized void addBug(String name, String platform, String desc) {
        Bug temp = new Bug(name, platform, desc);
        bugList.add(temp);
    }

    public synchronized void addBug(String id, String name, String date, String desc ,String platform, String status) {
        Bug temp = new Bug(id, name, date, desc, platform, status);
        bugList.add(temp);
    }

    // Remove a Bug from the list at a given index
    public synchronized void removeBug(int index) {
        bugList.remove(index);
    }

    // Get all bugs and its details in the list
    public synchronized String getBugList() {
        Iterator<Bug> iter = bugList.iterator();
        Bug temp;
        String result = "";
        while (iter.hasNext()) {
            temp = iter.next();
            result = result + temp.getBugID() + "*"
                    + temp.getName() + "*"
                    + temp.getDate() + "*"
                    + temp.getDescription() + "*"
                    + temp.getPlatform() + "*"
                    + temp.getStatus() + "?";
        }

        return result;
    }

    // Get all BugIDs in the list
    public synchronized String getBugIDs() {
        Iterator<Bug> iter = bugList.iterator();
        Bug temp;
        String result = "";

        while (iter.hasNext()) {
            temp = iter.next();
            result = result + temp.getBugID() + "\n";
        }

        return result;
    }

    // Get a bug at a given index
    public synchronized Bug getABug(int index) {
        Bug result = bugList.get(index);

        return result;
    }

    // Get the size of the bug list
    public synchronized int getTotalBugs() {
        return bugList.size();
    }

    // Save the bug list to a file
    public synchronized void saveBugList() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(bugFile));
        writer.append(getBugList());
        
        writer.close();

    }

    // Load the bug list from a file
    public synchronized void loadBugList() throws IOException {
        Scanner reader = new Scanner(bugFile);
        String readIn;
        String[] bugs;
        String[] details;

        readIn = reader.nextLine();
        bugs = readIn.split("\\?");

        for (int i = 0; i < bugs.length; i++) {
            details = bugs[i].split("\\*");

            addBug(details[0], details[1], details[2], details[3], details[4], details[5]);
        }

        reader.close();
    }
}
