import actor.AggregatorActor;
import actor.WrapperActor;
import actor.WrapperActor.Resolve;
import actor.WrapperActor.ExtractWords;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import helpers.Categorization;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static config.AppVars.RULES_PATH;
import static config.AppVars.SENTENCES_PATH;

public class Main {
    public static void main(String[] args) {

        final ActorSystem actorSystem = ActorSystem.create("sentence-cat");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Categorization categorization = new Categorization();
        ArrayList<String> sentences;
        try {
            categorization.readRules(RULES_PATH);
            sentences = categorization.readSentences(SENTENCES_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("Rules or sentences not found /n" + e);
            return;
        }
        Boolean order = true;

        final ActorRef aggregatorActor =
                actorSystem.actorOf(
                        AggregatorActor.props(categorization.getRules()),
                        "worker");
        final ActorRef wrapperActor =
                actorSystem.actorOf(WrapperActor.props(aggregatorActor),
                        "wrapperActor");

        while (order) {
            String input = "";
            int num = 0;
            System.out.println("Give queries number: ");
            try {
                input = bufferedReader.readLine();
                if (NumberUtils.isCreatable(input)) {
                    num = Integer.parseInt(input);
                } else {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            int aviableSentences = sentences.size();
            int random = 0;

            
            /*for (int i = num; i > 0; i--) {
                random = ThreadLocalRandom.current().nextInt(0, aviableSentences);
                wrapperActor.tell(new ExtractWords(sentences.get(random)), ActorRef.noSender());
                wrapperActor.tell(new Resolve(), ActorRef.noSender());
            }*/
        }
    }
}
