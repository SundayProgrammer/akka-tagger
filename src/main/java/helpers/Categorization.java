package helpers;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Categorization {
    private ArrayList<Rule> rules;

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void readRules(String path) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(path));
        Rule[] rulesArray = gson.fromJson(reader, Rule[].class);
        this.rules = new ArrayList<Rule>(Arrays.asList(rulesArray));
    }
}

