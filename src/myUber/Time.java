package myUber;

/**
 * Thread definissant le temps dans l'application. 
 */

public class Time extends Thread {
	
	/**
     * timeUnit correspond dans la realite a une heure. Si timeUnit = 10, en 10 secondes il se passera 1h dans Uber.       
     */
	protected static int timeUnit = 10;
	protected int time;
	protected String traffic;
	
	
	
	
	public String getTraffic() {
		return traffic;
	}



	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}



	public int getTime() {
		return time;
	}



	public void setTime(int time) {
		this.time = time;
	}


	public Time(int time) {
		super();
		this.time = time;
		this.traffic = Traffic.getTraffic(time);
	}



	public void run() {
		while (true) {
			try {
				Thread.sleep(Time.timeUnit*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.time++;
			GUI.print("");
			GUI.print( "[ Hour changed to " + time % 24 + " ]");
			GUI.print("");
			this.traffic = Traffic.getTraffic(time);
		}
	}
}
