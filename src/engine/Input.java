package engine;

import org.lwjgl.glfw.GLFW;

public class Input
{
	private long window;
	
	public Input(Window window)
	{
		this.window = window.getWindowID();
	}
	
	public boolean isKeyDown(int keyCode)
	{
		return GLFW.glfwGetKey(window, keyCode) == 1;
	}
	
	public boolean isMouseDown(int mouseButton)
	{
		return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
	}
}
