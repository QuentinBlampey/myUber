package myUber.Compare;

import java.util.Comparator;

import myUber.Driver;
import myUber.Mean;
import myUber.Uber;

public class DriverPolicy1 implements Comparator<Driver> {

	@Override
	public int compare(Driver o1, Driver o2) {
		Uber uber = Uber.getInstance();
		double n1 = Mean.mean(uber.getMarks().get(o2.getIdD()));
		double n2 = Mean.mean(uber.getMarks().get(o1.getIdD()));
		return Double.compare(n1,n2);
	}

}
