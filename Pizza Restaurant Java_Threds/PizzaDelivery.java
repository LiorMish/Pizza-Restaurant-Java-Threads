
public class PizzaDelivery {

	private Order order;

	public PizzaDelivery(Order order) {//Constructor
		this.order=order;
	}

	public synchronized Order getOrder() {//get order
		return order;
	}


}
