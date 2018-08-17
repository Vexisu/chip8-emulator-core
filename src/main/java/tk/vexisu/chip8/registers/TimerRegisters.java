package tk.vexisu.chip8.registers;

public class TimerRegisters
{
	private short[] data = new short[2];

	public short read(byte location)
	{
		return data[location];
	}

	public void write(byte location, short data)
	{
		this.data[location] = data;
	}

	public void tick()
	{
		for (short i = 0; i < this.data.length; i++)
		{
			if (this.data[i] > 0x0)
			{
				this.data[i] -= 0x1;
			}
		}
	}
}
