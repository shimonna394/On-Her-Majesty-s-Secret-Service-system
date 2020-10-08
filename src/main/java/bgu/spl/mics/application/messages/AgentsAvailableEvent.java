package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Message;
//import javafx.util.Pair;

import java.util.List;

/**
 * only Moneypenny subscribes to it
 * @param agentsSerial holds all of the agents serials
 */
public class AgentsAvailableEvent implements Event<Object []> {
    private List<String> agentsSerial;
    public AgentsAvailableEvent(List<String> agentsSerial){
        this.agentsSerial=agentsSerial;
    }
    public List<String> getAgentsSerial() {
        return agentsSerial;
    }


    @Override
    public Message getMessage() {
        return this;
    }
}
