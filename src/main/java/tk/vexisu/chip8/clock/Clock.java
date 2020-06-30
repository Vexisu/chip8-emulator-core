package tk.vexisu.chip8.clock;

import java.util.ArrayList;
import tk.vexisu.chip8.processor.Processor;

public class Clock
{
	private ArrayList<ClockEvent> clockEvents;
	private Processor processor;

	public Clock()
	{
		this.clockEvents = new ArrayList<>();
	}

	private void onTick()
	{
		processor.tick();
	}

	public void addEvent(ClockEvent event)
	{
		this.clockEvents.add(event);
	}

	public void setProcessor(Processor processor)
	{
		this.processor = processor;
	}
}
