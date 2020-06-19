package tk.vexisu.chip8.processor.instructions;

import tk.vexisu.chip8.display.Display;
import tk.vexisu.chip8.memory.Memory;
import tk.vexisu.chip8.processor.opcode.Operator;
import tk.vexisu.chip8.registers.Registers;
import tk.vexisu.chip8.registers.impl.GeneralPurposeRegisters;
import tk.vexisu.chip8.registers.impl.IRegister;
import tk.vexisu.chip8.registers.impl.ProgramCounterRegister;
import tk.vexisu.chip8.registers.impl.StackPointerRegister;
import tk.vexisu.chip8.registers.impl.StackRegisters;
import tk.vexisu.chip8.registers.impl.TimerRegisters;

public class Graphics
{
	private GeneralPurposeRegisters generalPurposeRegisters;
	private IRegister iRegister;
	private ProgramCounterRegister programCounterRegister;
	private StackPointerRegister stackPointerRegister;
	private StackRegisters stackRegisters;
	private TimerRegisters timerRegisters;
	private Memory memory;
	private Display display;

	public Graphics(Registers registers, Memory memory, Display display)
	{
		this.generalPurposeRegisters = registers.getGeneralPurposeRegisters();
		this.iRegister = registers.getIRegister();
		this.programCounterRegister = registers.getProgramCounterRegister();
		this.stackPointerRegister = registers.getStackPointerRegister();
		this.stackRegisters = registers.getStackRegisters();
		this.timerRegisters = registers.getTimerRegisters();
		this.memory = memory;
		this.display = display;
	}

	public void cls()
	{
		display.clear();
	}

	public void drw(Operator operator)
	{
		var registerXAdress = operator.getFourBits(2);
		var registerYAdress = operator.getFourBits(1);
		var length = operator.getFourBits(0);
		var spriteAddress = this.iRegister.read();
		var xCoordinate = this.generalPurposeRegisters.read(registerXAdress);
		var yCoordinate = this.generalPurposeRegisters.read(registerYAdress);
		var sprites = new short[length];
		for (int i = 0; i < length; i++)
		{
			sprites[i] = this.memory.read((short) (spriteAddress + i));
		}
		for (int i = 0; i < length; i++)
		{
			var bitYCoordinate = yCoordinate + i;
			for (int byteOfSprite = 0; byteOfSprite < 8; byteOfSprite++)
			{
				var separatedBitOfSprite = ((sprites[i] >> (7 - byteOfSprite)) & 0b1) > 0;
				var bitXCoordinate = xCoordinate + byteOfSprite;
				var xoredBit = display.read(bitXCoordinate, bitYCoordinate) ^ separatedBitOfSprite;
				this.display.write(bitXCoordinate, bitYCoordinate, xoredBit);
				if (separatedBitOfSprite && xoredBit)
				{
					this.generalPurposeRegisters.write((short) 0xF, (short) 0x1);
				}
			}
		}
	}
}
