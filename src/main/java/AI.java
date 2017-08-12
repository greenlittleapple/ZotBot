import java.io.*;
import java.util.*;

public class AI {

    private static ArrayList<String> words = new ArrayList<>();
    private static ArrayList<Integer> wordTimes = new ArrayList<>();
    private static ArrayList<Integer> wordWeights = new ArrayList<>();
    private static ArrayList<String> classes = new ArrayList<>();
    private static Map<String, ArrayList<String>> classMap = new HashMap<>();
    private static PrintWriter printer;

    public static void start() throws FileNotFoundException {
//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//            public void run() {
//                writeFiles();
//            }
//        }, "Shutdown-thread"));
        classMap.put("finance", new ArrayList<>());
        classMap.put("bio", new ArrayList<>());
        classMap.put("compsci", new ArrayList<>());
        classMap.put("eng", new ArrayList<>());
        readData();
        writeTempFiles();
    }

    public static String receiveInput(String inputSentence) {
        double maxScore = calculateScore(inputSentence, "finance");
        String bestType = "finance";
        for(String type : classes) {
            double score = calculateScore(inputSentence, type);
            if (score > maxScore) {
                maxScore = score;
                bestType = type;
            }
        }
        writeTempFiles();
        System.out.println(bestType + " " + maxScore);
        if(maxScore > 1) {
            return bestType + " " + maxScore;
        }
        return "null " + maxScore;
    }

    public static void receiveFeedback(boolean feedback) {

    }

    private static double calculateScore(String sentence, String classtype) {
        double strength = 0;
        for(String x : tokenize(sentence)) {
            System.out.println("word: " + x + ", class: " + classtype + ", contains: " + classMap.get(classtype).contains(x));
            if(classMap.get(classtype).contains(x)) {
                strength += (1.00 / wordTimes.get(words.indexOf(x)));
            }
            System.out.println("strength: " + strength);
        }
        return strength;
    }

    private static void countWords(String[] input) {
        for(int i = 0; i < input.length; i++) {
            if(words.contains(input[i])) {
                int pos = words.indexOf(input[i]);
                wordTimes.set(pos, wordTimes.get(pos) + 1);
            } else {
                words.add(input[i]);
                wordTimes.add(1);
            }
        }
    }

    private static String[] tokenize(String sentence) {
        String[] result = sentence.split("\\s");
        return result;
    }

    private static void readData() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("words.txt"));
        while (scan.hasNext()) {
            words.add(scan.next());
            wordTimes.add(Integer.parseInt(scan.next()));
        }
        System.out.println("words: " + words.toString() + ", wordTimes: " + wordTimes.toString());
        scan.close();
        scan = new Scanner(new File("classes.txt"));
        while (scan.hasNext()) {
            classes.add(scan.next());
        }
        scan.close();
        scan = new Scanner(new File("finance.txt"));
        while (scan.hasNext()) {
            classMap.get("finance").add(scan.next());
        }
        System.out.println("Finance: " + classMap.get("finance").toString());
        scan.close();
        scan = new Scanner(new File("bio.txt"));
        while (scan.hasNext()) {
            classMap.get("bio").add(scan.next());
        }
        scan.close();
        scan = new Scanner(new File("compsci.txt"));
        while (scan.hasNext()) {
            classMap.get("compsci").add(scan.next());
        }
        scan.close();
        scan = new Scanner(new File("eng.txt"));
        while (scan.hasNext()) {
            classMap.get("eng").add(scan.next());
        }
        scan.close();
    }

    private static void writeTempFiles() {
        try {
            printer = new PrintWriter("wordsTemp.txt");
            for(String x : words) {
                printer.println(x);
            }
            printer = new PrintWriter("classesTemp.txt");
            for(String x : classes) {
                printer.println(x);
            }
            printer = new PrintWriter("financeTemp.txt");
            for(String x : classMap.get("finance")) {
                printer.println(x);
            }
            printer = new PrintWriter("bioTemp.txt");
            for(String x : classMap.get("bio")) {
                printer.println(x);
            }
            printer = new PrintWriter("compsciTemp.txt");
            for(String x : classMap.get("compsci")) {
                printer.println(x);
            }
            printer = new PrintWriter("engTemp.txt");
            for(String x : classMap.get("eng")) {
                printer.println(x);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        printer.close();
    }

    private static void writeFiles() {
        try {
            printer = new PrintWriter("words.txt");
            Scanner scan = new Scanner(new File("wordsTemp.txt"));
            while (scan.hasNext()) {
                printer.println(scan.next());
            }
            scan.close();
            printer = new PrintWriter("classes.txt");
            scan = new Scanner(new File("classesTemp.txt"));
            while (scan.hasNext()) {
                printer.println(scan.next());
            }
            scan.close();
            printer = new PrintWriter("finance.txt");
            scan = new Scanner(new File("financeTemp.txt"));
            while (scan.hasNext()) {
                printer.println(scan.next());
            }
            scan.close();
            printer = new PrintWriter("bio.txt");
            scan = new Scanner(new File("bioTemp.txt"));
            while (scan.hasNext()) {
                printer.println(scan.next());
            }
            scan.close();
            printer = new PrintWriter("compsci.txt");
            scan = new Scanner(new File("compsciTemp.txt"));
            while (scan.hasNext()) {
                printer.println(scan.next());
            }
            scan.close();
            printer = new PrintWriter("eng.txt");
            scan = new Scanner(new File("engTemp.txt"));
            while (scan.hasNext()) {
                printer.println(scan.next());
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        printer.close();
    }
}
