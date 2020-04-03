package tk.vexisu.chip8.display;

public class Display
{
	private boolean[][] data = new boolean[64][32];

	public void clear()
	{
		data = new boolean[64][32];
	}

	public void set(int x, int y, boolean value)
	{
		this.data[x][y] = value;
	}

	public boolean get(int x, int y)
	{
		return this.data[x][y];
	}
}
