package tk.vexisu.chip8;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tk.vexisu.chip8.display.Display;
import tk.vexisu.chip8.memory.Memory;
import tk.vexisu.chip8.processor.instructions.Arithmetics;
import tk.vexisu.chip8.processor.instructions.FlowControls;
import tk.vexisu.chip8.processor.instructions.Graphics;
import tk.vexisu.chip8.processor.instructions.Logics;
import tk.vexisu.chip8.processor.opcode.Operator;
import tk.vexisu.chip8.registers.Registers;

public class InstructionsTest
{
	private Registers registers;
	private Memory memory;
	private Display display;
	private Arithmetics arithmeticInstructions;
	private FlowControls flowControlInstructions;
	private Graphics graphicInstructions;
	private Logics logicInstructions;

	@Before
	public void initalizeRequiredVariables()
	{
		this.registers = new Registers();
		this.memory = new Memory();
		this.display = new Display();
		this.arithmeticInstructions = new Arithmetics(registers);
		this.flowControlInstructions = new FlowControls(registers, memory);
		this.graphicInstructions = new Graphics(registers, display);
		this.logicInstructions = new Logics(registers);
	}

	@Test
	public void clsImplementationCheck()
	{
		this.display.set(1, 1, true);
		this.graphicInstructions.cls();
		for (int x = 0; x < 64; x++)
		{
			for (int y = 0; y < 32; y++)
			{
				if (this.display.get(x, y))
				{
					Assert.fail("Found true value in array. All expected false.");
				}
			}
		}
	}

	@Test
	public void retImplementationCheck()
	{
		this.registers.getStackPointerRegister().write((byte) 0x2);
		this.registers.getStackRegisters().write((byte) 0x2, 0x38);
		this.flowControlInstructions.ret();
		Assert.assertEquals(0x38, this.registers.getProgramCounterRegister().read());
		Assert.assertEquals((byte) 0x1, this.registers.getStackPointerRegister().read());
	}

	@Test
	public void jpcImplementationCheck()
	{
		this.registers.getProgramCounterRegister().write(0x000);
		Operator operator = new Operator(0x13F8);
		this.flowControlInstructions.jpc(operator);
		Assert.assertEquals(0x3F8, this.registers.getProgramCounterRegister().read());
	}

	@Test
	public void callImplementationCheck()
	{
		this.registers.getStackPointerRegister().write((byte) 0x3);
		this.registers.getProgramCounterRegister().write(0x2AB);
		Operator operator = new Operator(0x2B1C);
		this.flowControlInstructions.call(operator);
		Assert.assertEquals((byte) 0x4, this.registers.getStackPointerRegister().read());
		Assert.assertEquals(0x2AB, this.registers.getStackRegisters().read((byte) 0x4));
		Assert.assertEquals(0xB1C, this.registers.getProgramCounterRegister().read());
	}

	@Test
	public void sebImplementationCheckForEqualValue()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x5, (short) 0xCE);
		this.registers.getProgramCounterRegister().write(0xD21E);
		Operator operator = new Operator(0x35CE);
		this.logicInstructions.seb(operator);
		Assert.assertEquals(0xD220, this.registers.getProgramCounterRegister().read());
	}

	@Test
	public void sebImplementationCheckForNotEqualValue()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x5, (short) 0xCB);
		this.registers.getProgramCounterRegister().write(0xD21E);
		Operator operator = new Operator(0x35CE);
		this.logicInstructions.seb(operator);
		Assert.assertEquals(0xD21E, this.registers.getProgramCounterRegister().read());
	}

	@Test
	public void snebImplementationCheckForEqualValue()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x3, (short) 0x19);
		this.registers.getProgramCounterRegister().write(0x1F3C);
		Operator operator = new Operator(0x4319);
		this.logicInstructions.sneb(operator);
		Assert.assertEquals(0x1F3C, this.registers.getProgramCounterRegister().read());
	}

	@Test
	public void snebImplementationCheckForNotEqualValue()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x3, (short) 0x18);
		this.registers.getProgramCounterRegister().write(0x1F3C);
		Operator operator = new Operator(0x4319);
		this.logicInstructions.sneb(operator);
		Assert.assertEquals(0x1F3E, this.registers.getProgramCounterRegister().read());
	}

	@Test
	public void sevImplementationCheckForEqualValue()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x1, (short) 0x2F);
		this.registers.getGeneralPurposeRegisters().write((short) 0x2, (short) 0x2F);
		this.registers.getProgramCounterRegister().write(0xB3B3);
		Operator operator = new Operator(0x5120);
		this.logicInstructions.sev(operator);
		Assert.assertEquals(0xB3B5, this.registers.getProgramCounterRegister().read());
	}

	@Test
	public void sevImplementationCheckForNotEqualValue()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x1, (short) 0x2F);
		this.registers.getGeneralPurposeRegisters().write((short) 0x2, (short) 0x28);
		this.registers.getProgramCounterRegister().write(0xB3B3);
		Operator operator = new Operator(0x5120);
		this.logicInstructions.sev(operator);
		Assert.assertEquals(0xB3B3, this.registers.getProgramCounterRegister().read());
	}
}
