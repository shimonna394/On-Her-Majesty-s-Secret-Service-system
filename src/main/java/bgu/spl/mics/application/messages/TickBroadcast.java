package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Message;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Sends the time to all the subscribers
 * @param tick holds the time that needs to be sent
 */
public class TickBroadcast implements Broadcast {
    AtomicInteger tick;

    public TickBroadcast(AtomicInteger tick) {
        this.tick = tick;
    }

    @Override
    public Message getMessage() {
        return null;
    }

    public int getTick() {
        return this.tick.get();
    }
}
