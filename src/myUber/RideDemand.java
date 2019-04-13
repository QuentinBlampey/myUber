package myUber;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import myUber.Ride.Ride;

@SuppressWarnings("serial")
public class RideDemand extends ReentrantLock {
	protected List<Ride> rideDemand;

	public List<Ride> getRideDemand() {
		return rideDemand;
	}

	public void setRideDemand(List<Ride> rideDemand) {
		this.rideDemand = rideDemand;
	}

	public RideDemand(List<Ride> rideDemand) {
		super();
		this.rideDemand = rideDemand;
	}
	
	
}
