import java.util.Vector;

public class InformationSystem{

	private Vector <Order> ordersVector1;//orders until 3 KM
	private Vector <Order> ordersVector2;//orders 3-8 KM
	private Vector <Order> ordersVector3;//orders from 8 KM 
	private Vector<Vector<Order>> dataBaseVector;
	private Order order;
	private boolean dayIsOver;
	private double totalPizzasPrice;

	public InformationSystem() {//Constructor
		ordersVector1 = new Vector<Order>();
		ordersVector2 = new Vector<Order>();
		ordersVector3 = new Vector<Order>();
		createDataBase();	
		dayIsOver=false;
		this.totalPizzasPrice=0;
	}

	private void createDataBase() {
		dataBaseVector=new Vector<Vector<Order>>();
		dataBaseVector.add(0,new Vector<Order>());
		dataBaseVector.add(1, new Vector<Order>());
		dataBaseVector.add(2, new Vector<Order>());

	}

	public synchronized void insertOrder(Order order) {//insert a new order to the correct vector by the distance
		if (order.distance<=3) {
			ordersVector1.add(order);	
			dataBaseVector.get(0).add(order);
		}
		if (order.distance>3 && order.distance<=8  ) {
			ordersVector2.add(order);
			dataBaseVector.get(1).add(order);
		}
		if (order.distance>8) {
			ordersVector3.add(order);
			dataBaseVector.get(2).add(order);
		}
		this.notifyAll();//notify all the workers that are waiting to extract an order
		this.totalPizzasPrice+=order.getPriceAfterDiscount();
	}

	public synchronized Order getOrder(KitchenWorker kitchenWorker) {	//extract an order from the vectors
		try{
			while (noOrders()&&!(dayIsOver))//wait until there will be orders
				this.wait();
		}
		catch( InterruptedException ie) {
		}
		if (!(ordersVector1.isEmpty())) {	
			order=ordersVector1.get(0);
			ordersVector1.remove(0);
			return order;
		}
		else if (!(ordersVector2.isEmpty())) {
			order=ordersVector2.get(0);
			ordersVector2.remove(0);
			return order;
		}
		else if (!(ordersVector3.isEmpty())) {
			order=ordersVector3.get(0);
			ordersVector3.remove(0);
			return order;
		}
		return null;
	}

	public synchronized void printOrderDetails(Order order, KitchenWorker kitchenWorker) {//print the next order details
		System.out.println();
		System.out.println("kitchen worker: " + kitchenWorker.getEmployeeName());
		System.out.println("Worker's salary: " + kitchenWorker.getSalary());
		System.out.println("Number of pizzas: " + order.getCall().getNumOfPizzas());
		System.out.println("Total Price " + order.getPriceAfterDiscount());
		System.out.println("Distance: " + order.distance);
		System.out.println("Address: " + order.getCall().getAddress());
		System.out.println("Credit card number: " + order.getCall().getCreditCardNumber());	
		System.out.println();
	}

	public boolean noOrders() {//check if there are new orders
		if (ordersVector1.isEmpty() && ordersVector2.isEmpty() && ordersVector3.isEmpty())
			return true;
		return false;
	}

	public  synchronized void wakeUp() {//if the day is over, wake all the workers that are waiting for new orders
		this.dayIsOver=true;
		this.notifyAll();	
	}

	public double getTotalPizzaPrice() {//get the total sales for today
		return this.totalPizzasPrice;
	}

}