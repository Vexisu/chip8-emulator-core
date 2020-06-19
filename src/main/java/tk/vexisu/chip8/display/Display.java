package tk.vexisu.chip8.display;

public class Display
{
	private boolean[][] data = new boolean[64][32];

	public void clear()
	{
		data = new boolean[64][32];
	}

	public void write(int x, int y, boolean value)
	{
		this.data[x % 64][y % 32] = value;
	}

	public boolean read(int x, int y)
	{
		return this.data[x % 64][y % 32];
	}
}
