package writing_utils.io;

public class Word {
	String theWord;
	String definition;
	String synonyms;
	String antonyms;

	public Word() { this("", "", "", ""); }
	public Word(String theWord, String definition, String synonyms, String antonyms) {
		this.theWord = theWord.toLowerCase();
		this.definition = definition;
		this.synonyms = synonyms;
		this.antonyms = antonyms;
	}

	public String getTheWord() { return theWord; }
	public String getDefinition() { return definition; }
	public String getSynonyms() { return synonyms; }
	public String getAntonyms() { return antonyms; }

	void setTheWord(String theWord) { this.theWord = theWord.toLowerCase(); }
	void setDefinition(String definition) { this.definition = definition; }
	void setSynonyms(String synonyms) { this.synonyms = synonyms; }
	void setAntonyms(String antonyms) { this.antonyms = antonyms; }
}
