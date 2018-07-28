package writing_utils.data_structures;

public class TernarySearchTree {
	private TSTNode root = null;

	void insertWord(String word, Object[] extraData) {
		word = word.toLowerCase();

		if (this.root == null) {
			this.root = new TSTNode(word.charAt(0), word.length() == 1);
		}
		rinsertWord(this.root, null, extraData, word, 0);
	}

	public boolean containsWord(String word) {
		word = word.toLowerCase();
		TSTNode ret =  rfindWordNode(this.root, word, 0);
		return ( ret != null && ret.isEndOfWord );
	}

	public Object[] getExtraDataForWord(String word) {
		word = word.toLowerCase();
		TSTNode ret =  rfindWordNode(this.root, word, 0);
		return ( ret != null ? ret.extraData : null );
	}

	private void rinsertWord(TSTNode curNode, TSTNode prevNode, Object[] extraData, String word, int idx) {
		char chr = word.charAt(idx);
		if (curNode == null) {
			curNode = new TSTNode(chr, idx == word.length() - 1);
			curNode.setParent(curNode != this.root ? prevNode : null);
			char prevChr = idx > 0 ? word.charAt(idx - 1) : chr;
			if (prevChr < prevNode.getChar()) {
				prevNode.setLesserChild(curNode);
			} else if (prevChr > prevNode.getChar()) {
				prevNode.setGreaterChild(curNode);
			} else {
				prevNode.setEqualChild(curNode);
			}
		}

		if (chr < curNode.getChar()) {
			rinsertWord(curNode.lesserChild, curNode, extraData, word, idx);
		} else if (chr > curNode.getChar()) {
			rinsertWord(curNode.greaterChild, curNode, extraData, word, idx);
		} else {
			if (idx == word.length()-1) {
				curNode.setEndOfWord(true);
				curNode.setExtraData(extraData);
			} else { rinsertWord(curNode.equalChild, curNode, extraData, word, ++idx); }
		}
	}

	private TSTNode rfindWordNode(TSTNode curNode, String word, int idx) {
		if(curNode == null) {
			return null;
		}

		char chr = word.charAt(idx);

		if (chr < curNode.getChar()) {
			return rfindWordNode(curNode.lesserChild, word, idx);
		} else if (chr > curNode.getChar()) {
			return rfindWordNode(curNode.greaterChild, word, idx);
		} else {
			if (idx == word.length()-1 && curNode.isEndOfWord) { return curNode; }
			else { return rfindWordNode(curNode.equalChild, word, ++idx); }
		}
	}

	final class TSTNode {
		char chr;
		boolean isEndOfWord;
		TSTNode parent;
		TSTNode lesserChild;
		TSTNode equalChild;
		TSTNode greaterChild;
		Object[] extraData = null;

		TSTNode(char chr) { this(chr, false); }
		TSTNode(char chr, boolean isEndOfWord) { this(chr, isEndOfWord,null, null ,null); }
		TSTNode(char chr, TSTNode lesser, TSTNode equal, TSTNode greater) { this(chr, false, lesser, equal, greater); }
		TSTNode(char chr, boolean isEndOfWord, TSTNode lesser, TSTNode equal, TSTNode greater) {
			this.chr = chr;
			this.isEndOfWord = isEndOfWord;
			this.lesserChild = lesser;
			this.equalChild = equal;
			this.greaterChild = greater;
		}

		char getChar() { return this.chr; }
		Object[] getExtraData() { return this.extraData; }

		void setExtraData(Object[] extraData) { this.extraData = extraData; }
		void setEndOfWord(boolean isEndOfWord) { this.isEndOfWord = isEndOfWord; }

		void setParent(TSTNode parent) {this.parent = parent; }
		void setLesserChild(TSTNode lesser) { this.lesserChild = lesser; }
		void setEqualChild(TSTNode equal) { this.equalChild = equal; }
		void setGreaterChild(TSTNode greater) { this.greaterChild = greater; }
	}
}
