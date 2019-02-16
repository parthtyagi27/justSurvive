package entities;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;

public class Bullet
{
	
	public static ArrayList<Bullet> bulletList;
	public static float bulletDistance = 30;
	
	private Model mesh;
	private static Texture texture = new Texture("/res/bullet.png");
	
	public Matrix4f modelMatrix;
	public Vector3f position;
	

	
	public Bullet(float x, float y)
	{
		float[] verticies = new float[]
				{
					-1f, 1f, 0.3f, //Top left     1
					1f, 1f, 0.3f,	//Top Right    2
					1f, -1f, 0.3f, //Bottom Right 3
					-1f, -1f,0.3f, //Bottom Left  4
					
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
				
		mesh = new Model(verticies, textures, indicies);
		
		modelMatrix = new Matrix4f().translate(x, y, 0).scale(3);
		position = new Vector3f();
	}
	
	public static void createBullet()
	{
		if(Player.facingRight)
			bulletList.add(new Bullet(Player.modelMatrix.m30() + 45, Player.modelMatrix.m31() - 12));
		else
			bulletList.add(new Bullet(Player.modelMatrix.m30() - 45, Player.modelMatrix.m31() - 12));
	}
	
	private void renderBullet(Shader shader, Camera camera)
	{
		shader.bind();
		texture.bind();
		modelMatrix.translate(position);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection());
		shader.setUniform("model", modelMatrix.translate(position));
		
		mesh.render();
		shader.unBind();		
	}
	
	public static void render(Shader shader, Camera camera)
	{
		for(int i = 0; i < bulletList.size(); i++)
		{
			bulletList.get(i).renderBullet(shader, camera);
		}
	}

	public float getX()
	{
		return modelMatrix.m30();
	}
	
	public void accelerateX(float acc, float bound)
	{
		if(bound > 0)
		{
			if(position.x <= bound)
				position.x += acc;
		}else if(bound < 0)
		{
			if(position.x > bound)
				position.x -= acc;
		}
	}
	
	public void update()
	{
		if(position.x() == 0)
		{
			if(Player.facingRight)
				position.x = 0.5f;
			else
				position.x = -0.5f;
		}else
		{
			if(position.x() > 0)
				accelerateX(0.25f, 2f);
			else if(position.x() < 0)
				accelerateX(0.25f, -2f);
		}
//		}else 
//		{
//			if(Player.facingRight)
//				position.x += 0.02f;
//			else
//				position.x -= 0.02f;
//		}
		
	}
}
