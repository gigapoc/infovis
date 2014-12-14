package infovisproject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import processing.core.PFont;
import processing.core.PGraphicsJava2D;

public class CountryPanel extends PGraphicsJava2D {

	
	InfoVisProject mainPanel;
	
	public static final int[][] colors = { {255, 0, 0}, 
									 {0, 255, 0}, 
									 {0, 0, 255},
									 {255, 255, 0},
									 {0, 255, 255},
									 {255, 0, 255} };
	
	
	String nameToDisplay;
	int width, height, graphWidth;
	int informationNb = 6;
	
	WebGraph cg;
	InformationPanel ip;
	
	public CountryPanel(InfoVisProject mainPanel, int width, int height) {
		super();
		
		this.width = width;
		this.height = height;
		
		this.mainPanel = mainPanel;
		setParent(mainPanel);
		setSize(width, height);
		
		stroke(2);

		graphWidth = (5*width)/6;
		cg = new WebGraph(this, graphWidth, informationNb);
		ip = new InformationPanel(this, graphWidth, height - (graphWidth+60), informationNb);
	}
	
	public void draw() {
		// backgrounds elements
		beginDraw();
		background(200);
		
		stroke(0);
		strokeWeight(2);
		line(width, 0, width, height);
		
		cg.draw();
		image(cg, width/12, 20);
		
		ip.draw();
		image(ip, width/12, graphWidth + 40);
		
		if(nameToDisplay != null) {
			textFont(mainPanel.loadFont("LiberationSerif-40.vlw"));
			text(nameToDisplay, 0, 30);
		}
		
		endDraw();
	}
	
	public PFont loadFont() {
		return mainPanel.loadFont();
	}
	
	// update to the new information given
	public void update(String continent, String country, Data data, LinkedHashMap<String, Float> caracteristics) {
		nameToDisplay = country;
		
		int branchIndex = 0;
		for(Map.Entry<String, Float> entry: caracteristics.entrySet()) {
			cg.setMax(branchIndex, data.getMaxColumn(continent, entry.getKey()));
			cg.setMin(branchIndex, data.getMinColumn(continent, entry.getKey()));
			branchIndex++;
		}
		
		cg.update(caracteristics);
		ip.update(caracteristics);
	}
}
