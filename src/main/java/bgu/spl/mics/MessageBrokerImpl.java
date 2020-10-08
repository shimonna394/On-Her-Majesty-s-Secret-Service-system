package bgu.spl.mics;

import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.*;

import java.util.concurrent.*;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 * @perm missions holds the queue for each subscriber
 * @perm broadcastTypes holds all the subscribers for a specific broadcast
 * @perm eventTypes holds all the subscribers for a specific event
 */
public class MessageBrokerImpl implements MessageBroker {
	private ConcurrentHashMap<Subscriber, BlockingQueue<Message>> missions = new ConcurrentHashMap<Subscriber, BlockingQueue<Message>>();
	private ConcurrentHashMap<Class<? extends Broadcast>, BlockingQueue<Subscriber>> broadcastTypes = new ConcurrentHashMap<Class<? extends Broadcast>, BlockingQueue<Subscriber>>();
	private ConcurrentHashMap<Class<? extends Event>, BlockingQueue<Subscriber>> eventTypes = new ConcurrentHashMap<Class<? extends Event>, BlockingQueue<Subscriber>>();
	private ConcurrentHashMap<Event,Future> hashEvent= new ConcurrentHashMap<>();
	private Object lock=new Object();
	private Object lock2=new Object();
	private Object lock3=new Object();
	private MessageBrokerImpl(){
		broadcastTypes.put(TickBroadcast.class,new LinkedBlockingQueue<Subscriber>());
		broadcastTypes.put(TerminateBroadcast.class,new LinkedBlockingQueue<Subscriber>());
		eventTypes.put(MissionReceivedEvent.class,new LinkedBlockingQueue<Subscriber>());
		eventTypes.put(AgentsAvailableEvent.class,new LinkedBlockingQueue<Subscriber>());
		eventTypes.put(GadgetAvailableEvent.class,new LinkedBlockingQueue<Subscriber>());
		eventTypes.put(ReleaseAgents.class,new LinkedBlockingQueue<Subscriber>());
		eventTypes.put(SendAgents.class,new LinkedBlockingQueue<Subscriber>());
	}
	private static class SingletonHolder {
		private static MessageBrokerImpl instance = new MessageBrokerImpl();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static MessageBroker getInstance() {
		return MessageBrokerImpl.SingletonHolder.instance;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
			eventTypes.get(type).add(m);

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
			broadcastTypes.get(type).add(m);
	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		hashEvent.get(e).resolve(result);

	}

	@Override
	public void  sendBroadcast(Broadcast b) {
        for (Subscriber s : broadcastTypes.get((b.getClass()))) {
            if(b instanceof TerminateBroadcast)
            	synchronized (lock) {
					emptyMissions(s);
				}
            	missions.get(s).add(b);
        }
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		synchronized (lock2) {
        Subscriber first;
            Future result = new Future();
            hashEvent.put(e, result);
            try {
                first = eventTypes.get(e.getClass()).remove();
                eventTypes.get(e.getClass()).add(first);
                missions.get(first).add(e);
            }
            catch (Exception ex) {
                return null;
            }
			return result;
		}
	}

	@Override
	public void register(Subscriber m) {
		missions.put(m,new LinkedBlockingQueue<Message>());
	}

	@Override
	public void unregister(Subscriber m) {
		synchronized (lock) {
        	emptyMissions(m);
            missions.remove(m, missions.get(m));
        }
		emptyEventsAndBroadcasts(m);
	}

	@Override
	public Message awaitMessage(Subscriber m)  {
		try {
				return missions.get(m).take();

		}catch (InterruptedException ignored){}
		return null;
	}
	/**
	 * deletes the subscribers queue
	 */
    private void emptyMissions(Subscriber s){
		synchronized (lock){
       	 while (!missions.get(s).isEmpty()) {
				Message m = missions.get(s).remove();
				if (m instanceof Event) {
					eventTypes.get(m.getClass()).remove(s);
					complete((Event) m, null);
				} else {
					broadcastTypes.get(m.getClass()).remove(s);
				}
			}
        }
    }
	/**
	 * deletes all queue related to the subscribers by events and broadcasts
	 */
    private void emptyEventsAndBroadcasts(Subscriber m){
		if(eventTypes.get(GadgetAvailableEvent.class).contains(m))
			eventTypes.get(GadgetAvailableEvent.class).remove(m);
		if(eventTypes.get(AgentsAvailableEvent.class).contains(m))
			eventTypes.get(AgentsAvailableEvent.class).remove(m);
		if(eventTypes.get(MissionReceivedEvent.class).contains(m))
			eventTypes.get(MissionReceivedEvent.class).remove(m);
		if(eventTypes.get(SendAgents.class).contains(m))
			eventTypes.get(SendAgents.class).remove(m);
		if(eventTypes.get(ReleaseAgents.class).contains(m))
			eventTypes.get(ReleaseAgents.class).remove(m);
		if(broadcastTypes.get(TickBroadcast.class).contains(m));
		broadcastTypes.get(TickBroadcast.class).remove(m);
		if(broadcastTypes.get(TerminateBroadcast.class).contains(m));
		broadcastTypes.get(TerminateBroadcast.class).remove(m);
	}
}

