package actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import helpers.Rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AggregatorActor extends AbstractActor {

    static public Props props(ArrayList<Rule> rules) {
        return Props.create(AggregatorActor.class, () -> new AggregatorActor(rules));
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final ArrayList<Rule> rules;
    private LinkedList<String> filteredCategories;

    public AggregatorActor(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    static public class Words {
        public final List<String> extractedWords;

        public Words(List extractedWords) {
            this.extractedWords = extractedWords;
        }
    }

    static public class MatchedCategories {
        public final String message;

        public MatchedCategories(String message) {
            this.message = message;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Words.class, x -> {
                    List<Rule> matchedRules = new LinkedList<>();
                    for (Rule rule: this.rules) {
                        int count = rule.requiredWords.length;
                        for (String word: rule.requiredWords) {
                            for (String extractedWord: x.extractedWords) {
                                if (extractedWord.equals(word)) {
                                    count--;
                                    break;
                                }
                            }
                        }
                        if (count == 0) {
                            matchedRules.add(rule);
                        } else {
                            continue;
                        }
                    }
                    if (matchedRules.size() > 0) {
                        Iterator<Rule> it = matchedRules.iterator();
                        while(it.hasNext()) {
                            Rule rule = it.next();
                            Boolean matched = false;
                            for (String word: rule.bannedWords) {
                                matched = false;
                                for (String extractedWord: x.extractedWords) {
                                    if (extractedWord.equals(word)) {
                                        matched = true;
                                        break;
                                    }
                                }
                                if (matched) {
                                    break;
                                }
                            }
                            if (matched) {
                                it.remove();
                            }
                        }
                    }
                    for (Rule rule: matchedRules) {
                        this.filteredCategories.add(rule.category);
                    }
                })
                .match(MatchedCategories.class, x -> {
                    log.info("Request: " + x.message);
                    log.info(String.join(" ", this.filteredCategories));
                })
                .build();
    }
}
