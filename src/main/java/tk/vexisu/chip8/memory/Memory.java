package tk.vexisu.chip8.memory;

public class Memory
{
	private short[] data = new short[4096];

	public short read(short location)
	{
		return data[location];
	}

	public void write(short location, short data)
	{
		this.data[location] = data;
	}
}
