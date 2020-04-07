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

	@Test
	public void ldbImplementationCheck()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x7, (short) 0xAB);
		Operator operator = new Operator(0x678A);
		this.flowControlInstructions.ldb(operator);
		Assert.assertEquals((short) 0x8A, this.registers.getGeneralPurposeRegisters().read((short) 0x7));
	}

	@Test
	public void addbImplementationCheck()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0xC, (short) 0x15);
		Operator operator = new Operator(0x7C4B);
		this.arithmeticInstructions.addb(operator);
		Assert.assertEquals((short) 0x60, this.registers.getGeneralPurposeRegisters().read((short) 0xC));
	}

	@Test
	public void addbImplementationCheckWhenOverflowOccurs()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0xC, (short) 0xE5);
		Operator operator = new Operator(0x7CF3);
		this.arithmeticInstructions.addb(operator);
		Assert.assertEquals((short) 0xD8, this.registers.getGeneralPurposeRegisters().read((short) 0xC));
	}

	@Test
	public void ldvImplementationCheck()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x4, (short) 0x31);
		this.registers.getGeneralPurposeRegisters().write((short) 0x8, (short) 0xC4);
		Operator operator = new Operator(0x8480);
		this.flowControlInstructions.ldv(operator);
		Assert.assertEquals((short) 0xC4, this.registers.getGeneralPurposeRegisters().read((short) 0x4));
	}

	@Test
	public void orImplementationCheck()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x2, (short) 0x5B);
		this.registers.getGeneralPurposeRegisters().write((short) 0x3, (short) 0x20);
		Operator operator = new Operator(0x8230);
		this.logicInstructions.or(operator);
		Assert.assertEquals((short) 0x7B, this.registers.getGeneralPurposeRegisters().read((short) 0x2));
	}

	@Test
	public void andImplementationCheck()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x7, (short) 0x54);
		this.registers.getGeneralPurposeRegisters().write((short) 0x9, (short) 0x3B);
		Operator operator = new Operator(0x8792);
		this.logicInstructions.and(operator);
		Assert.assertEquals((short) 0x10, this.registers.getGeneralPurposeRegisters().read((short) 0x7));
	}

	@Test
	public void xorImplementationCheck()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0xB, (short) 0x40);
		this.registers.getGeneralPurposeRegisters().write((short) 0x1, (short) 0xB3);
		Operator operator = new Operator(0x8B13);
		this.logicInstructions.xor(operator);
		Assert.assertEquals((short) 0xF3, this.registers.getGeneralPurposeRegisters().read((short) 0xB));
	}

	@Test
	public void addvImplementationCheck()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x3, (short) 0x45);
		this.registers.getGeneralPurposeRegisters().write((short) 0x4, (short) 0x2F);
		Operator operator = new Operator(0x8344);
		this.arithmeticInstructions.addv(operator);
		Assert.assertEquals((short) 0x74, this.registers.getGeneralPurposeRegisters().read((short) 0x3));
		Assert.assertEquals((short) 0x0, this.registers.getGeneralPurposeRegisters().read((short) 0xF));
	}

	@Test
	public void addvImplementationCheckWhenOverflowOccurs()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x3, (short) 0xB4);
		this.registers.getGeneralPurposeRegisters().write((short) 0x4, (short) 0xDF);
		Operator operator = new Operator(0x8344);
		this.arithmeticInstructions.addv(operator);
		Assert.assertEquals((short) 0x93, this.registers.getGeneralPurposeRegisters().read((short) 0x3));
		Assert.assertEquals((short) 0x1, this.registers.getGeneralPurposeRegisters().read((short) 0xF));
	}

	@Test
	public void subImplementationCheck()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x2, (short) 0xB4);
		this.registers.getGeneralPurposeRegisters().write((short) 0xD, (short) 0x1C);
		Operator operator = new Operator(0x82D5);
		this.arithmeticInstructions.sub(operator, false);
		Assert.assertEquals((short) 0x98, this.registers.getGeneralPurposeRegisters().read((short) 0x2));
		Assert.assertEquals((short) 0x1, this.registers.getGeneralPurposeRegisters().read((short) 0xF));
	}

	@Test
	public void subImplementationCheckWhenOverflowOccurs()
	{
		this.registers.getGeneralPurposeRegisters().write((short) 0x2, (short) 0xB4);
		this.registers.getGeneralPurposeRegisters().write((short) 0xD, (short) 0xB6);
		Operator operator = new Operator(0x82D5);
		this.arithmeticInstructions.sub(operator, false);
		Assert.assertEquals((short) 0xFE, this.registers.getGeneralPurposeRegisters().read((short) 0x2));
		Assert.assertEquals((short) 0x0, this.registers.getGeneralPurposeRegisters().read((short) 0xF));
	}
}
