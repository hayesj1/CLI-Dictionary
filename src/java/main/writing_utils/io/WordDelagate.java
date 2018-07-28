package writing_utils.io;

import org.xml.sax.Attributes;
import writing_utils.data_structures.Dictionary;
import writing_utils.data_structures.SpellCheck;
import writing_utils.data_structures.Thesaurus;

import java.util.HashSet;

public class WordDelagate implements  DataDelegate {
	private HashSet<Word> words = null;

	private Dictionary dict;
	private Thesaurus thes;
	private SpellCheck spell;

	@Override
	public void initialize() {
		words = new HashSet<>();
	}

	@Override
	public void receiveData(String element, Attributes atts) {
		switch(element) {
			case "word":
				Word word = new Word();
				for (int i = 0; i < atts.getLength(); i++) {
					String value = atts.getValue(i);
					switch (atts.getQName(i)) {
						case "val":
							word.setTheWord(value);
							break;
						case "def":
							word.setDefinition(value);
							break;
						case "syn":
							word.setSynonyms(value);
						case "ant":
							word.setAntonyms(value);
						default:
							break;
					}
				}
				this.words.add(word);
				break;
			default:
				break;
		}
	}

	@Override
	public void completed() {
		Word[] words = this.words.toArray(new Word[0]);
		dict.init(words);
		thes.init(words);
		spell.init(words);
	}

	public void setOutlets(Dictionary dict, Thesaurus thes, SpellCheck spell) {
		this.dict = dict;
		this.thes = thes;
		this.spell = spell;
	}
}
