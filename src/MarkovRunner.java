import edu.duke.FileResource;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size) {
        markov.setTraining(text);
        System.out.println("running with " + markov);
        for (int k = 0; k < 3; k++) {
            String st = markov.getRandomText(size);
            printOut(st);
        }
    }

    public void runModel(IMarkovModel markov, String text, int size, int seed) {
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for (int k = 0; k < 3; k++) {
            String st = markov.getRandomText(size);
            printOut(st);
        }
    }

    public void runMarkov() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovWordOne markovWord = new MarkovWordOne();
        runModel(markovWord, st, 200);
    }

    public void runMarkovTwo() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovWordTwo mw2 = new MarkovWordTwo();
        runModel(mw2, st, 120, 832);
    }

    private void printOut(String s) {
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for (int k = 0; k < words.length; k++) {
            System.out.print(words[k] + " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println();
                psize = 0;
            }
        }
        System.out.println("\n----------------------------------");
    }

    public static void main(String[] args) {
        MarkovWordOne mw1 = new MarkovWordOne();
        String s = "this is just a test yes this is a simple test";
        //FileResource fr = new FileResource();
        //String st = fr.asString();
        //st = st.replace('\n', ' ');
        MarkovRunner mr = new MarkovRunner();
        //mr.runModel(mw1, st, 120, 139);
        //mr.runMarkovTwo();

        MarkovWordTwo mw2 = new MarkovWordTwo();
        //mr.runModel(mw2, s, 120, 549);
    }

}