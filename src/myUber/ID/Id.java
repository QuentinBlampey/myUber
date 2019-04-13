package myUber.ID;

/**
 * Id compose d'un nom et d'un numero.
 */

public class Id {
	/**
	 * Nom.    
	 */
	protected String name;
	
	/**
	 * Numero.    
	 */
	protected int num;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	@Override
	public String toString() {
		return name + Integer.toString(num);
	}
	
	public Id(String name, int num) {
		super();
		this.name = name;
		this.num = num;
	}
 
}
