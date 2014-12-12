package infovisproject;

import java.util.ArrayList;
import processing.core.PApplet;

public class Continent 
{
	PApplet app;
	
	ArrayList<Pays> pays;
	
	int cx, cy;
	int d;
	float start, stop;
	String name;
	
	public Continent(PApplet a, String n)
	{
		app = a;
		
		pays = new ArrayList<Pays>();
		
		name = n;
	}
	
	public Continent(PApplet a, String name, int x, int y, int d, float start, float stop, ArrayList<Pays> payss)
	{
		app = a;
		
		pays = new ArrayList<Pays>(payss);
		this.start = app.radians(start);
		this.stop = app.radians(stop);
		this.d = d;
		cx = x;
		cy = y;
		
		this.name = name;
		
	}
	
	public void draw()
	{	
		if (mouseInside())
			app.fill(0,0,255);
		
		app.arc(cx, cy, d, d, start, stop, app.PIE);
		
		app.fill(255);
	}
	
	public void add(Pays p)
	{
		pays.add(p);
	}
	
	public void setStart(float s) { start = s; }
	public void setStop(float s) { stop = s; }
	
	public boolean mouseInside()
	{	
		return IsPointInsideArc(app.mouseX, app.mouseY, cx, cy, d, start, stop);
	}
	
	boolean IsPointInsideArc(float pointX, float pointY, float centerX, float centerY, float diameter, float angle1, float angle2)
	{
		// Find if the mouse is close enough of center
		boolean nearCenter = app.sqrt(app.sq(pointX - centerX) + app.sq(pointY - centerY)) <= diameter /2 && app.sqrt(app.sq(pointX - centerX) + app.sq(pointY - centerY)) >= diameter /4;
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
