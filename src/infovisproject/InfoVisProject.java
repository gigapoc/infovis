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
	int r  = h/2 - 100;
	int rPays = 2*r;
	int midR = r/2;
	
	//Arcs de cercle
	ArrayList<Continent> conts = new ArrayList<Continent>();
	ArrayList<Pays> aCont = new ArrayList<Pays>();
	
	
	//Selection continent
	Continent selec = null;
	
	//Animation TODO
	//boolean animReduceCont = false;
	
	/*
	 * Couleurs :
	 * 	- asia : jaune (100-220, 100-220, 58)
	 *  - europe : bleu  (45, 65, 100-220)
	 *  - AM. N. : rouge (100-220, 58, 58)
	 *  - AM. S. : orange (200-255, 148, 59)
	 *  - Afrique : noir (gray scale)
	 *  - Océanie : vert (47, 100-220, 47)
	 */
	
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
			drawMiddle();
			
			//Mouse interaction
			for (Continent c: conts)
			{
				for (Pays p : c.pays)
					if (p.mouseInside())
						mouseInteraction(p);
				if (c.mouseInside())
					mouseInteraction(c);
			}
			
			
			conts.clear();
		}
		else //Vue focus sur un continent
		{
			prepareCont();
			
			for (Pays p : aCont)
				p.draw();
			
			drawMiddle();
			
			//Mouse interaction
			for (Pays p : aCont)
				if (p.mouseInside())
					mouseInteraction(p);
			
			aCont.clear();
		}
		
		interactionMiddle();
		
		cp.draw();
		image(cp, cpX, cpY);
		
	}
	
///////////////SECTION MIDDLE
	public void drawMiddle()
	{
		ellipse(cx, cy, midR, midR);
	}
	
	
	public void interactionMiddle()
	{
		if (selec != null) //Si en mode continent
		{
			//Check if click inside mid circle
			if (isPointInsideCircle(mouseX, mouseY, cx, cy, midR) && mousePressed)
			{
				selec = null;
			}
		}
 	}
/////////////// FIN SECTION MIDDLE
	
/////////////// SECTION VUE CONTINENT
	public void prepareCont()
	{
		//Ratio
		float rat = 360 / data.getPopCont(selec.name);
		float degCurr = 0;
		float degPays = 0;
		
		strokeWeight(0.2f);
		
		TreeMap<String, Float> continent = data.getContsCountries().get(selec.name);
		
		for (Entry<String, Float> p : continent.entrySet())
		{
			if (p.getValue() > 0)
			{
				degPays = p.getValue() * rat;
				Pays a = new Pays (this, cx, cy, r, degCurr, degCurr + degPays, selec.name, p.getKey(), p.getValue());
				aCont.add(a);
				degCurr += degPays;		
			}
		}
	}
/////////////// FIN SECTION VUE CONTINENT
	
	
/////////////// SECTION VUE GLOABALE
	public void prepareCountries()
	{
		ArrayList<Pays> arcs = new ArrayList<Pays>();
		
		//Ratio :
		float rat = 360/data.getPop();
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
					Pays a = new Pays(this, cx, cy, rPays, degCurr, degCurr+degPays, e.getKey(), p.getKey(), p.getValue());
					arcs.add(a);
					degCurr += degPays;
					degCont += degPays;
				}
			}
			
			Continent c = new Continent(this, e.getKey(), cx, cy, r, degCurrCont, degCurrCont + degCont, arcs);
					
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
		fill(0,255,0);
		rect(mouseX+10, mouseY+10, textWidth(a.pays) + 20, 50, 10);
		fill(0);
		text(a.pays, mouseX+20, mouseY+22);
		fill(255);
	}
	
	public void mouseInteraction(Continent c)
	{
		fill(0,255,0);
		rect(mouseX+10, mouseY+10, textWidth(c.name) + 20, 50, 10);
		fill(0);
		text(c.name, mouseX+20, mouseY+22);
		fill(255);
		
		if (mousePressed)
			selec = c;
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
