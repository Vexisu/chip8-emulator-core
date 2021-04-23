package tk.vexisu.chip8.clock;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import tk.vexisu.chip8.processor.Processor;

public class Clock
{
	private static final int DEFAULT_FREQUENCY = 60;

	private ArrayList<ClockEvent> clockEvents;
	private Processor processor;
	private Timer timer;

	public Clock()
	{
		this.clockEvents = new ArrayList<>();
		this.timer = new Timer(this);
	}

	public void tick()
	{
		this.processor.tick();
		this.clockEvents.forEach(ClockEvent::onTick);
	}

	public void addEvent(ClockEvent event)
	{
		this.clockEvents.add(event);
	}

	public void setProcessor(Processor processor)
	{
		this.processor = processor;
	}

	public Timer getTimer()
	{
		return timer;
	}

	public static class Timer implements Runnable
	{
		private final Clock clock;
		private int frequency;
		private long frequencyBasedCycleDuration;
		private long cycleDuration;

		public Timer(Clock clock)
		{
			this.clock = clock;
			this.setFrequency(DEFAULT_FREQUENCY);
		}

		@Override
		public void run()
		{
			var cycleStartTimestamp = System.nanoTime();
			try
			{
				this.clock.tick();
				var tickEndTimestamp = System.nanoTime();
				long tickDuration = tickEndTimestamp - cycleStartTimestamp;
				long sleepDuration = this.frequencyBasedCycleDuration - tickDuration;
				TimeUnit.NANOSECONDS.sleep(sleepDuration > 0 ? sleepDuration : 1);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			this.cycleDuration = System.nanoTime() - cycleStartTimestamp;
		}

		public void setFrequency(int frequency)
		{
			this.frequency = frequency;
			this.frequencyBasedCycleDuration = TimeUnit.SECONDS.toNanos(1) / frequency;
		}

		public int getFrequency()
		{
			return frequency;
		}

		public long getCycleDuration()
		{
			return cycleDuration;
		}
	}
}
