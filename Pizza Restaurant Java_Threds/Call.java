public class Call extends Thread {
	private int numOfPizzas;
	private String address;	
	private String creditCardNumber;	
	private int waitForQueue;
	private double callDuration;
	private boolean isOn;
	private Queue<Call> callsQueue;

	public Call (String creditCardNumber,String numOfPizzas,String waitForQueue,String callDuration,String address,Queue<Call> callsQueue ) {//Constructor
		this.numOfPizzas=Integer.parseInt(numOfPizzas);
		this.address=address;		
		this.creditCardNumber=creditCardNumber;		
		this.callDuration=1;
		this.waitForQueue=1;
		this.callsQueue=callsQueue;
		this.isOn=true;
	}
	
	//Getters
	public int getNumOfPizzas() {
		return numOfPizzas;
	}
	public String getAddress() {
		return address;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public double getCallDuration() {
		return callDuration;
	}

	@Override
	public void run() {//run call
		while (this.isOn) {//until the manager or the clerk finish the call- wait
			waitForQueue();
			this.callsQueue.insert(this);
			waitUntilFinished();
		}
	}

		private void waitForQueue() {//wait to enter to the calls queue
			try {
				Thread.sleep(this.waitForQueue*1000);
			}
			catch( InterruptedException ie) {
				ie.printStackTrace();
			}	
		}

		private synchronized void waitUntilFinished() {//until the manager or the clerk finish the call- wait
			try {
					this.wait();
			}
			catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		public synchronized void wakeUp() {//when the clerk or the manager finish the call- wake her up and finish run
			isOn=false;
			this.notifyAll();
		}

	}