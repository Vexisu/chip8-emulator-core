package tk.vexisu.chip8.memory;

public enum ProgramLocation
{
	DEFAULT((short) 0x200),
	ETI660((short) 0x600);

	private short location;

	ProgramLocation(short location)
	{
		this.location = location;
	}

	public short value()
	{
		return location;
	}
}
