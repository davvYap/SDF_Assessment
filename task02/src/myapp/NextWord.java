package myapp;

import java.io.BufferedReader;
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

public class NextWord {
    private String path;
    private List<String> allWordsList = new ArrayList<>();
    private List<String> uniqueList = new ArrayList<>();

    //constructor
    public NextWord(String path) throws IOException {
        this.path = path;
        // this.allWordsList = Main.getWordsFromPhrase(path);
    }


    public List<String> getDataFromText() throws IOException{
        
        BufferedReader br = new BufferedReader(new FileReader(path));

        List<String> list = new LinkedList<>();

        String line;

        while ((line = br.readLine()) != null){
            list.add(line);
        }
        br.close();
        return list;
    }

    public List<String> getWordsFromPhrase() throws IOException{
        List<String> rawList = getDataFromText();
        List<String> wordList = new LinkedList<>();

        for (String line : rawList) {
            String newLine = line.replaceAll("\\p{Punct}", "");
            String[] tempArray = newLine.split(" ");
            for (String word : tempArray) {
                String newWord = word.replaceAll("\\s+","");
                wordList.add(newWord.trim().toLowerCase()); //here
            }
        }
        allWordsList = wordList;
        return wordList;
    }

    public List<String> getUniqueList() throws IOException{
        List<String> uniqueWords = new ArrayList<>();
        for (String word : allWordsList) {
            if(!uniqueWords.contains(word)){
                uniqueWords.add(word);
            }
        }
        uniqueList = uniqueWords;
        return uniqueWords;
    }

    // to get count of each word
    public Map<String,Integer> getWordCount() throws IOException{
        List<String> words = getWordsFromPhrase();
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
    public Map<String,Integer> getNextWordFreqCount(String word) throws IOException{
        // get all words (not unique) from txt file
        List<String> allWords = getWordsFromPhrase();

        List<Integer> nextWordIndex = new ArrayList<>();
        List<String> nextWordList = new ArrayList<>();

        //System.out.println("Word selected >>> " + word);
        
        int index = allWords.indexOf(word);
        // System.out.println("Index of word selected >>> " + index);
        // System.out.println(">>> " + allWords.get(index));

        Map<String,Integer> map = getWordCount();
        int count = map.get(word);
        // System.out.println("Count of word selected >>> " + count);

        // use iterator to iterate the list and get the index
        Iterator<String> iterator = allWords.iterator();
        int i = 0;
        while(iterator.hasNext() && i < allWords.size()-1){
            String item = iterator.next();
            if(word.equals(item)){
                nextWordIndex.add(i+1);
            }
            i++;
        }
        //System.out.println("last index >>> " + allWords.lastIndexOf(word));
        //System.out.println(nextWordIndex);

        for (int position : nextWordIndex) {
            nextWordList.add(allWords.get(position));
        }
        // System.out.println(nextWordList);

        // put the next words in a map
        Map<String,Integer> nextWordFreqMap = new HashMap<>();
        for (String string : nextWordList) {
            if(nextWordFreqMap.containsKey(string)){
                nextWordFreqMap.put(string,nextWordFreqMap.get(string)+1);
            }else{
                nextWordFreqMap.put(string,1);
            }
        }
        return nextWordFreqMap;
    }


    public Map<String,Map<String,Integer>> getFinalMap() throws IOException{
        Map<String,Map<String,Integer>> finalMap = new HashMap<>();
        
        for (String string : uniqueList) {
            finalMap.put(string, getNextWordFreqCount(string));
        }
        return finalMap;
    }

    public Map<String,Map<String,Double>> getSolutionMap(Map<String,Map<String,Integer>> map){
        Map<String,Map<String,Double>> answerMap = new HashMap<>();
    
        //calculate the probability
        for(Map.Entry<String, Map<String,Integer>> t :map.entrySet()){
            String key = t.getKey();

            Set<Entry<String,Integer>> set = t.getValue().entrySet();
            List<Entry<String,Integer>> tempList = new ArrayList<>(set);
            
            //tempList.forEach(e -> System.out.println(e));
            // for each key, put the key.entrySet() after the division of total
            double total = 0;
            Map<String,Double> tempMap = new HashMap<>();
            for (Entry<String,Integer> entry : tempList) {
                // System.out.println("value >>> " + entry.getValue());
                total += entry.getValue();
                tempMap.put(entry.getKey(), entry.getValue()/total);
            }
            answerMap.put(key, tempMap);
        }
        return answerMap;
    }

    public void print(Map<String,Map<String,Double>> map){
        for(Map.Entry<String, Map<String,Double>> t :map.entrySet()){
            String key = t.getKey();
            System.out.println(key + "\n");
          for (Map.Entry<String,Double> e : t.getValue().entrySet())
            System.out.println("\t" + e.getKey()+ " " +e.getValue());
        }
     }
}
