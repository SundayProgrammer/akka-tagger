package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import helpers.Rule;

import java.util.ArrayList;

public class Supervisor extends AbstractActor {

    public static Props props(ArrayList<Rule> rules) {
        return Props.create(Supervisor.class, () -> new Supervisor(rules));
    }

    ActorRef router;

    public Supervisor(ArrayList<Rule> rules) {
        this.router = getContext().actorOf(new RoundRobinPool(3).props(Props.create(WrapperActor.class, () -> new WrapperActor(rules))), "router1");
    }

    static public class Categorize {
        public final String sentence;

        public Categorize(String sentence) {
            this.sentence = sentence;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Categorize.class, x -> this.router.tell(new WrapperActor.ExtractWords(x.sentence), ActorRef.noSender()))
                .build();
    }
}
