package tk.vexisu.chip8.registers.impl;

public class GeneralPurposeRegisters
{
	private short[] data = new short[16];

	public short read(short location)
	{
		return data[location];
	}

	public void write(short location, short data)
	{
		this.data[location] = data;
	}
}
