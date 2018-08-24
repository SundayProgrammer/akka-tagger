package helpers;

import java.util.ArrayList;

public class Categorization {
    private ArrayList<Rule> rules;

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void readRules(String path) {
        
    }
}

