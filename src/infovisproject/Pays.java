package infovisproject;

import java.awt.Color;
import java.util.Random;

import processing.core.PApplet;

public class Pays 
{
	PApplet app;
	
	int cx, cy;
	int r;
	float start, stop;
	
	String continent, pays;
	
	float GDPAgri, GDPIndustry, GDPServices, childrenPerWoman, co2Emissions, democracy, energyUse, poverty125, GDPPerCapital, GDPTotal, HDI;
	float lifeExpectancy, murders, density, population, poverty2, workingHours;
	boolean mode;
	
	Color color;
	
	
	
	public Pays(PApplet app, String continent, String pays, float gDPAgri,
			float gDPIndustry, float gDPServices, float childrenPerWoman,
			float co2Emissions, float democracy, float energyUse,
			float poverty125, float gDPPerCapital, float gDPTotal, float hDI,
			float lifeExpectancy, float murders, float density,
			float population, float poverty2, float workingHours) {
		
		this.app = app;
		this.continent = continent;
		this.pays = pays;
		GDPAgri = gDPAgri;
		GDPIndustry = gDPIndustry;
		GDPServices = gDPServices;
		this.childrenPerWoman = childrenPerWoman;
		this.co2Emissions = co2Emissions;
		this.democracy = democracy;
		this.energyUse = energyUse;
		this.poverty125 = poverty125;
		GDPPerCapital = gDPPerCapital;
		GDPTotal = gDPTotal;
		HDI = hDI;
		this.lifeExpectancy = lifeExpectancy;
		this.murders = murders;
		this.density = density;
		this.population = population;
		this.poverty2 = poverty2;
		this.workingHours = workingHours;
		
		color = Colors.getColor(continent);
	}

	public Pays(PApplet a, String name, String continentName)
	{
		app = a;
		this.pays = name;
		continent = continentName;
		color = Colors.getColor(continent);
	}
	
	public Pays (PApplet a, int x, int y, int r1, float stop, String cont, String p, float po, boolean mode)
	{
		app = a;
		
		cx = x;
		cy = y;
		r = r1;
		// start = app.radians(sta);
		this.stop = app.radians(stop);
		
		continent = cont;
		pays = p;
		population = po;
		
		this.mode = mode;
		
		color = Colors.getColor(continent);
	}
	
	public void draw(boolean contMode)
	{
		app.stroke(255);
		app.strokeWeight(1f);
		app.fill(color.getRed(), color.getGreen(), color.getBlue(), 200);
		app.arc(cx, cy, r, r, start, stop, app.PIE);
		if (Math.abs(stop-start) > 0.04)
		{
			float angle = (start + stop)/2;
			float d = r/3 + (angle * 20);
			
			float x = d * app.cos(angle) - app.textWidth(pays)/2;
			float y = d * app.sin(angle);
			
			if (continent.equals("africa"))
				app.fill(255);
			else
				app.fill(0);
			
			app.text(Data.capitalize(pays), (cx+x), (cy+y));
			
			
		}
		app.fill(255);
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
		float dist = app.sqrt(app.sq(pointX - centerX) + app.sq(pointY - centerY));
		
		if (mode)
			nearCenter = dist <= r /2 && dist >= r /4;
		else
			nearCenter = dist <= r /2 && dist >= r/8;
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
