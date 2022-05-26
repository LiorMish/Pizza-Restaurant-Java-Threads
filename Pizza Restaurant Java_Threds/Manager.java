import java.util.Vector;

public class Manager extends Employee {

	private Queue<Call> callsQueue;
	private Queue <Order> ordersQueue;
	private Queue<Call> managerCallsQueue;
	private BoundedQueue <PizzaDelivery> pizzaDeliveryQueue;
	private InformationSystem infoSystem;
	private Call call;
	private Order order;
	private int totalCallsForToday;
	protected int totalCallsCounter;
	protected int totalOrdersDeliverd;
	protected boolean finishWork;
	private double totalWorkersSalary;
	private Vector<Employee> employeesVector;

	//Getters
	public Queue<Call> getCallsQueue() {
		return callsQueue;
	}

	public Queue<Order> getOrdersQueue() {
		return ordersQueue;
	}

	public Queue<Call> getManagerCallsQueue() {
		return managerCallsQueue;
	}

	public BoundedQueue<PizzaDelivery> getPizzaDeliveryQueue() {
		return pizzaDeliveryQueue;
	}

	public InformationSystem getInfoSystem() {
		return infoSystem;
	}

	public Call getCall() {
		return call;
	}

	public Order getOrder() {
		return order;
	}

	public int getTotalCallsForToday() {
		return totalCallsForToday;
	}

	public int getTotalCallsCounter() {
		return totalCallsCounter;
	}

	public  synchronized int getTotalOrdersDeliverd() {
		return totalOrdersDeliverd;
	}

	public  synchronized boolean isFinishWork() {
		return finishWork;
	}


	Manager(Vector<Employee> employeesVector, String name, Queue <Call>callsQueue, Queue <Order> ordersQueue,Queue <Call> managerCallsQueue, BoundedQueue <PizzaDelivery> pizzaDeliveryQueue,InformationSystem infoSystem,Manager manager,int numberOfCalls) {//Constructor
		super(manager,name);
		this.callsQueue=callsQueue;
		this.infoSystem=infoSystem;
		this.managerCallsQueue=managerCallsQueue;
		this.ordersQueue=ordersQueue;
		this.pizzaDeliveryQueue=pizzaDeliveryQueue;
		this.totalCallsForToday=numberOfCalls;
		this.totalCallsCounter=numberOfCalls;
		this.totalOrdersDeliverd=0;
		this.finishWork=false;
		this.totalWorkersSalary=0;
		this.employeesVector=employeesVector;
	}

	protected synchronized boolean checkLessThenTenOrders() {//check if less then 10 orders left
		return(this.totalCallsForToday-this.totalOrdersDeliverd<=10);
	}

	protected void calculateSalary() {//calculate salary
		this.salary=0;
	}

	public void run() {//run the manager
		while(!endOfTheDay()) 
			work();
		finishWork();
	}

	private boolean endOfTheDay() {//if the number of calls for today equals to the number of orders delivered, finish the day
		return (this.totalCallsForToday==this.totalOrdersDeliverd);
	}

	private void finishWork() {	//finish the work for today
		announceDayIsOver();
		printDetails();
	}

	protected synchronized void changeCallsCounter() {//count every call that answered 
		this.totalCallsCounter--;
	}
	
	private void announceDayIsOver() {//tell all the workers that the day is over
		finishWork=true;
		this.infoSystem.wakeUp();
		this.ordersQueue.queueIsOver();
		this.pizzaDeliveryQueue.queueIsOver();

	}

	private void printDetails() {//print today's details
		System.out.println("Total orders deliverd :"+" "+this.totalOrdersDeliverd);
		System.out.println("Total workers salary :"+" "+calculateTotalSalary());
		System.out.println("Total income for today : "+this.infoSystem.getTotalPizzaPrice());
	}

	private double calculateTotalSalary() {//calculate the total salary of all the workers for today
		for(int s=0;s<this.employeesVector.size();s++) {
			this.totalWorkersSalary+=this.employeesVector.get(s).getSalary();
		}
		return 	this.totalWorkersSalary;
	}

	protected  void work() {//work
		takeCall();
		if (call!=null) {	
			takeCareOfOrder();
			finishCall();
		}
	}

	private void takeCall() {//take the call of big orders
		this.call=managerCallsQueue.extract();
	}


	private double calculatePriceOfOrder() {//calculate the price of the orders after discounts
		if (this.call!=null) {
			if(this.call.getNumOfPizzas()<20)	
				return this.call.getNumOfPizzas()*15;
			return (this.call.getNumOfPizzas()*15)*0.9;
		}
		return 0;
	}
	private Order createNewOrder() {//create a new order from the call details
		if (this.call!=null) {
			this.order=new Order(this.ordersQueue.getPlace(),calculatePriceOfOrder(),this.call);
			calculateDistance();
		}return this.order;
	}

	private void calculateDistance() {//calculate the order distance
		for (int i=0; i<order.getCall().getAddress().length();i++) {
			if (order.getCall().getAddress().charAt(i)==' ') 
				order.distance+=1;
		}
		if (order.getCall().getAddress().charAt(0)>='A' && order.getCall().getAddress().charAt(0)<='H')
			order.distance+=0.5;
		if (order.getCall().getAddress().charAt(0)>='I' && order.getCall().getAddress().charAt(0)<='P')
			order.distance+=2;
		if (order.getCall().getAddress().charAt(0)>='Q' && order.getCall().getAddress().charAt(0)<='Z')
			order.distance+=7;
		if (order.getCall().getAddress().charAt(0)>='0' && order.getCall().getAddress().charAt(0)<='9')
			order.distance+=(int)(order.getCall().getAddress().charAt(0));
	}

	private void finishCall() {//finish the call
		call.wakeUp();
	}

	private void takeCareOfOrder() {//make the call and insert the order to the info system
		try {
			Thread.sleep(2000);
		}
		catch(InterruptedException ie) {
		}
		infoSystem.insertOrder(createNewOrder());
	}

	protected synchronized void checkIsDayOver() {//check if the day is over and tell the workers
		this.managerCallsQueue.managerWakeUp();
		if(endOfTheDay())	
			this.managerCallsQueue.queueIsOver();
	}

	protected synchronized boolean noCalls() {//check if there are more calls left
		return this.totalCallsCounter==0;
	}

	protected synchronized void addTotalOrdersDelivered() {
		this.totalOrdersDeliverd++;
	}

}