package com.koncle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Utils {
    public static <T> void println(T s){
        System.out.println(s);
    }
    public static <T> void print(T s){
        System.out.print(s);
    }

    public static Map<String, String> readFile(String name) {
        // name = "dic_ec.txt"
        // splitNum = 2
        Map<String, String> dict = new HashMap<String, String>();
        try {
            String encoding = "UTF-8";
            InputStream stream = Utils.class.getClassLoader().getResourceAsStream(name);
            println("process...");
            InputStreamReader reader = new InputStreamReader(
                    stream, encoding
            );
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ", 2);
                if (strings.length < 2) {
                    println(line);
                    continue;
                }
                dict.put(strings[0], strings[1]);
            }
            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dict;
    }

    public static void printAttributeSet(){
        Map<String, String> map = readFile("dic_ec.txt");
        Set<String> attr = new HashSet<>();
        for (String s : map.keySet()){
            attr.add(map.get(s).split(" ")[0]);
        }
        int time = 0;
        for (String s : attr){
            print(s+" ");
            if (++time % 5 == 0){
                println("");
            }
        }
    }

    public static void main(String[] args){
        printAttributeSet();
    }
}
