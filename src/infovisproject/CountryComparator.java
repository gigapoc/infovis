package infovisproject;

import java.util.Comparator;

/* private class */
public class CountryComparator implements Comparator<Pays> {
	@Override
	public int compare(Pays p1, Pays p2) {
		return (int) Math.signum(p2.population - p1.population);
	}
	
	public boolean equals(Pays p1, Pays p2) {
		return p1.pays.equals(p2.pays);
	}
}
