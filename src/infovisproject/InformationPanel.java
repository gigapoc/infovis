package infovisproject;

import java.util.LinkedHashMap;
import java.util.Map;

import processing.core.PGraphicsJava2D;

public class InformationPanel extends PGraphicsJava2D {

	CountryPanel countryPanel;
	
	int width, height;
	int informationNb;
	
	String[] info;
	int[][] colors;
	
	LinkedHashMap<String, Float> data;
	
	public InformationPanel(CountryPanel cp, int width, int height, int informationNb) {
		super();
		this.countryPanel = cp;
		
		this.width = width;
		this.height = height;
		setSize(width, height);
		
		this.informationNb = informationNb;
		
		// information to display in a different order
		String[] tmpInfo = InfoVisProject.infoToDisplay;
		int[][] tmpColors = CountryPanel.colors;
		int half = tmpInfo.length/2;
		
		info = new String[tmpInfo.length];
		colors = new int[tmpColors.length][3];
		
		int newIndex = 0;
		for(int index = 0; index < half; index++) {
			info[newIndex] = tmpInfo[index];
			colors[newIndex++] = tmpColors[index];
			info[newIndex] = tmpInfo[index + half];
			colors[newIndex++] = tmpColors[index + half];
		}
	}
	
	public void draw() {
		beginDraw();
		strokeWeight(2);
		fill(200);
		stroke(255);
		
		if(data != null) { showInformation(); }
		
		endDraw();
		clear();
	}
	
	public void update(LinkedHashMap<String, Float> data) {
		this.data = data;
	}
	
	public void showInformation() {
		textFont(countryPanel.loadFont());
		
		float y = 10;
		for(int index = 0; index < info.length; index++) {
			stroke(0);
			fill(color(colors[index][0], colors[index][1], colors[index][2]));
			rect(10, y, 18, 18);
			
			fill(0);
			String text = Data.normalizeData(info[index], data.get(info[index])); 
			text(text, 40, y+16);
			
			y += 30;
			if(index%2 != 0) { y += 30; }
		}
		fill(255);
	}
}
