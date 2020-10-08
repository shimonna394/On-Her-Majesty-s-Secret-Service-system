package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Message;

import java.util.List;
/**
 *If the mission was aborted after agents are acquired
 * @param serials holds the serials of agents to be released
 */
public class ReleaseAgents implements Event<Boolean> {
List<String> serials;
    public ReleaseAgents(List<String> serials){
    this.serials=serials;
    }

    public List<String> getSerials() {
        return serials;
    }


    @Override
    public Message getMessage() {
        return null;
    }
}
