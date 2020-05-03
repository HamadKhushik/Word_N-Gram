import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> map;

    public EfficientMarkovWord(int order) {
        myOrder = order;
        myRandom = new Random();
        map = new HashMap<WordGram, ArrayList<String>>();
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text) {
        myText = text.split("\\s+");
        buildMap();
    }

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
        return map.get(kGram);
    }

    public String getRandomText(int numWords) {
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        WordGram key = new WordGram(myText, index, myOrder);
        sb.append(key.toString());
        sb.append(" ");
        //buildMap();
        //printHashMapInfo();
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
        return "Efficient Markov Word";
    }

    public void buildMap() {
        //HashMap<WordGram, ArrayList<String>> map = new HashMap<WordGram, ArrayList<String>>();
        map.clear();
        WordGram wGram = new WordGram(myText, 0, myOrder);
        //ArrayList<String> follows = new ArrayList<String>();
        //follows.add(myText[myOrder]);
        for (int k = myOrder; k < myText.length; k++) {
            if (myText.length == 0) {
                break;
            }
            if (!map.containsKey(wGram)) {
                ArrayList<String> follows = new ArrayList<String>();
                follows.add(myText[k]);
                map.put(wGram, follows);
                wGram = wGram.shiftAdd(myText[k]);
            } else if (map.containsKey(wGram)) {
                // {
                ArrayList<String> follows = new ArrayList<String>();
                follows = map.get(wGram);
                follows.add(myText[k]);
                map.put(wGram, follows);
                wGram = wGram.shiftAdd(myText[k]);
            }
        }
        wGram = new WordGram(myText, myText.length - myOrder, myOrder);
        //System.out.println("WordGram : " + wGram);
        if (!map.containsKey(wGram)) {
            ArrayList<String> follows = new ArrayList<String>();
            follows = new ArrayList<String>();
            map.put(wGram, follows);
        }
        printHashMapInfo();
    }

    public static void main(String[] args) {
        EfficientMarkovWord emw = new EfficientMarkovWord(2);
        String s = "this is just a test yes this is a simple test";
        emw.setTraining(s);
        emw.buildMap();
        System.out.println("myText : " + Arrays.toString(emw.myText));
        /*for (WordGram wg : emw.map.keySet()) {
            ArrayList<String> folowed = emw.map.get(wg);
            System.out.println("Word Gram : " + wg + ", Followed : " + folowed);
        }*/
        emw.printHashMapInfo();
    }

    private void printHashMapInfo() {
        WordGram key = null;
        int maxValue = 0;
        ArrayList<WordGram> maxValueInMap = new ArrayList<WordGram>();
        for (WordGram wg : map.keySet()) {
            if (key == null) {
                key = wg;
            }
            ArrayList<String> values = map.get(wg);
            if (values.size() > maxValue) {
                key = wg;
                maxValue = values.size();
                maxValueInMap.clear();
                maxValueInMap.add(key);
            } else if (values.size() == maxValue) {
                maxValueInMap.add(wg);
            }
            //System.out.println("Key : " + wg + ", Value : " + values);
        }
        System.out.println("Number of keys in HashMap : " + map.size());
        System.out.println("Largest ArrayList in map with the keys : " + maxValueInMap);
        System.out.println("values for the keys are : ");
        for (WordGram wg : maxValueInMap) {
            System.out.println("Size : " + map.get(wg).size());
            System.out.println(map.get(wg));
        }
    }

}
