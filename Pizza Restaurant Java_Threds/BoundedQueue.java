import java.util.Vector;

public class BoundedQueue <T> {
	private Vector<T> bQueue;
	private boolean queueIsOver;
	
	public BoundedQueue() {//Constructor
		bQueue = new Vector<T>();
		this.queueIsOver=false;
	}
	public synchronized void insert( T item) {//insert an object to the queue (maximum of 12 objects)
		try{
			while (bQueue.size()>=12)
				this.wait();
		}
		catch( InterruptedException ie) {
		}
		bQueue.add(item);
		notifyAll();  
	}

	public synchronized T extract(){//extract an object from the queue and remove him
		try {	
			while (bQueue.isEmpty() && !this.queueIsOver)
			wait();
		}
		catch(InterruptedException ie) {
	}	
		if (this.bQueue.size()>0) {
			this.notifyAll();
			T item = bQueue.elementAt(0);
			bQueue.remove(item);
			return item;
		}
		return null;
	}

	public Vector<T> getbQueue() {//getter to the queue
		return bQueue;
	}
	
	public synchronized void queueIsOver() {//if the queue is over- notify all the waiting objects
		this.queueIsOver=true;
		this.notifyAll();	
	}
}