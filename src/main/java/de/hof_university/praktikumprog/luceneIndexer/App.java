package de.hof_university.praktikumprog.luceneIndexer;

import java.io.File;

import de.hof_university.praktikumprog.luceneIndexer.model.IndexDirectory;

/**
 * Einsprungpunkt
 * Hier wird die Anwendung gestartet
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        IndexDirectory indexer = new IndexDirectory();
        // hier das Verzeichnis eintragen, in das der Index geschrieben werden soll
        indexer.setIndexDirectory(new File("/home/ottp/Testindex"));
        // hier das Verzeichnis eintrage, das indiziert werden soll
        indexer.setDirectoryToIndex(new File("/home/ottp/Downloads"));
        // Indizierung starten
        indexer.startIndexing();
        
        
    }
}
