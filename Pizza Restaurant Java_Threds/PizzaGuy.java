import java.util.Vector;

public class PizzaGuy extends Employee {
	private BoundedQueue<PizzaDelivery> pizzaDeliveryQueue;
	private int maxOrders;
	private double randomNum;
	private Vector <PizzaDelivery> pizzaDeliveryList;
	private int ordersCounter;
	private int totalTips;
	private double totalDistance;

	public PizzaGuy(String name, BoundedQueue <PizzaDelivery> pizzaDeliveryQueue,Manager manager) {//Constructor
		super(manager,name);
		this.pizzaDeliveryQueue=pizzaDeliveryQueue;
		this.maxOrders=calculateNumberOfOrders();
		pizzaDeliveryList=new Vector <PizzaDelivery>();
		this.ordersCounter=0;
	}

	//Getters
	public BoundedQueue<PizzaDelivery> getPizzaDeliveryQueue() {
		return pizzaDeliveryQueue;
	}

	public int getMaxOrders() {
		return maxOrders;
	}

	public Vector<PizzaDelivery> getPizzaDeliveryList() {
		return pizzaDeliveryList;
	}

	public int getTotalTips() {
		return totalTips;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	private int calculateNumberOfOrders() {//max number of orders in each delivery
		randomNum=Math.random();
		if (randomNum<=0.33)
			return 2;
		if (randomNum<=0.66)
			return 3;
		return 4;
	}

	@Override
	public void run() {//run pizza guy
		while(!(this.myManager.isFinishWork() ))
			work();
		finishWork();
	}

	protected void work() {//take orders and deliver them
		collectOrders();
		startDelivery();
	}

	protected void finishWork() {//finish the work for today
		calculateSalary();

	}

	protected void calculateSalary() {//calculate the salary of today
		this.salary+=3*numOfDeliveries()+4*this.getTotalDistance()+this.getTotalTips();	
	}

	private int numOfDeliveries() {//get the numbers of deliveries for today
		return pizzaDeliveryList.size();
	}

	private void startDelivery() {//deliver
		while (ordersCounter>0 && !(this.myManager.isFinishWork())) {
			rideToDestination();
			takeTip();
			ordersCounter--;
		}
		this.myManager.checkIsDayOver();
	}

	private void takeTip() {//take tip
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException ie) {
		}
		totalTips+=(int)(Math.random()*15);		
	}

	private void rideToDestination() {//ride to the next destination
		try {
			Thread.sleep((int) ((pizzaDeliveryList.get(pizzaDeliveryList.size()-ordersCounter).getOrder().distance)*1000));
		}
		catch(InterruptedException ie) {
		}	
		totalDistance += pizzaDeliveryList.get(pizzaDeliveryList.size()-ordersCounter).getOrder().distance;
	}

	private void collectOrders() {//collect orders
		if(!this.myManager.checkLessThenTenOrders()) { //collect orders until you get to your maximum	
			while (ordersCounter<maxOrders) {
				pizzaDeliveryList.add((PizzaDelivery)(pizzaDeliveryQueue.extract()));
				if (pizzaDeliveryList.get(pizzaDeliveryList.size()-1)!=null) {
					this.myManager.addTotalOrdersDelivered();
					ordersCounter++;
				}
				else break;
			}
		}
		else {//if there are less than 10 orders take one in a time
			pizzaDeliveryList.add((PizzaDelivery)(pizzaDeliveryQueue.extract()));
			if (pizzaDeliveryList.get(pizzaDeliveryList.size()-1)!=null) {
				this.myManager.addTotalOrdersDelivered();
				ordersCounter++;
			}
		}
	}
	
	
}

