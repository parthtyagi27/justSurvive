package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import core.Main;
import engine.Camera;
import engine.Handler;
import engine.Input;
import engine.Model;
import engine.Shader;
import engine.Texture;
import world.Ground;

public class Player
{
	private Model mesh;
	private Texture texture;
	private Texture[] animationTexture;
	
	public static float health = 100;
	
	public static Vector3f position;
	public static Matrix4f modelMatrix;
	
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
//		modelMatrix.translate(position);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection());
		shader.setUniform("model", modelMatrix.translate(position));
		
		mesh.render();
		shader.unBind();		
//		animationCounter++;
		
	}
	
	public void update(Input input)
	{
		if(health > 0)
		{
//
//		if(UnitManager.isMoving && (UnitManager.bg[0].getX() % 5 == 0 || UnitManager.bg[1].getX() % 5 == 0 || UnitManager.bg[2].getX() % 5 == 0))
//		{
//			animationCounter++;
//		}else
//		{
//			System.out.println(UnitManager.isMoving);
//			animationCounter = 4;
//		}
		
		if(animationCounter > animationTexture.length)
		{
			animationCounter = 0;
		}
		//Camera moving player code
//		if(input.isKeyDown(GLFW.GLFW_KEY_D))
//		{
//			if(!facingRight)
//			{
//				facingRight = true;
//				reflect();				
//			}
//			position.x = 1;
//		}
//		else if(input.isKeyDown(GLFW.GLFW_KEY_A))
//		{
//			if(facingRight)
//			{								
//				facingRight = false;
//				reflect();
//				System.out.println("reflected");
//			}
//			position.x = 1;
//		}
//		else
//		{
//			position.x = 0;
//			animationCounter = 0;
//		}
		
		if(Handler.isKeyDown(GLFW.GLFW_KEY_W) && position.y >= 0)
		{
			if(modelMatrix.m31() < Ground.HEIGHT + 150 && position.y <= 2)
				accelerateY(0.2f, 1.2f);
			else
				accelerateY(0.25f, -1.2f);
		}
		else
		{
			if(modelMatrix.m31() > Ground.HEIGHT + 40)
			{
//				if(position.y > 0)
					accelerateY(0.25f, -1.2f);
			}
			else if(modelMatrix.m31() < Ground.HEIGHT + 40)
			{
				modelMatrix.m31(Ground.HEIGHT + 40);
				position.y = 0;
			}
		}
	
	
		if(Handler.isKeyDown(GLFW.GLFW_KEY_SPACE))
		{
			if(Bullet.bulletList.isEmpty())
			{
				Bullet.createBullet();			
			}
			else if(facingRight)
			{
				if (Bullet.bulletList.get(Bullet.bulletList.size() - 1).getX() >= modelMatrix.m30() + 38 + Bullet.bulletDistance || Bullet.bulletList.get(Bullet.bulletList.size() - 1).position.x() <= -0.5f)
					Bullet.createBullet();
			}
			else if(!facingRight)
			{
				if (Bullet.bulletList.get(Bullet.bulletList.size() - 1).getX() <= modelMatrix.m30() - (38 + Bullet.bulletDistance)|| Bullet.bulletList.get(Bullet.bulletList.size() - 1).position.x() >= 0.5f)
					Bullet.createBullet();
			}
		}
		}
	}
	
	private void accelerateY(float acc, float bound)
	{
		if(bound > 0)
		{
			if(position.y <= bound)
				position.y += acc;
		}
		else if(bound < 0)
		{
			if(position.y > bound)
				position.y -=2* acc;
		}
	}
	
	public static void reflect()
	{
		if(facingRight == false)
			modelMatrix.reflect(1, 0, 0, 0);		
		else if(facingRight == true)
			modelMatrix.reflect(-1, 0, 0, 0);	
		
//		modelMatrix.m30(x);
	}
	
	public float getPosition()
	{
		return modelMatrix.m30();
	}
}
