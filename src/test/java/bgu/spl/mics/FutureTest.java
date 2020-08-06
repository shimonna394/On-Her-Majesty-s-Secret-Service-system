package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class FutureTest {
    Future<String> future;
    @BeforeEach
    public void setUp(){
        future = new Future<String>();
    }

    /**
     * <p>
     *  testing resolve and get methods
     */
    @Test
    public void testResolveAndGet () {
        String s= "check";
        future.resolve(s); //changing the future result
        assertEquals(s,future.get()); //check if future resolve action was updated right.
    }

    /**
     * <p>
     *  testing isDone method retrieves true only after future is resolved.
     */
    @Test
    public void testIsDone () {
        assertFalse(future.isDone()); // future is not done
        future.resolve("check"); //changing the future result
        assertTrue(future.isDone()); // check if future is done
    }

    @Test
    public void testSecondGet () {
        String str= "check";
        future.resolve(str);// changing the future result
        long start = System.nanoTime();
        assertSame(str,(future.get(100000,TimeUnit.NANOSECONDS)));//check if the result is equal to the get method with this unit time
        long end = System.nanoTime();
        assertTrue(end-start>=100000);//check if the time is at most 100000
    }

}
