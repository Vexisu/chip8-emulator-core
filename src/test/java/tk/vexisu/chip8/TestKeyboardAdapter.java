package tk.vexisu.chip8;

import tk.vexisu.chip8.keyboard.Key;
import tk.vexisu.chip8.keyboard.KeyboardAdapter;

public class TestKeyboardAdapter implements KeyboardAdapter
{
	int pressedKey = 2;

	@Override
	public boolean isPressed(Key key)
	{
		return key.getKeyCode() == this.pressedKey;
	}

	@Override
	public Key getPressed()
	{
		return Key.getByCode(pressedKey);
	}
}
