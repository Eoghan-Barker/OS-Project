public class Employee {
    private String name;
	private String id;
    private String email;
    private String department;
	private String bugID;

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

	public String getEmail() {
		return email;
	}

	public String getDepartment() {
		return department;
	}

	public String getBugID() {
		return bugID;
	}

	public void assignBug(String id){
		bugID = id;
	}
}
