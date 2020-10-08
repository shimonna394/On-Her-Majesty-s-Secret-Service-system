package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Message;

import java.util.List;
/**
 * Sends agents to the mission
 * @param time holds the mission time
 */
public class SendAgents implements Event<List<String>> {
int time;
List<String> serials;
    public SendAgents(int time,List<String> serials){
    this.time=time;
    this.serials=serials;
    }

    public int getTime() {
        return time;
    }

    public List<String> getSerials() {
        return serials;
    }



    @Override
    public Message getMessage() {
        return null;
    }
}
