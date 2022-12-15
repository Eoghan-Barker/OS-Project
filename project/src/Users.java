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

    public synchronized String getList() {
        Iterator<Employee> iter = userList.iterator();
        Employee temp;
        String result = "";
        while (iter.hasNext()) {
            temp = iter.next();
            result = result + temp.getName() + "*" + temp.getID() + "?";
        }

        return result;
    }
}
