Installation der Datenbankanbindung:

1) In die Java-Entwicklungsumgebung muss die JDBC-Anbindung für SQLite als Paket eingebunden werden. Die entsprechende JAR-Datei ist derzeit unter folgender URL zu beziehen: https://bitbucket.org/xerial/sqlite-jdbc/downloads

2) Zudem wird die Klasse Queue<ContentType> aus den aktuellen Datenstrukturklassen für das Abitur benötigt.

Anschließend können die Klassen DatabaseConnector und QueryResult verwendet werden.

Beachten Sie, dass der Konstruktor für die Klasse DatabaseConnector eine IP, einen Port, einen Benutzernamen und ein Passwort erwartet. Da SQLite diese Funktionen gar nicht unterstützt, können jeweils beliebige Werte übergeben werden. Lediglich die Datenbankdatei muss korrekt angegeben werden.