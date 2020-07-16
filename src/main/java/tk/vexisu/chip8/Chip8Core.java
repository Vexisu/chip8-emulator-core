package tk.vexisu.chip8;

import java.io.IOException;
import java.io.InputStream;
import tk.vexisu.chip8.clock.Clock;
import tk.vexisu.chip8.display.Display;
import tk.vexisu.chip8.display.Sprite;
import tk.vexisu.chip8.keyboard.KeyboardAdapter;
import tk.vexisu.chip8.keyboard.impl.DummyKeyboardAdapter;
import tk.vexisu.chip8.memory.Memory;
import tk.vexisu.chip8.processor.Processor;
import tk.vexisu.chip8.registers.Registers;

public class Chip8Core
{
	private Processor processor;
	private Registers registers;
	private Memory memory;
	private Display display;
	private Clock clock;
	private KeyboardAdapter keyboardAdapter;

	public Chip8Core()
	{
		this.registers = new Registers();
		this.memory = new Memory();
		this.display = new Display();
		this.clock = new Clock();
		this.keyboardAdapter = new DummyKeyboardAdapter();
		this.processor = new Processor(registers, memory, display, keyboardAdapter);
		this.initialize();
	}

	private void initialize()
	{
		this.loadSpritesToMemory();
		this.clock.setProcessor(processor);
	}

	private void loadSpritesToMemory()
	{
		short address = 0;
		for (Sprite sprite : Sprite.values())
		{
			for (short value : sprite.get())
			{
				this.memory.write(address, value);
				address++;
			}
		}
	}

	public void loadProgram(InputStream data, short programLocation) throws IOException
	{
		this.registers.getProgramCounterRegister().write(programLocation);
		int value;
		while ((value = data.read()) != -1)
		{
			this.memory.write(programLocation, (short) value);
			programLocation++;
		}
	}

	public void setKeyboardAdapter(KeyboardAdapter keyboardAdapter)
	{
		this.keyboardAdapter = keyboardAdapter;
	}

	public Registers getRegisters()
	{
		return registers;
	}

	public Memory getMemory()
	{
		return memory;
	}

	public Display getDisplay()
	{
		return display;
	}

	public Clock getClock()
	{
		return clock;
	}
}
