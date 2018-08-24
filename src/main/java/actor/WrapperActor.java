package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import actor.AggregatorActor.Words;

import java.util.Arrays;
import java.util.List;

public class WrapperActor extends AbstractActor {

    static public Props props(final ActorRef aggregatorActor) {
        return Props.create(WrapperActor.class, () -> new WrapperActor(aggregatorActor));
    }

    private final ActorRef aggregatorActor;
    private List<String> extractedWords;

    public WrapperActor(ActorRef aggregatorActor) {
        this.aggregatorActor = aggregatorActor;
    }

    static public class ExtractWords {
        private final String message;

        public ExtractWords(String message) {
            this.message = message;
        }
    }

    static public class Resolve {
        public Resolve() {}
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ExtractWords.class, x -> {
                    extractedWords = Arrays.asList(x.message.split(" "));
                })
                .match(Resolve.class, x -> {
                    aggregatorActor.tell(new Words(extractedWords), getSelf());
                })
                .build();
    }
}
