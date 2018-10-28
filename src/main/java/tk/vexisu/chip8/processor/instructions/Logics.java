package tk.vexisu.chip8.processor.instructions;

import tk.vexisu.chip8.processor.opcode.Operator;
import tk.vexisu.chip8.registers.Registers;
import tk.vexisu.chip8.registers.impl.GeneralPurposeRegisters;
import tk.vexisu.chip8.registers.impl.IRegister;
import tk.vexisu.chip8.registers.impl.ProgramCounterRegister;
import tk.vexisu.chip8.registers.impl.StackPointerRegister;
import tk.vexisu.chip8.registers.impl.StackRegisters;
import tk.vexisu.chip8.registers.impl.TimerRegisters;

public class Logics
{
	private GeneralPurposeRegisters generalPurposeRegisters;
	private IRegister               iRegister;
	private ProgramCounterRegister  programCounterRegister;
	private StackPointerRegister    stackPointerRegister;
	private StackRegisters          stackRegisters;
	private TimerRegisters          timerRegisters;

	public Logics(Registers registers)
	{
		this.generalPurposeRegisters = registers.getGeneralPurposeRegisters();
		this.iRegister = registers.getIRegister();
		this.programCounterRegister = registers.getProgramCounterRegister();
		this.stackPointerRegister = registers.getStackPointerRegister();
		this.stackRegisters = registers.getStackRegisters();
		this.timerRegisters = registers.getTimerRegisters();
	}

	public void seb(Operator operator)
	{
		var registerAddress = operator.getFourBits(2);
		var comparisonValue = ((int) operator.getFourBits(1) << 4);
		comparisonValue += (int) operator.getFourBits(0);
		var regiterValue = this.generalPurposeRegisters.read(registerAddress);
		if (comparisonValue == regiterValue)
		{
			this.programCounterRegister.increment(2);
		}
	}

	public void sneb(Operator operator)
	{
		var registerAddress = operator.getFourBits(2);
		var comparisonValue = ((int) operator.getFourBits(1) << 4);
		comparisonValue += (int) operator.getFourBits(0);
		var regiterValue = this.generalPurposeRegisters.read(registerAddress);
		if (!(comparisonValue == regiterValue))
		{
			this.programCounterRegister.increment(2);
		}
	}

	public void sev(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var regiterXValue = this.generalPurposeRegisters.read(registerXAddress);
		var regiterYValue = this.generalPurposeRegisters.read(registerYAddress);
		if (regiterXValue == regiterYValue)
		{
			this.programCounterRegister.increment(2);
		}
	}

	public void or(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		var operationResult = (short) (registerXValue | registerYValue);
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	public void and(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		var operationResult = (short) (registerXValue & registerYValue);
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	public void xor(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		var operationResult = (short) (registerXValue ^ registerYValue);
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	public void snev(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		if (registerXValue != registerYValue)
		{
			this.programCounterRegister.increment(2);
		}
	}
}
