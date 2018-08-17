package tk.vexisu.chip8.registers;

public class IRegister
{
	private int data = 0x0;

	public int read()
	{
		return data;
	}

	public void write(int data)
	{
		this.data = data;
	}
}
