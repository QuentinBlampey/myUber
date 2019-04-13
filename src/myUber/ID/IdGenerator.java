package myUber.ID;

/**
 * Generateur d'Id.    
 */

public class IdGenerator {
	
	/**
     * Singleton IdGenerator. 
     */
	private static IdGenerator instance = null;
	
	private Id standard;
	private Id berline;
	private Id van;
	private Id customer;
	private Id driver;
	private Id card;
	
	private IdGenerator() {}
	
	/**
     * Renvoie le singleton IdGenerator ou l'instancie.
     * 
     * @return Le singleton IdGenerator.
     */
	public static IdGenerator getInstance() { 
		if (instance == null) {
			instance = new IdGenerator();
			instance.standard = new Id("standard",-1);
			instance.berline = new Id("berline",-1);
			instance.van = new Id("van",-1);
			instance.customer = new Id("customer",-1);
			instance.driver = new Id("driver",-1);
			instance.card = new Id("card",-1);
		}
		return instance;
	}
	
	protected Id getStandard() {
		this.standard.num += 1;
		return new Id(this.standard.name,this.standard.num);
	}
	
	protected Id getBerline() {
		this.berline.num += 1;
		return new Id(this.berline.name,this.berline.num);
	}
	
	protected Id getVan() {
		this.van.num += 1;
		return new Id(this.van.name,this.van.num);
	}
	
	protected Id getCustomer() {
		this.customer.num += 1;
		return new Id(this.customer.name,this.customer.num);
	}
	
	protected Id getDriver() {
		this.driver.num += 1;
		return new Id(this.driver.name,this.driver.num);
	}
	
	protected Id getCard() {
		this.card.num += 1;
		return new Id(this.card.name,this.card.num);
	}

	
	/**
     * Renvoie un Id pour le type voulu.
     * 
     *   @param name
     *   	Type d'Id voulu.
     *   
     *   @return Un nouvel Id inexistant pour le type d'Id voulu.
     */
	
	public Id getId(String name) {
		if (name.equalsIgnoreCase("standard")) {
			return this.getStandard();
		}
		else if (name.equalsIgnoreCase("berline")) {
			return this.getBerline();
		}
		else if (name.equalsIgnoreCase("van")) {
			return this.getVan();
		}
		else if (name.equalsIgnoreCase("customer")) {
			return this.getCustomer();
		}
		else if (name.equalsIgnoreCase("driver")) {
			return this.getDriver();
		}
		else if (name.equalsIgnoreCase("card")) {
			return this.getCard();
		}
		else {
			throw new RuntimeException("String in argument is not an accepted String value");
		}
	}

}
