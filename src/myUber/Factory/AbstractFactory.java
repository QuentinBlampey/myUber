package myUber.Factory;

import java.util.List;

import myUber.GPS;
import myUber.Car.Car;
import myUber.ID.Id;
import myUber.Ride.Ride;

/**
 * Factory abstraite englobant les factory de voiture et de trajet.    
 */

public abstract class AbstractFactory {
	/**
     * Genere une voiture.    
     * 
     *   @param carName
     *   		Type de la voiture.
     *   
     *   @param gps
     *   		Position de la voiture.
     *   
     *   @param drivers
     *   		Liste de conducteurs pouvant utiliser cette voiture.
     */
	public abstract Car getCar(String carName, GPS gps, List<Id> drivers);
	
	/**
     * Genere un trajet.
     * 
     *  @param rideName
     *  		Type de trajet.
     *  
     *  @param price
     *  		Prix du trajet.
     *  
     *  @param gps1
     *  		Position de depart.
     *  
     *  @param gps2
     *  		Positon d'arrivee.
     */
	public abstract Ride getRide(String rideName, GPS gps1, GPS gps2 );
}
