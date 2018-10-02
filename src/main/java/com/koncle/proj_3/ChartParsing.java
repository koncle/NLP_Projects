package com.koncle.proj_3;

import com.koncle.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChartParsing {
    private static final boolean DEBUG = true;

    private List<Edge> activeEdges = new ArrayList<Edge>();
    private List<Edge> inactiveEdges = new ArrayList<Edge>();

    public void run(){
        Agenda agenda = new Agenda("The cat caught a mouse");
        agenda.initFile();
        List<Edge> tmpActiveEdges = new ArrayList<>();

        while (!agenda.isEmpty()){
            // get a new element
            Word word = agenda.next();
            // get corresponding rules
            List<Edge> rules = Rules.getRulesByWord(word);
            // add active edges
            if(rules.size() != 0){
                for (Edge rule : rules){
                    if (rule.getRightExpressionSize() > 1) {
                        Edge edge = new Edge(word.getStart(), word.getEnd(), rule.getAttr(), rule.getRightExpression(), true);
                        edge.getRightExpression().set(0, word.getAppendedInfo());
                        tmpActiveEdges.add(edge);
                    }else{
                        Attribute leftAttr = rule.getAttr();
                        Word nextWord = new Word(leftAttr.getName(), leftAttr, word.getStart(), word.getEnd());
                        nextWord.setAppendedInfo(rule);
                        agenda.add(nextWord);
                    }
                }
            }

            //create  a new rule for inactive edge with right attributes = null
            Edge inactiveEdge = new Edge(word.getStart(), word.getEnd(), word.getAttr(), word.getAppendedInfo().getRightExpression(), false);
            // add inactive edges
            inactiveEdges.add(inactiveEdge);

            // expand active edges
            for(Edge edge : activeEdges){
                if (edge.match(inactiveEdge)){
                    if (edge.isNearEnd()){
                        Attribute leftAttribute = edge.getAttr();
                        Word tmpWord = new Word(leftAttribute.getName(), leftAttribute, edge.getStartPos(), edge.getEndPos()+1);
                        Edge copyEdge = edge.copy();
                        copyEdge.getRightExpression().set(copyEdge.getIndex(), word.getAppendedInfo());
                        tmpWord.setAppendedInfo(edge);
                        agenda.add(tmpWord);
                    }else {
                        Edge newActive = edge.expand(inactiveEdge);
                        activeEdges.add(newActive);
                    }
                }
            }
            activeEdges.addAll(tmpActiveEdges);
            tmpActiveEdges.clear();

            if (DEBUG) {
                printEdges();
                Utils.println("");
            }
        }
        if(inactiveEdges.get(inactiveEdges.size()-1).getAttr() == Attribute.S){
            Utils.println("Right syntax");
            inactiveEdges.get(inactiveEdges.size()-1).printSyntaxTree();
        }
    }

    private void printEdges(){
        Utils.println("Inactive edges");
        inactiveEdges.forEach(System.out::println);

        Utils.println("Active edges");
        activeEdges.forEach(System.out::println);
    }

    public static void main(String[] args){
        new ChartParsing().run();
    }
}
