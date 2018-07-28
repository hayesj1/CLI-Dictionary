package writing_utils;

import writing_utils.data_structures.Dictionary;
import writing_utils.data_structures.Reference;
import writing_utils.data_structures.SpellCheck;
import writing_utils.data_structures.Thesaurus;
import writing_utils.io.AppIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class WritingUtils {
	private static Scanner in = new Scanner(System.in);
	private static Scanner inFile = null;
	private static boolean interactive = false;
	private static boolean useFile = false;

	private static Dictionary dict = new Dictionary();
	private static Thesaurus thes = new Thesaurus();
	private static SpellCheck spell = new SpellCheck();
	private static AppIO appIO = null;

	public static void main(String[] args) {
		System.out.println("Welcome to Writing Utils!");
		parseArgs(args);
		initDataStructures();
		parseCommands();
	}

	private static void initDataStructures() {
		appIO = new AppIO(dict, thes, spell);

		Class loader = System.class;
		try (InputStream data_file = loader.getResource("/words.xml").openStream()) {
			appIO.readWords(data_file);
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseArgs(String[] args) {
		if (args.length <= 0) {
			return;
		}
	}

	private static void parseCommands() {
		boolean exit = false;
		do {
			while(!in.hasNextLine()) {
				try { Thread.sleep(200); }
				catch (InterruptedException e) {}
			}

			String command = in.nextLine();
			String[] tokens = command.split("\\s+");
			for (int i = 0; i < tokens.length; i++) { tokens[i] = tokens[i].toLowerCase(); }

			try { exit = parseCommand(tokens); }
			catch (FileNotFoundException e) {
				System.err.println(e.getLocalizedMessage());
				exit = false;
			}
		} while (!exit);
	}

	private static boolean parseCommand(String[] tokens) throws FileNotFoundException {
		interactive = tokens.length > 1 && ( tokens[1].equals("-i") );

		switch(tokens[0]) {
			case "dict":
			case "dictionary":
				useReference(tokens, dict);
				break;
			case "thes":
			case "thesaurus":
				useReference(tokens, thes);
				break;
			case "spell":
			case "check":
			case "spell-check":
				spellCheck(tokens);
				break;
			case "quit":
			case "exit":
				//TODO: cleanup resources
				return true;
			default:
				printUsage();
				return false;
		}

		return false;
	}

	private static void useReference(String[] tokens, Reference ref) {
		if (!interactive) {
			String word = tokens[1];
			System.out.println(ref.lookup(word));
		} else {
			do {
				String word = in.next();
				if (word.equals("#quit")) { break; }

				System.out.println(ref.lookup(word));
			} while (true);
		}
	}

	private static void spellCheck(String[] tokens) throws FileNotFoundException {
		final int paramIdx = interactive ? 2 : 1;
		useFile = (tokens[ paramIdx ].matches("-f=?+"));

		if (useFile) {
			String fname = tokens[ paramIdx ].substring(3);
			try {
				inFile = new Scanner(new File(fname));
			} catch (FileNotFoundException e) {
				FileNotFoundException fnfe = new FileNotFoundException("File "+fname+" does not exist or isn't a file!");
				fnfe.initCause(e);
				throw fnfe;
			}
		}
	}

	private static void printUsage() {
		System.out.println("Usages:");
		System.out.println("< dict | dictionary > [ -i ] < word >");
		System.out.println("< thes | thesaurus > [ -i ] < word >");
		System.out.println("< spell | check | spell-check  > [ -i ] < word | -f=file_name >");
		System.out.println("< quit | exit >");
		System.out.println("Flags:");
		System.out.println("-i --> interactive mode; type \'#quit\' to exit interactive mode; default is false");
		System.out.println("-f --> spell check an entire file");
		System.out.println("");

	}
}
