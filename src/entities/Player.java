package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import core.Main;
import engine.Camera;
import engine.Input;
import engine.Model;
import engine.Shader;
import engine.Texture;
import world.Ground;
import world.World;

public class Player
{
	private Model mesh;
	private Texture texture;
	private Texture[] animationTexture;
	
	private Vector3f position;
	private static Matrix4f modelMatrix;
	public static boolean facingRight = true;
	public static int animationCounter = 0;
	
	public Player()
	{
		float[] verticies = new float[]
				{
					-10f, 10f, 0.2f, //Top left     1
					10f, 10f, 0.2f,	//Top Right    2
					10f, -10f, 0.2f, //Bottom Right 3
					-10f, -10f,0.2f, //Bottom Left  4
					
				};
		
		float[] textures = new float[]
		{
			0,0,
			1,0,
			1,1,
			0,1,
			
		};
		
		int[] indicies = new int[]
		{
			0,1,2,
			2,3,0
		};
		
		texture = new Texture("/res/survivor_idleRight.png");
		
		animationTexture = new Texture[4];
		animationTexture[0] = texture;
		
		for(int i = 1; i <= animationTexture.length - 1; i++)
			animationTexture[i] = new Texture("/res/survivor_runningF" + (i) + "Right.png");
		
		mesh = new Model(verticies, textures, indicies);
		
		modelMatrix = new Matrix4f().translate(Main.WIDTH/2, Ground.HEIGHT + 40, 0).scale(4);
		position = new Vector3f();
	}
	
	public void render(Shader shader, Camera camera)
	{
		if(animationCounter >= animationTexture.length)
			animationCounter = 0;
		shader.bind();
		texture.bind();
		animationTexture[animationCounter].bind(0);
		modelMatrix.translate(position);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection());
		shader.setUniform("model", modelMatrix.translate(position));
		
		mesh.render();
		shader.unBind();		
//		animationCounter++;
		
	}
	
	public void update(World world, Input input)
	{
		if(World.moving && world.getTile().getModelMatrix().m30() % 3 == 0)
			animationCounter++;
		
		if(input.isKeyDown(GLFW.GLFW_KEY_SPACE))
		{
			if(Bullet.bulletList.isEmpty())
			{
				Bullet.createBullet();			
			}
			else if(facingRight)
			{
				if (Bullet.bulletList.get(Bullet.bulletList.size() - 1).getX() >= Main.WIDTH/2 + 38 + 25)
					Bullet.createBullet();
			}
			else if(!facingRight)
			{
				if (Bullet.bulletList.get(Bullet.bulletList.size() - 1).getX() <= Main.WIDTH/2 - (38 + 25))
					Bullet.createBullet();
			}
	}
	}
	
	public static void reflect()
	{
		if(facingRight == false)
			modelMatrix.reflect(1, 0, 0, 0);		
		else if(facingRight == true)
			modelMatrix.reflect(-1, 0, 0, 0);		
	}
	
	public float getPosition()
	{
		return modelMatrix.m30();
	}
}
