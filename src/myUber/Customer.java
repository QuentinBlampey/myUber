package myUber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import myUber.ID.Id;
import myUber.ID.IdGenerator;
import myUber.Ride.Ride;
import myUber.Ride.UberBlack;
import myUber.Ride.UberPool;
import myUber.Ride.UberVan;
import myUber.Ride.UberX;

/**
 * Lock definissant un client.   
 */

@SuppressWarnings("serial")
public class Customer extends ReentrantLock {
	protected String name;
	protected String surname;
	protected String status;
	protected Id id;
	protected GPS gps;
	protected Id cardNumber;
	/**
     * Liste des trajets proposes par Uber.
     */
	protected List<Ride> messageBox;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		this.id = id;
	}
	public GPS getGps() {
		return gps;
	}
	public void setGps(GPS gps) {
		this.gps = gps;
	}
	public Id getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(Id cardNumber) {
		this.cardNumber = cardNumber;
	}
	public List<Ride> getMessageBox() {
		return messageBox;
	}
	
	
	
	public Customer(String name, String surname, GPS gps) {
		super();
		this.name = name;
		this.surname = surname;
		this.gps = gps;
		this.status = "offline";
		IdGenerator ig = IdGenerator.getInstance();
		this.id = ig.getId("Customer");
		this.cardNumber = ig.getId("card");
		this.messageBox = new ArrayList<Ride>();
	}
	
	
	
	public Customer(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
		this.gps = new GPS(0,0);
		this.status = "offline";
		IdGenerator ig = IdGenerator.getInstance();
		this.id = ig.getId("Customer");
		this.cardNumber = ig.getId("card");
		this.messageBox = new ArrayList<Ride>();
	}
	@Override
	public String toString() {
		return name + " " + surname + ", " + status + ", in position" + gps.toString();
	}
	public void setMessageBox(List<Ride> messageBox) {
		this.messageBox = messageBox;
	}
	
	/**
     * Demande un trajet.
     * @param gps
     * 			Position d'arrivee.
     * 
     * @param seats
     * 			Nombre de places demandees.
     */
	
	public void bookRide(GPS gps, int seats) {
		if (this.status == "offline") {
			Uber uber = Uber.getInstance();
			uber.sendProposal(gps, seats, this);
		}
	}
	
	/**
     * Choisi un trajet parmi ceux propose par myUber.
     * 
     *       @param n
     *       		Numéro du trajet choisi.
     */
	
	public void chooseAndPayRide(int n) {
		if (this.status == "offline") {
			Ride ride = this.messageBox.get(n);
			Uber uber = Uber.getInstance();
			uber.getRideDemand().add(ride);
			this.status = "waiting-uber";
		}
	}
	
	public void chooseRide(Ride ride) {
		if (this.status == "offline") {
			Uber uber = Uber.getInstance();
			uber.getRideDemand().add(ride);
			if (ride instanceof UberX) {
				uber.getRideDemandX().add(ride);
			}
			else if (ride instanceof UberBlack) {
				uber.getRideDemandBlack().add(ride);
			}
			else if (ride instanceof UberVan) {
				uber.getRideDemandVan().add(ride);
			}
			else if (ride instanceof UberPool) {
				uber.getRideDemandPool().add(ride);
			}
			this.status = "waiting-uber";
		}
		else {
			GUI.print(this.toString() + " is already occupied...");
		}
	}
	
	/**
     * Note un conducteur.
     * 
     *    @param driver
     *    			Conducteur.
     *    @param mark
     *    			Note pour ce conducteur.
     */
	
	public void noteDriver(Driver driver, int mark) {
		Uber uber = Uber.getInstance();
		uber.getMarks().get(driver.getIdD()).add(mark);
	}
	
}

