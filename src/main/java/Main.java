import actor.AggregatorActor;
import actor.WrapperActor;
import actor.WrapperActor.Resolve;
import actor.WrapperActor.ExtractWords;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import helpers.Categorization;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static config.AppVars.RULES_PATH;

public class Main {
    public static void main(String[] args) {

        final ActorSystem actorSystem = ActorSystem.create("sentence-cat");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Categorization categorization = new Categorization();
        try {
            categorization.readRules(RULES_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("Rules not found");
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
            System.out.println("c - check sentence");
            System.out.println("q - quit");
            try {
                input = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (input.charAt(0)) {
                case 'c':
                    String sentence;
                    try {
                        sentence = bufferedReader.readLine();
                        wrapperActor.tell(new ExtractWords(sentence), ActorRef.noSender());
                        wrapperActor.tell(new Resolve(), ActorRef.noSender());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 'q':
                    order = false;
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }
    }
}
