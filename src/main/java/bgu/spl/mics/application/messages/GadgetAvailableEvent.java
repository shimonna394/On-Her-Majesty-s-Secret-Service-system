package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBrokerImpl;

/**
 * tells M if the gadget is available
 * Only Q subscribes to it
 * @param gadget holds the gadget name
 */
public class GadgetAvailableEvent implements Event<Integer> {
    private String gadget;
    public GadgetAvailableEvent(String gadget){
        this.gadget=gadget;
    }
    public String getGadget(){
        return this.gadget;
    }


    @Override
    public Message getMessage() {
        return this;
    }
}
