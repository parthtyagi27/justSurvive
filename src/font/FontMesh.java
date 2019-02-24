package font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import engine.Camera;
import engine.Model;
import engine.Shader;
import engine.Texture;

public class FontMesh
{
	private Font font;
	private BufferedImage image;
	private Map<Character, CharInfo> charMap;
	private Texture texture;
	
	private int width, height;
	
	public FontMesh(String path, float size)
	{
		charMap = new HashMap<Character, CharInfo>();
		try
		{
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(path)).deriveFont(size);
		} catch (FontFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedImage charImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gCImg = charImg.createGraphics();
		gCImg.setFont(font);
		FontMetrics fontMetrics = gCImg.getFontMetrics(font);
		
		String allChars = getAllAvailableChars("ISO-8859-1");
		this.width = 0;
		this.height = 0;
		
		for(char c : allChars.toCharArray())
		{
			CharInfo charInfo = new CharInfo(width, fontMetrics.charWidth(c));
			charMap.put(c, charInfo);
			width += charInfo.getWidth();
			height = Math.max(height, fontMetrics.getHeight());
		}
		gCImg.dispose();
		charImg.flush();
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		fontMetrics = g.getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.drawString(allChars, 0, fontMetrics.getAscent());
		g.dispose();
		
//		try
//		{
//			ImageIO.write(image, "png", new File("/res/test.png"));
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		texture = new Texture(image);
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public Map<Character, CharInfo> getCharMap()
	{
		return charMap;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	private String getAllAvailableChars(String charsetName)
	{
		CharsetEncoder ce = Charset.forName(charsetName).newEncoder();
		StringBuilder result = new StringBuilder();
		for (char c = 0; c < Character.MAX_VALUE; c++) {
		if (ce.canEncode(c)) {
		result.append(c);
		}
		}
		return result.toString();
	}
	
	public void renderTexture(Shader shader, Camera camera)
	{
		float[] verticies = 
			{
				0, height, 0.3f,
				width, height, 0.3f,
				width, 0, 0.3f,
				0, 0, 0.3f
			};
		
		float[] textures = new float[]
				{
					0,0,
					1,0,
					1,1,
					0,1,
					
				};
		
		Model m = new Model(verticies, textures, Text.indicies);
		Matrix4f mat = new Matrix4f();
		
		shader.bind();
		texture.bind();
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection());
		shader.setUniform("model", mat);
		
		m.render();
		shader.unBind();
	}
}
