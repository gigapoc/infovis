package infovisproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class Data 
{
	Table data;
	/*
	 * 0 : continent     1 : country    2 : %GDP Agriculture    3 : %GDP Industry    4 : %GDP Services     5 : Children Per Women
	 * 6 : Cumulative CO2 Emissions2007     7 : Democracy Score    8 : Energy Use Total    9 : Extreme Poverty headcount ratio at $1.25 a day 
	 * 10 : GDP Per Capita   11 : GDP Total  12 : HDI   13 : Life Expectancy    14 : Murders Total Deaths     15 : Population Density
	 * 16 : Population Total     17 : Poverty headcount ratio at $2 a day      18 : Working Hours per Week
	 * 
	 * 0 : continent     1 : country      16 : Population Total         (?? mapper 14 : Population Density  par teintes différentes des couleurs par continents ?? )
	 * 
	 */
	HashMap<String, Table> contsPays = new HashMap<String, Table>();
	
	PApplet app;
	
	public Data(PApplet p)
	{
		app = p;
		
		data = app.loadTable("countries.csv", "header");
		
		ArrayList<String> nomConts = new ArrayList<String>();
		
		for (TableRow tr : data.rows())
		{
			if(!nomConts.contains(tr.getString(0)))
			{
				contsPays.put(tr.getString(0), new Table());
				
				for (TableRow tr2 : data.findRows(tr.getString(0), 0))
				{
					contsPays.get(tr.getString(0)).addRow(tr2);
				}
				nomConts.add(tr.getString(0));
			}
		}
		//data.clearRows();
	}
	
	public ArrayList<String> getContinents()
	{
		ArrayList<String> conts = new ArrayList<String>();
		
		for (TableRow tr : data.rows())
			if (!conts.contains(tr.getString(0)))
				conts.add(tr.getString(0));
		
		return conts;
	}
	
	//Renvoie Continent : Pays : pop
	public TreeMap<String, TreeMap<String, Float>> getContsCountries()
	{
		TreeMap<String, TreeMap<String, Float>> tm = new TreeMap<String, TreeMap<String, Float>>();
		
		for (TableRow tr : data.rows())
		{
			if (!tm.containsKey(tr.getString(0)))
			{
				TreeMap<String, Float> temp = new TreeMap<String, Float>();
				temp.put(tr.getString(1), tr.getFloat(16));
				tm.put(tr.getString(0), temp);
			}
			else if (!tm.get(tr.getString(0)).containsKey(tr.getString(1)))
				tm.get(tr.getString(0)).put(tr.getString(1), tr.getFloat(16));
		}
		
		return tm;
	}
	
	//Renvoie continent : pop
	public TreeMap<String, Float> getContsPop()
	{
		TreeMap<String, Float> tm = new TreeMap<String, Float>();
		
		for (TableRow tr : data.rows())
		{
			if (!tm.containsKey(tr.getString(0)))
				tm.put(tr.getString(0), tr.getFloat(16));
			else
			{
				float pop = tm.get(tr.getString(0)) + tr.getFloat(16);
				tm.remove(tr.getString(0));
				tm.put(tr.getString(0), pop);
			}
		}
		
		return tm;
	}
	
	//Renvoie le nb de continents
	public int nbConts()
	{
		int nb = 1; String lastCont = data.getRow(0).getString(0);
		for (TableRow tr : data.rows())
		{
			if (!tr.getString(0).equals(lastCont))
			{
				nb++;
				lastCont = tr.getString(0);
			}
		}
		return nb;
	}
	
	//Renvoie la population d'un continent
	public float getPopCont(String cont)
	{
		float pop = 0;
		
		for (TableRow tr : data.rows())
			if (tr.getString(0).equals(cont) && tr.getFloat(16) > 0)
				pop += tr.getFloat(16);
		
		return pop;
	}
	
	//Renvoie population mondiale totale
	public float getPop()
	{
		float pop = 0;
		
		for (TableRow tr : data.rows())
		{
			if (tr.getFloat(16)>0)
				pop += tr.getFloat(16);
		}
		
		return pop;
	}
	
	// retourne la valeur maximum dans une colonne pour un continent donné
	public float getMaxColumn(String continent, String column) {
		float max = Float.MIN_VALUE;
		for(TableRow row : data.findRows(continent, "Continent")) {
			float value = row.getFloat(column);
			if(!Float.isNaN(value)) {
				max = Math.max(max, value);
			}
		}
		return max;
	}
	
	// retourne la valeur minimum dans une colonne pour un continent donné
	public float getMinColumn(String continent, String column) {
		float min = Float.MAX_VALUE;
		for(TableRow row : data.findRows(continent, "Continent")) {
			float value = row.getFloat(column);
			if(!Float.isNaN(value)) {
				min = Math.min(min, value);
			}
		}
		return min;
	}
	
	// récupère toutes les valeurs des colonnes apparaissant dans InfoVisProject.infoToDisplay
	public LinkedHashMap<String, Float> getCaracteristics(String country) {
		TableRow row = data.findRow(country, "Country");
		
		LinkedHashMap<String, Float> caracteristics = new LinkedHashMap<String, Float>();
		 
		String[] columns = InfoVisProject.infoToDisplay;
		for(String column: columns) {
			caracteristics.put(column, row.getFloat(column));
		}
		
		return caracteristics;
	}
}
