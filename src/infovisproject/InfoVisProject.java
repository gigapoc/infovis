package infovisproject;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import processing.core.PApplet;
import processing.core.PFont;


public class InfoVisProject extends PApplet {
	
	Data data;
	
	int w = 1920;
	int h = 1080;
	int cx = w/2 + 200;
	int cy = h/2;
	int r  = h/2 - 50;
	int rPays = 2*r;
	int midR = r/2;
	
	//Arcs de cercle
	ArrayList<Continent> conts = new ArrayList<Continent>();
	TreeMap<Float, Pays> aCont = new TreeMap<Float, Pays>();
	
	
	//Selection continent
	Continent selec = null;
	
	//Animation
	int degCurr = 0;
	
	//Panel
	CountryPanel cp;
	int cpWidth = 400;
	int cpHeight = h;
	float cpX = 0;
	float cpY = 0;
	
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
		
		if (selec == null) //Si on est en vue générale
		{
			prepareCountries(degCurr);
			
			//Draw
			for (Continent c : conts)
			{
				for (Pays p : c.pays)
					p.draw();
				c.draw();
			}
			
			drawMiddle();
			
			//Mouse interaction
			for (Continent c: conts)
			{
				for (Pays p : c.pays)
					if (p.mouseInside())
					{
						mouseInteraction(p);
					}
				if (c.mouseInside())
					mouseInteraction(c);
			}
			
			conts.clear();
			
			if (degCurr < 360)
				degCurr+= 30;
		}
		else //Vue focus sur un continent
		{

			prepareCont(degCurr);
			
			strokeWeight(1);
			for (Entry<Float, Pays> p : aCont.entrySet())
				p.getValue().draw();
			strokeWeight(0.2f);
			
			drawMiddle();
				
			//Mouse interaction
			for (Entry<Float, Pays> p : aCont.entrySet())
				if (p.getValue().mouseInside())
					mouseInteraction(p.getValue());
			
			fill(0);
			text(selec.name, cx-textWidth(selec.name)/2, cy + 30);
			fill(255);
			aCont.clear();
			
			if (degCurr < 360)
				degCurr+= 30;
			
		}
		
		interactionMiddle();
		
		cp.draw();
		image(cp, cpX, cpY);
		
	}
	
///////////////SECTION MIDDLE
	public void drawMiddle()
	{
		//Middle
		strokeWeight(1);
		ellipse(cx, cy, midR, midR);
		strokeWeight(0.2f);
	}
	
	
	public void interactionMiddle()
	{
		if (selec != null) //Si en mode continent
		{
			//Check if click inside mid circle
			if (isPointInsideCircle(mouseX, mouseY, cx, cy, midR) && mousePressed)
			{
				selec = null;
				degCurr = 0;
			}
		}
 	}
/////////////// FIN SECTION MIDDLE
	
/////////////// SECTION VUE CONTINENT
	public void prepareCont(int deg)
	{
		//Ratio
		float rat = deg / data.getPopCont(selec.name);
		float degCurr = 0;
		float degPays = 0;
		
		strokeWeight(0.2f);
		
		TreeMap<String, Float> continent = data.getContsCountries().get(selec.name);
		
		for (Entry<String, Float> p : continent.entrySet())
		{
			if (p.getValue() > 0)
			{
				degPays = p.getValue() * rat;
				Pays a = new Pays (this, cx, cy, rPays, degCurr, degCurr + degPays, selec.name, p.getKey(), p.getValue(), false);
				aCont.put(p.getValue(), a);
				degCurr += degPays;
			}
		}
		
	}
/////////////// FIN SECTION VUE CONTINENT
	
/////////////// SECTION VUE GLOABALE
	public void prepareCountries(int deg)
	{
		ArrayList<Pays> arcs = new ArrayList<Pays>();
		
		//Ratio :
		float rat = deg/data.getPop();
		float degCurr = 0;
		float degPays = 0;
		float degCurrCont = 0;
		float degCont = 0;
		
		strokeWeight(0.2f);
		
		for (Entry<String, TreeMap<String, Float>> e : data.getContsCountries().entrySet())
		{
			for (Entry<String, Float> p : e.getValue().entrySet())
			{
				if (p.getValue() > 0)
				{
					degPays = p.getValue() * rat;
					Pays a = new Pays(this, cx, cy, rPays, degCurr, degCurr+degPays, e.getKey(), p.getKey(), p.getValue(), true);
					arcs.add(a);
					degCurr += degPays;
					degCont += degPays;
				}
			}
			
			Continent c = new Continent(this, e.getKey(), cx, cy, r, degCurrCont, degCurrCont + degCont, arcs);
			c.sortByPop();
			conts.add(c);
			
			degCurrCont += degCont;
			degCont = 0;
			arcs.clear();
		}
		
	}
/////////////// FIN SECTION VUE GLOBALE
	
	
/////////////// SECTION INTERACTION
	public void mouseInteraction(Pays a)
	{
		fill(0);
		text(a.pays, cx-textWidth(a.pays)/2, cy);
		if (selec == null)
			text(a.continent, cx-textWidth(a.continent)/2, cy + 30);
		fill(255);
	}
	
	public void mouseInteraction(Continent c)
	{
		fill(0);
		text(c.name, cx-textWidth(c.name)/2, cy);
		fill(255);
		
		if (mousePressed)
		{
			selec = c;
			degCurr = 0;
		}
	}
/////////////// FIN SECTION INTERACTION
	
	boolean isPointInsideCircle(float pointX, float pointY, float centerX, float centerY, float diameter)
	{
		return sqrt(sq(pointX - centerX) + sq(pointY - centerY)) <= diameter /2;
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { infovisproject.InfoVisProject.class.getName() });
	}
}
