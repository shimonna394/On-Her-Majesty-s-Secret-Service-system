package bgu.spl.mics.application;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.Squad;
import bgu.spl.mics.application.publishers.TimeService;
import bgu.spl.mics.application.subscribers.Intelligence;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Moneypenny;
import bgu.spl.mics.application.subscribers.Q;
import bgu.spl.mics.json.*;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;


/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args) {
        List<Thread> subscriberList = new LinkedList();
        Gson gson = new Gson();
        BufferedReader b;
        try {
            b = new BufferedReader(new FileReader(args[0]));
            ParseJ json = gson.fromJson(b, ParseJ.class);
            Squad.getInstance().load(json.getSquad());
            Inventory.getInstance().load(json.getInventory());
            for (int i = 0; i < json.getServices().getM(); i++) {
                subscriberList.add(new Thread(new M(i + 1)));
            }
            addMoneyPenny(json,subscriberList);
            subscriberList.add(new Thread(new Q()));//adds only subscriber Q
            addIntell(json,subscriberList);
            Thread timeService=new Thread(new TimeService(json.getServices().getTime()));
            for(Thread t:subscriberList){
                t.start();
            }
            timeService.start();
            for(Thread t:subscriberList){
                t.join();
            }
            timeService.join();
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        Inventory.getInstance().printToFile(args[1]);
        Diary.getInstance().printToFile(args[2]);
    }
    /**
     *This function creates as many Intelligence threads as needed
     */
    private static void addIntell(ParseJ json, List<Thread> subscriberList) {
        Integer i=1;
        for (JsonIntelligence jIntell : json.getServices().getIntelligence()) {
            Intelligence intell = new Intelligence(i);
            intell.setMissions(jIntell.getMissions());
            subscriberList.add(new Thread(intell));
            i=i+1;
        }
    }
    /**
     *This function creates as many MoneyPenny threads as needed
     */
    private static void addMoneyPenny(ParseJ json,List<Thread> subscriberList){
        if (json.getServices().getMoneypenny() > 1) {
            for (int i = 0; i < json.getServices().getMoneypenny(); i++) {
                subscriberList.add(new Thread(new Moneypenny(i + 1)));
            }
        } else {
            Moneypenny moneypenny = new Moneypenny(1);
            moneypenny.setOnlyPenny(true);
            subscriberList.add(new Thread(moneypenny));
        }
    }
}
