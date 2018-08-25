import actor.Supervisor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import helpers.Categorization;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static config.AppVars.RULES_PATH;
import static config.AppVars.SENTENCES_PATH;

public class Main {
    public static void main(String[] args) {

        final ActorSystem actorSystem = ActorSystem.create("sentence-cat");

        Categorization categorization = new Categorization();
        ArrayList<String> sentences;
        try {
            categorization.readRules(RULES_PATH);
            sentences = categorization.readSentences(SENTENCES_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("Rules or sentences not found /n" + e);
            return;
        }

        final ActorRef taggerActor =
                actorSystem.actorOf(Supervisor.props(categorization.getRules()), "supervisor");

        // Number of sentence queries to actor system
        int num = 10000;

        int aviableSentences = sentences.size();
        int random = 0;
        String sentence;


        for (int i = num; i > 0; i--) {
            random = ThreadLocalRandom.current().nextInt(0, aviableSentences);
            sentence = sentences.get(random);
            taggerActor.tell(new Supervisor.Categorize(sentence), ActorRef.noSender());
        }
    }
}
