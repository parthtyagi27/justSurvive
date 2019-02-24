package engine;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryUtil;

public class Texture
{
	private int id, width, height;
	
	public Texture(String name)
	{
		BufferedImage bufferedImage;
		try
		{
			bufferedImage = ImageIO.read(getClass().getResourceAsStream(name));
			width = bufferedImage.getWidth();
			height = bufferedImage.getHeight();
			
			int[] rawPixels = new int[width * height * 4];
			rawPixels = bufferedImage.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for(int i = 0; i < width; i++)
			{
				for(int j = 0; j < height; j++)
				{
					int pixel = rawPixels[i*width + j];
					pixels.put((byte)((pixel >> 16) & 0xFF)); //RED
					pixels.put((byte)((pixel >> 8) & 0xFF));  //GREEN
					pixels.put((byte)(pixel & 0xFF));		  //BLUE
					pixels.put((byte)((pixel >> 24) & 0xFF)); //ALPHA
				}
			}
			
			pixels.flip();
			
			id = GL11.glGenTextures();
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}
	
	public Texture(BufferedImage bufferedImage)
	{
		try
		{
			width = bufferedImage.getWidth();
			height = bufferedImage.getHeight();
			
			int[] pixels = new int[width * height];
	        bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);

	        /* Put pixel data into a ByteBuffer */
	        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
	        for (int i = 0; i < height; i++) {
	            for (int j = 0; j < width; j++) {
	                /* Pixel as RGBA: 0xAARRGGBB */
	                int pixel = pixels[i * width + j];
	                /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
	                buffer.put((byte) ((pixel >> 16) & 0xFF));
	                /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
	                buffer.put((byte) ((pixel >> 8) & 0xFF));
	                /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
	                buffer.put((byte) (pixel & 0xFF));
	                /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
	                buffer.put((byte) ((pixel >> 24) & 0xFF));
	            }
	        }
	        /* Do not forget to flip the buffer! */
	        buffer.flip();
			
			id = GL11.glGenTextures();
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}
	
	public Texture(ByteBuffer pixels, int w, int h)
	{
		try
		{
			

		
			
//			pixels.flip();
			
			id = GL11.glGenTextures();
			width = w;
			height = h;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}
	
	
		
		
	
	
	
	public Texture(int textureId, ByteBuffer pixels, int charWidth, int charHeight)
	{
		id = textureId;
		width = charWidth;
		height = charHeight;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void bind(int sampler)
	{
		if(sampler >= 0 && sampler <= 31)
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + sampler);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		}
	}
	
}
