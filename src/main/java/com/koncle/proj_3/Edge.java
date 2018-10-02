package com.koncle.proj_3;

import com.koncle.Utils;

import java.util.List;

public class Edge {
    private int start;
    private int end;
    private Attribute attr;
    private List<Edge> rightExpression;
    private int index;
    private boolean active;

    public Edge(int start, int end, Attribute attr, List<Edge> rightExpression, boolean active) {
        this.start = start;
        this.end = end;
        this.index = 1;
        this.active = active;
        this.attr = attr;
        this.rightExpression = rightExpression;
    }

    public Edge(int start, int end, Attribute attr, List<Edge> rightExpression) {
        this(start, end, attr, rightExpression, false);
    }

    public int getStartPos() {
        return start;
    }

    public Boolean isActive() {
        return this.active;
    }

    public int getEndPos() {
        return this.end;
    }

    public Attribute getAttr() {
        return this.attr;
    }

    public int getIndex(){
        return index;
    }

    public int getRightExpressionSize() {
        return this.rightExpression == null ? 0 : this.rightExpression.size();
    }

    public List<Edge> getRightExpression() {
        return this.rightExpression;
    }

    public Attribute getLeftAttribute() {
        if (rightExpression == null || rightExpression.size() == 0) {
            return attr;
        } else {
            // return rightExpression.get(0).getLeftAttribute();
            return rightExpression.get(0).getAttr();
        }
    }

    public boolean match(Edge inactiveEdge) {
        if (!inactiveEdge.isActive()) {
            if (this.end == inactiveEdge.start &&
                    this.rightExpression.get(index).getAttr() == inactiveEdge.getAttr()) {
                return true;
            } else {
                return false;
            }
        }
        Utils.println("ERROR : get active rightExpression in Edge.match");
        return false;
    }

    public boolean isNearEnd() {
        return index + 1 == this.rightExpression.size();
    }

    public Edge expand(Edge inactiveEdge) {
        // move the index to next
        Edge expendedEdge =  new Edge(this.start, this.end + 1, this.attr, this.rightExpression, true);
        expendedEdge.getRightExpression().set(expendedEdge.getIndex()-1, inactiveEdge);
        return expendedEdge;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("(" + this.start + ", " + this.end + "), " + this.index + " " + this.attr);
        if (this.rightExpression != null) {
            sb.append(" -> ");
            for (Edge edge : this.rightExpression) {
                sb.append(edge.getAttr().getName() + " ");
            }
        }
        return sb.toString();
    }

    public void printSyntaxTree(){
        Utils.print(this.attr + " ");
        if (this.rightExpression != null) {
            Utils.print("-> ( ");
            for (Edge edge :this.rightExpression){
                edge.printSyntaxTree();
            }
            Utils.print(") ");
        }
    }

    public Edge copy(){
        return new Edge(this.start, this.end, this.attr, this.rightExpression, this.active);
    }
}
