import java.util.Iterator;
import java.util.LinkedList;

public class BugList {

    private LinkedList<Bug> bugList;
    

    public BugList() {
        bugList = new LinkedList<Bug>();
    }

    public synchronized void addBug(String name, String platform, String desc) {
        Bug temp = new Bug(name, platform, desc);
        bugList.add(temp);
    }

    public synchronized void removeBug(int index) {
        bugList.remove(index);
    }

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

    public synchronized Bug getABug(int index) {
        Bug result = bugList.get(index);

        return result;
    }

    public synchronized int getTotalBugs() {
        return bugList.size();
    }
}
