package infovisproject;

import java.util.LinkedHashMap;
import java.util.Map;

import processing.core.PFont;
import processing.core.PGraphicsJava2D;

public class CountryPanel extends PGraphicsJava2D {

	
	InfoVisProject mainPanel;
	
	public static final int[][] colors = { {0x5A, 0xAA, 0xFA}, 
										   {0xFF, 0x78, 0x32}, 
										   {0x00, 0xB4, 0xA0},
										   {0xAF, 0x6E, 0xE8},
										   {0xE7, 0x1D, 0x32},
										   {0x8C, 0xD2, 0x11}			    };
	
	
	String nameToDisplay;
	int width, height, graphWidth;
	
	WebGraph cg;
	InformationPanel ip;
	
	int backgroundColor;
	
	public CountryPanel(InfoVisProject mainPanel, int width, int height) {
		super();
		
		this.width = width;
		this.height = height;
		
		this.mainPanel = mainPanel;
		setParent(mainPanel);
		setSize(width, height);
		
		backgroundColor = mainPanel.color(100);
			
		graphWidth = width;
		cg = new WebGraph(this, backgroundColor, graphWidth, InfoVisProject.infoToDisplay.length);
		ip = new InformationPanel(this, graphWidth, height - (graphWidth+60), InfoVisProject.infoToDisplay.length);
	}
	
	public void draw() {
		// backgrounds elements
		beginDraw();
		background(backgroundColor);
		
		cg.draw();
		image(cg, 0, height/8);
		
		ip.draw();
		image(ip, 0, height/8 + graphWidth + 10);
		
		textAlign(LEFT);
		textSize(12);
		fill(255);
		text("Data compare to continent's", 5, 14);
		
		if(nameToDisplay != null) {
			//textFont(mainPanel.loadFont("LiberationSerif-40.vlw"));
			textSize(30);
			textAlign(CENTER);
			text(Data.capitalize(nameToDisplay), width/2, height/12);
		}
		

		stroke(0);
		strokeWeight(2);
		line(width, 0, width, height);
		
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
			cg.setMin(branchIndex, data.getMinColumn(continent, entry.getKey()));
			cg.setMax(branchIndex, data.getMaxColumn(continent, entry.getKey()));
			branchIndex++;
		}
		
		cg.update(caracteristics);
		ip.update(caracteristics);
	}
}
