package bgu.spl.mics.application.publishers;

import bgu.spl.mics.Publisher;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {
		int duration;
		private AtomicInteger currentTick;
		public TimeService(int duration) {
			super("");
			this.duration = duration;
			this.currentTick=new AtomicInteger(0);
		}

		@Override
		protected void initialize() {
			while (currentTick.intValue() < duration) {
				getSimplePublisher().sendBroadcast(new TickBroadcast(currentTick));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentTick.set(currentTick.intValue() + 1);
			}
			getSimplePublisher().sendBroadcast(new TerminateBroadcast());
		}

		@Override
		public void run() {
			initialize();
		}
}
