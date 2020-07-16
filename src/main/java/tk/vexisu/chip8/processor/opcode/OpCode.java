package tk.vexisu.chip8.processor.opcode;

public enum OpCode
{
	SYS(0x0000),
	CLS(0x00E0),
	RET(0x00EE),
	JPC(0x1000),
	CALL(0x2000),
	SEB(0x3000),
	SNEB(0x4000),
	SEV(0x5000),
	LDB(0x6000),
	ADDB(0x7000),
	LDV(0x8000),
	OR(0x8001),
	AND(0x8002),
	XOR(0x8003),
	ADDV(0x8004),
	SUB(0x8005),
	SHR(0x8006),
	SUBN(0x8007),
	SHL(0x800E),
	SNEV(0x9000),
	LDI(0xA000),
	JPV(0xB000),
	RND(0xC000),
	DRW(0xD000),
	SKP(0xE09E),
	SKNP(0xE0A1),
	LDVT(0xF007),
	LDVK(0xF00A),
	LDDV(0xF015),
	LDSV(0xF018),
	ADDI(0xF01E),
	LDFV(0xF029),
	LDBV(0xF033),
	LDIV(0xF055),
	LDVI(0xF065);
	private int code;

	OpCode(int code)
	{
		this.code = code;
	}

	public static OpCode get(Operator op)
	{
		for (OpCode opCode : OpCode.values())
		{
			var lastFourBitsOfOperator = op.getCode() >> 12;
			if (lastFourBitsOfOperator == 0x0)
			{
				if (~((~op.getCode()) | ~(0xFF)) == ~((~opCode.code) | ~(0xFF)))
				{
					return opCode;
				}
				continue;
			}
			if (lastFourBitsOfOperator == 0x8)
			{
				if (~((~op.getCode()) | ~(0xF)) == ~((~opCode.code) | ~(0xF)))
				{
					return opCode;
				}
				continue;
			}
			if (lastFourBitsOfOperator == 0xF)
			{
				if (~((~op.getCode()) | ~(0xFF)) == ~((~opCode.code) | ~(0xFF)))
				{
					return opCode;
				}
				continue;
			}
			if (lastFourBitsOfOperator == 0xE)
			{
				if (~((~op.getCode()) | ~(0xFF)) == ~((~opCode.code) | ~(0xFF)))
				{
					return opCode;
				}
				continue;
			}
			if (opCode.code >> 12 == op.getCode() >> 12)
			{
				return opCode;
			}
		}
		return null;
	}
}
