package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory;

    /**
     * <p>
     *
     */
    @BeforeEach
    public void setUp(){//initialize whatever we want to test
        inventory= Inventory.getInstance();
    }


    /**
     * <p>
     *  test instance is not null
     */
    @Test
    public void testGetInstance(){
        assertNotNull(inventory);//check that inventory is not null
    }

    /**
     * <p>
     *  test load really add the item to the inventory
     */
    @Test
    public void testLoadingAndGettingItems(){
        String [] newGadgets = new String [3];
        newGadgets[0]="superGun";
        newGadgets[1]="boom";
        newGadgets[2]="vanishCar";
        inventory.load(newGadgets);// add the gadgets to the inventory
        boolean val=true;
        for(int i =0; i<3 & val; i++){
            val=inventory.getItem(newGadgets[i]);// for each new gadget, if it is exsist in the inventory val stay true
        }
        assertTrue(val);// if val is true, the load succeed
    }

    /**
     * <p>
     *  test getItem really removes the item from the inventory
     */
    @Test
    public void testRemoving(){
        String [] newGadgets = new String [3];
        newGadgets[0]="deathRay";
        newGadgets[1]="Aston Martin";
        newGadgets[2]="Martini";
        inventory.load(newGadgets);// add the gadgets to the inventory
        for(int i =0 ; i<3 ; i++){
            inventory.getItem(newGadgets[i]);//after this method the item should be removed from the inventory
        }
        assertFalse(inventory.getItem("deathRay"));//this Item should not be exist in the inventory anymore and false should be returned
        assertFalse(inventory.getItem("Aston Martin"));//this Item should not be exist in the inventory anymore and false should be returned
        assertFalse(inventory.getItem("Martini"));//this Item should not be exist in the inventory anymore and false should be returned
    }


    /**
     * <p>
     *  test inventory is a singletone
     */
    @Test
    public void testSingelton(){
        assertSame(inventory,Inventory.getInstance());//check if inventory is a singleton
    }




}