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
	public static boolean canMoveRight = true, canMoveLeft = true;;
	
	public World()
	{
		tile = new Ground();
		tile.getModelMatrix().translate(-200, 0, 0);
	}
	public void render(Shader shader, Camera camera)
	{
		tile.render(shader, camera);
	}
	
	public void update(Camera camera, Input input)
	{
		float groundPosition = tile.getModelMatrix().m30();
		if(groundPosition <= -1326)
		{
			canMoveRight = false;
			canMoveLeft = true;
			tile.translate(0, 0, 0);
			moving = false;
			Player.animationCounter = 0;
		}
		else if(groundPosition >= 228)
		{
			canMoveLeft = false;
			canMoveRight = true;
			tile.translate(0, 0, 0);
			moving = false;
			Player.animationCounter = 0;
		}else
		{
			canMoveLeft = true;
			canMoveRight = true;
		}
		
		if(input.isKeyDown(GLFW.GLFW_KEY_RIGHT) && canMoveRight)
		{
			tile.translate(-xSpeed,  0, 0);
			moving = true;
			if(Player.facingRight == false)
			{
				Player.facingRight = true;
				Player.reflect();
			}
		}
		else if(input.isKeyDown(GLFW.GLFW_KEY_LEFT) && canMoveLeft)
		{
			tile.translate(xSpeed, 0, 0);
			moving = true;
			if(Player.facingRight == true)
			{
				Player.facingRight = false;
				Player.reflect();
			}
		}else
		{
			tile.translate(0, 0, 0);
			moving = false;
			Player.animationCounter = 0;
		}
	}
	
	public Ground getTile()
	{
		return tile;
	}
	
}
