package font;

import java.util.ArrayList;
import java.util.Map;

import org.joml.Matrix4f;

import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;

public class Text
{
	private FontMesh fontMesh;
	private Texture fontTexture;
	private Map<Character, CharInfo> map;
	public static final int[] indicies = new int[]
			{
					0,1,2,
					2,3,0
				};
	
	private ArrayList<Model> modelList;
	private Matrix4f matrix;
	private int width = 0;
	
	public Text(FontMesh mesh)
	{
		fontMesh = mesh;
		fontTexture = fontMesh.getTexture();
		map = fontMesh.getCharMap();
		
		modelList = new ArrayList<Model>();
		matrix = new Matrix4f();
	}
	
	public void loadText(String text)
	{
		modelList.clear();
		float startX = 0;
		for(int i = 0; i < text.length(); i++)
		{
			CharInfo charInfo = map.get(text.charAt(i));
			System.out.println(charInfo.getStartX() + " " + charInfo.getWidth() +  " " + fontMesh.getWidth());
			float[] verticies = 
			{
				0 + startX, fontMesh.getHeight(), 0.3f,
				charInfo.getWidth() + startX, fontMesh.getHeight(), 0.3f,
				charInfo.getWidth() + startX, 0, 0.3f,
				0 + startX, 0, 0.3f
			};
			//character coordinates for debugging
//			System.out.println((float)((float)charInfo.getStartX() / (float)fontMesh.getWidth()));
//			System.out.println((float)((charInfo.getStartX() + charInfo.getWidth()) / fontMesh.getWidth()));
//			System.out.println((float)((charInfo.getStartX() + charInfo.getWidth()) / fontMesh.getWidth()));
//			System.out.println((float)(charInfo.getStartX() / fontMesh.getWidth()));
			float[] tex = 
			{
				(float)((float)charInfo.getStartX() / (float)fontMesh.getWidth()), 0,
				(float)((float)(charInfo.getStartX() + (float)charInfo.getWidth()) / (float)fontMesh.getWidth()), 0,
				(float)((float)(charInfo.getStartX() +(float) charInfo.getWidth()) / (float)fontMesh.getWidth()), 1,
				(float)((float)charInfo.getStartX() / (float)fontMesh.getWidth()), 1
			};
			
			modelList.add(new Model(verticies, tex, indicies));
			startX += charInfo.getWidth();
			width += charInfo.getWidth();
		}
	}
	
	public void translate(float x, float y, float z)
	{
		matrix.translate(x, y, z);
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public float getHeight()
	{
		return fontMesh.getHeight();
	}
	
	public void render(Shader shader, Camera camera)
	{
		shader.bind();
		fontTexture.bind();
		
		
		for(Model m : modelList)
		{
			shader.setUniform("sampler", 0);
			shader.setUniform("projection", camera.getProjection());
			shader.setUniform("model", matrix);
			m.render();
		}
		shader.unBind();
	}
}
