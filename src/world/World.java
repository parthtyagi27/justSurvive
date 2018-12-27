package world;

import org.lwjgl.glfw.GLFW;

import engine.Camera;
import engine.Input;
import engine.Shader;
import entities.Player;

public class World
{
	private Ground tile;
	private float xSpeed = 1f;
	public static boolean moving = false;

	
	public World()
	{
		tile = new Ground();
		tile.getModelMatrix().translate(-150, 0, 0);
	}
	public void render(Shader shader, Camera camera)
	{
		tile.render(shader, camera);
	}
	
	public void update(Camera camera, Input input)
	{
		if(input.isKeyDown(GLFW.GLFW_KEY_RIGHT))
		{
			tile.translate(-xSpeed,  0, 0);
			moving = true;
			if(Player.facingRight == false)
			{
				Player.facingRight = true;
				Player.reflect();
			}
		}
		else if(input.isKeyDown(GLFW.GLFW_KEY_LEFT))
		{
			tile.translate(xSpeed, 0, 0);
			moving = true;
			if(Player.facingRight == true)
			{
				Player.facingRight = false;
				Player.reflect();
			}
		}
		else
		{
			tile.translate(0, 0, 0);
			moving = false;
			Player.animationCounter = 0;
//			delta = tile.getModelMatrix().m30() - init;
//			System.out.println(delta);
		}
		System.out.println(tile.getModelMatrix().m30());
	}
	
	public Ground getTile()
	{
		return tile;
	}
}
