package myUber.Compare;

import java.util.Comparator;

import myUber.Customer;
import myUber.Uber;

public class CustomerPolicy1 implements Comparator<Customer> {

	@Override
	public int compare(Customer o1, Customer o2) {
		Uber uber = Uber.getInstance();
		int n1 = uber.getNumberOfCustomerRides().get(o2.getId());
		int n2 = uber.getNumberOfCustomerRides().get(o1.getId());
		return Integer.compare(n1,n2);
	}
}
