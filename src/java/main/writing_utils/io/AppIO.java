package writing_utils.io;

import writing_utils.config.ConfigHandler;
import writing_utils.config.Configuration;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import writing_utils.data_structures.Dictionary;
import writing_utils.data_structures.SpellCheck;
import writing_utils.data_structures.Thesaurus;

import java.io.IOException;
import java.io.InputStream;


public class AppIO {
	private XMLReader configReader;
	private XMLReader dataReader;

	private ConfigHandler config;
	private DataHandler dataHandler;

	public AppIO(Dictionary dict, Thesaurus thes, SpellCheck spell) {
		try {
			configReader = XMLReaderFactory.createXMLReader();
			dataReader = XMLReaderFactory.createXMLReader();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		config = new ConfigHandler();
		dataHandler = new DataHandler();
		WordDelagate del = new WordDelagate();
		del.setOutlets(dict, thes, spell);
		dataHandler.addDelegate(del);

		configReader.setContentHandler(config);
		dataReader.setContentHandler(dataHandler);
	}

	public void readWords(InputStream data_file) {
		try {
			dataReader.parse(new InputSource(data_file));
		} catch (IOException | SAXException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	public Configuration readConfig(InputStream config_file) {
		try {
			configReader.parse(new InputSource(config_file));
		} catch (IOException | SAXException | NullPointerException e) {
			e.printStackTrace();
		}
		return config.getConfiguration();
	}

	public void saveConfig() {
		//TODO save configuration
		return;
	}


}
