import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class BugList implements Serializable {

    private LinkedList<Bug> bugList;

    public BugList() {
        bugList = new LinkedList<Bug>();
    }

    // Create a new Bug and add it to the list
    public synchronized void addBug(String name, String platform, String desc) {
        Bug temp = new Bug(name, platform, desc);
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
}
