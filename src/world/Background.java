package world;

import core.Main;
import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;

public class Background
{
	
	private Model model;
	private Texture texture;
	
	public Background()
	{
		float[] verticies = 
		{
				0f, Main.HEIGHT, 0f,		//Top Left 1
				Main.WIDTH, Main.HEIGHT, 0f,//Top Right 2
				Main.WIDTH,  0f, 0f,		//Bottom Right 3
				0f, 0f, 0f					//Bottom Left 4
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
	}
	
	public void render(Shader shader, Camera camera)
	{
		shader.bind();
		texture.bind(0);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection());
		model.render();
		shader.unBind();
	}
}
