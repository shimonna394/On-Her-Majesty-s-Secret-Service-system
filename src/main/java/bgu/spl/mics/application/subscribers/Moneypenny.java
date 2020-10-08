package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.AgentsAvailableEvent;
import bgu.spl.mics.application.messages.ReleaseAgents;
import bgu.spl.mics.application.messages.SendAgents;
import bgu.spl.mics.application.passiveObjects.Squad;

import java.util.LinkedList;
import java.util.List;

/**
 * Only this type of Subscriber can access the squad.
 * Three are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 * Subscribes to AgentsAvailableEvent,SendAgentsEvent and to ReleaseAgentsEvent
 * @param moneyPenny hold mp id
 * @param onlyPenny tells as if there is only 1 mp
 */
public class Moneypenny extends Subscriber {
private int moneyPenny;
private boolean onlyPenny=false;
	public void setOnlyPenny(boolean onlyPenny) {
		this.onlyPenny = onlyPenny;
	}

	public Moneypenny(int moneyPenny) {
		super("MoneyPenny"+moneyPenny);
		this.moneyPenny=moneyPenny;
	}


	@Override
	protected void initialize() {
		MessageBrokerImpl.getInstance().register(this);
		Callback<AgentsAvailableEvent> callBackGetAgents = c -> {
			if (Squad.getInstance().getAgents(c.getAgentsSerial())) {
                Object [] comp=new Object[2];
                comp[0]=moneyPenny;
                comp[1]=Squad.getInstance().getAgentsNames(c.getAgentsSerial());
				complete(c,comp);
			}
			else {
			    List<String> list=new LinkedList<>();
			    Object [] comp ={-1,list};
                complete(c,comp );
            }
		};
		Callback<SendAgents> callBackSendAgents = c -> {
			try {
				Squad.getInstance().sendAgents(c.getSerials(), c.getTime());
			} catch (InterruptedException ignored) {}
			complete(c, Squad.getInstance().getAgentsNames(c.getSerials()));
		};
		Callback<ReleaseAgents> callBackReleaseAgent = c -> {
			Squad.getInstance().releaseAgents(c.getSerials());
			complete(c, true);
		};
		Callback<TerminateBroadcast>callBackTerminateBroadCast= c ->{
			terminate();
			List<String> l=new LinkedList<>();
			l.add("release all");
			Squad.getInstance().releaseAgents(l);
			MessageBrokerImpl.getInstance().unregister(Moneypenny.this);
		};
		if(!onlyPenny) {
			if (moneyPenny % 2 == 1) {
				this.subscribeEvent(AgentsAvailableEvent.class, callBackGetAgents);
			}
			else {
				this.subscribeEvent(SendAgents.class, callBackSendAgents);
				this.subscribeEvent(ReleaseAgents.class, callBackReleaseAgent);
			}
		}
		else
		{
			this.subscribeEvent(AgentsAvailableEvent.class, callBackGetAgents);
			this.subscribeEvent(SendAgents.class, callBackSendAgents);
			this.subscribeEvent(ReleaseAgents.class, callBackReleaseAgent);
		}
		this.subscribeBroadcast(TerminateBroadcast.class, callBackTerminateBroadCast);
	}

}
