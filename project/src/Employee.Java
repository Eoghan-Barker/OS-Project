public class Employee {

	private String name;
	private String id;
    private String email;
    private String department;

	public Employee(String n, String id, String e, String d) {
		name = n;
		this.id = id;
        email = e;
        department = d;
	}

	public String getName() {
		return name;
	}

	public String getID() {
		return id;
	}

}
