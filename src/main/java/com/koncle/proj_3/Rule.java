package com.koncle.proj_3;

import com.koncle.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rule {
    private Attribute startAttr;
    private List<Attribute> attributes;

    public Rule(Attribute startAttr, Attribute[] rightAttributes){
        this.startAttr = startAttr;
        if (rightAttributes != null) {
            this.attributes = Arrays.asList(rightAttributes);
        }else {
            this.attributes = null;
        }
    }

    public Attribute firstAttr() {
        return attributes.get(0);
    }

    public List<Attribute> getRightAttributes() {
        return attributes;
    }

    public Attribute getLeftAttribute() {
        return startAttr;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(this.startAttr.getName());
        if (this.attributes != null) {
            s.append("->");
            for (Attribute attr : this.attributes) {
                s.append(attr.getName()).append(" ");
            }
        }
        return s.toString();
    }
}
