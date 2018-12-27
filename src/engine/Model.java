package engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Model
{
	private int drawCount, vertexId, textureId, indexId;
	private boolean usingTexture = false;
	
	public Model(float[] verticies)
	{
		//This constructor creates a model without any textures
		//figure out the number of vertexes (one vertex has an x,y,z component)
		drawCount = verticies.length / 3;
		//generate a vertexId for the buffer
		vertexId = GL15.glGenBuffers();
		//bind buffers
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexId);
		//put vertex data in buffers
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, createFloatBuffer(verticies), GL15.GL_STATIC_DRAW);
		//unbind buffers
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public Model(float[] verticies, float[] textureCoordinates, int[] indicies)
	{
		//This constructor creates a model with Textures
		usingTexture = true;
		drawCount = indicies.length;
		vertexId = GL15.glGenBuffers();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, createFloatBuffer(verticies), GL15.GL_STATIC_DRAW);
		
		//generate texture id
		textureId = GL15.glGenBuffers();
		//bind buffers
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureId);
		//put texture data in buffer
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, createFloatBuffer(textureCoordinates), GL15.GL_STATIC_DRAW);
		
		indexId = GL15.glGenBuffers();
		
		IntBuffer buffer = BufferUtils.createIntBuffer(indicies.length);
		buffer.put(indicies);
		buffer.flip();
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	
	public void render()
	{
		/*
		GL11.glEnableClientState(GL15.GL_VERTEX_ARRAY);
		if(usingTexture)
			GL11.glEnableClientState(GL15.GL_TEXTURE_COORD_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexId);
		GL15.glVertexPointer(3, GL15.GL_FLOAT, 0, 0);
		
		if(usingTexture)
		{
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureId);
			GL15.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		}
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexId);
		
		GL15.glDrawElements(GL15.GL_TRIANGLES, drawCount, GL15.GL_UNSIGNED_INT, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL11.glDisableClientState(GL15.GL_VERTEX_ARRAY);
		if(usingTexture)
			GL11.glDisableClientState(GL15.GL_TEXTURE_COORD_ARRAY);
		*/
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexId);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		if(usingTexture)
		{
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureId);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		}
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexId);
		
		GL15.glDrawElements(GL15.GL_TRIANGLES, drawCount, GL15.GL_UNSIGNED_INT, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	private FloatBuffer createFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
