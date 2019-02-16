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
	
	private float health;
	
	private Vector3f position;
	private Matrix4f modelMatrix;
	
	public static boolean facingRight;
	public static int animationCounter = 0;
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
		
		facingRight = r.nextBoolean();
		
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
		modelMatrix.translate(position);
		
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
			if(modelMatrix.m30() > 228)
			{
				facingRight = true;
				reflect();
			}else if(modelMatrix.m30() < -1326)
			{
				facingRight = false;
				reflect();
			}
			if(!facingRight)
				position.x = -0.25f;
			else
				position.x = 0.25f;
			
			if(modelMatrix.m30() % 10 == 0)
				animationCounter++;
			if(animationCounter == animationTexture.length)
				animationCounter = 0;
			
		}
		
	}
	
	public static void createEnemy()
	{
		enemyList.add(new Enemy(r.nextInt(450), Ground.HEIGHT + 40));
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
