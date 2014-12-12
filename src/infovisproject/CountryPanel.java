package infovisproject;

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
	
	
	int width, height, graphWidth, graphHeight;
	int informationNb = 4;
	
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
		graphHeight = (5*width)/6;
		cg = new WebGraph(this, graphWidth, graphHeight, informationNb);
		ip = new InformationPanel(this, graphWidth, height - (graphHeight+60), informationNb);
	}
	
	public void setup() {
		background(255);
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
		image(ip, width/12, graphHeight + 20);
		
		endDraw();
	}
	
	public PFont loadFont() {
		return mainPanel.loadFont();
	}
	
}
