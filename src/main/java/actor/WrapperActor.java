package actor;

import actor.AggregatorActor.Words;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import helpers.Rule;

import java.util.ArrayList;
import java.util.Arrays;

public class WrapperActor extends AbstractActor {

    static public Props props(ArrayList<Rule> rules) {
        return Props.create(WrapperActor.class, () -> new WrapperActor(rules));
    }

    private ActorRef router;
    private static ArrayList<Rule> rules;

    public WrapperActor(ArrayList<Rule> rules) {
        this.router = getContext().actorOf(new RoundRobinPool(4).props(Props.create(AggregatorActor.class, () -> new AggregatorActor(rules))), "router2");
    }

    static public class ExtractWords {
        private final String message;

        public ExtractWords(String message) {
            this.message = message;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ExtractWords.class, x -> {
                    router.tell(new Words(Arrays.asList(x.message.split(" "))), getSelf());
                })
                .build();
    }
}
