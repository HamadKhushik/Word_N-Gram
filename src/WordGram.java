public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt " + index);
        }
        return myWords[index];
    }

    public int length() {
        return myWords.length;
    }

    public String toString() {
        String ret = "";
        for (String k : myWords) {
            ret = ret + k + " ";
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        if ((this.length() != other.length())) {
            return false;
        }
        for (int k = 0; k < myWords.length; k++) {
            // if (!myWords[k].equals(other.wordAt(k))){
            if (!myWords[k].equals(other.myWords[k])) {
                return false;
            }
        }
        return true;

    }

    public WordGram shiftAdd(String word) {
        WordGram out = new WordGram(myWords, 0, myWords.length);
        // shift all words one towards 0 and add word at the end.
        // you lose the first word
        for (int k = 0; k < out.length(); k++) {
            if (k == out.length() - 1) {
                out.myWords[k] = word;
            } else {
                out.myWords[k] = out.myWords[k + 1];
            }
        }
        return out;
    }

    public int hashCode() {
        //String words = myWords.toString();
        //String words = Arrays.toString(myWords);
        return toString().hashCode();
    }

}
