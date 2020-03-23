package tk.vexisu.chip8.processor.instructions;

import java.util.concurrent.ThreadLocalRandom;
import tk.vexisu.chip8.processor.opcode.Operator;
import tk.vexisu.chip8.registers.Registers;
import tk.vexisu.chip8.registers.impl.GeneralPurposeRegisters;
import tk.vexisu.chip8.registers.impl.IRegister;
import tk.vexisu.chip8.registers.impl.ProgramCounterRegister;
import tk.vexisu.chip8.registers.impl.StackPointerRegister;
import tk.vexisu.chip8.registers.impl.StackRegisters;
import tk.vexisu.chip8.registers.impl.TimerRegisters;

public class Arithmetics
{
	private GeneralPurposeRegisters generalPurposeRegisters;
	private IRegister iRegister;
	private ProgramCounterRegister programCounterRegister;
	private StackPointerRegister stackPointerRegister;
	private StackRegisters stackRegisters;
	private TimerRegisters timerRegisters;

	public Arithmetics(Registers registers)
	{
		this.generalPurposeRegisters = registers.getGeneralPurposeRegisters();
		this.iRegister = registers.getIRegister();
		this.programCounterRegister = registers.getProgramCounterRegister();
		this.stackPointerRegister = registers.getStackPointerRegister();
		this.stackRegisters = registers.getStackRegisters();
		this.timerRegisters = registers.getTimerRegisters();
	}

	public void addb(Operator operator)
	{
		var registerAddress = operator.getFourBits(2);
		var valueToAdd = (short) (operator.getFourBits(1) << 4);
		valueToAdd += operator.getFourBits(0);
		var registerValue = this.generalPurposeRegisters.read(registerAddress);
		registerValue += valueToAdd;
		this.generalPurposeRegisters.write(registerAddress, registerValue);
	}

	public void addv(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		var operationResult = (short) (registerXValue + registerYValue);
		if (operationResult >= 0x100)
		{
			this.generalPurposeRegisters.write((short) 0xf, (short) 0x1);
		}
		else
		{
			this.generalPurposeRegisters.write((short) 0xf, (short) 0x0);
		}
		operationResult = (short) ~((~operationResult) | ~(0xFF));
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	public void sub(Operator operator, boolean reversed)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		short operationResult;
		if (reversed)
		{
			if (registerYValue > registerXValue)
			{
				this.generalPurposeRegisters.write((short) 0xf, (short) 0x1);
			}
			else
			{
				this.generalPurposeRegisters.write((short) 0xf, (short) 0x0);
			}
			operationResult = (short) (registerYValue - registerXValue);
		}
		else
		{
			if (registerXValue > registerYValue)
			{
				this.generalPurposeRegisters.write((short) 0xf, (short) 0x1);
			}
			else
			{
				this.generalPurposeRegisters.write((short) 0xf, (short) 0x0);
			}
			operationResult = (short) (registerXValue - registerYValue);
		}
		operationResult = (short) ~((~operationResult) | ~(0xFF));
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	public void shr(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		if (~((~registerXValue) | ~(0b0)) == 0b1)
		{
			this.generalPurposeRegisters.write((short) 0xf, (short) 0x1);
		}
		else
		{
			this.generalPurposeRegisters.write((short) 0xf, (short) 0x0);
		}
		var operationResult = (short) (registerXValue / 2);
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	public void shl(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		if (~((~registerXValue) | ~(0b0)) == 0b1)
		{
			this.generalPurposeRegisters.write((short) 0xf, (short) 0x1);
		}
		else
		{
			this.generalPurposeRegisters.write((short) 0xf, (short) 0x0);
		}
		var operationResult = (short) (registerXValue * 2);
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	public void rnd(Operator operator)
	{
		var registerXAdress = (operator.getFourBits(2));
		var value = (operator.getFourBits(1) << 4);
		value += operator.getFourBits(0);
		var random = ThreadLocalRandom.current();
		var randomNumber = (short) random.nextInt(0xFF);
		var result = (short) (randomNumber & value);
		this.generalPurposeRegisters.write(registerXAdress, result);
	}

	public void addi(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var iValue = this.iRegister.read();
		var additionValue = registerXValue + iValue;
		this.iRegister.write(additionValue);
	}
}
