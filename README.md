# PflanzenKleinanzeigen

## Projektbeschreibung

### Veranstaltung
Softwarepraktikum im Sommersemester 2024

### Projektstart
11.04.2024

### Ziel des Projekts
Ziel dieses Projekts ist die Erstellung einer Webseite für den Verkauf und Kauf von Secondhand-Pflanzen. Das Konzept orientiert sich an Kleinanzeigenportalen, fokussiert sich jedoch ausschließlich auf Pflanzen jeder Art.

### Verwendung
- **Einloggen**: Logge dich mit deinen Nutzerdaten ein, um Pflanzen zu verkaufen oder zu kaufen.
- **Pflanzen anzeigen**: Sieh dir alle Pflanzen an, die zum Verkauf stehen. Du kannst die Pflanzen auch einzeln mit allen Details ansehen.
- **Nachricht schicken**: Schicke dem Verkäufer eine Nachricht, um mehr über die Pflanze zu erfahren.
- **Pflanze suchen**: Suche nach Pflanzen, die deinen Kriterien entsprechen. Du kannst auch nach Pflanzen mit bestimmten Eigenschaften filtern.
- **Pflanzen verwalten**: Lade Pflanzen hoch, bearbeite sie oder markiere sie als verkauft.
- **Profile ansehen**: Sieh dir dein eigenes Profil an.
- **Merkliste**: Merke dir Pflanzen, die du später kaufen möchtest.
- **Pflanzen vergleichen**: Vergleiche Pflanzen aus deiner Merkliste miteinander, um die beste für dich zu finden.
- **Pflanzen kaufen und verkaufen**: Kaufe oder verkaufe Pflanzen.
- **Pflegetipps einsehen**: Sieh dir Pflegetipps für deine Pflanzen an. Du kannst diese auch als PDF herunterladen und ausdrucken.
- **Bei Fragen**: Schreibe über das Kontaktformular eine Nachricht, wenn du Fragen hast. Es gibt auch ein Forum, das häufig gestellte Fragen beantwortet. Sieh dir zuerst dort an, ob deine Frage bereits beantwortet wurde.

### Wichtige Hinweise
- **Anzeige entfernen**: Möchtest du deine Pflanze doch nicht mehr verkaufen? Entferne sie unter "Meine Pflanzen". Die Anzeige wird dann nicht mehr angezeigt, aber die Nachrichten, die du mit anderen Usern geschrieben hast, bleiben erhalten.
- **Status der Pflanzenanzeige**: In den Chats wird angezeigt, ob eine Pflanzenanzeige noch aktiv oder bereits deaktiviert ist.
- **Pflanze als verkauft markieren**: Wenn du eine Pflanze als verkauft markierst, wird diese nicht mehr auf der Pflanzenübersichtsseite angezeigt. Alle User sehen in den Chats, dass die Pflanze verkauft wurde.
- **Pflanzenanzeige bearbeiten**: Du kannst deine Pflanzenanzeige bearbeiten, z.B. den Preis oder das Bild ändern.

## Installation
1. **Repository klonen**
    ```bash
    git clone https://github.com/AndreaChazarreta/PflanzenKleinanzeige.git
    ```
2. **In das Projektverzeichnis wechseln**
    ```bash
    cd PflanzenKleinanzeige
    ```
3. **Abhängigkeiten installieren**
   - Stelle sicher, dass du Gradle installiert hast.
   - Starte die Anwendung über den Gradle Task `bootRun` (im Tasks Ordner `application`) oder über:
    ```bash
    ./gradlew bootRun
    ```
4. **Auf die Anwendung zugreifen**
   - Öffne [http://localhost:8080/login](http://localhost:8080/login) in deinem Webbrowser, um dich anzumelden und auf die Anwendung zuzugreifen.
   - Öffne [http://localhost:8080/console](http://localhost:8080/console), um auf die Datenbank zuzugreifen.

5. **Einloggen**:
   Da in dieser Anwendung keine Registrierung ermöglicht wird, müssen die Benutzerdaten manuell eingetragen werden. Teste die Anwendung gerne mit den Daten: Username: `admin` und Password: `admin` oder füge die benötigten Benutzerdaten in die Datei `PflanzenKleinanzeige/src/main/java/com/sopra/pflanzenkleinanzeigen/config/TestDataLoader.java` ein.

## Autoren
- **Andrea Chazarreta** - [AndreaChazarreta](https://github.com/AndreaChazarreta)
- **Natalie Schulz** - [NatalieSchulz](https://github.com/NatalieSchulz99)
- **Sharon Tabot** - [SharonTabot](https://github.com/sharii9)
- **Sena Demiröz** - [SenaDemiröz](https://github.com/Senadem03)
