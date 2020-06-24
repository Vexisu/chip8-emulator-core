package tk.vexisu.chip8.keyboard;

public interface KeyboardAdapter
{
	boolean isPressed(Key key);

	Key getPressed();
}
