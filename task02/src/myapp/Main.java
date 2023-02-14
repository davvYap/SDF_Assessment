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

    private static String dirName = "seuss";

    public static void main(String[] args) throws IOException{

        if(args.length > 0){

            dirName = args[0];
            List<String> dirList = listDirFiles(dirName);

            for (String path : dirList) {
                System.out.println("Current path >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + path);
                NextWord doc = new NextWord(path);
                List<String> wordTextData = doc.getWordsFromPhrase();
                List<String> list = doc.getUniqueList();
                Map<String,Map<String,Integer>> map = doc.getFinalMap();
                Map<String,Map<String,Double>> finalMap = doc.getSolutionMap(map);
                doc.print(finalMap);
            }

            // testing for 1st text file
            // String path1 = dirList.get(0);
            // NextWord doc1 = new NextWord(path1);
            // List<String> wordTextData1 = doc1.getWordsFromPhrase();
            // List<String> list1 = doc1.getUniqueList();
            // Map<String,Map<String,Integer>> map1 = doc1.getFinalMap();
            // Map<String,Map<String,Double>> finalMap1 = doc1.getSolutionMap(map1);
            // doc1.print(finalMap1);
        }
    }

    public static List<String> listDirFiles(String dirName) throws IOException{

        List<String> list = new ArrayList<>();
        File directory = new File(dirName);
        if (directory.exists()){
            File[] fileList = directory.listFiles();
            
            for (File file : fileList) {
                // System.out.println("File absolute path >>> " + file.getAbsolutePath());
                list.add(file.getAbsolutePath());
            }

        }else{
            System.out.println("Directory " + dirName + " does not exist.");
        }

        return list;
    }

    
}


