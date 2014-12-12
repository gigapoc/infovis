package infovisproject;

import java.util.ArrayList;

import processing.core.PApplet;

public class Continent 
{
	PApplet app;
	
	ArrayList<Pays> pays;
	
	float start, stop;
	String name;
	
	public Continent(PApplet a, String n)
	{
		app = a;
		
		pays = new ArrayList<Pays>();
		
		name = n;
	}
	
	public Continent(PApplet a, String name, float start, float stop, ArrayList<Pays> payss)
	{
		app = a;
		
		pays = new ArrayList<Pays>(payss);
		app.println(pays.size());
		this.start = app.radians(start);
		this.stop = app.radians(stop);
		
		this.name = name;
		
	}
	
	public void draw()
	{
		if (name == "europe")
			app.fill(0,0,255,90);
		
		app.arc(400, 300, 400, 400, start, stop);
		
		app.fill(0);
	}
	
	public void add(Pays p)
	{
		pays.add(p);
	}
	
	public void setStart(float s) { start = s; }
	public void setStop(float s) { stop = s; }
	
}
