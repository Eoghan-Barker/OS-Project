import java.util.Iterator;
import java.util.LinkedList;

public class Users {

    private LinkedList<Employee> userList;

    public Users() {
        userList = new LinkedList<Employee>();
    }

    public synchronized void addUser(String name, String id, String email, String department) {
        Employee temp = new Employee(name, id, email, department);
        userList.add(temp);
    }

    public synchronized String getUserList() {
        Iterator<Employee> iter = userList.iterator();
        Employee temp;
        String result = "";
        while (iter.hasNext()) {
            temp = iter.next();
            result = result + temp.getName() + "*"
                    + temp.getID() + "*"
                    + temp.getEmail() + "*"
                    + temp.getDepartment() + "?";
        }

        return result;
    }

    public synchronized String getLoginDetails() {
        Iterator<Employee> iter = userList.iterator();
        Employee temp;
        String result = "";
        while (iter.hasNext()) {
            temp = iter.next();
            result = result + temp.getEmail() + "*"
                    + temp.getID() + "?";
        }

        return result;
    }

    public synchronized String getUserNames() {
        Iterator<Employee> iter = userList.iterator();
        Employee temp;
        String result = "";
        int counter = 0;

        while (iter.hasNext()) {
            temp = iter.next();
            counter++;
            result = result + counter + ") " + temp.getName() + "\n";
        }

        return result;
    }

    public synchronized int getTotalUsers() {
        return userList.size();
    }

    public synchronized Employee getAnEmp(int index) {
        Employee result = userList.get(index);

        return result;
    }
}
