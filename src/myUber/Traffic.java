package myUber;


/**
 * Classe permettant de calculer le trafic a une heure donnee.    
 */

public class Traffic {
	
	/**
     * Calcul du trafic pour une heure donnee.
     * 
     * @param hour
     *            Heure dans myUber.
     *            
     * @return Trafic (low-traffic, medium-trafic ou heavy-trafic).
     * 
     *         
     */
	
	public static String getTraffic(int hour) {
		String traffic = "";
		double random = Math.random();
		if ((hour >= 22) || (hour < 7)) {
			if (random <= 0.95) {
				traffic = "low-traffic";
			}
			else if (random <= 0.99) {
				traffic = "medium-traffic";
			}
			else {
				traffic = "heavy-traffic";
			}
		}
		else if (hour < 11) {
			if (random <= 0.05) {
				traffic = "low-traffic";
			}
			else if (random <= 0.25) {
				traffic = "medium-traffic";
			}
			else {
				traffic = "heavy-traffic";
			}
		}
		else if (hour < 17) {
			if (random <= 0.15) {
				traffic = "low-traffic";
			}
			else if (random <= 0.85) {
				traffic = "medium-traffic";
			}
			else {
				traffic = "heavy-traffic";
			}
		}
		else {
			if (random <= 0.01) {
				traffic = "low-traffic";
			}
			else if (random <= 0.05) {
				traffic = "medium-traffic";
			}
			else {
				traffic = "heavy-traffic";
			}
		}
		return traffic;
	}
}
