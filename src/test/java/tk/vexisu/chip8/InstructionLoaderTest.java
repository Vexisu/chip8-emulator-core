package tk.vexisu.chip8;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tk.vexisu.chip8.memory.Memory;
import tk.vexisu.chip8.memory.ProgramLocation;

public class InstructionLoaderTest
{
	private Chip8Core chip8Core;
	private Memory memory;

	@Before
	public void initialize()
	{
		this.chip8Core = new Chip8Core();
		this.memory = chip8Core.getMemory();
	}

	@Test
	public void test() throws IOException
	{
		byte[] array = {0x60, 0x0A};
		InputStream inputStream = new ByteArrayInputStream(array);
		this.chip8Core.loadProgram(inputStream, ProgramLocation.DEFAULT.value());
		Assert.assertEquals(array[0], this.memory.read((short) 0x200));
		Assert.assertEquals(array[1], this.memory.read((short) 0x201));
	}
}
