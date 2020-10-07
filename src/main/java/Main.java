/* Given a starting word and an ending word, write a function that
 * returns the shortest chain of words that link the two in which
 * two adjacent words differ by only one letter.
 *
 * Note: I interpret "the shortest chain" to mean the first path I
 * can find that has the shortest length from the start word to the
 * target word.
 */

import java.util.*;

class Main
{
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

    private static Optional<List<String>> getShortestPath(String startWord, String targetWord) {
        if (startWord == null) {
            startWord = "";
        }

        if (targetWord == null) {
            targetWord = "";
        }

        startWord = startWord.toLowerCase();
        targetWord = targetWord.toLowerCase();

        if (!isWord(startWord)) {
            throw new RuntimeException("The start word is not a dictionary word.");
        }

        if (!isWord(targetWord)) {
            throw new RuntimeException("The end word is not a dictionary word.");
        }

        if (startWord.length() != targetWord.length()) {
            throw new RuntimeException("The start and target words must have the same length.");
        }

        if (startWord.equals(targetWord)) {
            throw new RuntimeException("The start and target words must be different words.");
        }

        List<String> storedWords = new ArrayList<>();

        storedWords.add(startWord);

        List<String> startPath = new ArrayList<>();

        startPath.add(startWord);

        Queue<List<String>> queue = new LinkedList<>();

        queue.add(startPath);

        while (!queue.isEmpty()) {
            List<String> path = queue.remove();
            // We need to focus on the last item added to the path.
            char[] word = path.get(path.size() - 1).toCharArray();

            for (int pos = 0; pos < word.length; pos++) {
                char origChar = word[pos];

                for (char ch = 'a'; ch <= 'z'; ch++) {
                    word[pos] = ch;

                    String newWord = String.valueOf(word);

                    if (newWord.equals(targetWord)) {
                        path.add(targetWord);

                        return Optional.of(path);
                    }

                    if (!isWord(newWord) || storedWords.contains(newWord)) {
                        continue;
                    }

                    storedWords.add(newWord);

                    List<String> newPath = new ArrayList<>(path);

                    newPath.add(newWord);

                    queue.add(newPath);
                }

                word[pos] = origChar;
            }
        }

        return Optional.empty();
    }

    private static boolean isWord(String str) {
        return DICTIONARY.contains(str);
    }

    public static void main(String[] args) {
        Optional<List<String>> shortestPath = getShortestPath(START_WORD, TARGET_WORD);

        if (shortestPath.isPresent()) {
            System.out.println(shortestPath.get());
        } else {
            System.out.println("There is no path.");
        }

        // Just flip the start word with the target word and re-run getShortestPath.

        shortestPath = getShortestPath(TARGET_WORD, START_WORD);

        if (shortestPath.isPresent()) {
            System.out.println(shortestPath.get());
        } else {
            System.out.println("There is no path.");
        }
    }
}
