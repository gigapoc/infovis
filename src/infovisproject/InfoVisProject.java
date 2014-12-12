package infovisproject;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.TreeMap;

import processing.core.PApplet;
import processing.core.PFont;


public class InfoVisProject extends PApplet {
	
	Data data;
	
	int w = 1280;
	int h = 1060;
	int cx = w/2;
	int cy = h/2;
	int r  = 400;
	
	CountryPanel cp;
	int cpWidth = 400;
	int cpHeight = 1000;
	float cpX = 0;
	float cpY = 0;
	
	//Arcs de cercle
	ArrayList<Continent> conts = new ArrayList<Continent>();
	
	/*
	 * Couleurs :
	 * 	- asia : jaune (100-220, 100-220, 58)
	 *  - europe : bleu  (45, 65, 100-220)
	 *  - AM. N. : rouge (100-220, 58, 58)
	 *  - AM. S. : orange (200-255, 148, 59)
	 *  - Afrique : noir (gray scale)
	 *  - Océanie : vert (47, 100-220, 47)
	 */
	
	public void setup()
	{
		size(w, h);
		smooth();
		
		//Initialize data
		data = new Data(this);
		
		cp = new CountryPanel(this, cpWidth, cpHeight);
	}


	public PFont loadFont() {
		return loadFont("DejaVuSans-30.vlw");
	}
	
	public void draw()
	{
		background(255);
		
		prepareCountries();
		
		//Draw
		for (Continent c : conts)
		{
			for (Pays p : c.pays)
			{
				p.draw();
			}
			c.draw();
		}
		
		//Mouse interaction
		for (Continent c: conts)
			for (Pays p : c.pays)
				if (p.mouseInside())
					mouseInteraction(p);
		
		drawMiddle();
		
		cp.draw();
		image(cp, cpX, cpY);
		
		conts.clear();
		
	}
	
	public void prepareCountries()
	{
		ArrayList<Pays> arcs = new ArrayList<Pays>();
		
		//Ratio :
		float rat = 360/data.getPop();
		float degCurr = 0;
		float degPays = 0;
		float degCont = 0;
		
		strokeWeight(0.2f);
		
		for (Entry<String, TreeMap<String, Float>> e : data.getContsCountries().entrySet())
		{
			for (Entry<String, Float> p : e.getValue().entrySet())
			{
				if (p.getValue() > 0)
				{
					degPays = p.getValue() * rat;
					Pays a = new Pays(this, cx, cy, r, degCurr, degCurr+degPays, e.getKey(), p.getKey(), p.getValue());
					arcs.add(a);
					degCurr += degPays;
					degCont += degPays;
				}
			}
			Continent c = new Continent(this, e.getKey(), degCurr, degCont, arcs);
			
			conts.add(c);
			degCont = 0;
			arcs.clear();
		}
		
	}
	
	public void mouseInteraction(Pays a)
	{
		fill(0,255,0);
		rect(mouseX+10, mouseY+10, textWidth(a.pays) + 20, 50, 10);
		fill(0);
		text(a.pays, mouseX+20, mouseY+22);
		fill(255);
	}
	
	public void drawMiddle()
	{
		
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { infovisproject.InfoVisProject.class.getName() });
	}
}
