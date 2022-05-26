
public class Scheduler extends Employee{
	private Queue<Order> ordersQueue;
	private Order order;
	private InformationSystem infoSystem;

	public Scheduler (String name, Queue<Order> ordersQueue,InformationSystem infoSystem,Manager manager) {//Constructor
		super(manager,name);
		this.ordersQueue=ordersQueue;
		this.infoSystem=infoSystem;
	}

	@Override
	public void run() {//run scheduler
		while (!(this.myManager.isFinishWork())) 
			work();	
	}

	  protected void work() {//start work
		order = ordersQueue.extract();//extract an order from the queue
		if(order!=null) {
			calculateDistance();
			calculateSalary();

			try {
				Thread.sleep((long) (order.distance*250));
			}
			catch(InterruptedException ie) {
			}
			infoSystem.insertOrder(order);//insert the order to the info system
			System.out.println("New Order Arrived, ID: "+order.getID());//print order ID
		}
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
			order.distance+=(int)(order.getCall().getAddress().charAt(0)-'0');
	}

	//Getters
	public Queue<Order> getQo() {
		return ordersQueue;
	}

	public Order getOrder() {
		return order;
	}

protected 	void calculateSalary() {//calculate salary
		this.salary+=order.distance;	
	}

}
