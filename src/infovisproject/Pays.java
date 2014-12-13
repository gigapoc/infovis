package infovisproject;

import processing.core.PApplet;

public class Pays 
{
	PApplet app;
	
	int cx, cy;
	int r;
	float start, stop;
	
	String continent, pays;
	float population;
	
	boolean mode;
	
	public Pays (PApplet a, int x, int y, int r1, float sta, float sto, String cont, String p, float po, boolean mode)
	{
		app = a;
		
		cx = x;
		cy = y;
		r = r1;
		start = app.radians(sta);
		stop = app.radians(sto);
		
		continent = cont;
		pays = p;
		population = po;
		
		this.mode = mode;
	}
	
	public void draw()
	{
		app.stroke(0);
		app.arc(cx, cy, r, r, start, stop, app.PIE);
		app.fill(255);
	}
	
	public boolean mouseInside()
	{	
		return IsPointInsideArc(app.mouseX, app.mouseY, cx, cy, r, start, stop);
	}
	
	boolean IsPointInsideArc(float pointX, float pointY, float centerX, float centerY, float diameter, float angle1, float angle2)
	{
		// Find if the mouse is close enough of center
		boolean nearCenter;
		
		if (mode)
			nearCenter = app.sqrt(app.sq(pointX - centerX) + app.sq(pointY - centerY)) <= r /2 && app.sqrt(app.sq(pointX - centerX) + app.sq(pointY - centerY)) >= r /4;
		else
			nearCenter = app.sqrt(app.sq(pointX - centerX) + app.sq(pointY - centerY)) <= r /2;
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
