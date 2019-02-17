package entities;

import java.util.ArrayList;
import java.util.Random;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;
import world.Ground;

public class Enemy
{
	
	public static ArrayList<Enemy> enemyList;
	
	private Model mesh;
	private Texture texture;
	private Texture[] animationTexture;
	private Vector3f position;
	private Matrix4f modelMatrix;
	
	private float health;
	private static final float xSpeed = 0.5f;
	
	
	public boolean facingRight;
	public int animationCounter = 0;
	private static Random r = new Random();
	
	public Enemy(float x, float y)
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
		
		texture = new Texture("/res/robot_runningF1Left.png");
		
		animationTexture = new Texture[3];
		animationTexture[0] = texture;
		
		for(int i = 1; i <= animationTexture.length - 1; i++)
			animationTexture[i] = new Texture("/res/robot_runningF" + (i + 1) + "Left.png");
		
		mesh = new Model(verticies, textures, indicies);
		
		modelMatrix = new Matrix4f().translate(x, y, 0).scale(4);
		position = new Vector3f();
		
		health = 100;
		
		
//		if(r.nextInt(2) == 0)
//		{
//			facingRight = true;
//			reflect();
////			modelMatrix.translate(0, Ground.HEIGHT + 40, 0).scale(4);
//		}
//		else
//		{
//			facingRight = false;
////			modelMatrix.translate(Main.WIDTH, Ground.HEIGHT + 40, 0).scale(4);
//		}
	}

	public void renderEnemy(Shader shader, Camera camera)
	{
		if(animationCounter >= animationTexture.length)
			animationCounter = 0;
		shader.bind();
		texture.bind();
		animationTexture[animationCounter].bind(0);
//		modelMatrix.translate(position);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection());
		shader.setUniform("model", modelMatrix);
		
		mesh.render();
		shader.unBind();		
	}

	public void update()
	{
		if(health > 0)
		{
			if(modelMatrix.m30() == Player.modelMatrix.m30())
				position.x = 0;
			else 
			{
			if(modelMatrix.m30() > Player.modelMatrix.m30() && facingRight == true)
			{
				facingRight = false;
				reflect();
//				position.x = -xSpeed;
			}
			else if(modelMatrix.m30() < Player.modelMatrix.m30() && facingRight == false)
			{
				facingRight = true;
				reflect();
//				position.x = xSpeed;
			}
			else if(modelMatrix.m30() == Player.modelMatrix.m30())
				position.x = 0;
			
			if(facingRight)
				position.x = -xSpeed;
			else
				position.x = -xSpeed;
			}
			System.out.println(facingRight);
		}
		
		modelMatrix.translate(position);
	}
	
	public static void createEnemy()
	{
		Enemy e = new Enemy(r.nextInt(450), Ground.HEIGHT + 40);
		if(e.modelMatrix.m30() > Player.modelMatrix.m30())
		{
			e.facingRight = false;
			e.position.x = xSpeed;
		}
		else if(e.modelMatrix.m30() < Player.modelMatrix.m30())
		{
			e.facingRight = true;
			e.position.x = xSpeed;
			e.reflect();
		}
		enemyList.add(e);
	}
	
	public static void render(Shader shader, Camera camera)
	{
		for(Enemy e : enemyList)
			e.renderEnemy(shader, camera);
	}
	
	private void reflect()
	{
		if(facingRight == false)
			modelMatrix.reflect(-1, 0, 0, 0);		
		else if(facingRight == true)
			modelMatrix.reflect(1, 0, 0, 0);		
		
	}

}
