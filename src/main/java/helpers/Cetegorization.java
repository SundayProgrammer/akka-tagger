package helpers;

import java.util.ArrayList;

public class Cetegorization {
    private ArrayList<Rule> rules;

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void addRules(Rule rule) {
        rules.add(rule);
    }
}

