package de.hof_university.praktikumprog.luceneIndexer.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchDirectory {

	private List<String> results = new ArrayList<String>();
	
	private Directory indexStore;
	private IndexSearcher indexSearcher;
	private File indexDirectory;
	
	public SearchDirectory(final File indexDirectory) {
		this.indexDirectory = indexDirectory;
		initializeIndexSearcher();
	}
	
	private void initializeIndexSearcher() {
		try {
			indexStore = FSDirectory.open(indexDirectory);
			IndexReader indexReader = DirectoryReader.open(indexStore);
			indexSearcher = new IndexSearcher(indexReader);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> searchWithQuery(String queryString) {
		QueryParser parser = new QueryParser(Version.LUCENE_45, "content", new StandardAnalyzer(Version.LUCENE_45));
		Query query = null;
		try {
			query = parser.parse(queryString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		TopScoreDocCollector collector = TopScoreDocCollector.create(10000, false);
		try {
			indexSearcher.search(query, collector);
			ScoreDoc[] docs = collector.topDocs().scoreDocs;
			for(int i = 0; i < docs.length; ++i) {
				int docId = docs[i].doc;
				results.add(indexSearcher.doc(docId).get("content") + "," + indexSearcher.doc(docId).get("title"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	public List<String> searchWithPhrase(String queryString) {
		Analyzer analyzer = new KeywordAnalyzer();
		QueryParser parser	= new QueryParser(Version.LUCENE_45, "content", analyzer);
		parser.setAllowLeadingWildcard(true);
		parser.setAutoGeneratePhraseQueries(true);
		
		if(indexSearcher == null) {
			initializeIndexSearcher();
		}
		
		try {
			Query query = parser.parse(queryString);
			TopScoreDocCollector collector = TopScoreDocCollector.create(100, true);
			indexSearcher.search(query, collector);
			ScoreDoc[] docs = collector.topDocs().scoreDocs;
			for(ScoreDoc sc : docs) {
				results.add("Score: " + sc.score + " " + indexSearcher.doc(sc.doc).get("content"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}
	public void closeSearchDirectory() {
		if(indexStore != null) {
			try {
				indexStore.close();
			} catch (IOException e) {
				
			}
			
		}
	}
}
