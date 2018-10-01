package com.koncle.proj_3;

import com.koncle.Utils;

public class Edge {
    private int start;
    private int end;
    private Rule rule;
    private int index;
    private boolean active;

    public Edge(int start, int end, Rule rule, boolean active){
        this.start = start;
        this.end = end;
        this.rule = rule;
        this.index = 1;
        this.active = active;
    }
    public int getStartPos(){
        return start;
    }

    public Boolean isActive(){
        return this.active;
    }

    public boolean match(Edge inactiveEdge) {
        if (!inactiveEdge.isActive()){
            if (this.end == inactiveEdge.start &&
                    this.rule.getRightAttributes().get(index)== inactiveEdge.rule.getLeftAttribute()){
                return true;
            }else {
                return false;
            }
        }
        Utils.println("ERROR : get active edge in Edge.match");
        return false;
    }

    public boolean isNearEnd(){
        return index + 1 == rule.getRightAttributes().size();
    }

    public Edge expand() {
        // move the index to next
        return new Edge(this.start, this.end+1, this.rule, true);
    }

    public Attribute getLeftAttribute() {
        return rule.getLeftAttribute();
    }

    public int getEndPos() {
        return this.end;
    }

    public String toString(){
        return "(" + this.start + ", " + this.end + "), " + this.index + " " + rule.toString();
    }
}
