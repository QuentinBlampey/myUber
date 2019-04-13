package myUber.Compare;

import java.util.Comparator;

import myUber.Customer;
import myUber.Uber;

public class CustomerPolicy2 implements Comparator<Customer> {

	@Override
	public int compare(Customer o1, Customer o2) {
		Uber uber = Uber.getInstance();
		double n1 = uber.getMoneySpent().get(o2.getId());
		double n2 = uber.getMoneySpent().get(o1.getId());
		return Double.compare(n1,n2);
	}
}
