package tk.vexisu.chip8.registers;

import tk.vexisu.chip8.registers.impl.GeneralPurposeRegisters;
import tk.vexisu.chip8.registers.impl.IRegister;
import tk.vexisu.chip8.registers.impl.ProgramCounterRegister;
import tk.vexisu.chip8.registers.impl.StackPointerRegister;
import tk.vexisu.chip8.registers.impl.StackRegisters;
import tk.vexisu.chip8.registers.impl.TimerRegisters;

public class Registers
{
	private GeneralPurposeRegisters generalPurposeRegisters = new GeneralPurposeRegisters();
	private IRegister               iRegister               = new IRegister();
	private ProgramCounterRegister  programCounterRegister  = new ProgramCounterRegister();
	private StackPointerRegister    stackPointerRegister    = new StackPointerRegister();
	private StackRegisters          stackRegisters          = new StackRegisters();
	private TimerRegisters          timerRegisters          = new TimerRegisters();

	public void tick()
	{
		timerRegisters.tick();
	}

	public GeneralPurposeRegisters getGeneralPurposeRegisters()
	{
		return generalPurposeRegisters;
	}

	public IRegister getIRegister()
	{
		return iRegister;
	}

	public ProgramCounterRegister getProgramCounterRegister()
	{
		return programCounterRegister;
	}

	public StackPointerRegister getStackPointerRegister()
	{
		return stackPointerRegister;
	}

	public StackRegisters getStackRegisters()
	{
		return stackRegisters;
	}

	public TimerRegisters getTimerRegisters()
	{
		return timerRegisters;
	}
}
