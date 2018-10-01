package com.koncle;


import java.io.*;
import java.util.*;

public class WordRecover {
    private static String[][] RULES = {
            {"ing", ""},
            {"ing", "e"},
            {"s", ""},
            {"ies", "y"},
            {"es", ""},
            {"ying", "ie"},
            {"ed", ""},
            {"ed", "e"},
            {"ied", "y"}
    };
    private static String[][] DOUBLE_RULES = {
            {"ing", ""},
            {"ed", ""}
    };

    public static void main(String[] args) {
        WordRecover wordRecover = new WordRecover();
        wordRecover.start();
    }

    public Map<String, String> readFile(String path) {
        Map<String, String> dict = new HashMap<String, String>();
        try {
            String encoding = "UTF-8";
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("dic_ec.txt");
            print("process...");
            InputStreamReader reader = new InputStreamReader(
                    stream, encoding
            );
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ", 2);
                if (strings.length < 2) {
                    print(line);
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

    public <T> void print(T s) {
        System.out.println(s);
    }

    public void start() {
        String path = "src/dic_ec.txt";
        Map<String, String> dict = this.readFile(path);
        while (true) {
            print("Input a word");
            Scanner scanner = new Scanner(System.in);
            String word = scanner.nextLine().trim();

            if (word.startsWith(".E")) {
                break;
            }

            if (word.contains(" ")) {
                print("Invalid word!");
            }

            String[] res = findWord(dict, word);
            String recoveredWord = res[0];
            String info = res[1];
            if (recoveredWord == null) {
                if (info == null) {
                    print("Failed to find and recover the word!");
                } else {
                    print("word : " + word + ". Attribute : " + info);
                }
            } else {
                print("Recovered word : " + recoveredWord + ". Attribute : " + info);
            }
        }
    }

    private String[] findWord(Map<String, String> dict, String word) {
        String info = null;
        String recover_word = null;
        if (dict.keySet().contains(word)) {
            info = dict.get(word);
        } else {
            // search for first RULES
            for (String[] rule : RULES) {
                if (word.endsWith(rule[0])) {
                    recover_word = word.replace(rule[0], rule[1]);
                    if (dict.keySet().contains(recover_word)) {
                        info = dict.get(recover_word);
                        break;
                    }
                }
            }
            int len = word.length();
            if (info == null && len > 5 && word.charAt(len - 4) == word.charAt(len - 5)) {
                char last_char = word.charAt(len - 4);
                for (String[] rule : DOUBLE_RULES) {
                    recover_word = word.replace(last_char + rule[0], rule[1]);
                    if (dict.containsKey(recover_word)) {
                        info = dict.get(recover_word);
                        break;
                    }
                }
            }
        }
        return new String[]{recover_word, info};
    }
}
