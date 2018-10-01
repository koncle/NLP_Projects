package com.koncle.proj_3;

public class Word {
    private String word;
    private Attribute attr;
    private int start;
    private int end;

    public Word(String word, Attribute attr, int start, int end){
        this.word = word;
        this.attr = attr;
        this.start = start;
        this.end = end;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Attribute getAttr() {
        return attr;
    }

    public void setAttr(Attribute attr) {
        this.attr = attr;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
