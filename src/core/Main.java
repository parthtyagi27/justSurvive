package core;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import engine.Camera;
import engine.Handler;
import engine.Input;
import engine.Shader;
import engine.Timer;
import engine.Window;
import entities.Bullet;
import entities.Enemy;
import entities.Player;
import world.Background;
import world.UnitManager;

public class Main
{
	static Input windowInput;
	
	public static final int WIDTH = 500, HEIGHT = 700;
	private static final double fpsCap = 1.0/60.0;
	private static double time, unprocessedTime = 0;
	private static double frameTime;
	
	private static Camera camera;
	private static Background background;
//	private static World world;
	public static Player player;
	
	private static UnitManager uManager;
	
	
	public static void main(String[] args)
	{
		//Create Window Object and Display it
		Window.setCallBack();
		Window window = new Window(WIDTH, HEIGHT, "Just Survive GL");
		window.render();
		windowInput = new Input(window);
		
		//Tell GLFW to make the currentContext to the window object by referencing its ID
		GLFW.glfwMakeContextCurrent(window.getWindowID());
		//Get Ready to render
		GL.createCapabilities();
		System.out.println("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
		System.out.println("Welcome to the Just Survive!"+ System.getProperty("line.separator") +
		"OpenGL implementation written by Parth Tyagi" + System.getProperty("line.separator") +
		"Original game design by Al Bondad");
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		

		GLFW.glfwSetKeyCallback(window.getWindowID(), new Handler());
		
		init();
		
		time = Timer.getTime();
		frameTime = 0;
		unprocessedTime = 0;
		int frames = 0;
		
//		Enemy.createEnemy();
		
		while(window.isClosed() == false)
		{
			boolean canRender = false;
			double currentTime = Timer.getTime();
			double delta = currentTime - time;
			unprocessedTime += delta;
			frameTime += delta;
			time = currentTime;
			
			while(unprocessedTime >= fpsCap)
			{
				unprocessedTime -= fpsCap;
				
				canRender = true;
				
				//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				window.update();
				//rendering code here
				update();
				//render();
				//window.swapBuffers();
				//
								
				
				if(frameTime >= 1.0)
				{
					frameTime = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
//					if(Enemy.enemyList.size() <= 3)
//						Enemy.createEnemy();
				}
			}
			
			if(canRender)
			{
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				render();
				int error = GL11.glGetError();
				if(error != GL11.GL_NO_ERROR)
					System.err.println(error);
				window.swapBuffers();
				frames++;
			}
		}
		Shader.deleteAll();
		window.flush();
		GL.destroy();
		GLFW.glfwTerminate();
		System.out.println("Disposing resources...");
		System.exit(0);
	}
	
	private static void init()
	{
		camera = new Camera(WIDTH, HEIGHT);
		Shader.loadShaders();
		background = new Background();
//		world = new World();
//		camera.setPosition(new Vector3f(-2*400/2, 0, 0));
		player = new Player();
		Bullet.bulletList = new ArrayList<Bullet>();
		Enemy.enemyList = new ArrayList<Enemy>();
		
//		unit = new Unit(Shader.backgroundShader, Shader.groundShader, camera);
		uManager = new UnitManager(Shader.groundShader, camera);
	}
	
	private static void render()
	{	
		background.render(Shader.backgroundShader, camera);
//		world.render(Shader.groundShader, camera);
		uManager.render();
		player.render(Shader.playerShader, camera);
		Bullet.render(Shader.bulletShader, camera);
		Enemy.render(Shader.enemyShader, camera);
	}
	
	private static void update()
	{

		for(Bullet b : Bullet.bulletList)
			b.update();
		
		uManager.update(windowInput);
		player.update(windowInput);
//		world.update(camera, windowInput);
//		System.out.println(Player.modelMatrix.m30());
		
		for(int i = 0; i < Bullet.bulletList.size(); i++)
			if(Bullet.bulletList.get(i).modelMatrix.m30() > 800 || Bullet.bulletList.get(i).modelMatrix.m30() < -1326)
				Bullet.bulletList.remove(i);
		
		for(Enemy e : Enemy.enemyList)
			e.update();
		
//		if(windowInput.isKeyDown(GLFW.GLFW_KEY_D))
//		{
////			if(Player.modelMatrix.m30() % 400 == 0)
//				camera.addPosition(new Vector3f(-4, 0, 0));
//			Camera.cameraMoving = true;
//		}else if(windowInput.isKeyDown(GLFW.GLFW_KEY_A))
//		{
//			camera.addPosition(new Vector3f(4, 0, 0));
//			Camera.cameraMoving = true;
//		}else
//		{
//			Camera.cameraMoving = false;
//		}
		
//		System.out.println(camera.getPosition().toString());
	}
}
