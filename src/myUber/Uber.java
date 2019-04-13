package myUber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myUber.Car.Car;
import myUber.ID.Id;
import myUber.Ride.Ride;
import myUber.Ride.SimpleRide;
import myUber.Ride.UberBlack;
import myUber.Ride.UberPool;
import myUber.Ride.UberVan;
import myUber.Ride.UberX;

/**
 * myUber core : classe au coeur du projet, observable faisant le lien entre les clients et les conducteurs et stockant les statistiques.
 * @author Clement Di Domenico et Quentin Blampey
 */

public class Uber {
	/**
     * Singleton Uber.        
     */
	private static Uber instance = null;
	
	/**
     * Liste de conducteurs.        
     */
	protected List<Driver> drivers;
	
	/**
     * Liste de clients.        
     */
	protected List<Customer> customers;
	
	/**
     * Liste de voitures.        
     */
	protected List<Car> cars;
	
	/**
     * Liste des demandes de trajets non encore traitees.        
     */
	protected RideDemand rideDemand;
	
	protected List<Ride> rideDemandX;
	protected List<Ride> rideDemandBlack;
	protected List<Ride> rideDemandVan;
	protected List<Ride> rideDemandPool;
	
	public List<Ride> getRideDemandX() {
		return rideDemandX;
	}

	public void setRideDemandX(List<Ride> rideDemandX) {
		this.rideDemandX = rideDemandX;
	}

	public List<Ride> getRideDemandBlack() {
		return rideDemandBlack;
	}

	public void setRideDemandBlack(List<Ride> rideDemandBlack) {
		this.rideDemandBlack = rideDemandBlack;
	}

	public List<Ride> getRideDemandVan() {
		return rideDemandVan;
	}

	public void setRideDemandVan(List<Ride> rideDemandVan) {
		this.rideDemandVan = rideDemandVan;
	}

	public List<Ride> getRideDemandPool() {
		return rideDemandPool;
	}

	public void setRideDemandPool(List<Ride> rideDemandPool) {
		this.rideDemandPool = rideDemandPool;
	}

	/**
     * Liste des sauvegardes de trajets effectues.        
     */
	protected List<RideRegistration> rideRegistrations;
	
	/**
     * Map associant pour chaque conducteur sa liste de note.        
     */
	protected Map<Id, List< Integer >> marks;
	
	/**
     * Map associant pour chaque conducteur l'argent gagne.        
     */
	protected Map<Id, Double > moneyCashed;
	
	/**
     * Map associant pour chaque client l'argent depense.        
     */
	protected Map<Id, Double > moneySpent;
	
	/**
     * Map associant pour chaque conducteur le nombre de trajets effectue.        
     */
	protected Map<Id, Integer > numberOfRides;
	
	/**
     * Map associant pour chaque client le nombre de trajets effectue.        
     */
	protected Map<Id, Integer > numberOfCustomerRides;
	
	/**
     * Map associant pour chaque conducteur son ratio d'activite.        
     */
	protected Map<Id, Double > rateOfActivity;
	
	public RideDemand getRideDemandLock() {
		return rideDemand;
	}
	
	public Map<Id, Double> getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(Map<Id, Double> moneySpent) {
		this.moneySpent = moneySpent;
	}

	public Map<Id, Integer> getNumberOfCustomerRides() {
		return numberOfCustomerRides;
	}

	public void setNumberOfCustomerRides(Map<Id, Integer> numberOfCustomerRides) {
		this.numberOfCustomerRides = numberOfCustomerRides;
	}

	public Map<Id, Double> getRateOfActivity() {
		return rateOfActivity;
	}

	public void setRateOfActivity(Map<Id, Double> rateOfActivity) {
		this.rateOfActivity = rateOfActivity;
	}

	public Id getMostFrequentCustomer() {
		return mostFrequentCustomer;
	}

