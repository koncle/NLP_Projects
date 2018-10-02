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

    private static List<Edge> rules = new ArrayList<>();

    static {
        for (int i = 0; i < startAttributes.length; ++i){
            List<Edge> rightEdges = new ArrayList<>();
            for (int j = 0; j < rightAttributes[i].length; ++j){
                rightEdges.add(new Edge(-1, -1, rightAttributes[i][j], null));
            }
            rules.add(new Edge(-1, -1, startAttributes[i], rightEdges));
        }
    }

    public static List<Edge> getRulesByWord(Edge word){
        Attribute rightFirstAttr = word.getAttr();
        List<Edge> ret = new ArrayList<>();
        for (Edge rule : rules){
            if (rule.getLeftAttribute() == rightFirstAttr){
                ret.add(rule);
            }
        }
        return ret;
    }
}
