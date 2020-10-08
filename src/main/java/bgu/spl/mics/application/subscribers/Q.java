package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.passiveObjects.Inventory;


/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 * @param timeTick holds the last timeTick Q got
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 * Subscribes the GadgetAvailableEvent
 * Should remember in which time tick he received the Event
 */
public class Q extends Subscriber {
   private Integer tickTime=0;
	public Q() {
		super("Q");
	}
	public void setTickTime(int tickTime) {
		this.tickTime = tickTime;
	}

	@Override
	protected void initialize() {
		MessageBrokerImpl.getInstance().register(this);
		Callback<GadgetAvailableEvent> callBack = c -> {
			if (Inventory.getInstance().getItem(c.getGadget())) {
				complete(c, tickTime);
			}
			else {
				complete(c, -1);
			}
		};
		Callback<TickBroadcast> callBack2= c -> setTickTime(c.getTick());
		Callback<TerminateBroadcast>callback3= c ->{
			terminate();
			MessageBrokerImpl.getInstance().unregister(Q.this);
		};
		this.subscribeEvent(GadgetAvailableEvent.class,callBack);
		this.subscribeBroadcast(TickBroadcast.class,callBack2);
		this.subscribeBroadcast(TerminateBroadcast.class,callback3);
	}

}
