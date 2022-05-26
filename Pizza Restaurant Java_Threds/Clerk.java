public class Clerk extends Employee {
	private Queue<Call> callsQueue;
	private Queue<Order> ordersQueue;
	private Queue<Call> managerCallsQueue;
	private Call call;
	private Order order;


	public Clerk(String name,Queue<Call> callsQueue,Queue<Order> ordersQueue,Queue<Call> managerCallsQueue,Manager manager) {//Constructor
		super(manager,name);
		this.callsQueue=callsQueue;
		this.ordersQueue=ordersQueue;
		this.managerCallsQueue=managerCallsQueue;
	}

	public void run() {//run clerk
		while(!(this.myManager.noCalls())) //work until there is no more calls for today
			work();
		finishWork();
	}

	private void finishWork() {//notify all the clerks that waiting to extract from the queue
		this.callsQueue.queueIsOver();
	}

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

	public Call getCall() {
		return call;
	}

	public Order getOrder() {
		return order;
	}

	protected void work() {
		takeCall();
		if (this.call!=null) {
			this.myManager.changeCallsCounter() ;
			calculateSalary();
			if(this.call.getNumOfPizzas()<10) {
				takeCareOfOrder();
				finishCall();
			}
			else passCallToManger();
		}
	}

	private void takeCall() {//take a new call from calls queue
		this.call=callsQueue.extract();
	}

	 protected void calculateSalary() {//+2 salary for each call  
		this.salary+=2;
	}

	private Order createNewOrder() {//make a new order from the call details
		order=new Order(this.ordersQueue.getPlace(),25*this.call.getNumOfPizzas(),this.call);
		return this.order;
	}

	private  void finishCall() {//finish the call
		this.call.wakeUp();
	}

	private void takeCareOfOrder() {//insert the new order to the orders queue
		try {
			Thread.sleep((int)(call.getCallDuration()*1000));
		}
		catch(InterruptedException ie) {
		}
		ordersQueue.insert(createNewOrder());
	}

	private void passCallToManger() {//if the it is a big order, pass the call to the manager
		try {
			Thread.sleep((int)(500));
		}
		catch(InterruptedException ie) {

		}
		managerCallsQueue.insert(this.call);
	}

}