package engine;

import org.joml.Vector3f;

public class Entity
{
	private Model model;
	private Vector3f position;
	private float rotX, rotY;
	private float scale;
	
	public Entity(Model model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
	{
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.scale = scale;
	}
	
	public void increasePosition(float dx, float dy)
	{
		this.position.x += dx;
		this.position.y += dy;
	}
	
	public void increaseRotation(float dx, float dy)
	{
		this.rotX += dx;
		this.rotY += dy;
	}
	
	public Model getModel()
	{
		return model;
	}
	public void setModel(Model model)
	{
		this.model = model;
	}
	public Vector3f getPosition()
	{
		return position;
	}
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	public float getRotX()
	{
		return rotX;
	}
	public void setRotX(float rotX)
	{
		this.rotX = rotX;
	}
	public float getRotY()
	{
		return rotY;
	}
	public void setRotY(float rotY)
	{
		this.rotY = rotY;
	}
	public float getScale()
	{
		return scale;
	}
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	
	
}
