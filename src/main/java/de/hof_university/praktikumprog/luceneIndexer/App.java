package de.hof_university.praktikumprog.luceneIndexer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import de.hof_university.praktikumprog.luceneIndexer.model.IndexDirectory;
import de.hof_university.praktikumprog.luceneIndexer.model.SearchDirectory;

/**
 * Einsprungpunkt
 * Hier wird die Anwendung gestartet
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // IndexDirectory indexer = new IndexDirectory();
        // hier das Verzeichnis eintragen, in das der Index geschrieben werden soll
        // indexer.setIndexDirectory(new File("/home/ottp/Testindex"));
        // hier das Verzeichnis eintrage, das indiziert werden soll
        // indexer.setDirectoryToIndex(new File("/home/ottp/Downloads"));
        // Indizierung starten
        // indexer.startIndexing();
    	
    	/*
    	SearchDirectory searchDirectory = new SearchDirectory(new File("/home/ottp/Testindex"));
    	String queryString = "\"IMG_20120519_142936.jpg\"";
    	List<String> searchWithQuery = searchDirectory.searchWithQuery(queryString);
    	System.out.println("Ergebnis(se) der Query");
    	for(String s : searchWithQuery) {
    		System.out.println(s);
    	}
    	searchDirectory.closeSearchDirectory();
    	*/
    	
    	SearchDirectory searchDirectory = new SearchDirectory(new File("/home/ottp/Testindex"));
    	List<String> searchResults = searchDirectory.searchWithPhrase("*ie werde");
    	for(String s : searchResults) {
    		System.out.println(s);
    	}
    }
}