package actor;

import actor.AggregatorActor.MatchedCategories;
import actor.AggregatorActor.Words;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import helpers.Categorization;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static config.AppVars.RULES_PATH;
import static org.junit.Assert.assertEquals;

public class AggregatorActorTest {
    static ActorSystem actorSystem;

    @BeforeClass
    public static void setup() {
        actorSystem = ActorSystem.create();
    }

    @AfterClass
    public static void shutdown() {
        TestKit.shutdownActorSystem(actorSystem);
        actorSystem = null;
    }

    @Test
    public void testAggregatorActorSendingOfCategories() {
        /*Categorization categorization = new Categorization();
        try {
            categorization.readRules(RULES_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final TestKit testProbe = new TestKit(actorSystem);
        final ActorRef wrapperActor = actorSystem.actorOf(
                WrapperActor.props(testProbe.getRef()));

        wrapperActor.tell(new Words(Arrays.asList("rybe", "dorszcz")), ActorRef.noSender());
        wrapperActor.tell(new MatchedCategories("rybe dorszcz"), ActorRef.noSender());
        MatchedCategories categories = testProbe.expectMsgClass(MatchedCategories.class);
        assertEquals("Request: rybe dorszcz", categories.message);*/
    }
}
