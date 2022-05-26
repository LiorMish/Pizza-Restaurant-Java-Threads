public class Order {
	private int ID;
	private double priceAfterDiscount;
	private Call call;
	private double timeToCall;
	protected double distance;

	public Order(int ID, double priceAfterDiscount, Call callDetails) {//Constructor
		
		this.ID=ID;
		this.priceAfterDiscount=priceAfterDiscount;
		this.call=callDetails;
		this.timeToCall=callDetails.getCallDuration();
		this.distance=1;
		}
	
	//Getters
	public Call getCall() {
		return call;
	}

	public synchronized int getID() {
		return ID;
	}

	public double getPriceAfterDiscount() {
		return priceAfterDiscount;
	}
	public  double getTimeToCall() {
		return timeToCall;
	}
	
}


