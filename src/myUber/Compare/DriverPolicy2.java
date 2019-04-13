package myUber.Compare;

import java.util.Comparator;

import myUber.Driver;
import myUber.Uber;

public class DriverPolicy2 implements Comparator<Driver> {

	@Override
	public int compare(Driver o1, Driver o2) {
		Uber uber = Uber.getInstance();
		double n1 = uber.getRateOfActivity().get(o2.getIdD());
		double n2 = uber.getRateOfActivity().get(o1.getIdD());
		return Double.compare(n1,n2);
	}

}
