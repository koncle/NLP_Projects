package com.koncle.proj_3;

public enum Attribute {
    S("S"), NP("NP"), VP("VP"), ART("ART"), N("N"), ADJ("ADJ"), V("V");

    private final String attr;

    private Attribute(String attr){
        this.attr = attr;
    }

    public String getName(){
        return this.attr;
    }

}
