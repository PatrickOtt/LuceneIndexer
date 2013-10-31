package de.hof_university.praktikumprog.luceneIndexer.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexDirectory {

	private File indexFolder;
	private File folder2Index;
	private final Version matchVersion = Version.LUCENE_45;
	
	private IndexWriter iw;
	
	
	public IndexDirectory() {
		
	}
	
	public void setIndexDirectory(File file) {
		this.indexFolder = file;
	}

	public void setDirectoryToIndex(File file) {
		this.folder2Index = file;
	}

	public void startIndexing() {
		try {
			// neues StandardAnalyzer Objekt besorgen
			Analyzer analyzer 		= new StandardAnalyzer(matchVersion);
			// IndexVerzeichnis öffnen
			Directory store			= FSDirectory.open(indexFolder);
			// Configurationsobjekt für den IndexWriter erstellen
			IndexWriterConfig iwc	= new IndexWriterConfig(matchVersion, analyzer);
			// IndexWriter Objekt anlegen
			iw						= new IndexWriter(store, iwc);
			
			runThroughDirectoryToIndex(folder2Index);
			
			
			// wir arbeiten ja ordentlich und schließen den IndexWriter wieder
			// ordnungsgemaess nach der Verwendung ;)
			iw.close();
		} catch (IOException e) {
			System.out.println();
		}
	}
	
	private void runThroughDirectoryToIndex(File folder2Index) {
		
		File[] files = folder2Index.listFiles();
		for(File f : files) {
			if(f.isDirectory()) {
				runThroughDirectoryToIndex(f);
			} else {
				System.out.println(f.getName());
					if(f.getName().endsWith(".xml")) {
						// falls es ein XML File ist holen wir uns einen
						// XMLParser und besorgen uns die Informationen
						// die uns interessieren
						
						// XMLParser parser = new XMLParser(f);
						
					} else {
						addDoc(iw, f.getName(), f.getName(), f.getAbsolutePath());
					}
			}
		}
	}

	private void addDoc(IndexWriter iw, String title, String content, String path) {
		
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("content", content, Field.Store.YES));
		doc.add(new TextField("path", path, Field.Store.YES));
		try {
			iw.addDocument(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Document added");
	}
}
