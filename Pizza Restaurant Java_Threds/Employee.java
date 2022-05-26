
abstract class Employee extends Thread {//An abstract class
	
	protected Manager myManager;
	protected double salary;
	private String name;
	
	public Employee(Manager manager,String name) {//Constructor
		this.name=name;
		this.myManager=manager;
		this.salary=0;
	}
	//Getters
	public Manager getMyManager() {
		return myManager;
	}
	
	public double getSalary() {		
		return this.salary;
	}
	
	public String getEmployeeName() {
		return this.name;
	}
	
	protected abstract void calculateSalary();//each employee has to calculate his salary
	protected abstract void work();//each employee has to work
	
}
