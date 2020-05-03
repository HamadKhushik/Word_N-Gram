import java.util.ArrayList;
import java.util.Random;

public class MarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;

    public MarkovWord(int order) {
        myOrder = order;
        myRandom = new Random();
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text) {
        myText = text.split("\\s+");
    }

    /*public int indexOf(String[] words, WordGram target, int start) {
        int index = 0;
        for (int k = start; k < words.length - myOrder; k++) {
            if (words[k].equals(target.wordAt(0))) {
                for (int i = 0; i < target.length(); i++) {
                    System.out.println("Word in array : " + words[k + i] + ", " + "words in gram : " + target.wordAt(i));
                    System.out.println("index : " + index);
                    if (!words[k + i].equals(target.wordAt(i))) {
                        break;
                    }
                    index = k;
                }
            }
        }
        return index;
    }*/

    public int indexOf(String[] words, WordGram target, int start) {
        WordGram test = new WordGram(words, start, target.length());
        //int index = 0;
        for (int k = start; k < words.length - myOrder; k++) {
            //System.out.println(test);
            if (test.equals(target)) {
                //System.out.println("test : " + test + ", " + "Target : " + target + ", K : " + k);
                return k;
            }
            //System.out.println(words[k + 1]);
            test = test.shiftAdd(words[k + myOrder]);

        }
        return -1;
    }

    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        //for (int k = 0; k < myText.length - kGram.length(); k++) {
        int k = 0;
        while (k < myText.length - kGram.length()) {
            int index = indexOf(myText, kGram, k);
            if (index == -1 || index >= myText.length - kGram.length()) {
                break;
            }
            follows.add(myText[index + myOrder]);
            k = index + myOrder;
        }
        //System.out.println("Follows : " + follows);
        return follows;
    }

    public String getRandomText(int numWords) {
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        WordGram key = new WordGram(myText, index, myOrder);
        sb.append(key.toString());
        sb.append(" ");
        for (int k = 0; k < numWords - myOrder; k++) {
            //System.out.println("Key : " + key);
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }
        return sb.toString().trim();
    }

    public String toString() {
        return "Markov Word";
    }

    public static void main(String[] args) {
        MarkovWord mWord = new MarkovWord(3);
        String s = "this is just a test yes this is a simple test";
        mWord.setTraining(s);
        WordGram wg = new WordGram(mWord.myText, 8, 3);
        System.out.println("Word Gram words : " + wg);
        int index = mWord.indexOf(mWord.myText, wg, 0);
        System.out.println("Index : " + index);
    }
}
