package writing_utils.data_structures;

import writing_utils.io.Word;

public class TernarySearchTreeFactory {

	public static TernarySearchTree buildDictionaryTST(Word[] words) {
		TernarySearchTree ret = new TernarySearchTree();
		rbuildDictionaryTST(ret, words);
		return ret;
	}

	public static TernarySearchTree buildThesaurusTST(Word[] words) {
		TernarySearchTree ret = new TernarySearchTree();
		rbuildThesaurusTST(ret, words);
		return ret;
	}

	private static void rbuildDictionaryTST(TernarySearchTree tst, Word[] words) {
		if (words.length == 1) {
			tst.insertWord(words[0].getTheWord(), new Object[] { words[0].getDefinition() });
		} else {
			int middle = words.length / 2;
			tst.insertWord(words[middle].getTheWord(), new Object[] { words[middle].getDefinition() });

			Word[] newWords = new Word[middle];
			System.arraycopy(words, 0, newWords, 0, middle);
			rbuildDictionaryTST(tst, newWords);

			int parity = words.length % 2;
			if (parity == 0) {
				newWords = new Word[middle-1];
			}
			System.arraycopy(words, middle+1, newWords, 0, (parity == 0 ? middle-1 : middle) );
			rbuildDictionaryTST(tst, newWords);
		}
	}

	private static void rbuildThesaurusTST(TernarySearchTree tst, Word[] words) {
		if (words.length == 1) {
			tst.insertWord(words[0].getTheWord(), new Object[] { words[0].getSynonyms(), words[0].getAntonyms() });
		} else {
			int middle = words.length / 2;
			tst.insertWord(words[middle].getTheWord(), new Object[] { words[middle].getSynonyms(), words[middle].getAntonyms() } );

			Word[] newWords = new Word[middle];
			System.arraycopy(words, 0, newWords, 0, middle);
			rbuildThesaurusTST(tst, newWords);

			int parity = words.length % 2;
			if (parity == 0) {
				newWords = new Word[middle-1];
			}
			System.arraycopy(words, middle+1, newWords, 0, (parity == 0 ? middle-1 : middle) );
			rbuildThesaurusTST(tst, newWords);
		}
	}
}
