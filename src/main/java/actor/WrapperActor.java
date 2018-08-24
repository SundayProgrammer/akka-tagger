package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import actor.AggregatorActor.Words;

import java.util.Arrays;
import java.util.List;

public class WrapperActor extends AbstractActor {

    static public Props props(final String message, final ActorRef aggregatorActor) {
        return Props.create(WrapperActor.class, () -> new WrapperActor(message, aggregatorActor));
    }

    private final ActorRef aggregatorActor;
    private final String message;
    private List extractedWords;

    public WrapperActor(String message, ActorRef aggregatorActor) {
        this.message = message;
        this.aggregatorActor = aggregatorActor;
    }

    static public class ExtractWords {
        public ExtractWords() {}
    }

    static public class Resolve {
        public Resolve() {}
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ExtractWords.class, x -> {
                    extractedWords = Arrays.asList(this.message.split(" "));
                })
                .match(Resolve.class, x -> {
                    aggregatorActor.tell(new Words(extractedWords), getSelf());
                })
                .build();
    }
}
