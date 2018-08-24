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

class Rule {
    public final String[] requiredWords;
    public final String[] bannedWords;
    public final String category;

    public Rule(String[] requiredWords, String[] bannedWords, String category) {
        this.requiredWords = requiredWords;
        this.bannedWords = bannedWords;
        this.category = category;
    }
}