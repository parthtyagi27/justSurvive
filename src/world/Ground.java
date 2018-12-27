package world;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;

public class Ground
{
	private Model model;
	private Texture texture;
	public Matrix4f mMatrix;
	private Vector3f position;
	public static final float HEIGHT = 50 * 4;
	
	public Ground()
	{
		float[] verticies = new float[]
				{
					0f, 50f, 0.1f, //Top left     1
					400f, 50f, 0.1f,	//Top Right    2
					400f, 0f, 0.1f, //Bottom Right 3
					0f, 0f,0.1f, //Bottom Left  4
					
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
		model = new Model(verticies, textures, indicies);
		position = new Vector3f();
		mMatrix = new Matrix4f().scale(4);
	}
	
	public void render(Shader shader, Camera camera)
	{
		shader.bind();
		texture.bind(0);

		mMatrix.translate(position);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection());
		shader.setUniform("model", mMatrix);
		model.render();
		shader.unBind();
	}
	
	public void translate(float x, float y, float z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	public float getX()
	{
		return position.x;
	}
	
	public Matrix4f getModelMatrix()
	{
		return mMatrix;
	}
}
