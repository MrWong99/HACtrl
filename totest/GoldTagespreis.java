package totest;

import java.io.File; // TODO: Auch unnötig
import java.util.Scanner;

public class GoldTagespreis {
public String datum="";
public double preis;
//Konstruktor der Klasse Goldpreis
public GoldTagespreis(String dateiname) // TODO: Diese Klasse sollte nur die Daten erhalten, nicht den String selber evaluieren.
{
/*	Scanner sc = null;
	try {
		sc = new Scanner(new File(dateiname));
	} catch (Exception e) {
		System.out.println("hallo");
	}*/
	if(dateiname.charAt(11)>47 && dateiname.charAt(11)<58)
	{
	for(int i=0; i<10; i++) // TODO: Schöner und einfacher: Mit dateiname.split("\t") arbeiten.
	{
		this.datum +=dateiname.charAt(i);
	}
	String d="";
	for(int i=11; i<dateiname.length(); i++) // TODO: Schöner und einfacher: Mit dateiname.split("\t") arbeiten.
	{
		d+=dateiname.charAt(i);		
	}
	String p=d.replace(".", "");
	String p1=p.replace(",", ".");
	this.preis=Double.parseDouble(p1);
	}else
	{
		for(int i=0; i<10; i++)
		{
			this.datum +=dateiname.charAt(i);
		}
		this.preis=-1;
	}
}
//eine toString methode für Goldpreis
public String toString()
{
	String s=this.datum+"\t"+this.preis; // TODO: wieder unnötig
	return s;
}

}