package actor;

import akka.actor.AbstractActor;

import java.util.List;

public class AggregatorActor extends AbstractActor {

    static public class Words {
        public final List extractedWords;

        public Words(List extractedWords) {
            this.extractedWords = extractedWords;
        }
    }

    @Override
    public Receive createReceive() {
        return null;
    }
}
