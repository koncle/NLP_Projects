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
            Edge word = agenda.next();
            // get corresponding rules
            List<Edge> rules = Rules.getRulesByWord(word);
            // add active edges
            if(rules.size() != 0){
                for (Edge rule : rules){
                    // more than 1 nodes
                    if (rule.getRightExpressionSize() > 1) {
                        Edge edge = new Edge(word.getStart(), word.getEnd(), rule.getAttr(), rule.getRightExpression(), true);
                        // the first element is just the word, so
                        // replace the it can preserve tree structure
                        edge.getRightExpression().set(0, word);
                        tmpActiveEdges.add(edge);
                    }else{
                        // just one node
                        Edge nextWord = new Edge(word.getStart(), word.getEnd(),  rule.getAttr(), rule.getRightExpression(), false);
                        agenda.add(nextWord);
                    }
                }
            }

            //create  a new rule for inactive edge with right attributes = null
            Edge inactiveEdge = new Edge(word.getStart(), word.getEnd(), word.getAttr(), word.getRightExpression(), false);
            // add inactive edges
            inactiveEdges.add(inactiveEdge);

            // expand active edges
            for(Edge activeEdge : activeEdges){
                // match next edge
                if (activeEdge.match(inactiveEdge)){
                    // if is near end
                    if (activeEdge.isNearEnd()){
                        // get a copy of current active edge
                        Edge copyEdge = activeEdge.copy();
                        // replace corresponding edge to preserve tree structure
                        copyEdge.getRightExpression().set(copyEdge.getIndex(), word);
                        // end + 1 due to expand
                        copyEdge.setEnd(copyEdge.getEnd()+1);
                        agenda.add(copyEdge);
                    }else {
                        Edge newActive = activeEdge.expand(inactiveEdge);
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
        // if the last element is End attribute
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
