package com.koncle.proj_3;

import java.util.ArrayList;
import java.util.List;

public class Rules {
    private static final Attribute[] startAttributes = {
            Attribute.S,
            Attribute.NP,
            Attribute.NP,
            Attribute.VP,
            Attribute.VP
    };

    private static final Attribute[][] rightAttributes = {
            {Attribute.NP, Attribute.VP},
            {Attribute.ART, Attribute.N},
            {Attribute.ART, Attribute.ADJ, Attribute.N},
            {Attribute.V},
            {Attribute.V, Attribute.NP}
    };

    private static List<Rule> rules = new ArrayList<Rule>();

    static {
        for (int i = 0; i < startAttributes.length; ++i){
            Rule rule = new Rule(startAttributes[i], rightAttributes[i]);
            rules.add(rule);
        }
    }

    public static List<Rule> getRulesByWord(Word word){
        Attribute rightFirstAttr = word.getAttr();
        List<Rule> ret = new ArrayList<Rule>();
        for (Rule rule : rules){
            if (rule.firstAttr() == rightFirstAttr){
                ret.add(rule);
            }
        }
        return ret;
    }
}
