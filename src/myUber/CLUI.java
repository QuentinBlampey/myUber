package myUber;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import myUber.Car.Car;
import myUber.Compare.CustomerPolicy1;
import myUber.Compare.CustomerPolicy2;
import myUber.Compare.DriverPolicy1;
import myUber.Compare.DriverPolicy2;
import myUber.Factory.AbstractFactory;
import myUber.Factory.FactoryProducer;
import myUber.ID.Id;
import myUber.Ride.Ride;


public class CLUI {
	
	protected static double x_waiting;
	protected static double y_waiting;
	protected static Customer customer_waiting;
	protected static Driver driver_waiting;
	
	
	
	public static Driver getDriver_waiting() {
		return driver_waiting;
	}

	public static void setDriver_waiting(Driver driver_waiting) {
		CLUI.driver_waiting = driver_waiting;
	}

	public static List<String> stringToCommand(String choice) {
		List<String> result = new ArrayList<String>();
		GUI.print(">>> " + choice);
		for (String mot : choice.split("[ ]+")) {
			result.add(mot);
		}
		return result;
	}
	
	public static List<String> separateLine(String stringOfFile) {
		List<String> result = new ArrayList<String>();
		for (String ligne : stringOfFile.split("[\n]+")) {
			result.add(ligne);
		}
		return result;
	}
	
