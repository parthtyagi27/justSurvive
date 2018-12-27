package world;

import org.joml.Matrix4f;

import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;

public class Background
{
	
	private Model model;
	private Texture texture;
	private Matrix4f scale;
	
	public Background()
	{
		float[] verticies = new float[]
				{
					-100f, 100f, 0, //Top left     1
					100f, 100f, 0,	//Top Right    2
					100f, -100f, 0, //Bottom Right 3
					-100f, -100f,0, //Bottom Left  4
					
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
		
		texture = new Texture("/res/background.png");
		model = new Model(verticies, textures, indicies);
		scale = new Matrix4f().scale(10);
	}
	
	public void render(Shader shader, Camera camera)
	{
		shader.bind();
		texture.bind(0);
		Matrix4f target = new Matrix4f();
		Matrix4f position = new Matrix4f().translate(0,0, 0);
		camera.getProjection().mul(scale, target);
		target.mul(position);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		model.render();
		shader.unBind();
	}
}
