package tk.vexisu.chip8.processor.instructions;

import tk.vexisu.chip8.keyboard.Key;
import tk.vexisu.chip8.keyboard.KeyboardAdapter;
import tk.vexisu.chip8.memory.Memory;
import tk.vexisu.chip8.processor.opcode.Operator;
import tk.vexisu.chip8.registers.Registers;
import tk.vexisu.chip8.registers.impl.GeneralPurposeRegisters;
import tk.vexisu.chip8.registers.impl.IRegister;
import tk.vexisu.chip8.registers.impl.ProgramCounterRegister;
import tk.vexisu.chip8.registers.impl.StackPointerRegister;
import tk.vexisu.chip8.registers.impl.StackRegisters;
import tk.vexisu.chip8.registers.impl.TimerRegisters;

public class FlowControls
{
	private GeneralPurposeRegisters generalPurposeRegisters;
	private IRegister iRegister;
	private ProgramCounterRegister programCounterRegister;
	private StackPointerRegister stackPointerRegister;
	private StackRegisters stackRegisters;
	private TimerRegisters timerRegisters;
	private Memory memory;
	private KeyboardAdapter keyboardAdapter;

	public FlowControls(Registers registers, Memory memory, KeyboardAdapter keyboardAdapter)
	{
		this.generalPurposeRegisters = registers.getGeneralPurposeRegisters();
		this.iRegister = registers.getIRegister();
		this.programCounterRegister = registers.getProgramCounterRegister();
		this.stackPointerRegister = registers.getStackPointerRegister();
		this.stackRegisters = registers.getStackRegisters();
		this.timerRegisters = registers.getTimerRegisters();
		this.memory = memory;
		this.keyboardAdapter = keyboardAdapter;
	}

	public void ret()
	{
		var stackPointer = this.stackPointerRegister.read();
		var stackTop = this.stackRegisters.read(stackPointer);
		this.programCounterRegister.write(stackTop);
		this.stackPointerRegister.decrement(1);
	}

	public void jpc(Operator operator)
	{
		var jumpAddress = ((int) operator.getFourBits(2) << 8);
		jumpAddress += ((int) operator.getFourBits(1) << 4);
		jumpAddress += (int) operator.getFourBits(0);
		this.programCounterRegister.write(jumpAddress);
	}

	public void call(Operator operator)
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

	public void ldb(Operator operator)
	{
		var registerAddress = operator.getFourBits(2);
		var valueToStore = (short) (operator.getFourBits(1) << 4);
		valueToStore += operator.getFourBits(0);
		this.generalPurposeRegisters.write(registerAddress, valueToStore);
	}

	public void ldv(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerYAddress = operator.getFourBits(1);
		var registerYValue = this.generalPurposeRegisters.read(registerYAddress);
		this.generalPurposeRegisters.write(registerXAddress, registerYValue);
	}

	public void ldi(Operator operator)
	{
		var value = ((int) operator.getFourBits(2) << 8);
		value += ((int) operator.getFourBits(1) << 4);
		value += (int) operator.getFourBits(0);
		this.iRegister.write(value);
	}

	public void jpv(Operator operator)
	{
		var jumpValue = ((int) operator.getFourBits(2) << 8);
		jumpValue += ((int) operator.getFourBits(1) << 4);
		jumpValue += (int) operator.getFourBits(0);
		var v0RegisterValue = this.generalPurposeRegisters.read((short) 0x0);
		var jumpAddress = jumpValue + v0RegisterValue;
		this.programCounterRegister.write(jumpAddress);
	}

	public void skp(Operator operator)
	{
		var keyCode = (int) operator.getFourBits(2);
		Key key = Key.getByCode(keyCode);
		if (this.keyboardAdapter.isPressed(key))
		{
			this.programCounterRegister.increment(2);
		}
	}

	public void sknp(Operator operator)
	{
		var keyCode = (int) operator.getFourBits(2);
		Key key = Key.getByCode(keyCode);
		if (!this.keyboardAdapter.isPressed(key))
		{
			this.programCounterRegister.increment(2);
		}
	}

	public void ldvt(Operator operator)
	{
		var value = operator.getFourBits(2);
		this.timerRegisters.write((byte) 0x0, value);
	}

	public void ldvk(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
	}

	public void lddv(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		this.timerRegisters.write((byte) 0x0, registerXValue);
	}

	public void ldsv(Operator operator)
	{
		var registerXAddress = operator.getFourBits(2);
		var registerXValue = this.generalPurposeRegisters.read(registerXAddress);
		this.timerRegisters.write((byte) 0x1, registerXValue);
	}

	public void ldfv(Operator operator)
	{
		var fontValue = operator.getFourBits(2);
		var fontAddress = fontValue * 5;
		this.iRegister.write(fontAddress);
	}

	public void ldbv(Operator operator)
	{
		var decimalValue = operator.getFourBits(2);
		var hunderdsFromDecimal = (decimalValue / 100) % 10;
		var tensFromDecimal = (decimalValue / 10) % 10;
		var onesFromDecimal = decimalValue % 10;
		var iRegisterValue = this.iRegister.read();
		this.memory.write((short) iRegisterValue, (short) hunderdsFromDecimal);
		this.memory.write((short) (iRegisterValue + 0x1), (short) tensFromDecimal);
		this.memory.write((short) (iRegisterValue + 0x2), (short) onesFromDecimal);
	}

	public void ldiv(Operator operator)
	{
		var iRegisterValue = this.iRegister.read();
		var endingAddress = operator.getFourBits(2);
		for (short i = 0x0; i <= endingAddress; i++)
		{
			var registerValue = this.generalPurposeRegisters.read(i);
			var memoryDestinationAddress = (short) (iRegisterValue + i);
			this.memory.write(memoryDestinationAddress, registerValue);
		}
	}

	public void ldvi(Operator operator)
	{
		var iRegisterValue = this.iRegister.read();
		var endingAddress = operator.getFourBits(2);
		for (short i = 0x0; i <= endingAddress; i++)
		{
			var memorySourceAddress = (short) (iRegisterValue + i);
			var memoryValue = this.memory.read(memorySourceAddress);
			this.generalPurposeRegisters.write(i, memoryValue);
		}
	}
}