	public static void execute(List<String> command) {
	    Uber uber = Uber.getInstance();
	    if (command.get(0).length() == 1) {
	    	customer_waiting.noteDriver(driver_waiting, Integer.parseInt(command.get(0)));
	    	GUI.print("You gave mark " + command.get(0) + " to the driver " + driver_waiting.toString());
	    }
	    else switch (command.get(0))
	    {
	      case "init":
	          CLUI.init(command.get(1));
	          break;
	      case "runtest":
	          CLUI.init(command.get(1));
	          break;
	      case "setup":
	          for (int i=0 ; i < Integer.parseInt(command.get(4)) ; i++) {
	        	  uber.addCustomer( new Customer("Customer", Integer.toString(i)));
	          }
	          AbstractFactory cf = FactoryProducer.getFactory("Car");
	          for (int i=0 ; i < Integer.parseInt(command.get(1)) ; i++) {
	        	  Car car = cf.getCar("standard", new GPS(0,0), new ArrayList<Id>());
	        	  uber.addCar( car );
	        	  Driver driver = new Driver("DriverStandard",Integer.toString(i), car);
	        	  uber.addDriver( driver );
	        	  driver.start();
	          }
	          for (int i=0 ; i < Integer.parseInt(command.get(2)) ; i++) {
	        	  Car car = cf.getCar("berline", new GPS(0,0), new ArrayList<Id>());
	        	  uber.addCar( car );
	        	  Driver driver = new Driver("DriverBerline",Integer.toString(i), car);
	        	  uber.addDriver( driver );
	        	  driver.start();
	          }
	          for (int i=0 ; i < Integer.parseInt(command.get(3)) ; i++) {
	        	  Car car = cf.getCar("van", new GPS(0,0), new ArrayList<Id>());
	        	  uber.addCar( car );
	        	  Driver driver = new Driver("DriverVan",Integer.toString(i), car);
	        	  uber.addDriver( driver );
	        	  driver.start();
	          }
	          break;
	      case "addCustomer":
	         Customer customer = new Customer(command.get(2),command.get(1), new GPS(0,0));
	         uber.addCustomer(customer);
	         GUI.print(uber.getCustomers().toString());
	         break;
	      case "addCarDriver":
	    	  Driver driver = new Driver( command.get(2), command.get(1), command.get(3));
	          uber.addDriver( driver );
	          driver.start();
	          GUI.print(uber.getCars().toString());
	          GUI.print(uber.getDrivers().toString());
	          break;
	      case "addDriver":
	    	  Car car = uber.findCar(command.get(3));
	    	  driver = new Driver( command.get(2), command.get(1), car);
	          uber.addDriver( driver );
	          driver.start();
	          GUI.print(uber.getCars().toString());
	          GUI.print(uber.getDrivers().toString());
	          break;
	      case "setDriverStatus":
	          uber.findDriver(command.get(1),command.get(2)).setStatus(command.get(3));
	          GUI.print(uber.getDrivers().toString());
	          break;
	      case "moveCar":
	          double x = Double.parseDouble(command.get(2));
	          double y = Double.parseDouble(command.get(3));
	          GPS gps = new GPS(x,y);
	          uber.findCar(command.get(1)).setGps(gps);
	          GUI.print(uber.getCars().toString());
	          break;
	      case "moveCustomer":
	          x = Double.parseDouble(command.get(2));
	          y = Double.parseDouble(command.get(3));
	          gps = new GPS(x,y);
	          uber.findCustomer(command.get(1)).setGps(gps);
	          GUI.print(uber.getCustomers().toString());
	          break;
	      case "displayState":
	          GUI.print("State of Uber :");
	          GUI.print("    Cars :");
	          for (Car c : uber.getCars()) {
	        	  GUI.print("               "+c.toString());
	          }
	          GUI.print("    Drivers : ");
	          for (Driver d : uber.getDrivers()) {
	        	  GUI.print("               "+d.toString());
	          }
	          GUI.print("    Customers : ");
	          for (Customer cu : uber.getCustomers()) {
	        	  GUI.print("               "+cu.toString());
	          }
	          break;
	      case "ask4price":
	          x = Double.parseDouble(command.get(2));
	          y = Double.parseDouble(command.get(3));
	          gps = new GPS(x,y);
	          AbstractFactory rf = FactoryProducer.getFactory("Ride");
	          GPS gps1 = uber.findCustomer(command.get(1)).getGps();
	          Ride ride1 = rf.getRide("UberX", gps1 , gps);
	          Ride ride2 = rf.getRide("UberBlack", gps1 , gps);
	          Ride ride3 = rf.getRide("UberVan", gps1 , gps);
	          Ride ride4 = rf.getRide("UberPool", gps1 , gps);
	          double price1 = Price.getLaterPrice(ride1, Integer.parseInt(command.get(4)));
	          System.out.println("UberX price : " + Math.floor(price1*100)/100 + " €");
	          GUI.print("UberBlack price : " + Double.toString(Math.floor(price1*100)/100) + " €");
	          double price2 = Price.getLaterPrice(ride2, Integer.parseInt(command.get(4)));
	          System.out.println("UberBlack price : " + Math.floor(price2*100)/100 + " €");
	          GUI.print("UberBlack price : " + Double.toString(Math.floor(price2*100)/100) + " €");
	          double price3 = Price.getLaterPrice(ride3, Integer.parseInt(command.get(4)));
	          System.out.println("UberVan price : " + Math.floor(price3*100)/100 + " €");
	          GUI.print("UberBlack price : " + Double.toString(Math.floor(price3*100)/100) + " €");
	          double price4 = Price.getLaterPrice(ride4, Integer.parseInt(command.get(4)));
	          System.out.println("UberPool price : " + Math.floor(price4*100)/100 + " €");
	          GUI.print("UberBlack price : " + Double.toString(Math.floor(price4*100)/100) + " €");
	          break;
	      case "simRide":
	          customer = uber.findCustomer(command.get(1));
	          x = Double.parseDouble(command.get(2));
	          y = Double.parseDouble(command.get(3));
	          rf = FactoryProducer.getFactory("Ride");
	          Ride ride = rf.getRide(command.get(5), customer.getGps() , new GPS(x,y));
	          ride.setCustomer(customer);
	          ride.setMark(Integer.parseInt(command.get(6)));
	          //while ((uber.getTime().getTime()) % 24 != Integer.parseInt(command.get(4))) {
	          //}
	          ride.setPrice(Price.getPrice(ride));
	          customer.chooseRide(ride);
	          break;
	      case "simRide_i":
	    	  x = Double.parseDouble(command.get(2));
	          y = Double.parseDouble(command.get(3));
	          gps = new GPS(x,y);
	          rf = FactoryProducer.getFactory("Ride");
	          gps1 = uber.findCustomer(command.get(1)).getGps();
	          ride1 = rf.getRide("UberX", gps1 , gps);
	          ride2 = rf.getRide("UberBlack", gps1 , gps);
	          ride3 = rf.getRide("UberVan", gps1 , gps);
	          ride4 = rf.getRide("UberPool", gps1 , gps);
	          price1 = Price.getLaterPrice(ride1, Integer.parseInt(command.get(4)));
	          GUI.print("UberX price : " + Double.toString(Math.floor(price1*100)/100) + " €");
	          price2 = Price.getLaterPrice(ride2, Integer.parseInt(command.get(4)));
	          GUI.print("UberBlack price : " + Double.toString(Math.floor(price2*100)/100) + " €");
	          price3 = Price.getLaterPrice(ride3, Integer.parseInt(command.get(4)));
	          GUI.print("UberVan price : " + Double.toString(Math.floor(price3*100)/100) + " €");
	          price4 = Price.getLaterPrice(ride4, Integer.parseInt(command.get(4)));
	          GUI.print("UberPool price : " + Double.toString(Math.floor(price4*100)/100) + " €");
	          customer_waiting = uber.findCustomer(command.get(1));
	          x_waiting = x;
	          y_waiting = y;
	          GUI.print("");
	          GUI.print("<<< Ride chosen ?");
	          break;
	      case "UberX":
	          rf = FactoryProducer.getFactory("Ride");
	          ride = rf.getRide("UberX", customer_waiting.getGps() , new GPS(x_waiting,y_waiting));
	          ride.setCustomer(customer_waiting);
	          ride.setPrice(Price.getPrice(ride));
	          customer_waiting.chooseRide(ride);
	          break;
	      case "UberBlack":
	          rf = FactoryProducer.getFactory("Ride");
	          ride = rf.getRide("UberBlack", customer_waiting.getGps() , new GPS(x_waiting,y_waiting));
	          ride.setCustomer(customer_waiting);
	          ride.setPrice(Price.getPrice(ride));
	          customer_waiting.chooseRide(ride);
	          break;
	      case "UberVan":
	          rf = FactoryProducer.getFactory("Ride");
	          ride = rf.getRide("UberVan", customer_waiting.getGps() , new GPS(x_waiting,y_waiting));
	          ride.setCustomer(customer_waiting);
	          ride.setPrice(Price.getPrice(ride));
	          customer_waiting.chooseRide(ride);
	          break;
	      case "UberPool":
	          rf = FactoryProducer.getFactory("Ride");
	          ride = rf.getRide("UberPool", customer_waiting.getGps() , new GPS(x_waiting,y_waiting));
	          ride.setCustomer(customer_waiting);
	          ride.setPrice(Price.getPrice(ride));
	          customer_waiting.chooseRide(ride);
	          break;
	      case "displayDrivers":
	    	  List<Driver> drivers = uber.getDrivers();
	    	  if (command.get(1).equalsIgnoreCase("mostappreciated")) {
	    		  Collections.sort(drivers, new DriverPolicy1() );
	    		  for (Driver d : drivers) {
	    			  GUI.print(d.toString());
	    		  }
	    	  }
	    	  else if (command.get(1).equalsIgnoreCase("mostoccupied")) {
	    		  Collections.sort(drivers, new DriverPolicy2() );
	    		  for (Driver d : drivers) {
	    			  GUI.print(d.toString());
	    		  }
	    	  }
	    	  else {
	    		  GUI.print("xxxxxxxx - Command does not exist - xxxxxxxx");
	    	  }
	          break;
	      case "displayCustomers":
	    	  List<Customer> customers = uber.getCustomers();
	    	  if (command.get(1).equalsIgnoreCase("mostfrequent")) {
	    		  Collections.sort(customers, new CustomerPolicy1() );
	    		  for (Customer c : customers) {
	    			  GUI.print(c.toString());
	    		  }
	    	  }
	    	  else if (command.get(1).equalsIgnoreCase("mostcharged")) {
	    		  Collections.sort(customers, new CustomerPolicy2() );
	    		  for (Customer c : customers) {
	    			  GUI.print(c.toString());
	    		  }
	    	  }
	    	  else {
	    		  GUI.print("xxxxxxxx - Command does not exist - xxxxxxxx");
	    	  }
	          break;
	      case "totalCashed":
	          GUI.print(Double.toString(Math.floor(uber.getTotalMoney()*100)/100) + " € have been cashed by the drivers.");
	          break;
	      case "marks":
	          GUI.print( " Drivers marks : " + uber.getMarks());
	          break;
	      case "numberRides":
	          GUI.print( " Customer rides : " + uber.getNumberOfCustomerRides());
	          break;
	      case "activity":
	          GUI.print(uber.getRateOfActivity() + " , activity rates.");
	          break;
	      case "charge":
	          GUI.print(uber.getMoneySpent() + " , money spent.");
	          break;
	      default:
	        GUI.print("xxxxxxxx - Command does not exist - xxxxxxxx");
	    }
	    GUI.print("");
	  }
	
	public static String loadFile(File f) {
	    try {
	       BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
	       StringWriter out = new StringWriter();
	       int b;
	       while ((b=in.read()) != -1)
	           out.write(b);
	       out.flush();
	       out.close();
	       in.close();
	       return out.toString();
	    }
	    catch (IOException ie)
	    {
	         ie.printStackTrace(); 
	         return "";
	    }
	}
	
	public static void init(String filename) {
		File file = new File(filename);
		String texte = loadFile(file);
		List<String> commands = CLUI.separateLine(texte);
		for (String command : commands) {
			CLUI.execute(CLUI.stringToCommand(command));
		}
	}

}
