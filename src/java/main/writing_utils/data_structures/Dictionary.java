package writing_utils.data_structures;

import writing_utils.io.Word;

public class Dictionary implements Reference {

	private TernarySearchTree tst = null;

	@Override
	public String lookup(String word) {
		Object[] ret = this.tst.getExtraDataForWord(word);
		return ret != null ? (String) ret[0] : "Word not Found!";
	}

	@Override
	public boolean contains(String word) {
		return this.tst.containsWord(word);
	}

	@Override
	public void init(Word[] words) {
		this.tst = TernarySearchTreeFactory.buildDictionaryTST(words);
	}
}
