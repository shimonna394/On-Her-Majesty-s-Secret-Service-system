package bgu.spl.mics.application.passiveObjects;


import java.util.*;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {

	private Map<String, Agent> agents=new HashMap<String, Agent>();
	private static class SingletonHolder {
		private static Squad instance = new Squad();
	}
	private Object lock=new Object();
	/**
	 * Retrieves the single instance of this class.
	 */
	public static Squad getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param agents 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public void load (Agent[] agents) {
		for(int i=0;i<agents.length;i++)
			this.agents.put(agents[i].getSerialNumber(),agents[i]);
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials){
	    if(serials.contains("release all")){
	        for(Map.Entry<String,Agent> t:agents.entrySet()){
	            t.getValue().release();
            }
        }
        else {
            for (String it : serials) {
                agents.get(it).release();
            }
        }
        synchronized (lock) {
            lock.notifyAll();
        }
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   milliseconds to sleep
	 */
	public  void sendAgents(List<String> serials, int time) throws InterruptedException {
            Thread.sleep(time*100);
			releaseAgents(serials);
	}

	/**
	 * acquires an agent, i.e. holds the agent until the caller is done with it
	 * @param serials   the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
	 */
	public boolean getAgents(List<String> serials)  {
		synchronized (lock) {
			for (String it : serials) {
				if (!agents.containsKey(it))
					return false;
				while (!agents.get(it).isAvailable())
					try {
						lock.wait();
					} catch (InterruptedException ignored){}
			}
			for (String it : serials)
				agents.get(it).acquire();
			return true;
		}
}



    /**
     * gets the agents names
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials){
        List<String> ans= new LinkedList<>();
        for(String it:serials)
        	ans.add(agents.get(it).getName());
	    return ans;
    }

}
