package myUber;

import myUber.Ride.Ride;
import myUber.Ride.UberBlack;
import myUber.Ride.UberPool;
import myUber.Ride.UberVan;
import myUber.Ride.UberX;

/**
 * Classe permettant de calculer le prix d'un trajet.    
 */

public class Price {
	/**
     * Tableau de l'enonce pour des km rate.     
     */
	protected static double[][] kmRate = {   { 3.3, 4.2, 1.91, 1.5 },
            								  { 6.2, 5.5, 3.25, 2.6 },
            								  { 2.4, 3, 1.3, 1.1 },
            								  { 6.2, 7.7, 3.25, 2.6 }
        								  };
	
	/**
     * Tableau de l'enonce pour le facteur provenant du trafic.       
     */
	protected static double[][] trafficFactor = { { 1, 1.1,1.5 },
												  { 1, 1.3, 1.5 },
												  { 1, 1.1, 1.2 },
												  { 1, 1.5, 1.8 }
												};
	
	/**
     * Calcule le prix d'un trajet.
     * 
     * @param ride
     *            Trajet en question. 
     *            
     * @return Prix du trajet.
     * 
     *         
     */
	
	public static double getPrice(Ride ride) {
		double distance = ride.getGps1().distanceTo(ride.getGps2());
		String traffic = Uber.getInstance().getTime().getTraffic();
		double factor = 0;
		
		//Selection of the traffic factor:
		if (ride instanceof UberX) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[0][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[0][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[0][2];
			}
		}
		else if (ride instanceof UberBlack) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[1][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[1][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[1][2];
			}
		}
		else if (ride instanceof UberPool) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[2][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[2][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[2][2];
			}
		}
		else if (ride instanceof UberBlack) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[2][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[2][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[2][2];
			}
		}
		else if (ride instanceof UberVan) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[3][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[3][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[3][2];
			}
		}
		
		//Computation :
		if (ride instanceof UberX) {
			if (distance<5) {
				return distance*kmRate[0][0]*factor;
			}
			else if (distance<10 && distance>=5) {
				return distance*kmRate[0][1]*factor;
			}
			else if (distance<20 && distance>=10) {
				return distance*kmRate[0][2]*factor;
			}
			else if (distance>=20) {
				return distance*kmRate[0][3]*factor;
			
			}
		}
		else if (ride instanceof UberBlack) {
			if (distance<5) {
				return distance*kmRate[1][0]*factor;
			}
			else if (distance<10 && distance>=5) {
				return distance*kmRate[1][1]*factor;
			}
			else if (distance<20 && distance>=10) {
				return distance*kmRate[1][2]*factor;
			}
			else if (distance>=20) {
				return distance*kmRate[1][3]*factor;
			}
		}
		else if (ride instanceof UberPool) {
			if (distance<5) {
				return distance*kmRate[2][0]*factor;
			}
			else if (distance<10 && distance>=5) {
				return distance*kmRate[2][1]*factor;
			}
			else if (distance<20 && distance>=10) {
				return distance*kmRate[2][2]*factor;
			}
			else if (distance>=20) {
				return distance*kmRate[2][3]*factor;
			}
		}
		else if (ride instanceof UberVan) {
			if (distance<5) {
				return distance*kmRate[3][0]*factor;
			}
			else if (distance<10 && distance>=5) {
				return distance*kmRate[3][1]*factor;
			}
			else if (distance<20 && distance>=10) {
				return distance*kmRate[3][2]*factor;
			}
			else if (distance>=20) {
				return distance*kmRate[3][3]*factor;
			}
		}
		else {
			throw new RuntimeException("Ride in argument is not a known ride of getPrice method");
		}
		return (double) 0;
	}
	
	public static double getLaterPrice(Ride ride, int hour) {
		double distance = ride.getGps1().distanceTo(ride.getGps2());
		String traffic = Traffic.getTraffic(hour);
		double factor = 0;
		
		//Selection of the traffic factor:
		if (ride instanceof UberX) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[0][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[0][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[0][2];
			}
		}
		else if (ride instanceof UberBlack) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[1][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[1][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[1][2];
			}
		}
		else if (ride instanceof UberPool) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[2][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[2][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[2][2];
			}
		}
		else if (ride instanceof UberBlack) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[2][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[2][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[2][2];
			}
		}
		else if (ride instanceof UberVan) {
			if(traffic == "low-traffic") {
				factor = trafficFactor[3][0];
			}
			else if (traffic == "medium-traffic") {
				factor = trafficFactor[3][1];
			}
			else if (traffic == "heavy-traffic") {
				factor = trafficFactor[3][2];
			}
		}
		
		//Computation :
		if (ride instanceof UberX) {
			if (distance<5) {
				return distance*kmRate[0][0]*factor;
			}
			else if (distance<10 && distance>=5) {
				return distance*kmRate[0][1]*factor;
			}
			else if (distance<20 && distance>=10) {
				return distance*kmRate[0][2]*factor;
			}
			else if (distance>=20) {
				return distance*kmRate[0][3]*factor;
			
			}
		}
		else if (ride instanceof UberBlack) {
			if (distance<5) {
				return distance*kmRate[1][0]*factor;
			}
			else if (distance<10 && distance>=5) {
				return distance*kmRate[1][1]*factor;
			}
			else if (distance<20 && distance>=10) {
				return distance*kmRate[1][2]*factor;
			}
			else if (distance>=20) {
				return distance*kmRate[1][3]*factor;
			}
		}
		else if (ride instanceof UberPool) {
			if (distance<5) {
				return distance*kmRate[2][0]*factor;
			}
			else if (distance<10 && distance>=5) {
				return distance*kmRate[2][1]*factor;
			}
			else if (distance<20 && distance>=10) {
				return distance*kmRate[2][2]*factor;
			}
			else if (distance>=20) {
				return distance*kmRate[2][3]*factor;
			}
		}
		else if (ride instanceof UberVan) {
			if (distance<5) {
				return distance*kmRate[3][0]*factor;
			}
			else if (distance<10 && distance>=5) {
				return distance*kmRate[3][1]*factor;
			}
			else if (distance<20 && distance>=10) {
				return distance*kmRate[3][2]*factor;
			}
			else if (distance>=20) {
				return distance*kmRate[3][3]*factor;
			}
		}
		else {
			throw new RuntimeException("Ride in argument is not a known ride of getPrice method");
		}
		return (double) 0;
	}
}
