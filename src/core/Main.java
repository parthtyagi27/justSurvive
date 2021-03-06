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
import font.FontMesh;
import font.Text;
import world.Background;
import world.Ground;
import world.UnitManager;

public class Main
{
	static Input windowInput;
	
	public static final int WIDTH = 500, HEIGHT = 700;
	private static final double fpsCap = 1.0/60.0;
	private static double time, unprocessedTime = 0;
	private static double frameTime;
	
	public static final int maxEnemies = 5;
	
	private static Camera camera;
	private static Background background;
//	private static World world;
	public static Player player;
	
	private static UnitManager uManager;
	
	private static Window window;
	
	private static FontMesh fm;
	private static Text healthLabel;
	
	
	public static void main(String[] args)
	{
		//Create Window Object and Display it
		Window.setCallBack();
		window = new Window(WIDTH, HEIGHT, "Just Survive GL");
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
//		GL11.glEnable(GL11.GL_STENCIL_TEST);
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
					perSecondUpdate();
					
				}
			}
			
			if(canRender)
			{
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
//				GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
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
		Enemy.createEnemy();

		fm = new FontMesh("/res/font.ttf", 16);
		healthLabel = new Text(fm);
		healthLabel.loadText("Health = " + Player.health);
		healthLabel.translate(WIDTH - healthLabel.getWidth() - 5, HEIGHT - healthLabel.getHeight() - 5, 0);
	}
	
	private static void render()
	{	
		background.render(Shader.backgroundShader, camera);
//		world.render(Shader.groundShader, camera);
		uManager.render();
		player.render(Shader.playerShader, camera);
		Enemy.render(Shader.enemyShader, camera);
		Bullet.render(Shader.bulletShader, camera);
		healthLabel.render(Shader.textShader, camera);
	}
	
	private static void perSecondUpdate()
	{
		for(Enemy e : Enemy.enemyList)
		{
			if(e.isTouchingPlayer)
				Player.health -= 10;
		}
		
		System.out.println("Player Health = " + Player.health);
		healthLabel.loadText("Health = " + Player.health);
		
		if(Enemy.enemyList.size() < maxEnemies)
			Enemy.createEnemy();
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
		{
			Bullet b = Bullet.bulletList.get(i);
			for(Enemy e : Enemy.enemyList)
			{
//				if(b.modelMatrix.m30() >= e.modelMatrix.m30() && b.position.x > 0 && Player.facingRight)
//				{
//					e.health -= Bullet.bulletDamage;
//					System.out.println("Enemy hit = " + e.health);
//					Bullet.bulletList.remove(i);
//				}
//				
//				if(b.modelMatrix.m30() <= e.modelMatrix.m30() + 40 && b.position.x < 0 && !Player.facingRight)
//				{
//					e.health -= Bullet.bulletDamage;
//					System.out.println("Enemy hit = " + e.health);
//					Bullet.bulletList.remove(i);
//				}
//				if(b.modelMatrix.m30() <= e.modelMatrix.m30() || b.modelMatrix.m30() >= e.modelMatrix.m30() + 40)
//				{
//					if(Player.facingRight && b.modelMatrix.m30() == e.modelMatrix.m30())
//					{
//						e.health -= Bullet.bulletDamage;
//						System.out.println("Enemy hit = " + e.health);
//						Bullet.bulletList.remove(i);
//					}else if(!Player.facingRight && b.modelMatrix.m30() == e.modelMatrix.m30() + 40)
//					{
//						e.health -= Bullet.bulletDamage;
//						System.out.println("Enemy hit = " + e.health);
//						Bullet.bulletList.remove(i);
//					}
//				}
				
				if(b.modelMatrix.m30() < e.modelMatrix.m30() + 40 && b.modelMatrix.m30() + 3 > e.modelMatrix.m30())
				{
					if(b.modelMatrix.m31() >= Ground.HEIGHT && b.modelMatrix.m31() <= Ground.HEIGHT + 40)
					{
						e.health -= Bullet.bulletDamage;
//						System.out.println("Enemy hit = " + e.health);
						Bullet.bulletList.remove(i);
						break;
					}
				}
				
				if(b.modelMatrix.m30() <= - 500 || b.modelMatrix.m30() >= 1000)
					Bullet.bulletList.remove(b);
			}
			
//			if(Bullet.bulletList.get(i).modelMatrix.m30() > 800 || Bullet.bulletList.get(i).modelMatrix.m30() < -1326)
//				Bullet.bulletList.remove(i);

		}
		for(int i = 0; i < Enemy.enemyList.size(); i++)
		{
			if(Enemy.enemyList.get(i).health <= 0)
				Enemy.enemyList.remove(i);
			else
				Enemy.enemyList.get(i).update();
		}
		
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
