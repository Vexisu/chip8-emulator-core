package tk.vexisu.chip8.registers;

public class StackRegisters
{
	private int data[] = new int[16];

	public int read(byte location)
	{
		return data[location];
	}

	public void write(byte location, int data)
	{
		this.data[location] = data;
	}
}
