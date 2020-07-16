package tk.vexisu.chip8.processor.opcode;

public class Operator
{
	private int code;

	public Operator(int code)
	{
		this.code = code;
	}

	public short getFourBits(int index)
	{
		return (short) ~((~(this.code >> 4 * index)) | ~(0xF));
	}

	public int getCode()
	{
		return code;
	}
}
