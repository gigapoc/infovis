package infovisproject;

import java.awt.Color;
import java.util.TreeMap;

import processing.core.PApplet;

public class Continent 
{
	PApplet app;
	
	TreeMap<Pays, Float> pays;
	
	int cx, cy;
	int d;
	float start, stop;
	String name;
	
	Color color;
	
	
	public Continent(PApplet a, String name, TreeMap<Pays, Float> payss)
	{
		app = a;
		this.name = name;
		pays = payss;
		
		color = Colors.getColor(name);
	}
	
	public Continent(PApplet a, String name, int x, int y, int d)
	{
		app = a;
		this.name = name;
		cx = x;
		cy = y;
		this.d = d;
		
		pays = new TreeMap<Pays, Float>(new CountryComparator());
		
		color = Colors.getColor(name);
	}
	
	public Continent(PApplet a, String name, int x, int y, int d, float start, float stop, TreeMap<Pays, Float> payss)
	{
		app = a;
		
		pays = payss; // new TreeMap<Pays, Float>(payss);
		this.start = app.radians(start);
		this.stop = app.radians(stop);
		this.d = d;
		cx = x;
		cy = y;
		
		this.name = name;
		
		//Colors
		color = Colors.getColor(name);
		
	}
	
	public float getPopulation()
	{
		float pop = 0;
		for (Float population: pays.values())
			if (!Float.isNaN(population))
				pop += population;
		return pop;
	}
	
	public void prepareContinent(int deg)
	{
		float rat = deg / getPopulation();
		float degCurr = 0;
		float degPays = 0;
		
		for (Pays p : pays.keySet())
		{
			degPays = p.population * rat;
			p.start = degCurr;
			p.stop = degPays + degCurr;
			degCurr += degPays;
		}
	}
	
	public void prepareContinent(float start, float stop)
	{
		float rat = (stop-start) / getPopulation();
		float degCurr = start;
		float degPays;
		
		for (Pays p : pays.keySet())
		{
			if (!Float.isNaN(p.population))
			{
				degPays = p.population* rat;
				p.start = degCurr;
				p.stop = degCurr + degPays;
				degCurr += degPays;
			}
		}
	}
	
	public void draw()
	{	
		app.strokeWeight(1);
		app.stroke(0);
		app.arc(cx, cy, d, d, start, stop, app.PIE);
		
		int opacite = Colors.opacite;
		if (mouseInside())
			opacite = Colors.opaciteHover;
		app.fill(color.getRed(), color.getGreen(), color.getBlue(), opacite);
		app.arc(cx, cy, d*2, d*2, start, stop, app.PIE);
		app.fill(255);

		float angle = (start + stop)/2;
		float r = d/3;
		float x = r * app.cos(angle) - app.textWidth(name)/2;
		float y = r * app.sin(angle)+5;
		
		if (name.equals("africa"))
			app.fill(255);
		else
			app.fill(0);
		
		app.text(Data.capitalize(name), cx+x, cy+y);
		
		app.strokeWeight(0.2f);
		app.fill(255);
	}
	
	public void add(Pays p)
	{
		pays.put(p, p.population);
	}
	
	
	/*public void sortByPop()
	{
		TreeMap<Pays, Float> newMap = new TreeMap<Pays, Float>();
		ArrayList<Float> sorted = new ArrayList<Float>();
		
		for (Pays p : pays.keySet())
		{
			sorted.add(p.population);
		}
		Collections.sort(sorted);
		
		for (Float f : sorted)
		{
			for (Pays p : pays.keySet())
				if (p.population == f)
				{
					newMap.put(p, p.population);
					break;
				}
		}
		pays.clear();
		pays = newMap;
	}*/
	
	
	public void setStart(float s) { start = s; }
	public void setStop(float s) { stop = s; }
	
	public boolean mouseInside()
	{	
		return IsPointInsideArc(app.mouseX, app.mouseY, cx, cy, d, start, stop);
	}
	
	boolean IsPointInsideArc(float pointX, float pointY, float centerX, float centerY, float diameter, float angle1, float angle2)
	{
		// Find if the mouse is close enough of center
		float dist = app.sqrt(app.sq(pointX - centerX) + app.sq(pointY - centerY));
		boolean nearCenter = dist <= diameter /2 && dist >= diameter /4;
		if (!nearCenter)
			return false; // Quick exit...
	
		// Normalize angles
		float na1 = normalizeAngle(angle1);
		float na2 = normalizeAngle(angle2);
		// Find the angle between the point and the x axis from the center of the circle
		float a = normalizeAngle(app.atan2(pointY - centerY, pointX - centerX));
	
		boolean between;
		// First case: small arc, below half of circle
		if (na1 < na2)
		{
			// Just check we are between these two angles
			between = na1 <= a && a <= na2;
		}
		else  // Second case: wide arc, more than half of circle
		{
			// Check we are NOT in the remaining (empty) area...
			between = !(na2 <= a && a <= na1);
		}
		return between;
	}
	
	float normalizeAngle(float angle)
	{
	  // First, limit it between -2*PI and 2*PI, using modulo operator
	  float na = angle % (2 * app.PI);
	  // If the result is negative, bring it back to 0, 2*PI interval
	  if (na < 0) na = 2*app.PI + na;
	  return na;
	}
	
}
