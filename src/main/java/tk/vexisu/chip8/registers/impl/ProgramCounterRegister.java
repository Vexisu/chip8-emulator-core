package tk.vexisu.chip8.registers.impl;

public class ProgramCounterRegister
{
	private int data = 0x0;

	public int read()
	{
		return data;
	}

	public void write(int data)
	{
		this.data = data & 0xFFFF;
	}

	public void increment(int value)
	{
		this.data = (this.data + value) & 0xFFFF;
	}
}
