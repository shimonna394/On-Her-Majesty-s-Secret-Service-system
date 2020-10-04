package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.Squad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SquadTest {
    private Squad squad;

    @BeforeEach
    public void setUp(){
        squad=Squad.getInstance();
    }


    /**
     * <p>
     *
     */
    @Test
    public void testGetInstance(){
        assertNotNull(squad);//check that squad is not null
    }


    /**
     * <p>
     *  test loading agents really adds them to the squad
     *  test Getting agents from the squad list really works
     */
    @Test
    public void testLoadAndGetAgents(){
        Agent[] newAgents = new Agent[2];
        Agent Ag1 =new Agent();
        Agent Ag2 =new Agent();
        Ag1.setName("Bond");
        Ag1.setSerialNumber("007");
        Ag2.setName("Cohen");
        Ag2.setSerialNumber("001");
        newAgents[0]=Ag1;
        newAgents[1]=Ag2;
        squad.load(newAgents);//add the agents to the squad
        List<String> Serials=new LinkedList<String>();// list of the serial number of the agents
        Serials.add("001");
        Serials.add("007");
        assertTrue(squad.getAgents(Serials));// should return true because the serials number exist in the squad
        Serials.add("fdsjf");
        assertFalse(squad.getAgents(Serials));// should return false because there is a serials number that is not exist in the squad
    }


    /**
     * <p>
     *
     */
    @Test
    public void testGetAgentsNames(){
        Agent[] newAgents = new Agent[2];
        Agent Ag3 =new Agent();
        Agent Ag4 =new Agent();
        Ag3.setName("Shimi");
        Ag3.setSerialNumber("009");
        Ag4.setName("Itamar");
        Ag4.setSerialNumber("008");
        newAgents[0]=Ag3;
        newAgents[1]=Ag4;
        squad.load(newAgents);//add the agents to the squad
        List<String> Serials=new LinkedList<String>();//list of the serial number of the agents
        Serials.add("008");
        Serials.add("009");
        List<String> Names = new LinkedList<String>();// list of the names of the agents
        Names.add("Shimi");
        Names.add("Itamar");
        List<String> squadNames =squad.getAgentsNames(Serials);
        for(String name : squadNames){
            assertTrue(squadNames.contains(name));//should be true because the names of the agents exists in the list
        }
        assertFalse(squadNames.contains("008"));// should be false because the serial number doesn't exists in the list
        assertFalse(squadNames.contains("009"));// should be false because the serial number doesn't exists in the list
    }


    /**
     * <p>
     *  test JsonSquad is a singletone
     */
    @Test
    public void testSingelton(){
        assertSame(squad,Squad.getInstance());
    }

    /**
     * <p>
     *  testing agents on mission really changes their status
     */
    @Test
    public void testSendAndReleaseAgents() throws InterruptedException {
        Agent[] newAgents = new Agent[2];
        Agent Ag5 =new Agent();
        Agent Ag6 =new Agent();
        Ag5.setName("N");
        Ag5.setSerialNumber("002");
        Ag6.setName("Y");
        Ag6.setSerialNumber("003");
        newAgents[0]=Ag5;
        newAgents[1]=Ag6;
        squad.load(newAgents);//add the agents to the squad
        List<String> Serials=new LinkedList<String>();//list of the serial number of the agents
        Serials.add("002");
        Serials.add("003");
        assertTrue(squad.getAgents(Serials)); //the agents are available
        squad.sendAgents(Serials, Integer.MAX_VALUE);
        assertFalse(squad.getAgents(Serials));      // the agent should be missing because we sent him on a mission for the maximum time possible
        squad.releaseAgents(Serials);
        assertTrue(squad.getAgents(Serials)); //the agents should be available after being released
    }
}
