import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Users implements Serializable {

    private LinkedList<Employee> userList;

    public Users() {
        userList = new LinkedList<Employee>();
    }

    // Create a new employee and add it to the list
    public synchronized void addUser(String name, String id, String email, String department) {
        Employee temp = new Employee(name, id, email, department);
        userList.add(temp);
    }

    // Get all employees and their details
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

    // Get all employee's ID and email with regex for splitting
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

    // Get all user names in a menu format
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

    // Get size of list
    public synchronized int getTotalUsers() {
        return userList.size();
    }

    // Get an employee at a given index in the list
    public synchronized Employee getAnEmp(int index) {
        Employee result = userList.get(index);

        return result;
    }
}
