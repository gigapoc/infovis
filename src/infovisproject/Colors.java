package infovisproject;

import java.awt.Color;

public class Colors 
{
	//Jaune (Asie)
	static Color j = new Color(255, 239, 8);
	//Bleu (Europe)
	static Color b = new Color(0,41,140);
	//Noir (Afrique)
	static Color n = new Color(0,0,0);
	//Rouge (N. Amé.)
	static Color r = new Color(255, 11, 10);
	//Orange (S. Amé.)
	static Color o = new Color(255, 144, 5);
	//Vert (Océanie)
	static Color v = new Color(94, 248, 5);
	
	static int opacite = 90;
	static int opaciteHover = 200;
	
	public static Color getColor(String cont)
	{
		if (cont.equals("europe"))
			return b;
		else if (cont.equals("asia"))
			return j;
		else if (cont.equals("africa"))
			return n;
		else if (cont.equals("oceania"))
			return v;
		else if (cont.contains("america"))
			return r;
		else
			return o;
	}
}
