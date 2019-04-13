package myUber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myUber.Car.Berline;
import myUber.Car.Car;
import myUber.Car.Standard;
import myUber.Car.Van;
import myUber.Factory.AbstractFactory;
import myUber.Factory.FactoryProducer;
import myUber.ID.Id;
import myUber.ID.IdGenerator;
import myUber.Ride.Ride;
import myUber.Ride.SimpleRide;

/**
 * Thread definissant un conducteur. 
 */

public class Driver extends Thread {
	
	protected String name;
	protected String surname;
	protected String status;
	protected Id idD;
	protected Car car;
	protected Ride ride;
	protected List<Ride> rides;
	protected Boolean changed;
	protected Boolean changedPool;
	protected double price;
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getNameDriver() {
		return name;
	}
	public void setNameDriver(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String state) {
		this.status = state;
	}
	public Id getIdD() {
		return idD;
	}
	public void setIdD(Id id) {
		this.idD = id;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Ride getRide() {
		return ride;
	}
	public void setRide(Ride ride) {
		this.ride = ride;
	}
	
	@Override
	public String toString() {
		return name + " " + surname + " ("+idD.toString()+"), " + status + ", with car " + car.getId().toString();
	}
	
	
	
	public Driver() {
		super();
		IdGenerator  ig = IdGenerator.getInstance();
		this.idD = ig.getId("Driver");
	}
	public Driver(String name, String surname, Car car) {
		super();
		this.name = name;
		this.surname = surname;
		this.car = car;
		this.status = "on-duty";
		this.changed = false;
		this.changedPool = false;
		IdGenerator  ig = IdGenerator.getInstance();
		this.idD = ig.getId("Driver");
		car.getDrivers().add(this.idD);
	}
	
	public Driver(String name, String surname, String carType) {
		super();
		this.name = name;
		this.surname = surname;
		AbstractFactory cf = FactoryProducer.getFactory("Car");
		this.car = cf.getCar(carType, new GPS(0,0), new ArrayList<Id>());
		this.status = "on-duty";
		this.changed = false;
		this.changedPool = false;
		IdGenerator  ig = IdGenerator.getInstance();
		this.idD = ig.getId("Driver");
		car.getDrivers().add(this.idD);
	}

	/**
     * Accepte un trajet non de type Pool.        
     */
	
	public void acceptSimpleRide() {
		Uber uber = Uber.getInstance();
		List<Ride> rideDemand;
		if (this.car instanceof Standard) {
			rideDemand = uber.getRideDemandX();
		}
		else if (this.car instanceof Berline) {
			rideDemand = uber.getRideDemandBlack();
		}
		else {
			rideDemand = uber.getRideDemandVan();
		}
		
		if ( ! (rideDemand.isEmpty())) {
			double minDistance = rideDemand.get(0).getGps1().distanceTo(this.getCar().getGps());
			int indexMin = 0;
			for ( int i=0 ; i < rideDemand.size() ; i++) {
				double distance = rideDemand.get(i).getGps1().distanceTo(this.getCar().getGps());
				if (distance < minDistance) {
					minDistance = distance;
					indexMin = i;
				}
			}
			this.ride = rideDemand.get(indexMin);
			int mark = this.ride.getMark();
			if (mark != -1) {
				this.ride.getCustomer().noteDriver(this, mark);
			}
			else {
				GUI.print("");
				GUI.print("<<< Mark for the driver ?");
				CLUI.setDriver_waiting(this);
			}
			uber.getRideDemand().remove(ride);
			if (this.car instanceof Standard) {
				uber.getRideDemandX().remove(ride);
			}
			else if (this.car instanceof Berline) {
				uber.getRideDemandBlack().remove(ride);
			}
			else {
				uber.getRideDemandVan().remove(ride);
			}
			if ( ! ride.getCustomer().isLocked()) {
				ride.getCustomer().lock();
				this.changed = true;
				this.setRide(ride);
				this.price = Price.getPrice(ride);
				ride.getCustomer().setStatus("waiting-driver");
			}
		}
		
	}
	
	/**
     * Choisi un ensemble de trajets de type Pool. 
     * 
     * @param rideDemand
     * 		Liste des trajets demandes.
     * 
     * @return Liste des trajets choisis.
     */
	
	public List<Ride> choosePoolRide(List<Ride> rideDemand) {
		if (rideDemand.size() == 2) {
			return rideDemand;
		}
		else {
			List<Ride> result = Arrays.asList(rideDemand.get(0),rideDemand.get(1),rideDemand.get(2));
			double minDistance = Uber.poolDistance( this.car.getGps() , Arrays.asList(rideDemand.get(0).getGps1(),rideDemand.get(1).getGps1(),rideDemand.get(2).getGps1()), Arrays.asList(rideDemand.get(0).getGps2(),rideDemand.get(1).getGps2(),rideDemand.get(2).getGps2()));
			for ( int i = 0 ; i < rideDemand.size() ; i++) {
				for ( int j = i+1 ; j < rideDemand.size() ; j++) {
					for ( int k = j+1 ; k < rideDemand.size() ; k++) {
						double distance = Uber.poolDistance( this.car.getGps() , Arrays.asList(rideDemand.get(i).getGps1(),rideDemand.get(j).getGps1(),rideDemand.get(k).getGps1()), Arrays.asList(rideDemand.get(i).getGps2(),rideDemand.get(j).getGps2(),rideDemand.get(k).getGps2()));
						if (distance < minDistance) {
							minDistance = distance;
							result = Arrays.asList(rideDemand.get(i),rideDemand.get(j),rideDemand.get(k));
						}
					}
				}
			}
			return result;
		}
	}
	
	/**
     * Accepte un trajet de type Pool.        
     */
	
	public void acceptPoolRide() {
		Uber uber = Uber.getInstance();
		List<Ride> rideDemand = uber.getRideDemandPool();
		if ( rideDemand.size() >= 2 ) {
			this.rides = choosePoolRide(rideDemand);
			boolean allNotLocked = true;
			for (Ride ride : this.rides) {
				uber.getRideDemand().remove(ride);
				uber.getRideDemandPool().remove(ride);
				//allNotLocked = allNotLocked && (! ride.getCustomer().isLocked());
			}
			if ( allNotLocked ) {
				//for (Ride ride : this.rides) {
				//	ride.getCustomer().lock();
				//}
				this.changedPool = true;
				this.price = 0;
				for (Ride ride : this.rides) {
					ride.getCustomer().setStatus("waiting-driver");
					this.price += Price.getPrice(ride);
					int mark = ride.getMark();
					if (mark != -1) {
						ride.getCustomer().noteDriver(this, mark);
					}
					else {
						GUI.print("");
						GUI.print("<<< Mark for the driver ?");
						CLUI.setDriver_waiting(this);
					}
				}
			}
		}
		
	}
	
	/**
     * Effectue un trajet pas de type Pool.        
     */
	
	public void makeSimpleRide() {
		if (this.changed) {
			this.status = "on-duty";
			Uber uber = Uber.getInstance();
			double timeToCustomer = uber.time(this.car.getGps(), this.ride.getGps1());
			try {
				Thread.sleep((long) timeToCustomer);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			((SimpleRide) this.ride).getCustomer().setStatus("on-a-ride");
			this.status = "on-a-ride";
				
			double timeToDestination = uber.time(this.ride.getGps1(), this.ride.getGps2());
			GUI.print( "-- Driver " + this.idD.toString() + " pick up client (" + this.ride.getCustomer().toString() + ") : he will arrive in " + timeToDestination / 1000 + " seconds (real time)");
			try {
				Thread.sleep((long) timeToDestination);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			((SimpleRide) this.ride).getCustomer().setStatus("offline");
			this.status = "off-duty";
			((SimpleRide) this.ride).getCustomer().setGps(this.ride.getGps2());
			uber.addRideRegistration(new RideRegistration(this.idD, ((SimpleRide) this.ride).getCustomer().getId(), this.car.getId(), this.ride.getGps1(), this.ride.getGps2(), timeToDestination, this.price));
			this.changed = false;
			((SimpleRide) ride).getCustomer().unlock();
			this.getCar().setGps(this.ride.getGps2());
			GUI.print("-- Driver " + this.idD.toString() + " drop out client (" + ride.getCustomer() + "), after the ride " + ride);
		}
	}
	
	/**
     * Effectue un trajet de type Pool.        
     */
	
	public void makePoolRide() {
		if (this.changedPool) {
			this.status = "on-duty";
			Uber uber = Uber.getInstance();
			List<Ride> rides = this.rides;
			for (Customer customer : Uber.orderCustomerStart( this.car.getGps() , rides)) {
				double timeToCustomer = uber.time(this.car.getGps(), customer.getGps());
				try {
					Thread.sleep((long) timeToCustomer);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				customer.setStatus("on-a-ride");
				this.status = "on-a-ride";
				this.car.setGps(customer.getGps());
				GUI.print( "-- Driver " + this.idD.toString() + " pick up client (" + customer.toString() + ") in UberPool");
			}
			for (Ride ride : Uber.orderRidesDestination( this.car.getGps(), this.rides)) {
				double timeToDestination = uber.time(this.car.getGps(), ride.getGps2());
				try {
					Thread.sleep((long) timeToDestination);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ride.getCustomer().setStatus("offline");
				ride.getCustomer().setGps(ride.getGps2());
				this.getCar().setGps(ride.getGps2());
				uber.addRideRegistration(new RideRegistration(this.idD, ride.getCustomer().getId(), this.car.getId(), ride.getGps1(), ride.getGps2(), timeToDestination, ride.getPrice()));
				GUI.print( "-- Driver " + this.idD.toString() + " drop out client (" + ride.getCustomer().toString() + "), after the ride " + ride);
			}
			this.status = "off-duty";
			this.changedPool = false;
		}
	}
	
	public synchronized void acceptRide() {
		Uber.getInstance().getRideDemandLock().lock();
		if (this.car instanceof Van) {
			acceptPoolRide();
			if (!changedPool) {
				acceptSimpleRide();
			}
		}
		else {
			acceptSimpleRide();
		}
		Uber.getInstance().getRideDemandLock().unlock();
	}
	
	public void makeRide() {
		if (this.car instanceof Van) {
			if (changedPool) {
				makePoolRide();
			}
			else {
				makeSimpleRide();
			}
		}
		else {
			makeSimpleRide();
		}
	}
	
	public void run() {
		while (true) {
			acceptRide();
			makeRide();
		}
	}
	
}
