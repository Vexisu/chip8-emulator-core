package tk.vexisu.chip8.processor;

import java.util.concurrent.ThreadLocalRandom;
import tk.vexisu.chip8.processor.opcode.OpCode;
import tk.vexisu.chip8.processor.opcode.Operator;
import tk.vexisu.chip8.registers.*;

public class Processor
{
	private GeneralPurposeRegisters generalPurposeRegisters = new GeneralPurposeRegisters();
	private IRegister iRegister = new IRegister();
	private ProgramCounterRegister programCounterRegister = new ProgramCounterRegister();
	private StackPointerRegister stackPointerRegister = new StackPointerRegister();
	private StackRegisters stackRegisters = new StackRegisters();
	private TimerRegisters timerRegisters = new TimerRegisters();

	public void tick()
	{
		timerRegisters.tick();
	}

	public void processInstruction(Operator operator)
	{
		switch (OpCode.get(operator))
		{
			case CLS:
				this.cls();
				break;
			case RET:
				this.ret();
				break;
			case JPC:
				this.jpc(operator);
				break;
			case CALL:
				this.call(operator);
				break;
			case SEB:
				this.seb(operator);
				break;
			case SNEB:
				this.sneb(operator);
				break;
			case SEV:
				this.sev(operator);
				break;
			case LDB:
				this.ldb(operator);
				break;
			case ADDB:
				this.addb(operator);
				break;
			case LDV:
				this.ldv(operator);
				break;
			case OR:
				this.or(operator);
				break;
			case AND:
				this.and(operator);
				break;
			case XOR:
				this.xor(operator);
				break;
			case ADDV:
				this.addv(operator);
				break;
			case SUB:
				this.sub(operator, false);
				break;
			case SHR:
				this.shr(operator);
				break;
			case SUBN:
				this.sub(operator, true);
				break;
			case SHL:
				this.shl(operator);
				break;
			case SNEV:
				this.snev(operator);
				break;
			case LDI:
				this.ldi(operator);
				break;
			case JPV:
				this.jpv(operator);
				break;
			case RND:
				this.rnd(operator);
				break;
			case DRW:
				this.drw(operator);
				break;
		}
	}

	private void cls()
	{
		//Cleaning Screen will be implemented after implementing Display
	}

	private void ret()
	{
		var stackPointer = this.stackPointerRegister.read();
		var stackTop = this.stackRegisters.read(stackPointer);
		this.programCounterRegister.write(stackTop);
		this.stackPointerRegister.decrement(1);
	}

	private void jpc(Operator operator)
	{
		var jumpAddress = ((int) operator.getFourBits(2) << 8);
		jumpAddress += ((int) operator.getFourBits(1) << 4);
		jumpAddress += (int) operator.getFourBits(0);
		this.programCounterRegister.write(jumpAddress);
	}

	private void call(Operator operator)
	{
		this.stackPointerRegister.increment(1);
		var stackPointer = this.stackPointerRegister.read();
		var programCounter = this.programCounterRegister.read();
		this.stackRegisters.write(stackPointer, programCounter);
		var callAddress = ((int) operator.getFourBits(2) << 8);
		callAddress += ((int) operator.getFourBits(1) << 4);
		callAddress += (int) operator.getFourBits(0);
		this.programCounterRegister.write(callAddress);
	}

	private void seb(Operator operator)
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

	private void sneb(Operator operator)
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

	private void sev(Operator operator)
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

	private void ldb(Operator operator)
	{
		var registerAddress = operator.getFourBits(2);
		var valueToStore = (short) (operator.getFourBits(1) << 4);
		valueToStore += operator.getFourBits(0);
		this.generalPurposeRegisters.write(registerAddress, valueToStore);
	}

	private void addb(Operator operator)
	{
		var registerAddress = operator.getFourBits(2);
		var valueToAdd = (short) (operator.getFourBits(1) << 4);
		valueToAdd += operator.getFourBits(0);
		var registerValue = this.generalPurposeRegisters.read(registerAddress);
		registerValue += valueToAdd;
		this.generalPurposeRegisters.write(registerAddress, registerValue);
	}

	private void ldv(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		this.generalPurposeRegisters.write(registerXAddress, registerYValue);
	}

	private void or(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		var operationResult = (short) (registerXValue | registerYValue);
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	private void and(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		var operationResult = (short) (registerXValue & registerYValue);
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	private void xor(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		var operationResult = (short) (registerXValue ^ registerYValue);
		this.generalPurposeRegisters.write(registerXAddress, operationResult);
	}

	private void addv(Operator operator)
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

	private void sub(Operator operator, boolean n)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		short operationResult;
		if (n)
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

	private void shr(Operator operator)
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

	private void shl(Operator operator)
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

	private void snev(Operator operator)
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

	private void ldi(Operator operator)
	{
		var value = ((int) operator.getFourBits(2) << 8);
		value += ((int) operator.getFourBits(1) << 4);
		value += (int) operator.getFourBits(0);
		this.iRegister.write(value);
	}

	private void jpv(Operator operator)
	{
		var jumpValue = ((int) operator.getFourBits(2) << 8);
		jumpValue += ((int) operator.getFourBits(1) << 4);
		jumpValue += (int) operator.getFourBits(0);
		var v0RegisterValue = this.generalPurposeRegisters.read((short) 0x0);
		var jumpAddress = jumpValue + v0RegisterValue;
		this.programCounterRegister.write(jumpAddress);
	}

	private void rnd(Operator operator)
	{
		var registerXAdress = (operator.getFourBits(2));
		var value = (operator.getFourBits(1) << 4);
		value += operator.getFourBits(0);
		var random = ThreadLocalRandom.current();
		var randomNumber = (short) random.nextInt(0xFF);
		var result = (short) (randomNumber & value);
		this.generalPurposeRegisters.write(registerXAdress, result);
	}

	private void drw(Operator operator)
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