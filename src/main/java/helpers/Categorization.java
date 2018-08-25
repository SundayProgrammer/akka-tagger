package helpers;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public ArrayList<String> readSentences(String path) throws FileNotFoundException {

        ArrayList<String> sentences = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            String sentence;
            while ((sentence = br.readLine()) != null)  {
                sentences.add(sentence);
            }
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }

        return sentences;
    }
}

