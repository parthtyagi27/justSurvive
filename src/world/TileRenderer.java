package world;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;

public class TileRenderer
{
	private HashMap<String , Texture> tileTextures;
	private Model model;
	
	public TileRenderer()
	{
		tileTextures = new HashMap<String, Texture>();
		float[] verticies = new float[]
				{
					-10f, 10f, -0.1f, //Top left     1
					10f, 10f,-0.1f,	//Top Right    2
					10f, -10f, -0.1f, //Bottom Right 3
					-10f, -10f,-0.1f, //Bottom Left  4
					
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
		
		model = new Model(verticies, textures, indicies);
		
		for(int i = 0; i < Tile.tiles.length; i++)
		{
			if(Tile.tiles[i] != null)
			{
				if(!tileTextures.containsKey(Tile.tiles[i].getTexture()))
				{
					String tex = Tile.tiles[i].getTexture();
					tileTextures.put(tex, new Texture(tex + ".png"));
				}
			}
		}
	}
	
	public void renderTile(byte id, int x, int y, Shader shader, Matrix4f world, Camera camera)
	{
		shader.bind();
		if(tileTextures.containsKey(Tile.tiles[id].getTexture()))
			tileTextures.get(Tile.tiles[id].getTexture()).bind(0);
		
		Matrix4f tilePosition = new Matrix4f().translate(new Vector3f(x + 1f, y+1f, 0));
		Matrix4f target = new Matrix4f();
		
		camera.getProjection().mul(world, target);
		target.mul(tilePosition);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		
		model.render();
		shader.unBind();
	}
}
