package com.koncle.proj_3;
import com.koncle.Utils;
import org.w3c.dom.Attr;

import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Agenda {
    private String[] input;
    private int index = 0;
    Stack<Edge> wordStack = new Stack<>();

    public Agenda(){
        getInput();
    }

    public void getInput(){
        Utils.println("Please getInput a line");
        Scanner scanner = new Scanner(System.in);
        this.input = scanner.nextLine().trim().split(" ");
    }

    public Agenda(String s){
        this.input = s.trim().split(" ");
    }

    public boolean isEmpty(){
        return wordStack.isEmpty() && this.index == this.input.length;
    }

    public Edge next(){
        if (wordStack.isEmpty()){
            String w = input[index++];
            Attribute attr = getAttribute(w);
            // use edge class to represent word, so that it can put more
            // info to preserve tree structure.
            Edge word = new Edge(index-1, index, attr, null);
            this.wordStack.push(word);
        }
        return this.wordStack.pop();
    }

    public Attribute getAttribute(String word) {
        Attribute attr = null;
        String s = word.toLowerCase();

        switch (s) {
            case "the":
                attr = Attribute.ART;
                break;
            case "cat":
                attr = Attribute.N;
                break;
            case "caught":
                attr = Attribute.V;
                break;
            case "a":
                attr = Attribute.ART;
                break;
            case "mouse":
                attr = Attribute.N;
                break;
            default:
                Utils.println("Wrong!!!!");
                break;
        }
        return attr;
    }

    public void add(Edge word) {
        this.wordStack.push(word);
    }

    public void initFile() {
        Map<String, String> wordMap = Utils.readFile("dic_ec.txt" );
    }
}
