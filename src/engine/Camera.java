package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera
{
	private Vector3f position;
	private Matrix4f projection;

	public static boolean cameraMoving = false;
	
	// 500 by 700 - dimensions
	public Camera(int width, int height)
	{
		position = new Vector3f(0,0,0);
//		projection = new Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2);
		projection = new Matrix4f().ortho(0, width, 0, height, -1.0f, 1.0f);
	}
	
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public void addPosition(Vector3f position)
	{
		this.position.add(position);
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	
	public void translate(float x)
	{
		position.x = x;
	}
	

	public Matrix4f getProjection()
	{
		Matrix4f target = new Matrix4f();
		Matrix4f pos = new Matrix4f().setTranslation(position);
		
		target = projection.mul(pos, target);
		return target;
	}
}
