package infovisproject;

import java.util.ArrayList;
import java.util.Map.Entry;

import processing.core.PApplet;
import processing.core.PFont;
import processing.data.Table;
import processing.data.TableRow;


public class InfoVisProject extends PApplet {
	
	public static final String[] infoToDisplay = { "Population Total2007", 
		  										   "Population Density2007", 
												   "Life E-1pectancy2007", 
												   "Energy Use Total2007", 
												   "Murders Total Deaths2007", 
												   "GDP Total2007" };
	
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
	ArrayList<Pays> aCont = new ArrayList<Pays>();
	
	
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
		return loadFont("DejaVuSans-20.vlw");
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
			

			// testing purpose
			/*int countryNb = 50;
			java.util.LinkedHashMap<String, Float> data = new java.util.LinkedHashMap<String, Float>();
			Table table = this.data.data;
			String[] columnNames = { "Population Total2007", "Population Density2007", "Life E-1pectancy2007", "Energy Use Total2007", "Murders Total Deaths2007", "GDP Total2007" }; 
			for(int index = 0; index < columnNames.length; index++) {
				data.put(columnNames[index], table.getFloat(countryNb, columnNames[index]));
			}
			
			cp.update(table.getString(countryNb, "Continent"), table.getString(countryNb, "Country"), this.data, data);
			// end of test
			*/
			
			if (degCurr < 360)
				degCurr+= 40;
		}
		else //Vue focus sur un continent
		{

			prepareCont(degCurr);
			
			strokeWeight(1);
			for (Pays p : aCont)
				p.draw();
			strokeWeight(0.2f);
			
			drawMiddle();
				
			//Mouse interaction
			for (Pays p : aCont)
				if (p.mouseInside())
					mouseInteraction(p);
			
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
		
		Table cont = data.contsPays.get(selec.name);;
		float pop = 0;
		cont.sort(16);
		
		for (TableRow tr : cont.rows())
		{
			pop = tr.getFloat(16);
			println(tr.getString(1) + " : " + pop);
			if (pop > 0)
			{
				degPays = pop * rat;
				Pays a = new Pays (this, cx, cy, rPays, degCurr, degCurr + degPays, selec.name, tr.getString(1), pop, false);
				aCont.add(a);
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
		
		float pop = 0;
		
		for (Entry<String, Table> e : data.contsPays.entrySet())
		{
			for (TableRow tr : e.getValue().rows())
			{
				pop = tr.getFloat(16);
				if (pop > 0)
				{
					degPays = pop * rat;
					Pays a = new Pays(this, cx, cy, rPays, degCurr, degCurr+degPays, e.getKey(), tr.getString(1), pop, true);
					arcs.add(a);
					degCurr += degPays;
					degCont += degPays;
				}
			}
			Continent c = new Continent(this, e.getKey(), cx, cy, r, degCurrCont, degCurrCont + degCont, arcs);
			//c.sortByPop();
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
		
		// show country details on countryPanel
		if(mousePressed) {
			cp.update(a.continent, a.pays, data, data.getCaracteristics(a.pays)); 
		}
		
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
