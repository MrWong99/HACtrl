package totest;

import java.io.File;
import java.io.FileNotFoundException; // TODO: Unnötige imports. In kann in Eclipse via Strg + Shift + O automatisch gemacht werden.
import java.util.ArrayList;
import java.util.Scanner;

public class GoldPreis {
	private ArrayList<GoldTagespreis> list;
//Konstruktor der Klasse GoldPreis // TODO: Das ist kein JavaDoc sondern nur Kommentar. Lieber: /** Konstruktor[...] */
	public GoldPreis(String dateiname) {
		this.list=new ArrayList<GoldTagespreis>(); // TODO: this nicht benötigt.
		Scanner sc = null;
		try {
			sc = new Scanner(new File(dateiname)); // TODO: Scanner nicht geschlossen. (Resource leak)
		} catch (Exception e) {
			System.out.println("hallo"); // TODO: Hi :)
		}
		while (sc.hasNextLine()) {
			GoldTagespreis g = new GoldTagespreis(sc.nextLine());
			this.list.add(g);
		}
	}
//Gibt den Preis des Goldes an einem betsimmten Tag zurück.
	public double getPreis(String date) {
		for (int i = 0; i < this.list.size(); i++) {
			String s = this.list.get(i).datum;
			if (s.equals(date)) {
				return this.list.get(i).preis;
			}
		}
		throw new NumberFormatException("nope");
	}
//Gibt die Tage aus, an denen der Goldpreis am höchsten/niedrigsten war
	public void printMinMax() {
		double min;
		double max;
		min = this.list.get(0).preis;
		max = this.list.get(0).preis;
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).preis < min && this.list.get(i).preis!=-1)
				min = this.list.get(i).preis;
			if (this.list.get(i).preis > max)
				max = this.list.get(i).preis;
		}
		String mini="";
		String maxi="";
		for(int i=0; i<this.list.size(); i++)
		{
			if(this.list.get(i).preis==min)
				mini+=this.list.get(i).datum+", ";
			if(this.list.get(i).preis==max)
				maxi+=this.list.get(i).datum+", ";
		}
		String ret="Den niedrigsten Goldpreis von "+min+" gab es an folgenden Tagen:\n"+mini+"\n Den hoechsten Goldpreis von "+max+" gab es an folgenden Tagen:\n"+maxi;
		System.out.println(ret); 
	}
	public static void main(String[] args) {
	//try{
		GoldPreis test=new GoldPreis("gold.txt");
		System.out.println(test.getPreis("2009-10-20"));
		System.out.println(test.getPreis("2009-02-07"));
		test.printMinMax();
//	}catch(FileNotFoundException e){
	//	System.out.println("Datei nicht gefunden");
	//}
		
	
}}