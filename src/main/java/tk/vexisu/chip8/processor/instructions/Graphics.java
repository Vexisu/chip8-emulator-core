package tk.vexisu.chip8.processor.instructions;

import tk.vexisu.chip8.display.Display;
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
	private Display display;

	public Graphics(Registers registers, Display display)
	{
		this.generalPurposeRegisters = registers.getGeneralPurposeRegisters();
		this.iRegister = registers.getIRegister();
		this.programCounterRegister = registers.getProgramCounterRegister();
		this.stackPointerRegister = registers.getStackPointerRegister();
		this.stackRegisters = registers.getStackRegisters();
		this.timerRegisters = registers.getTimerRegisters();
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
		var nibble = operator.getFourBits(0);
		var spriteAddress = this.iRegister.read();
		var xCoordinate = this.generalPurposeRegisters.read(registerXAdress);
		var yCoordinate = this.generalPurposeRegisters.read(registerYAdress);
		//Drawing will be implemented after implementing Display
	}
}
