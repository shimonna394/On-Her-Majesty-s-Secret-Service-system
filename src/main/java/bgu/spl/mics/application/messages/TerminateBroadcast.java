package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Message;
/**
 *This event informs all Subscribers the the time has finished
 */
public class TerminateBroadcast implements Broadcast {
    @Override
    public Message getMessage() {
        return null;
    }
}
