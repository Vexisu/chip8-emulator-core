package tk.vexisu.chip8.keyboard.impl;

import tk.vexisu.chip8.keyboard.Key;
import tk.vexisu.chip8.keyboard.KeyboardAdapter;

public class DummyKeyboardAdapter implements KeyboardAdapter
{
	@Override
	public boolean isPressed(Key key)
	{
		return false;
	}

	@Override
	public Key getPressed()
	{
		return Key.NONE;
	}
}
