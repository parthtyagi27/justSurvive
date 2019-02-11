package entities;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import core.Main;
import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;
import world.Ground;

public class Bullet
{
	
	public static ArrayList<Bullet> bulletList;

	
	private Model mesh;
	private static Texture texture = new Texture("/res/bullet.png");
	
	public Matrix4f modelMatrix;
	private Vector3f position;
	
	private static float y = Ground.HEIGHT + 28;
	private float x;
	private float xSpeed;
	
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
			bulletList.add(new Bullet(Main.WIDTH/2 + 38,y));
		else
			bulletList.add(new Bullet(Main.WIDTH/2 - 38, y));
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
	
	public void update()
	{
		if(position.x() == 0)
		{
		if(Player.facingRight)
			position.x = 1;
		else
			position.x = -1;
		}
	}
}
