package tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

import asserters.Massert;
import totest.GoldPreis;
import totest.GoldTagespreis;

public class TestHA1 {
	
	private static Massert<GoldTagespreis> mGoldTagesperis = new Massert<GoldTagespreis>(GoldTagespreis.class);
	
	private static Massert<GoldPreis> mGoldPreis = new Massert<GoldPreis>(GoldPreis.class);

	public static void main(String[] args) throws ClassNotFoundException {
		testGoldTagespreis();
		testGoldPreis();
	}

	private static void testGoldPreis() throws ClassNotFoundException {
		mGoldPreis.assertFieldIs("list", ArrayList.class);
		Object goldPreis = mGoldPreis.createTestObject("otherName.txt");

		if (goldPreis != null) {
			Method m = null;
			try {
				m = goldPreis.getClass().getMethod("getPreis", String.class);
			} catch (NoSuchMethodException | SecurityException e) {
				System.err.println("Method getPreis not found.");
			}
			if (m != null) {
				String date = "2009-01-09";
				testPriceForDate(goldPreis, m, date);
				date = "1992-01-03";
				testPriceForDateException(goldPreis, m, date);
				date = "2009-01-18";
				testPriceForDate(goldPreis, m, date);
			} else {
				System.err.println("Method not found.");
			}
			try {
				m = goldPreis.getClass().getMethod("printMinMax", new Class<?>[0]);
			} catch (NoSuchMethodException | SecurityException e) {
				System.err.println("Method printMinMax not found.");
			}
			if (m != null) {
				try {
					System.out.println("Here should be the ouput of printMinMax: ");
					Object result = m.invoke(goldPreis, new Object[0]);
					System.out.println("Output end.");
					if (result != null) {
						System.err.println("printMinMax was not void but " + result.getClass().getName());
						System.err.println("Returned value:\n" + result);
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					System.err.println("Method printMinMax could not be invoked.");
					e.printStackTrace();
				}
			} else {
				System.err.println("No method given.");
			}
		} else {
			System.err.println("Object not instantiated.");
		}
	}

	private static void testPriceForDateException(Object goldPreis, Method m, String date) {
		try {
			m.invoke(goldPreis, date);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			System.err.println("Method not correct.");
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof NumberFormatException) {
				System.out.println("Got NumberFormatException as expected for " + date);
			} else {
				System.err.println("No number format exception, instead: " + e.getCause().getClass().getName()
						+ " value: " + e.getCause().toString());
			}
		}
	}

	private static void testPriceForDate(Object goldPreis, Method m, String date) {
		try {
			Object result = m.invoke(goldPreis, date);
			double expected = findExpected(date);
			if (result instanceof Double) {
				if ((double) result == expected) {
					System.out.println("Got " + result + " for date " + date + " as expected.");
				} else {
					System.err.println("Result " + result + " did not match " + expected);
				}
			} else {
				System.err.println("Result is not of type double but " + result.getClass().getName());
				System.err.println("Result is: " + result.toString());
				System.err.println("Expected is: " + expected);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.err.println("Method could not be called with '" + date + "' as parameter.");
			e.printStackTrace();
		}
	}

	private static void testGoldTagespreis() {
		Class<?> goldTagespreis = null;
		try {
			goldTagespreis = Class.forName("totest.GoldTagespreis");
		} catch (ClassNotFoundException e) {
			System.err.println("GoldTagespreis class not found.");
		}
		if (goldTagespreis != null) {
			mGoldTagesperis.assertFieldIs("datum", String.class);
			mGoldTagesperis.assertFieldIs("preis", Double.class);
		} else {
			System.err.println("GoldTagespreis is null.");
		}
	}

	

	private static double findExpected(String date) {
		try {
			Scanner sc = new Scanner(new File("gold.txt"));
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.startsWith(date)) {
					sc.close();
					if (line.contains("kein Nachweis")) {
						return -1;
					}
					return Double.parseDouble(line.split("\t")[1].replace(".", "").replace(',', '.'));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return -2;
	}
}
