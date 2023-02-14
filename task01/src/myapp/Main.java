package myapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main{

    private static int port = 5000;
    private static String host = "localhost";

    public static void main(String[] args)throws IOException{
        Socket socket = null;

        if(args.length > 0){
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

        socket = new Socket(host,port);

        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        while(true){
            String randomNumsString = ois.readUTF();;

            // System.out.println(randomNumsString);

            List<Double> doubleList = stringToList(randomNumsString);

            // check the list
            for (Double num : doubleList) {
                System.out.println(num);
            }

            // check mean
            double mean = calculateMean(doubleList);
            System.out.println("Mean by method >>> " + mean);

            // check standard deviation
            double standDeviation = calculateStandardDeviation(doubleList);
            System.out.println("Standard deviation >>> " + standDeviation);

            oos.writeUTF("Yap Tat Wai");
            oos.flush();

            oos.writeUTF("davvyap@gmail.com");
            oos.flush();

            oos.writeFloat((float)mean);
            oos.flush();

            oos.writeFloat((float)standDeviation);
            oos.flush();

            ois.close();
            oos.close();
        }
    }

    public static List<Double> stringToList(String string){
        List<Double> list = new LinkedList<>();

        String[] array = string.split(",");

        for (String number : array) {
            list.add(Double.parseDouble(number));
        }

        return list;
    }

    public static Double calculateMean(List<Double> list){
        Double sum = 0.0;
        Double totalNum = (double) list.size();

        for (Double num : list) {
            sum += num;
        }
        return (sum/totalNum);
    }

    public static Double calculateStandardDeviation(List<Double> list){
        Double mean = calculateMean(list);
        Double sum = 0.0;
        Double sdSum = 0.0;
        for (Double num : list) {
            sum += num;
        }

        for (Double numDouble : list) {
            sdSum += Math.pow((numDouble - mean), 2);
        }

        Double standDeviation = sdSum/list.size();
        Double result = Math.sqrt(standDeviation);

        return result;
    } 
}