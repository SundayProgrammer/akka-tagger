package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxRoutingLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Supervisor extends AbstractActor {

    public static Props props() {
        return Props.create(Supervisor.class, () -> new Supervisor());
    }


    Router router;
    {
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef routee = getContext().actorOf(Props.create(WrapperActor.class));
            getContext().watch(routee);
            routees.add(new ActorRefRoutee(routee));
        }
        router = new Router(new SmallestMailboxRoutingLogic(), routees);
    }

    public Supervisor() {}

    static public class Categorize {
        public final ArrayList<String> sentences;
        public final int sentNum;
        public final int sentLen;

        public Categorize(ArrayList<String> sentences, int sentNum, int sentLen) {
            this.sentences = sentences;
            this.sentNum = sentNum;
            this.sentLen = sentLen;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Categorize.class, x -> {
                    int random;
                    for (int i = x.sentNum; i > 0; i--) {
                        random = ThreadLocalRandom.current().nextInt(0, x.sentLen);
                        router.route(new WrapperActor.ExtractWords(x.sentences.get(random)), ActorRef.noSender());
                        router.route(new WrapperActor.Resolve(), ActorRef.noSender());
                    }
                })
                .build();
    }
}
