package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.eclipse.rdf4j.repository.Repository;

import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;

public class Worker {

	protected BigdataSail sail;
    protected Repository repository;
	
	public void work() throws IOException {
		final Properties props = loadProperties("/blazegraph.properties");
		// instantiate a sail
		final String journalFile = (String) props.get("com.bigdata.journal.AbstractJournal.file");
		FileUtils.deleteQuietly(new File(journalFile));

		System.out.println("Initializing sail");
		sail = new BigdataSail(props);
		System.out.println("Sail initialized");
		repository = (Repository) new BigdataSailRepository(sail);
		repository.initialize();
		System.out.println("Repository retrieved: " + repository);
	}

	public Properties loadProperties(final String resource) throws IOException {
		final Properties p = new Properties();
		final InputStream is = this.getClass().getResourceAsStream(resource);
		p.load(new InputStreamReader(new BufferedInputStream(is)));
		return p;
	}

	
}
