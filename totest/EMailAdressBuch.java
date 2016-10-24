package totest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class EMailAdressBuch {

	private HashMap<String, String> db = new HashMap<>();

	public EMailAdressBuch() {
	}

	public void einfuegen(String name, String email) {
		db.put(name, email);
	}

	public void einlesen(String dateiname) {
		try {
			Scanner sc = new Scanner(new FileInputStream(dateiname));
			while (sc.hasNextLine()) {
				String[] in = sc.nextLine().split(";");
				db.put(in[0], in[1]);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void mitarbeiterEinlesen(URL url) {
		try {
			URLConnection conn = url.openConnection();
			Object content = conn.getContent();
			Scanner sc = new Scanner((InputStream) content);
			while (sc.hasNextLine()) {
				String[] in = sc.nextLine().split(";");
				db.put(in[0], in[1]);
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String abfrage(String name) throws UnknownNameException {
		if (db.containsKey(name)) {
			return db.get(name);
		}
		throw new UnknownNameException("Name " + name + " not found.");
	}

	public String toString() {
		String res = "{";
		for (Entry<String, String> entries : db.entrySet()) {
			res += entries.getKey() + "=" + entries.getValue() + ", ";
		}
		res += "}";
		return res;
	}
}
