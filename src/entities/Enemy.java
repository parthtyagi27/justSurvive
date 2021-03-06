package entities;

import java.util.ArrayList;
import java.util.Random;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import core.Main;
import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;
import world.Ground;
import world.UnitManager;

public class Enemy
{
	
	public static ArrayList<Enemy> enemyList;
	
	private Model mesh;
	private Texture texture;
	private Texture[] animationTexture;
	private Vector3f position;
	public Matrix4f modelMatrix;
	public boolean isTouchingPlayer = false;
	
	public float health;
	private static final float xSpeed = 0.5f;
	
	
	public boolean facingRight;
	public int animationCounter = 0;
	private static Random r = new Random();
	private float deltaPositionX = 0;
	
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
		
		health = 75;
		
		facingRight = r.nextBoolean();
		
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
			if(modelMatrix.m30() + 50 >= Player.modelMatrix.m30() && modelMatrix.m30() <= Player.modelMatrix.m30() + 40)
			{
				position.x = 0;
				isTouchingPlayer = true;
				return;
			}
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
				
				if(facingRight)
					position.x = -xSpeed;
				else
					position.x = -xSpeed;
				
				isTouchingPlayer = false;
			}
			
			
			
			if(UnitManager.isMoving)
			{
				
				if(Player.facingRight && facingRight)
				{
					position.x += xSpeed;
				}else if(Player.facingRight && !facingRight)
				{
					position.x -= xSpeed;
				}else if(!Player.facingRight && facingRight)
					position.x -= xSpeed;
				else if(!Player.facingRight && !facingRight)
					position.x += xSpeed;
				
				
			}
			
		}
		float initial = modelMatrix.m30();
		modelMatrix.translate(position);
		deltaPositionX += Math.abs(modelMatrix.m30() - initial);
		if(deltaPositionX % 3 == 0)
		{
			animationCounter++;
			deltaPositionX = 0;
		}
	}
	
	public static void createEnemy()
	{
		Enemy e = new Enemy(0, Ground.HEIGHT + 40);
		if(e.facingRight)
			e.modelMatrix.m30(-40);
		else if(!e.facingRight)
			e.modelMatrix.m30(Main.WIDTH + 40);
		if(e.modelMatrix.m30() > Player.modelMatrix.m30() - 40)
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
