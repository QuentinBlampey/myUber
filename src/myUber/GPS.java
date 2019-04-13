package myUber;

/**
 * Classe definissant une position GPS.    
 */

public class GPS {
	/**
     * Position x dans le plan.       
     */
	protected double x;
	
	/**
     * Position y dans le plan.       
     */
	protected double y;
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "( " + x + " , " + y + " )";
	}
	
	/**
     * Calcule la distance de cette position a une autre position GPS.
     * 
     * @param gps
     *            Position GPS.
     *            
     * @return Distance entre cette position et la position GPS en argument.
     * 
     *         
     */
	
	public double distanceTo(GPS gps) {
		return Math.sqrt((x-gps.getX())*(x-gps.getX()) + (y-gps.getY())*(y-gps.getY()) );
	}
	
	public GPS(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
}
