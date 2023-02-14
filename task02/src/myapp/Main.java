package myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Main {

    private static String dirName = "";

    public static void main(String[] args) throws IOException{

        if(args.length > 0){

            dirName = args[0];
            List<String> dirList = listDirFiles(dirName);

            // testing for 1st text file
            String path1 = dirList.get(0);
            List<String> textData1 = getDataFromText(path1);
            for (String string : textData1) {
                System.out.println(string);
            }

            List<String> wordTextData1 = getWordsFromPhrase(path1);
            for (String string : wordTextData1) {
                System.out.print(string + " > ");
            }

            Map<String,Integer> mapTextData1 = getWordCount(path1);
            mapTextData1.forEach((k,v) -> System.out.println(k + " >>> " + v));

            Map<String,Integer> testingMap = getNextWordFreqCount(path1, "hands");
        }
    }

    public static List<String> listDirFiles(String dirName) throws IOException{

        List<String> list = new ArrayList<>();
        File directory = new File(dirName);
        if (directory.exists()){
            File[] fileList = directory.listFiles();
            
            for (File file : fileList) {
                System.out.println("File absolute path >>> " + file.getAbsolutePath());
                list.add(file.getAbsolutePath());
            }

        }else{
            System.out.println("Directory " + dirName + " does not exist.");
        }

        return list;
    }

    public static List<String> getDataFromText(String path) throws IOException{
        
        BufferedReader br = new BufferedReader(new FileReader(path));

        List<String> list = new LinkedList<>();

        String line;

        while ((line = br.readLine()) != null){
            list.add(line);
        }
        br.close();
        return list;
    }

    public static List<String> getWordsFromPhrase(String path) throws IOException{
        
        String[] punctuations = {".", ",", ":", "!", "-", "(", ")", "\'", "\"", ";"}; 
        List<String> rawList = getDataFromText(path);
        List<String> wordList = new LinkedList<>();

        for (String line : rawList) {
            String newLine = line.trim().replaceAll("\\p{Punct}", " ");
            String[] tempArray = newLine.split(" ");
            for (String word : tempArray) {
                wordList.add(word.toLowerCase());
            }
        }
        return wordList;
    }

    // to get count of each word
    public static Map<String,Integer> getWordCount(String path) throws IOException{
        List<String> words = getWordsFromPhrase(path);
        Map<String,Integer> wordsMap = new HashMap<>();

        for (String word : words) {
            if(wordsMap.containsKey(word)){
                wordsMap.put(word, wordsMap.get(word)+1);
            }else{
                wordsMap.put(word, 1);
            }
        }
        return wordsMap;
    }

    // to get the frequency of next word for each word
    public static Map<String,Map<String,Integer>> getFreqCountTest(String path) throws IOException{
        Map<String,Map<String,Integer>> nextWordFreq = new HashMap<>();

        // to get the unique key for each word
        Map<String,Integer> map = getWordCount(path);
        Set<Entry<String,Integer>> set = map.entrySet();
        List<Entry<String,Integer>> list = new ArrayList<>(set);

        // list to store unique key from the txt file
        List<String> words = new ArrayList<>();

        for (Entry<String,Integer> entry : list) {
            words.add(entry.getKey());
        }

        // print to check
        // for (String string : words) {
        //     System.out.println("Keys : " + string);
        // }

        // get all words (not unique) from txt file
        List<String> allWords = getWordsFromPhrase(path);

        for (int i = 0; i < allWords.size()-1; i++) {   // word
            for (int j = 1; j < allWords.size(); j++) {     // next word
                if(nextWordFreq.containsKey(allWords.get(i)) && nextWordFreq.get(allWords.get(i)).containsKey(allWords.get(j))){
 
                }
            }
        }
        
        return nextWordFreq;
    }

    // to get the frequency of next word for each word
    public static Map<String,Integer> getNextWordFreqCount(String path, String word) throws IOException{
        Map<String,Integer> nextWordFreqMap = new HashMap<>();

        // get all words (not unique) from txt file
        List<String> allWords = getWordsFromPhrase(path);

        List<Integer> nextWordIndex = new ArrayList<>();

        System.out.println("Word selected >>> " + word);
        System.out.println("Size of list >>> " + allWords.size());
        
        int index = allWords.indexOf(word);
        System.out.println("Index of word selected >>> " + index);
        System.out.println(">>> " + allWords.get(index));

        Map<String,Integer> map = getWordCount(path);
        int count = map.get(word);
        System.out.println("Count of word selected >>> " + count);

        // use iterator to iterate the list and get the index
        Iterator<String> iterator = allWords.iterator();
        int i = 0;
        while(iterator.hasNext()){
            String item = iterator.next();
            if(word.equals(item)){
                nextWordIndex.add(i);
            }
            i++;
        }
        System.out.println("last index >>> " + allWords.lastIndexOf(word));
        System.out.println(nextWordIndex);

        return nextWordFreqMap;
    }
}
