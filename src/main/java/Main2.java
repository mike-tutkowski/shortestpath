import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main2 {
    private static final String START_WORD = "cat";
    private static final String TARGET_WORD = "dog";

    private static final Set<String> DICTIONARY = new HashSet<>();

    static {
        // For the sake of convenience, all dictionary words are just three-characters long.
        DICTIONARY.add(START_WORD);
        DICTIONARY.add(TARGET_WORD);
        DICTIONARY.add("sat");
        DICTIONARY.add("bog");
        DICTIONARY.add("cot");
        DICTIONARY.add("cog");
        DICTIONARY.add("bat");
        DICTIONARY.add("bot");
        DICTIONARY.add("rat");
        DICTIONARY.add("rad");
    }

    public static void main(String[] args) {
        List<String> storedWords = new ArrayList<>();

        storedWords.add(START_WORD);

        List<String> path = new ArrayList<>();

        path.add(START_WORD);

        printPaths(path, TARGET_WORD, storedWords);
    }

    private static void printPaths(List<String> path, String targetWord, List<String> storedWords) {
        String mostRecentWord = path.get(path.size() - 1);
        char[] word = mostRecentWord.toCharArray();

        for (int pos = 0; pos < word.length; pos++) {
            char origChar = word[pos];

            for (char ch = 'a'; ch <= 'z'; ch++) {
                word[pos] = ch;

                String newWord = String.valueOf(word);

                if (newWord.equals(targetWord)) {
                    path.add(targetWord);

                    System.out.println(path);

                    return;
                }

                if (!isWord(newWord) || storedWords.contains(newWord)) {
                    continue;
                }

                storedWords.add(newWord);

                List<String> newPath = new ArrayList<>(path);

                newPath.add(newWord);

                printPaths(newPath, targetWord, storedWords);
            }

            word[pos] = origChar;
        }
    }

    private static boolean isWord(String str) {
        return DICTIONARY.contains(str);
    }
}
