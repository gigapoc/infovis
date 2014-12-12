package infovisproject;

import processing.core.PGraphics;
import processing.core.PGraphicsJava2D;

public class WebGraph extends PGraphicsJava2D {

	PGraphics parent;
	
	int width;
	int height;
	
	int branchNumber;
	
	Triangle[] triangles;
	
	public WebGraph(PGraphics parent, int width, int height, int branchNumber) {
		this.parent = parent;
		this.width = width;
		this.height = height;
		this.branchNumber = (branchNumber<3)?3:branchNumber;
		this.triangles = new Triangle[branchNumber];
		
		// to remove, for debuging purpose	
		addTriangle(0, 0, -100, (float) Math.cos(PI/6)*55, (float) Math.sin(PI/6)*55, CountryPanel.colors[0], CountryPanel.colors[1]);
		addTriangle(1, (float) Math.cos(PI/6)*55, (float) Math.sin(PI/6)*55, (float) Math.cos(5*PI/6)*120, (float) Math.sin(5*PI/6)*120, CountryPanel.colors[1], CountryPanel.colors[2]);
		addTriangle(2, (float) Math.cos(5*PI/6)*120, (float) Math.sin(5*PI/6)*120, 0, -100, CountryPanel.colors[2], CountryPanel.colors[0]);
		
		setSize(width, height);
	}
	
	public void drawBackgroundLines() {
		pushMatrix();
		float radius = width/2;
		
		strokeWeight(1);
		stroke(200);
		rotate(-PI/2);
		for(int index = 0; index < 5; index++) {
			float x = (radius/5) * (index + 1);
			float y = 0;
		
			for(int segmentIndex = 1; segmentIndex < branchNumber+1; segmentIndex++) {
				float newX = (((index+1)*radius)/5)*((float) Math.cos(((-2*PI)/branchNumber) * segmentIndex));
				float newY = (((index+1)*radius)/5)*((float) Math.sin(((-2*PI)/branchNumber) * segmentIndex));
			
				line(x, y, newX, newY);
				
				x = newX; y = newY;
			}
		}
		popMatrix();
	}
	
	public void drawBranches() {
		strokeWeight(4);
		pushMatrix();
		for(int branchIndex = 0; branchIndex < branchNumber; branchIndex++) {
			stroke(CountryPanel.colors[branchIndex][0], CountryPanel.colors[branchIndex][1], CountryPanel.colors[branchIndex][2]);
			line(0, -height/2, 0, 0);
			rotate((2*PI)/branchNumber);
		}
		popMatrix();
	}
	
	
	public void addTriangle(int index, float x1, float y1, float x2, float y2, int[] color1, int[] color2) {
		triangles[index] = new Triangle(x1, y1, x2, y2, color1, color2);
	}
	
	public void drawTriangles() {
		for(Triangle triangle: triangles) {
			if(triangle!=null) { triangle.draw(); }
		}
	}
	
	public void draw() {
		beginDraw();
		background(150);
		/*stroke(0);
		ellipse(width/2, height/2, width, height);*/
		
		// set origin to the middle of the panel
		translate(width/2, height/2);
		
		drawBackgroundLines();
		
		drawBranches();
		
		drawTriangles();
		
		endDraw();
	}
	
	
	private class Triangle {
		
		float x1, y1, x2, y2;
		int[] color1, color2;
		
		public Triangle(float x1, float y1, float x2, float y2, int[] color1, int[] color2) {
			this.x1 = x1; this.y1 = y1;
			this.x2 = x2; this.y2 = y2;
			this.color1 = color1;
			this.color2 = color2;
		}
		
		public void moveFirstPoint(float x1, float y1) {
			this.x1 = x1; this.y1 = y1;
		}
		
		public void moveSecondPoint(float x2, float y2) {
			this.x2 = x2; this.y2 = y2;
		}
		
		public void draw() {
			stroke(0);
			strokeWeight(2);
			
			float x = x1;
			float y = y1;
			
			float h_dist = (x1 - x2);
			float v_dist = (y1 - y2);
			float h_step = 0.01f*Math.abs(h_dist * h_dist)/(x2 - x1);
			float v_step = 0.01f*Math.abs(v_dist * v_dist)/(y2 - y1);
			
			for(float interpolation = 0.01f; interpolation < 1f; interpolation += 0.01f) {
				int colorGradient = lerpColor(color(color1[0], color1[1], color1[2]),
						  					  color(color2[0], color2[1], color2[2]),
						  					  interpolation);
						  
				stroke(colorGradient);
				strokeWeight(5);
				point(x, y);
				
				x += h_step;
				y += v_step;
			}		
		}
	}
}
