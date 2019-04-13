package myUber;

import java.util.List;

/**
 * Classe permettant de moyenner une liste.    
 */

public class Mean {
	
	/**
     * Calcule la moyenne d'une liste.
     * 
     * @param l
     *            Liste a moyenner.
     *            
     * @return Moyenne de la liste l.
     * 
     *         
     */
	
	public static double mean(List<Integer> l) {
		if (l.isEmpty()) {
			return 0;
		}
		else {
			int result = 0;
			for (int i : l) {
				result += i;
			}
			return result / l.size();
		}
	}
}
