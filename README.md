# HACtrl
Dieses Projekt kann genutzt werden um Klassen sehr einfach via Reflection zu testen.

# Benutzung
Zuerst muss eine Referenz Implementation der gewünschten Klassen gemacht werden die getestet werden sollen.
Diese Implementation sollte vollkommen funktionsfähig sein und die gewünschten Attribut- und Methodennamen besitzen.
Danach kann eine Testklasse geschrieben werden:  

```java
public class TestingExample {
  
  private static MassertAdvanced<AbgegebeneLoesung> testRun = new MassertAdvaced<>(AbgegebeneLoesung.class);
  
  public static void main(String... args) {
    // Initialisiert den Test Run mit dem default Konstruktor und dem übergebenem Referenz-Object.
    // Andere Konstruktoren können einfach aufgerufen werden indem die gewünschten Parameter übergeben werden.
    testRun.init(new ReferenzKlasse(), new Object[0]);
    
    // Füge Test hinzu, der prüft, ob die Ausgaben der toString Methode aus der Referenz Klasse mit den Ausgaben
    // der toString Methode der AbgegebeneLoesung Klasse übereinstimmt
    testRun.addRun("toString", new Object[0]);
    
    // Füge Test hinzu, der die Methode 'setName' mit dem String paramter 'test' auf beiden Objekten aufruft
    // deren Ausgaben abgleicht.
    testRun.addRun("setName", "test");
    
    // Füge Test hinzu, der die Methode 'getName' ohne parameter aufruft und deren Ausgaben abgleicht.
    testRun.addRun("getName", new Object[0]);
    
    // Führt alle tests in der Reihenfolge aus, in der sie mit 'addRun' hinzugefügt wurden.
    testRun.doRun();
    
    // Testet ob alle Attribute von gleichem Typ und Namen sind wie in der Referenz Klasse.
    testRun.testFields();
  }
}
```
