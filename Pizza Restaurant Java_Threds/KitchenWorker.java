public class KitchenWorker extends Employee {
	private Order order;
	private InformationSystem infoSystem;
	private BoundedQueue <PizzaDelivery> pizzasDeliveryQueue;
	private double cookingTime;


	public KitchenWorker(String name,double cookingTime, BoundedQueue <PizzaDelivery> pizzasDeliveryQueue, InformationSystem infoSystem,Manager manager) {//Constructor
		super(manager,name);
		this.cookingTime=cookingTime;
		this.infoSystem=infoSystem;
		this.pizzasDeliveryQueue=pizzasDeliveryQueue;
	}

	//Getters
	public Order getOrder() {
		return order;
	}

	@Override
	public void run() {//run kitchen worker
		while (!myManager.isFinishWork()) //until the manager doesn't finish working, work
			work();
	}

	 protected void work() {//until the manager doesn't finish working, work
		order = infoSystem.getOrder(this);//extract new order from the information system
		if (order!=null) {
			makeTheOrder();
			calculateSalary();
			this.infoSystem.printOrderDetails(order, this);//print order details
			sendPizzas();		
		}
	}

	private void sendPizzas() {//make a new pizza delivery and insert it to the bounded queue
		pizzasDeliveryQueue.insert(new PizzaDelivery(order));
	}

	protected void calculateSalary() {//calculate salary
		this.salary+=order.getCall().getNumOfPizzas()*2;	

	}

	private void makeTheOrder() {//cook the pizzas
		try {
			Thread.sleep((int)(this.cookingTime*this.order.getCall().getNumOfPizzas()*1000));
		}
		catch(InterruptedException ie) {
		}
	}

}