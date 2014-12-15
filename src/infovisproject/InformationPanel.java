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
		rect(0, 0, width, height);
		
		if(data != null) { showInformation(); }
		
		endDraw();
	}
	
	public void update(LinkedHashMap<String, Float> data) {
		this.data = data;
	}
	
	public void showInformation() {
		textFont(countryPanel.loadFont());
		textAlign(CENTER);
		
		for(int index = 0; index < info.length; index++) {
			fill(color(colors[index][0], colors[index][1], colors[index][2]));
		
			float y = (height*(index+1))/(informationNb+1) - height/(informationNb*2);

			System.out.println(y);
			text(info[index] + " : ", width/2, y);
			text(data.get(info[index]).isNaN()?"No data":data.get(info[index]).toString(),
				 width/2, y+35);
		}
		fill(255);
	}
}
