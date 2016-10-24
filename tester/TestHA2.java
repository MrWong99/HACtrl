package tester;

import java.net.MalformedURLException;
import java.net.URL;

import asserters.MassertAdvanced;
import totest.EMailAdressBuch;
import totest.EMailAdressBuchSample;

public class TestHA2 {

	private static EMailAdressBuchSample sample = new EMailAdressBuchSample();

	private static MassertAdvanced<EMailAdressBuch> massertAdv = new MassertAdvanced<>(EMailAdressBuch.class);

	public static void main(String[] args) {
		massertAdv.init(sample, new Object[0]);
		String param1 = "testNotKnown";
		massertAdv.addRun("abfrage", param1);
		try {
			massertAdv.addRun("mitarbeiterEinlesen", new URL(
					"https://doc.itc.rwth-aachen.de/download/attachments/5800183/mitarbeiter_matse_extern.txt"));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		param1 = "Gerhard Weiss";
		massertAdv.addRun("abfrage", param1);
		String val1 = "teststsetest";
		massertAdv.addRun("einfuegen", param1, val1);
		massertAdv.addRun("abfrage", param1);
		try {
			massertAdv.addRun("mitarbeiterEinlesen", new URL(
					"https://doc.itc.rwth-aachen.de/download/attachments/5800183/mitarbeiter_matse_intern.txt"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		massertAdv.addRun("toString", new Object[0]);
		massertAdv.addRun("einlesen", "TestMe");
		param1 = "Luk Aaas";
		massertAdv.addRun("abfrage", param1);
		massertAdv.doRun();
	}
}
