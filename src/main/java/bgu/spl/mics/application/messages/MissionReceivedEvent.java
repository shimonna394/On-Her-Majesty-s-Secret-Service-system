package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Message;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

import java.util.List;

/**
 * Only M subscribes to it
 * Starts a call back the initiate a mission
 * @param ms holds the entire Mission info
 */
public class MissionReceivedEvent implements Event<MissionInfo> {
    MissionInfo ms;
    public MissionReceivedEvent(MissionInfo m){
        this.ms=m;
    }

    public MissionInfo getMs() {
        return ms;
    }



    @Override
    public Message getMessage() {
        return this;
    }
}
