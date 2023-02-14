package myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
            for (String punc : punctuations) {
                String newLine = line.trim().replaceAll("\\p{Punct}", " ");
                String[] tempArray = newLine.split(" ");
                for (String word : tempArray) {
                    wordList.add(word);
                }
            }
        }

        return wordList;
    }
}
