package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.json.JsonMission;

import java.util.LinkedList;
import java.util.List;

/**
 * A Publisher only.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {
	private Integer tickTime=0;
    private List<MissionInfo> missions = new LinkedList<MissionInfo>();
	public Intelligence(Integer i) {
		super("Intelligence"+i.toString());
	}


	public void setMissions(List<JsonMission>Jmissions){
	for (JsonMission j:Jmissions) {
			MissionInfo missionInfo=new MissionInfo(j.getName(),j.getSerialAgentsNumbers(),j.getGadget(),j.getTimeIssued(),j.getTimeExpired(),j.getDuration());
			missions.add(missionInfo);
		}
	}
public int getTickTime(){
		return this.tickTime;
}
public void setTickTime(int tickTime){
		this.tickTime=tickTime;
}
	@Override
	protected void initialize()  {
		MessageBrokerImpl.getInstance().register(this);
		Callback<TickBroadcast> callBack1= c -> {
				setTickTime(c.getTick());
			for (MissionInfo mission:missions) {
				if(mission.getTimeIssued()==tickTime){
					getSimplePublisher().sendEvent(new MissionReceivedEvent(mission));
				}
			}
		};
		Callback<TerminateBroadcast> terminateBroadcastCallback= c -> {
		  terminate();
		 MessageBrokerImpl.getInstance().unregister(Intelligence.this);
		};
		this.subscribeBroadcast(TickBroadcast.class,callBack1);
		this.subscribeBroadcast(TerminateBroadcast.class,terminateBroadcastCallback);
	}
}
