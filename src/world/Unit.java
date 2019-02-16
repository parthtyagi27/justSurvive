package world;

import org.joml.Matrix4f;

import core.Main;
import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;

public class Unit
{
	
	private Shader groundShader;
	private Camera camera;
	
	private Model groundModel;

	private Matrix4f groundModelMatrix;
	private Texture groundTexture;
	
	public static final float groundHeight = 200;
	
	public Unit(Shader groundShader, Camera camera)
	{
		
		float[] groundVerticies = 
		{
			0f, groundHeight, 0.1f,		//Top Left 1
			Main.WIDTH, groundHeight, 0.1f,//Top Right 2
			Main.WIDTH,  0f, 0.1f,		//Bottom Right 3
			0f, 0f, 0.1f					//Bottom Left 4
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
		
		groundTexture = new Texture("/res/dirtGrass.png");
		groundModel = new Model(groundVerticies, textures, indicies);
		
		groundModelMatrix = new Matrix4f();
		
		this.camera = camera;
		this.groundShader = groundShader;
	}
	
	public void render()
	{
		//render ground
		groundShader.bind();
		groundTexture.bind(0);
		groundShader.setUniform("sampler", 0);
		groundShader.setUniform("projection", camera.getProjection());
		groundShader.setUniform("model", groundModelMatrix);
		groundModel.render();
		groundShader.unBind();
	}
	
	public float getX()
	{
		return groundModelMatrix.m30();
	}
	
	public void translateX(float xCoord)
	{
		groundModelMatrix.translate(xCoord, 0, 0);
	}
	
	public void setX(float n)
	{
		groundModelMatrix.m30(n);
	}
}
