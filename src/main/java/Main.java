import actor.AggregatorActor;
import actor.WrapperActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import helpers.Categorization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        final ActorSystem actorSystem = ActorSystem.create("sentence-cat");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Categorization categorization = new Categorization();
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

            switch (input) {
                case "c":
                    break;
                default:
                    return;
            }
        }
    }
}
