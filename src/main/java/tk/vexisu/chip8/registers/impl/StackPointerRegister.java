package tk.vexisu.chip8.registers.impl;

public class StackPointerRegister
{
	private byte data = 0x0;

	public byte read()
	{
		return data;
	}

	public void write(byte data)
	{
		this.data = data;
	}

	public void increment(int value)
	{
		this.data += value;
	}

	public void decrement(int value)
	{
		this.data -= value;
	}
}