	public void setMostFrequentCustomer(Id mostFrequentCustomer) {
		this.mostFrequentCustomer = mostFrequentCustomer;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	/**
     * Argent total gagne par Uber.        
     */
	protected double totalMoney;
	
	/**
     * Nombre total de trajets.      
     */
	protected int totalRides;
	
	/**
     * Id du client le plus frequent.        
     */
	protected Id mostFrequentCustomer;
	
	/**
     * Id du client ayant le plus paye.        
     */
	protected Id mostChargedCustomer;
	
	/**
     * Id du conducteur le mieux note.        
     */
	protected Id mostAppreciatedDriver;
	
	/**
     * Id du conducteur ayant le plus faible ration d'activite.        
     */
	protected Id leastOccupiedDriver;
	
	protected Time time;
	
	/**
     * Ajoute une sauvegarde d'un trajet effectue et met à jour les statistiques.
     * 
     * @param rg
     *            RideRegistration (sauvegarde d'un trajet effectue)
     *           
     *         
     */
	
	public void addRideRegistration(RideRegistration rg) {
		this.rideRegistrations.add(rg);
		this.moneyCashed.put(rg.getDriverId() , this.moneyCashed.get(rg.getDriverId()) + rg.getPrice());
		this.moneySpent.put(rg.getCustomerId() , this.moneySpent.get(rg.getCustomerId()) + rg.getPrice());
		this.numberOfRides.put(rg.getDriverId() , this.numberOfRides.get(rg.getDriverId()) + 1);
		this.numberOfCustomerRides.put(rg.getCustomerId() , this.numberOfCustomerRides.get(rg.getCustomerId()) + 1);
		this.totalMoney += rg.getPrice();
		this.totalRides += 1;
		
		if (this.mostFrequentCustomer == null) {
			this.mostFrequentCustomer = rg.getCustomerId();
		}
		else if (this.numberOfCustomerRides.get(rg.getCustomerId()) > this.numberOfCustomerRides.get(this.mostFrequentCustomer) ) {
			this.mostFrequentCustomer = rg.getCustomerId();
		}
		
		if (this.leastOccupiedDriver == null) {
			this.leastOccupiedDriver = rg.getDriverId();
		}
		if (this.rateOfActivity.get(rg.getDriverId()) < this.rateOfActivity.get(this.leastOccupiedDriver) ) {
			this.leastOccupiedDriver = rg.getDriverId();
		}
		
		double hour = this.time.getTime();
		for (Driver d : this.drivers) {
			double totalRide = 0;
			for (RideRegistration r : this.rideRegistrations) {
				if (r.getDriverId() == d.getIdD()) {
					totalRide += r.getDuration();
				}
				if (hour == 0) {
					this.rateOfActivity.put(d.getIdD(), (double) 1);
				}
				else {
					this.rateOfActivity.put(d.getIdD(),totalRide / hour / 10 / Time.timeUnit);
				}
			}
		}
		
		if (this.mostAppreciatedDriver == null) {
			this.mostChargedCustomer = rg.getDriverId();
		}
		else {
			for (Driver d : this.drivers) {
				if (this.marks.containsKey(d.getIdD())) {
					if (Mean.mean(this.marks.get(d.getIdD())) > Mean.mean(this.marks.get(this.mostAppreciatedDriver)) ) {
						this.mostAppreciatedDriver = d.getIdD();
					}
				}
			}
		}
		
		if (this.leastOccupiedDriver == null) {
			this.leastOccupiedDriver = rg.getDriverId();
		}
		else if (this.rateOfActivity.get(rg.getDriverId()) < this.rateOfActivity.get(this.leastOccupiedDriver) ) {
			this.leastOccupiedDriver = rg.getDriverId();
		}

		
	}
	
	public Map<Id, Double> getMoneyCashed() {
		return moneyCashed;
	}

	public void setMoneyCashed(Map<Id, Double> moneyCashed) {
		this.moneyCashed = moneyCashed;
	}

	public Map<Id, Integer> getNumberOfRides() {
		return numberOfRides;
	}

	public void setNumberOfRides(Map<Id, Integer> numberOfRides) {
		this.numberOfRides = numberOfRides;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getTotalRides() {
		return totalRides;
	}

	public void setTotalRides(int totalRides) {
		this.totalRides = totalRides;
	}

	public Id getMostChargedCustomer() {
		return mostChargedCustomer;
	}

	public void setMostChargedCustomer(Id mostChargedCustomer) {
		this.mostChargedCustomer = mostChargedCustomer;
	}

	public Id getMostAppreciatedDriver() {
		return mostAppreciatedDriver;
	}

	public void setMostAppreciatedDriver(Id mostAppreciatedDriver) {
		this.mostAppreciatedDriver = mostAppreciatedDriver;
	}

	public Id getLeastOccupiedDriver() {
		return leastOccupiedDriver;
	}

	public void setLeastOccupiedDriver(Id leastOccupiedDriver) {
		this.leastOccupiedDriver = leastOccupiedDriver;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}
	
	public void addDriver(Driver d) {
		this.drivers.add(d);
		this.marks.put(d.getIdD(), new ArrayList< Integer >());
		this.moneyCashed.put(d.getIdD(), 0.0);
		this.numberOfRides.put(d.getIdD(), 0);
		this.rateOfActivity.put(d.getIdD(), 0.0);
	}
	
	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}

	public List<Customer> getCustomers() {
		return customers;
	}
	
	public void addCustomer(Customer c) {
		this.customers.add(c);
		this.moneySpent.put(c.getId(), 0.0);
		this.numberOfCustomerRides.put(c.getId(), 0);
	}
	
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public List<Ride> getRideDemand() {
		return rideDemand.getRideDemand();
	}
	
	public List<RideRegistration> getRideRegistrations() {
		return rideRegistrations;
	}

	public void setRideRegistrations(List<RideRegistration> rideRegistrations) {
		this.rideRegistrations = rideRegistrations;
	}
	
	public Map< Id, List<Integer>> getMarks() {
		return marks;
	}

	public void setMarks(Map<Id, List<Integer>> marks) {
		this.marks = marks;
	}
	
	
	
	
	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Uber [drivers=" + drivers.toString() + ", customers=" + customers.toString() + ", cars=" + cars.toString() + ", rideDemand=" + rideDemand
				+ ", rideRegistrations=" + rideRegistrations.toString() + ", marks=" + marks + "]";
	}
	
	/**
     * Recupere le singleton Uber ou l'instancie.
     * 
     *     @return Le singleton Uber.    
     */
	
	public static Uber getInstance() { 
		if (instance==null) {
			instance = new Uber();
			instance.cars = new ArrayList<Car>();
			instance.drivers = new ArrayList<Driver>();
			instance.customers = new ArrayList<Customer>();
			instance.rideDemand = new RideDemand(new ArrayList<Ride>());
			instance.rideDemandX = new ArrayList<Ride>();
			instance.rideDemandBlack = new ArrayList<Ride>();
			instance.rideDemandVan = new ArrayList<Ride>();
			instance.rideDemandPool = new ArrayList<Ride>();
			instance.rideRegistrations = new ArrayList< RideRegistration >();
			instance.marks = new HashMap<Id,List< Integer >>();
			instance.time = new Time(0);
			instance.time.start();
			instance.moneyCashed = new HashMap<Id, Double >();
			instance.moneySpent = new HashMap<Id, Double >();
			instance.rateOfActivity = new HashMap<Id, Double >();
			instance.numberOfRides = new HashMap<Id, Integer >();
			instance.numberOfCustomerRides = new HashMap<Id, Integer >();
			instance.totalMoney = 0.0;
			instance.totalRides = 0;
		}
		return instance;
	}
	
	/**
     * Notifie un client des trajets possible pour sa demande.
     * 
     * @param customer
     *            Client ayant fait la demande.
     *            
     * @param rides
     *            Liste de trajets possibles.
     * 
     *         
     */
	
	public void notifyCustomer(Customer customer, List<Ride> rides) {
		customer.setMessageBox(rides);
	}
	
	/**
     * Calcul du temps de trajet entre 2 positions donnees.
     * 
     * @param gps1
     *            Position de depart.
     *            
     * @param gps2
     *            Position d'arrivee.
     * 
     * @return Temps de trajet.
     *         
     */
	
	public double time(GPS gps1, GPS gps2) {
		double distance = gps1.distanceTo(gps2);
		String traffic = time.getTraffic();
		double vitesse = 0;
		if (traffic.equals("low-traffic")) {
			vitesse = 15;
		}
		else if (traffic.equals("medium-traffic")) {
			vitesse = 7.5;
		}
		else {
			vitesse = 3;
		}
		return Time.timeUnit * 1000 * distance / vitesse; 
	}
	
	/**
     * Calcul des propositions de trajets et notification du client.
     * 
     * @param gps
     *            Destination demandee par le client
     *            
     * @param seats
     *            Nombre de sieges demandes par le client.
     * 
     * @param customer
     *            Client.
     *         
     */
	
	public void sendProposal(GPS gps, int seats, Customer customer) {
		SimpleRide black = new UberBlack(customer.getGps(), gps); 
		black.setPrice(Price.getPrice(black));
		black.setCustomer(customer);
		
		Ride pool = new UberPool( customer.getGps(), gps);
		pool.setPrice(Price.getPrice(pool));
		pool.setCustomer(customer);
		
		SimpleRide van = new UberVan( customer.getGps(), gps);
		van.setPrice(Price.getPrice(van));
		van.setCustomer(customer);
		
		SimpleRide x = new UberX( customer.getGps(), gps);
		x.setPrice(Price.getPrice(x));
		x.setCustomer(customer);
		
		notifyCustomer(customer, Arrays.asList(black,pool,van,x));
	}
	
	/**
     * Trouve la position la plus proche d'un point de depart.
     * 
     * @param start
     *            Position de l'Uber a l'instant initial (au depart).
     * 
     * @param destinations
     *            Liste des position.
     *         
     * 
     * @return Position de la plus proche destination.
     */
	
	public static GPS closest(GPS start, List<GPS> destinations) {
		GPS result = destinations.get(0);
		double minDistance = start.distanceTo(result);
		for (GPS gps : destinations) {
			double distance = start.distanceTo(gps); 
			if (distance < minDistance) {
				minDistance = distance;
				result = gps;
			}
		}
		return result;
	}
	
	/**
     * Trouve le plus proche client.
     * 
     * @param start
     *            Position de l'Uber a l'instant initial (au depart).
     *            
     * @param rides
     *            Liste des trajets demandes par les clients.        
     * 
     * @return Client le plus proche de start.
     */
	
	public static Customer closestCustomer(GPS start, List<Ride> rides) {
		Customer result = rides.get(0).getCustomer();
		double minDistance = start.distanceTo(rides.get(0).getGps1());
		for (Ride ride : rides) {
			double distance = start.distanceTo(ride.getGps1()); 
			if (distance < minDistance) {
				minDistance = distance;
				result = ride.getCustomer();
			}
		}
		return result;
	}
	
	/**
     * Ordonne la liste des destinations dans l'ordre des plus proches destinations.
     * 
     * @param start
     *            Position de l'Uber a l'instant initial (au depart).
     * 
     * @param destinations
     *            Liste des position pour deposer les clients.
     *         
     * 
     * @return Liste des positions (destinations) dans le bon ordre.
     */
	
	public static List<GPS> orderList(GPS start, List<GPS> destinations) {
		List<GPS> result = new ArrayList<GPS>();
		while (! destinations.isEmpty()) {
			GPS closest = closest(start, destinations);
			result.add(closest);
			destinations.remove(closest);
		}
		return result;
	}
	
	/**
     * Calcul de la distance que devra effectuer un UberPool apres avoir depose ses clients.
     * 
     * @param start
     *            Position de l'Uber a l'instant initial.
     *           
     * 
     * @param destinations
     *            Liste des position pour deposer les clients.
     *         
     * 
     * @return Distance voulue.
     */
	
	public static double distanceOrder(GPS start, List<GPS> destinations) {
		double distance = 0;
		for (GPS gps : destinations) {
			distance += start.distanceTo(gps);
			start = gps;
		}
		return distance;
	}
	
	/**
     * Calcul de la distance que devra effectuer un UberPool.
     * 
     * @param start
     *            Position de l'Uber a l'instant initial (au depart).
     *            
     * @param starts
     *            Liste des position pour recuperer les clients.
     * 
     * @param destinations
     *            Liste des position pour deposer les clients.
     *         
     * 
     * @return Distance voulue.
     */
	
	public static double poolDistance(GPS start, List<GPS> starts, List<GPS> destinations) {
		List<GPS> starts2 = new ArrayList<GPS>();
		for (GPS gps : starts) {
			starts2.add(gps);
		}
		List<GPS> order = orderList(start , starts2);
		List<GPS> destinations2 = new ArrayList<GPS>();
		for (GPS gps : destinations) {
			destinations2.add(gps);
		}
		List<GPS> orderDestinations = orderList(order.get(order.size()-1) , destinations2);
		for (GPS destination : orderDestinations) {
			order.add(destination);
		}
		return distanceOrder(start, order);
	}
	
	
	/**
     * Decrit a un UberPool dans quel ordre prendre ses clients.
     * 
     * @param start
     *            Position de l'Uber a l'instant initial (au depart).
     *            
     * @param rides
     *            Liste des trajets demandes par ses clients.
     * 
     * @return Liste de client dans l'ordre des plus proches clients.
     */
	public static List<Customer> orderCustomerStart(GPS start, List<Ride> rides) {
		List<Customer> result = new ArrayList<Customer>();
		GPS lastGPS = start;
		List<Ride> rides2 = new ArrayList<Ride>();
		for (Ride r : rides) {
			rides2.add(r);
		}
		for (int i = 0 ; i < rides.size() ; i++ ) {
			Customer c = closestCustomer( lastGPS , rides2);
			result.add(c);
			for (Ride r : rides2) {
				if (r.getCustomer().getId().getNum() == c.getId().getNum()) {
					rides2.remove(r);
					break;
				}
			}
			lastGPS = c.getGps();
		}
		return result;
	}
	
	/**
     * Decrit a un UberPool dans quel ordre deposer ses clients.
     * 
     * @param start
     *            Position de l'Uber a l'instant initial (apres avoir depose ses clients).
     *            
     * @param rides
     *            Liste des trajets demandes par ses clients.
     * 
     * @return Liste des trajets dans l'ordre des plus proches destinations.
     */
	public static List<Ride> orderRidesDestination(GPS start, List<Ride> rides) {
		List<Ride> result = new ArrayList<Ride>();
		GPS lastGPS = start;
		List<Ride> rides2 = new ArrayList<Ride>();
		for (Ride r : rides) {
			rides2.add(r);
		}
		for (int i = 0 ; i < rides.size() ; i++) {
			Ride r = closestRideDestination( lastGPS , rides2);
			result.add(r);
			rides2.remove(r);
			lastGPS = r.getGps1();
		}
		return result;
	}
	
	/**
     * Trouve le trajet dont la destination est la plus proche de l'argument start.
     * 
     * @param start
     *            Position GPS de départ.
     *            
     * @param rides
     *            Liste de trajets.
     * 
     * @return Le trajet dont la distination est la plus proche de start.
     */
	public static Ride closestRideDestination(GPS start, List<Ride> rides) {
		Ride result = rides.get(0);
		double minDistance = start.distanceTo(rides.get(0).getGps2());
		for (Ride ride : rides) {
			double distance = start.distanceTo(ride.getGps2()); 
			if (distance < minDistance) {
				minDistance = distance;
				result = ride;
			}
		}
		return result;
	}
	
	public Car findCar(String id) {
		for (Car car : cars) {
			if (car.getId().toString().equalsIgnoreCase(id)) {
				return car;
			}
		}
		throw new RuntimeException("Car not found");
	}
	
	public Customer findCustomer(String id) {
		for (Customer customer : customers) {
			if (customer.getId().toString().equalsIgnoreCase(id)) {
				return customer;
			}
		}
		throw new RuntimeException("Customer not found");
	}
	
	public Driver findDriver(String name, String surname) {
		for (Driver driver : drivers) {
			if (driver.getName().equalsIgnoreCase(name) && driver.getSurname().equalsIgnoreCase(surname)) {
				return driver;
			}
		}
		throw new RuntimeException("Driver not found");
	}
	
	public void addCar(Car car) {
		cars.add(car);
	}
}
