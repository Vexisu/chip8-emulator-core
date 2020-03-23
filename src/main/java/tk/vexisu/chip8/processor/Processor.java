package tk.vexisu.chip8.processor;

import tk.vexisu.chip8.memory.Memory;
import tk.vexisu.chip8.processor.instructions.Arithmetics;
import tk.vexisu.chip8.processor.instructions.FlowControls;
import tk.vexisu.chip8.processor.instructions.Graphics;
import tk.vexisu.chip8.processor.instructions.Logics;
import tk.vexisu.chip8.processor.opcode.OpCode;
import tk.vexisu.chip8.processor.opcode.Operator;
import tk.vexisu.chip8.registers.Registers;

public class Processor
{
	private Arithmetics arithmetics;
	private Logics logics;
	private Graphics graphics;
	private FlowControls flowControls;

	public Processor(Registers registers, Memory memory)
	{
		this.arithmetics = new Arithmetics(registers);
		this.logics = new Logics(registers);
		this.graphics = new Graphics(registers);
		this.flowControls = new FlowControls(registers, memory);
	}

	public void processInstruction(Operator operator)
	{
		switch (OpCode.get(operator))
		{
			case CLS:
				this.graphics.cls();
				break;
			case RET:
				this.flowControls.ret();
				break;
			case JPC:
				this.flowControls.jpc(operator);
				break;
			case CALL:
				this.flowControls.call(operator);
				break;
			case SEB:
				this.logics.seb(operator);
				break;
			case SNEB:
				this.logics.sneb(operator);
				break;
			case SEV:
				this.logics.sev(operator);
				break;
			case LDB:
				this.flowControls.ldb(operator);
				break;
			case ADDB:
				this.arithmetics.addb(operator);
				break;
			case LDV:
				this.flowControls.ldv(operator);
				break;
			case OR:
				this.logics.or(operator);
				break;
			case AND:
				this.logics.and(operator);
				break;
			case XOR:
				this.logics.xor(operator);
				break;
			case ADDV:
				this.arithmetics.addv(operator);
				break;
			case SUB:
				this.arithmetics.sub(operator, false);
				break;
			case SHR:
				this.arithmetics.shr(operator);
				break;
			case SUBN:
				this.arithmetics.sub(operator, true);
				break;
			case SHL:
				this.arithmetics.shl(operator);
				break;
			case SNEV:
				this.logics.snev(operator);
				break;
			case LDI:
				this.flowControls.ldi(operator);
				break;
			case JPV:
				this.flowControls.jpv(operator);
				break;
			case RND:
				this.arithmetics.rnd(operator);
				break;
			case DRW:
				this.graphics.drw(operator);
				break;
			case SKP:
				this.flowControls.skp(operator);
				break;
			case SKNP:
				this.flowControls.sknp(operator);
				break;
			case LDVT:
				this.flowControls.ldvt(operator);
				break;
			case LDVK:
				this.flowControls.ldvk(operator);
				break;
			case LDDV:
				this.flowControls.lddv(operator);
				break;
			case LDSV:
				this.flowControls.ldsv(operator);
				break;
			case ADDI:
				this.arithmetics.addi(operator);
				break;
		}
	}
}