package tk.vexisu.chip8.display;

public enum Sprite
{
	_0
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x90, 0x90, 0x90, 0xF0};
			}
		},
	_1
		{
			@Override
			public short[] get()
			{
				return new short[] {0x20, 0x60, 0x20, 0x20, 0x70};
			}
		},
	_2
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x10, 0xF0, 0x80, 0xF0};
			}
		},
	_3
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x10, 0xF0, 0x10, 0xF0};
			}
		},
	_4
		{
			@Override
			public short[] get()
			{
				return new short[] {0x90, 0x90, 0xF0, 0x10, 0x10};
			}
		},
	_5
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x80, 0xF0, 0x10, 0xF0};
			}
		},
	_6
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x80, 0xF0, 0x90, 0xF0};
			}
		},
	_7
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x10, 0x20, 0x40, 0x40};
			}
		},
	_8
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x90, 0xF0, 0x90, 0xF0};
			}
		},
	_9
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x90, 0xF0, 0x10, 0xF0};
			}
		},
	_A
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x90, 0xF0, 0x90, 0x90};
			}
		},
	_B
		{
			@Override
			public short[] get()
			{
				return new short[] {0xE0, 0x90, 0xE0, 0x90, 0xE0};
			}
		},
	_C
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x80, 0x80, 0x80, 0xF0};
			}
		},
	_D
		{
			@Override
			public short[] get()
			{
				return new short[] {0xE0, 0x90, 0x90, 0x90, 0xE0};
			}
		},
	_E
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x80, 0xF0, 0x80, 0xF0};
			}
		},
	_F
		{
			@Override
			public short[] get()
			{
				return new short[] {0xF0, 0x80, 0xF0, 0x80, 0x80};
			}
		};

	abstract public short[] get();
}
