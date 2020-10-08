package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Report;
//import javafx.util.Pair;
//import jdk.jfr.Event;

import java.util.List;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 * @param m will be her unique id
 * @param timeTick holds the last timeTick M got
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 * M Subscribes to MissionReceivedEvent
 * M first Publish an AgentsAvailableEvent to the MassageBroker
 * if we have the required agents she publish an GadgetAvailableEvent to the MassageBroker
 * if time haven't expired M sends a SendAgentsEvent to the MassageBroker and adds a report to the Diary
 * if time has expired M sends a ReleaseAgentsEvent to the MassageBroker
 */
public class M extends Subscriber {
	private int m;
	private int timeTick;
	public M(Integer m) {
		super("M"+m.toString());
		this.m=m;
	}

	@Override
	protected void initialize() {
		MessageBrokerImpl.getInstance().register(this);
		Callback<MissionReceivedEvent> MissionCallBack = c -> {
			if (timeTick < c.getMs().getTimeExpired()){
				Diary.getInstance().increment();
				Future<Object []> moneyPenny = getSimplePublisher().sendEvent(new AgentsAvailableEvent(c.getMs().getSerialAgentsNumbers()));
				if (moneyPenny != null && moneyPenny.get() != null) {// if this moneypenny is not terminated
					if ((Integer)moneyPenny.get()[0]!=-1) {
						Future<Integer> Q = getSimplePublisher().sendEvent(new GadgetAvailableEvent(c.getMs().getGadget()));
						if (Q != null && Q.get() != null) {
							if (Q.get().intValue() != -1) {
								timeTick = Q.get().intValue();
								if (timeTick < c.getMs().getTimeExpired()) {//if time hasn't expired
									getSimplePublisher().sendEvent(new SendAgents(c.getMs().getDuration(), c.getMs().getSerialAgentsNumbers()));
									createReport(c, moneyPenny ,Q);
								} else {//time has expired
									getSimplePublisher().sendEvent(new ReleaseAgents(c.getMs().getSerialAgentsNumbers()));
								}
							} else {//Gadget unavailable
								getSimplePublisher().sendEvent(new ReleaseAgents(c.getMs().getSerialAgentsNumbers()));
							}
						}
					}
				}
			}
		};
		Callback<TerminateBroadcast>callback2= c ->{
			terminate();
			MessageBrokerImpl.getInstance().unregister(M.this);
		};
		this.subscribeBroadcast(TerminateBroadcast.class,callback2);
		this.subscribeEvent(MissionReceivedEvent.class, MissionCallBack);
	}
	/**
	 * creates a new report and add to diary
	 * @param c has all the information about the event
	 * @peram moneyPenny holds the name of the agents and the responsible mp id
	 * @param Q holds q timeTick
	 */
	private void createReport(MissionReceivedEvent c,Future<Object []> moneyPenny,Future<Integer> Q){
		Report r = new Report();
		Diary.getInstance().addReport(r);
		r.setAgentsNames((List<String>)moneyPenny.get()[1]);
		r.setM(m);
		r.setAgentsSerialNumbersNumber(c.getMs().getSerialAgentsNumbers());
		r.setGadgetName(c.getMs().getGadget());
		r.setMissionName(c.getMs().getMissionName());
		r.setMoneypenny((Integer)moneyPenny.get()[0]);
		r.setQTime(Q.get());
		r.setTimeIssued(c.getMs().getTimeIssued());
		r.setTimeCreated(timeTick);
	}
}
