package world;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;

public class Tile
{
	
	private Texture texture;
	private Model mesh;
	private Matrix4f modelMatrix;
	private Vector3f position;
	
	public Tile(float x)
	{
		float vertices[] = 
		{
			0, 100f, 0.1f,
			100, 100f, 0.1f,
			100, 0f, 0.1f,
			0, 0f, 0.1f,
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
		
		texture = new Texture("/res/dirtGrass.png");
		mesh = new Model(vertices, textures, indicies);
		
		position = new Vector3f();
		modelMatrix = new Matrix4f().scale(2);
		modelMatrix.m30(x);
	}
	
	public void render(Shader shader, Camera camera)
	{
		shader.bind();
		texture.bind(0);
		
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection());
		shader.setUniform("model", modelMatrix);
		
		mesh.render();
		shader.unBind();
	}
	
	public Matrix4f getModelMatrix()
	{
		return modelMatrix;
	}
	
	public void translate(float x, float y, float z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	public void update()
	{
		modelMatrix.translate(position);
	}
}
