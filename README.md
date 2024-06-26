# PflanzenKleinanzeigen

## Projektbeschreibung

### Veranstaltung: Softwarepraktikum im Sommersemester 2024
### Projektstart: 11.04.2024

### Ziel des Projekts
Das Ziel dieses Projekts ist die Erstellung einer Webseite für den Verkauf und Kauf von Secondhand-Pflanzen.
Das Konzept orientiert sich an Kleinanzeigenportalen, fokussiert sich jedoch ausschließlich auf Pflanzen.

### Verwendung
- **Einloggen**: Logge dich ein, um Pflanzen zu verkaufen oder zu kaufen.
- **Pflanzen anzeigen**: Sieh dir alle Pflanzen an, die zum Verkauf stehen.
- **Nachricht schicken**: Schicke dem Verkäufer eine Nachricht, um mehr über die Pflanze zu erfahren.
- **Pflanze suchen**: Suche nach Pflanzen, die deinen Kriterien entsprechen.
- **Pflanzen verwalten**: Lade Pflanzen hoch, bearbeite sie oder markiere sie als verkauft.
- **Profile ansehen**: Sieh dir dein eigenes Profil an.
- 
### Achte darauf:
- **Deaktiviere eine Anzeige**: du willst deine Pflanze doch nicht mehr verkaufen? Dann deaktiviere sie unter meine Pflanzen. Diese Pflanzenanzeige wird dann nicht mehr angezeigt.
- **Lösche eine Pflanzenanzeige**: bitte beachte, dass du nur deine Pflanzen löschen kannst, wenn es keine Chats dazu existieren und die Pflanze noch nicht verkauft wurde. Sollte dies der Fall sein, dann kannst du diese nur noch deaktivieren.
- **Status der Pflanzenanzeige**: in den Chats wird angezeigt, ob eine Pflanzenanzeige immer noch aktiv ist, oder ob diese schon deaktiviert wurde.
- **Markiert eine Pflanze als verkauft**: Wenn du eine Pflanze als verkauft markierst, wird diese nicht mehr an die Pflanzenübersichtseite angezeigt, und alle users werden in den Chats sehen, dass du diese Pflanze verkauft hast.

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
   - Öffne [http://localhost:8080](http://localhost:8080/) in deinem Webbrowser, um auf die Anwendung zuzugreifen.
   - Öffne [http://localhost:8080/console](http://localhost:8080/console) um auf die Datenbank zuzugreifen.

5. **Einloggen**
   Da in dieser Anwendung keine Registrierung ermöglicht wird, müssen die Benutzerdaten manuell eingetragen werden.
   Teste die Anwendung gerne mit den Daten: Username: "user" und Password: "1234" oder füge die benötigten Benutzerdaten in die Datei:
   `PflanzenKleinanzeige/src/main/java/com/sopra/pflanzenkleinanzeigen/config/TestDataLoader.java` ein.

## Autoren
- **Andrea Chazarreta** - [AndreaChazarreta](https://github.com/AndreaChazarreta)
- **Natalie Schulz** - [NatalieSchulz](https://github.com/NatalieSchulz99)
- **Sharon Tabot** - [SharonTabot](https://github.com/sharii9)
- **Sena Demiröz** - [SenaDemiröz](https://github.com/Senadem03)

Test Issues