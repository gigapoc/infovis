package infovisproject;

import processing.core.PGraphicsJava2D;

public class InformationPanel extends PGraphicsJava2D {

	CountryPanel countryPanel;
	
	int width, height;
	int informationNb;
	
	public InformationPanel(CountryPanel cp, int width, int height, int informationNb) {
		super();
		this.countryPanel = cp;
		
		this.width = width;
		this.height = height;
		setSize(width, height);
		
		this.informationNb = informationNb;
	}
	
	public void draw() {
		beginDraw();
		background(255);
		strokeWeight(2);
		stroke(color(100,100,255));
		rect(0, 0, width, height);
		
		textFont(countryPanel.loadFont(), 30);
		
		for(int infoNb = 0; infoNb < informationNb; infoNb++) {
			fill(color(CountryPanel.colors[infoNb][0], CountryPanel.colors[infoNb][1], CountryPanel.colors[infoNb][2]));
		
			//line(width/4, (height*(infoNb+1))/3 - height/6, (3*width)/4, (height*(infoNb+1))/3 - height/6);
			text("Category " + (infoNb+1), width/4, (height*(infoNb+1))/informationNb - height/(informationNb*2));
		}
		fill(255);
		
		endDraw();
	}
}
