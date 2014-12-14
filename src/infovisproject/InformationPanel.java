package infovisproject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import processing.core.PGraphicsJava2D;

public class InformationPanel extends PGraphicsJava2D {

	CountryPanel countryPanel;
	
	int width, height;
	int informationNb;
	
	LinkedHashMap<String, Float> data;
	
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
		strokeWeight(2);
		fill(200);
		stroke(255);
		rect(0, 0, width, height);
		
		if(data != null) { showInformation(); }
		
		endDraw();
	}
	
	public void update(LinkedHashMap<String, Float> data) {
		this.data = data;
	}
	
	public void showInformation() {
		textFont(countryPanel.loadFont());
		
		int iterationCount = 0;
		for(Map.Entry<String, Float> caracteristic: data.entrySet()) {
			fill(color(CountryPanel.colors[iterationCount][0], CountryPanel.colors[iterationCount][1], CountryPanel.colors[iterationCount][2]));
		
			//line(width/4, (height*(infoNb+1))/3 - height/6, (3*width)/4, (height*(infoNb+1))/3 - height/6);
			float y = (height*(iterationCount+1))/(informationNb+1) - height/(informationNb*2);
			textAlign(CENTER);
			text(caracteristic.getKey() + " : ", width/2, y);
			text(caracteristic.getValue(), width/2, y+35);
			iterationCount++;
		}
		fill(255);
	}
}
