package writing_utils.data_structures;

import writing_utils.io.Word;

public interface Reference {

	String lookup(String word);
	boolean contains(String word);

	void init(Word[] words);
}
