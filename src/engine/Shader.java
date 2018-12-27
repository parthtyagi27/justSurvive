package engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class Shader
{
	private int program, vertexShaderId, fragmentShaderId;
	
	public static Shader groundShader, backgroundShader, playerShader;
	
	public Shader(String filename)
	{
		program = GL20.glCreateProgram();
		
		vertexShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShaderId, readFile(filename + ".vert"));
		GL20.glCompileShader(vertexShaderId);
		//Compile status of anything other than 1 is an error
		if(GL20.glGetShaderi(vertexShaderId, GL20.GL_COMPILE_STATUS) != 1)
		{
			System.err.println(GL20.glGetShaderInfoLog(vertexShaderId));
			System.exit(1);
		}
		
		fragmentShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShaderId, readFile(filename + ".frag"));
		GL20.glCompileShader(fragmentShaderId);
		//Compile status of anything other than 1 is an error
		if(GL20.glGetShaderi(fragmentShaderId, GL20.GL_COMPILE_STATUS) != 1)
		{
			System.err.println(GL20.glGetShaderInfoLog(fragmentShaderId));
			System.exit(1);
		}
		
		GL20.glAttachShader(program, vertexShaderId);
		GL20.glAttachShader(program, fragmentShaderId);
		
		GL20.glBindAttribLocation(program, 0, "vertices");
		GL20.glBindAttribLocation(program, 1, "textures");
		
		GL20.glLinkProgram(program);
		if(GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) != 1)
		{
			System.err.println(GL20.glGetProgramInfoLog(program));
			System.exit(1);
		}
		GL20.glValidateProgram(program);
		if(GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) != 1)
		{
			System.err.println(GL20.glGetProgramInfoLog(program));
			System.exit(1);
		}
	}
	
	public static void loadShaders()
	{
		groundShader = new Shader("/shaders/ground");
		backgroundShader = new Shader("/shaders/background");
		playerShader = new Shader("/shaders/player");
	}
	
	public void setUniform(String name, int value)
	{
		int location = GL20.glGetUniformLocation(program, name);
		if(location != -1)
			GL20.glUniform1i(location, value);
	}
	
	public void setUniform(String name, Matrix4f value)
	{
		int location = GL20.glGetUniformLocation(program, name);
		FloatBuffer  buffer = BufferUtils.createFloatBuffer(4 * 4);
		value.get(buffer);
		if(location != -1)
			GL20.glUniformMatrix4fv(location, false, buffer);
	}
	
	public void setUniform(String name, Vector3f value)
	{
		int location = GL20.glGetUniformLocation(program, name);
		FloatBuffer  buffer = BufferUtils.createFloatBuffer(3);
		value.get(buffer);
		if(location != -1)
			GL20.glUniformMatrix3fv(location, false, buffer);
	}
	
	public void bind()
	{
		GL20.glUseProgram(program);
	}
	
	private String readFile(String filename)
	{
		StringBuilder string = new StringBuilder();
		BufferedReader bufferedReader;
		try
		{
			bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
			String line;
			while((line = bufferedReader.readLine()) != null)
			{
				string.append(line);
				string.append("\n");
			}
			bufferedReader.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return string.toString();
	}
	
	public void delete()
	{
		GL20.glDetachShader(program, vertexShaderId);
		GL20.glDetachShader(program, fragmentShaderId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		GL20.glDeleteProgram(program);
	}
	
	public static void deleteAll()
	{
		groundShader.delete();
		backgroundShader.delete();
		playerShader.delete();
	}
	
	public void unBind()
	{
		GL20.glUseProgram(0);
	}
}
