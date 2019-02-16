package world;

import org.lwjgl.glfw.GLFW;

import core.Main;
import engine.Camera;
import engine.Input;
import engine.Shader;
import entities.Player;

public class UnitManager
{
	public static Unit[] bg;
	private static final float xSpeed = 3f;
	public static boolean isMoving = false;
	private static float playerStep;
	
	public UnitManager(Shader groundShader, Camera camera)
	{
		bg = new Unit[3];
		
		for(int i = 0; i < bg.length; i++)
			bg[i] = new Unit(groundShader, camera);
		bg[0].setX(-Main.WIDTH);
		bg[2].setX(Main.WIDTH);
	}
	
	public void update(Input windowInput)
	{
		if(windowInput.isKeyDown(GLFW.GLFW_KEY_A))
		{
			if(Player.facingRight)
			{
				Player.facingRight = false;
				Player.reflect();
			}
			for(Unit u : bg)
			{
				u.translateX(xSpeed);
				playerStep += xSpeed;
			}
			isMoving = true;
			adjustBG();
		}
		
		else if(windowInput.isKeyDown(GLFW.GLFW_KEY_D))
		{
			if(!Player.facingRight)
			{
				Player.facingRight = true;
				Player.reflect();
			}
			for(Unit u : bg)
			{
				u.translateX(-xSpeed);
				playerStep += xSpeed;
			}
			isMoving = true;
			adjustBG();
		}
		else
		{
			isMoving = false;
			playerStep = 0;
			Player.animationCounter = 0;
		}
		
		if(playerStep % 5 == 0 && playerStep != 0)
			Player.animationCounter++;
	}
	
	public void render()
	{
		for(Unit u : bg)
			u.render();
	}
	
	private void adjustBG()
	{
		if(bg[0].getX() >= 0)
		{
			bg[2].setX(bg[0].getX() - Main.WIDTH);
			Unit u2 = bg[0];
			bg[0] = bg[1];
			bg[1] = bg[2];
			bg[2] = u2;
		}
		
		if(bg[2].getX() <= 0)
		{
			bg[0].setX(bg[2].getX() + Main.WIDTH);
			Unit u0 = bg[0];
			bg[0] = bg[1];
			bg[1] = bg[2];
			bg[2] = u0;
		}
		
	}
}
