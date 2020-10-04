package bgu.spl.mics;
import bgu.spl.mics.application.*;
import org.junit.jupiter.api.BeforeEach;
import bgu.spl.mics.MessageBroker;
import bgu.spl.mics.MessageBrokerImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageBrokerTest {
    MessageBrokerImpl theBroker;
    Event event;
    Broadcast broadcast;
    Subscriber sub;


    @BeforeEach
    public void setUp(){
        //theBroker=new MessageBrokerImpl();
        event = new intEvent();
        broadcast = new stringBroadcast();
        sub = new Subscriber("testSub") {
            @Override
            protected void initialize() {
            }
        };

    }

    /**
     * <p>
     *  testing instance is not null
     */
    @Test
    public void testInstanceInstanceIsNotNull(){
        assertNotNull(theBroker);//check that message broker is not null
    }

    /**
     * <p>
     *  testing getInstance method doesn't retrieve null
     */
    @Test
    public void testGetInstance(){
        assertNotNull(MessageBrokerImpl.getInstance());//check that getInstance doesn't retrieves null
    }

    /**
     * <p>
     *  testing unregistering a subscriber that was un registered does nothing and doesn't result with an error
     */
    @Test
    public void testUnregisterAnUnregistered(){
        try {
            theBroker.unregister(sub); //unregistering sub
            theBroker.unregister(sub); //repeating the action of unregistering.
        }
        catch (Exception exc){
        }
    }


    /**
     * <p>
     *  testing  IllegalStateException is thrown when the subscriber was not registered.
     */
    @Test
    public void testIllegalStateException(){
        theBroker.unregister(sub); //making sure that sub won't be registered
        try {
            theBroker.subscribeEvent(intEvent.class, sub);//should throw exc because the sub is not registered
        }
        catch (Exception exc){
            assertEquals(exc, "IllegalStateException");
        }
}

    /**
     * <p>
     *  testing  sendEvent flow works as expected-a sent subscribed event is delivered to one of the registered subscribers.
     *  then, when calling the awaitMessage method with the subscriber that got the event the next message in the queue is returned.
     */
            @Test
    public void testSendEventFlow(){
        Subscriber s = new Subscriber("testSub") {
            @Override
            protected void initialize() {
            }
        };
        theBroker.unregister(sub);//unregistering the only other sub inorder to make sure the event will be added to s q.
        theBroker.register(s); //registering the subscriber which means a que is created for s.
        theBroker.subscribeEvent(intEvent.class, s);//subscribe s to the event
        theBroker.sendEvent(event);//adding the event to the subscribe queue
                assertEquals(theBroker.awaitMessage(s), event.getMessage()); //testing the next message in s queue is equal to the event that was sent to him.

            }

    /**
     * <p>
     *  testing subscribeBroadcast and sendBroadcast methods really works by subscribing 2 subscribers to the same broadcast and verifying the broadcast gets to both their queues.
     */
    @Test
    public void testBroadcast(){
        broadcast = new intBroadcast();
        theBroker.register(sub);
        theBroker.subscribeBroadcast(broadcast.getClass(), sub);//subscribe sub to the broadcast
        //creating another sub to subscribe to the broadcast
        Subscriber s = new Subscriber("testSub") {
            @Override
            protected void initialize() {}
        };
        theBroker.register(s);
        theBroker.subscribeBroadcast(broadcast.getClass(), s);//subscribe s to the broadcast
        theBroker.sendBroadcast(broadcast); //Adds the broadcast to the message queues of sub and s
        //now we'll check both s and sub have the broadcast message waiting for them in the queue
        assertEquals(theBroker.awaitMessage(s), broadcast.getMessage());//testing the next message in s queue is equal to the broadcast that was sent to him.
        assertEquals(theBroker.awaitMessage(sub), broadcast.getMessage());//testing the next message in sub queue is equal to the broadcast that was sent to him.
    }

    /**
     * <p>
     *  testing complete method
     */
   @Test
    public void testComplete(){
       theBroker.complete(event,"completed");// complete the event
        assertEquals("completed",theBroker.sendEvent(event));//check if the result is equal to the future object of the event
    }
}
