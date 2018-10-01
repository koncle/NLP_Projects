package com.koncle.proj_3;

import com.koncle.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChartParsing {
    private static final boolean DEBUG = false;

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
            List<Rule> rules = Rules.getRulesByWord(word);
            // add active edges
            if(rules.size() != 0){
                for (Rule rule : rules){
                    if (rule.getRightAttributes().size() > 1) {
                        Edge edge = new Edge(word.getStart(), word.getEnd(), rule, true);
                        tmpActiveEdges.add(edge);
                    }else{
                        Attribute leftAttr = rule.getLeftAttribute();
                        Word nextWord = new Word(leftAttr.getName(), leftAttr, word.getStart(), word.getEnd());
                        agenda.add(nextWord);
                    }
                }
            }

            //create  a new rule for inactive edge with right attributes = null
            Rule inactiveRule = new Rule(word.getAttr(), null);
            Edge inactiveEdge = new Edge(word.getStart(), word.getEnd(), inactiveRule, false);
            // add inactive edges
            inactiveEdges.add(inactiveEdge);

            // expand active edges
            for(Edge edge : activeEdges){
                if (edge.match(inactiveEdge)){
                    if (edge.isNearEnd()){
                        Attribute leftAttribute = edge.getLeftAttribute();
                        agenda.add(new Word(leftAttribute.getName(), leftAttribute, edge.getStartPos(), edge.getEndPos()+1));
                    }else {
                        Edge newActive = edge.expand();
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
        if(inactiveEdges.get(inactiveEdges.size()-1).getLeftAttribute() == Attribute.S){
            Utils.println("Right syntax");
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
