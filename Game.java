import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;

/**
 * Name: Figure.java
 * Authors: Al Bondad, Anand Batbaatar
 * Description: This class runs the game.
 * Bugs: robots randomly appearing/disappearing, weird vibrating of in game elements
 */
public class Game extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
    //CLASS VARIABLES
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static final int SCALE = 8;

    private Figure world[][] = new Figure[100][500];
    private ArrayList<Figure> mainMenu = new ArrayList<Figure>();
    private ArrayList<Figure> gamemodeMenu = new ArrayList<Figure>();
    private ArrayList<Figure> helpMenu = new ArrayList<Figure>();
    private ArrayList<Robot> robots = new ArrayList<Robot>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<AmmoBox> ammoBoxes = new ArrayList<AmmoBox>();

    private Player player;
    private int playerF = 1; 
    private int robotSpawnFactor;
    private Figure mouseHitBox;
    private Figure startButton;
    private String gameMode;
    private Figure easyButton;
    private Figure mediumButton;
    private Figure hardButton;
    private Figure helpButton;
    private Figure helpPage;
    private Figure rightArrowButton;
    private Figure leftArrowButton;

    private boolean starting = false;
    private boolean showMainMenu = true;
    private boolean showHelp = false;
    private boolean showGameModes = false;
    private int helpPageNumber = 1;
    private boolean gameOver = false;

    private long updateLastTime = System.nanoTime();
    private long frameLastTime = System.nanoTime();
    private long frameDifTime;
    private long ammoBoxLastTime = System.currentTimeMillis();
    private long ammoBoxDifTime;
    private long robotLastTime = System.currentTimeMillis();
    private long robotDifTime;
    private long resetLastTime = System.currentTimeMillis();
    private long resetDifTime;

    private int xChange;

    private int keyCode;
    private boolean leftArrowPressed = false;
    private boolean mousePressed = false;
    private boolean onePressed = false;
    private boolean twoPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private boolean spacePressed = false;

    private Timer timer;

    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon("imgs/icon.png");
        JFrame frame = new JFrame("Just Survive");
        frame.setIconImage(icon.getImage());
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(size);
        frame.setResizable(false);
        frame.add(new Game());
        frame.pack();
        frame.setVisible(true);
    }

    public Game() {
        //dealing with events
        timer = new Timer(0, this);
        timer.start();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();      

        //world
        for (int r = 0; r < world.length; r ++) {
            for (int c = 0; c < world[0].length; c ++) {
                if (r == 80 && c%10 == 0) {
                    if (Math.random() > .5) {
                        world[r][c] = new Grass(c, r);
                    }
                }
                if (r == 90 && c%10 == 0) {
                    world[r][c] = new Dirt(c, r);
                }
            }
        }

        player = new Player(45, 80);
        player.setAmmo(100);
        player.setHealth(500);

        mouseHitBox = new Figure(0, 0, "imgs/mousehitBox.png");

        startButton = new Figure(0, 50-20, "imgs/startButton.png");
        startButton.setXLoc(50-startButton.getWidth()/2);
        mainMenu.add(startButton);

        easyButton = new Figure(0, 50-30, "imgs/easyButton.png");
        easyButton.setXLoc(50-easyButton.getWidth()/2);
        gamemodeMenu.add(easyButton);

        mediumButton = new Figure(0, 50-10, "imgs/mediumButton.png");
        mediumButton.setXLoc(50-mediumButton.getWidth()/2);
        gamemodeMenu.add(mediumButton);

        hardButton = new Figure(0, 50+10, "imgs/hardButton.png");
        hardButton.setXLoc(50-hardButton.getWidth()/2);
        gamemodeMenu.add(hardButton);

        helpButton = new Figure(0, 50, "imgs/helpButton.png");
        helpButton.setXLoc(50-helpButton.getWidth()/2);
        mainMenu.add(helpButton);

        helpPage = new Figure(0, 0, "imgs/helpPage1.png");
        helpMenu.add(helpPage);

        rightArrowButton = new Figure(0, 80, "imgs/rightArrowButton.png");
        rightArrowButton.setXLoc(100-5-rightArrowButton.getWidth());
        helpMenu.add(rightArrowButton);

        leftArrowButton = new Figure(5, 80, "imgs/leftArrowButton.png");
        helpMenu.add(leftArrowButton);
    }

    public void actionPerformed(ActionEvent ae) {
        repaint();
    }

    public void paint(Graphics g){
        long nowTime = System.nanoTime();
        long dif = (nowTime-updateLastTime);
        if (dif >= 1000000000/60) {
            update();
            dif = 0;
            updateLastTime = nowTime;
        }
        render(g);
    }

    public void update() {
        long nanoTime = System.nanoTime();
        long millisTime = System.currentTimeMillis();
        frameDifTime = nanoTime-frameLastTime;
        ammoBoxDifTime = millisTime-ammoBoxLastTime;
        robotDifTime = millisTime-robotLastTime;

        if(showMainMenu) {
            if (mouseHitBox.touching(startButton)) {
                startButton.setImage("imgs/startButtonPressed.png");
                if (mousePressed) {
                    showMainMenu = false;
                    showGameModes = true;
                    mousePressed = false;
                }
            }
            else {
                startButton.setImage("imgs/startButton.png");
            }
            if (mouseHitBox.touching(helpButton)) {
                helpButton.setImage("imgs/helpButtonPressed.png");
                if (mousePressed) {
                    showHelp = true;
                    showMainMenu = false;
                    helpPageNumber = 1;
                    helpPage.setImage("imgs/helpPage"+helpPageNumber+".png");
                }
            }
            else {
                helpButton.setImage("imgs/helpButton.png");
            }
        }
        else if(showHelp) {
            if (mouseHitBox.touching(rightArrowButton)) {
                rightArrowButton.setImage("imgs/rightArrowButtonPressed.png");
                if (mousePressed) {
                    if (helpPageNumber < 4) {
                        helpPageNumber++;
                        helpPage.setImage("imgs/helpPage"+helpPageNumber+".png");
                        mousePressed = false;
                    }
                    else {
                        showMainMenu = true;
                        showHelp = false;
                    }
                }
            }
            else {
                rightArrowButton.setImage("imgs/rightArrowButton.png");
            }
            if (mouseHitBox.touching(leftArrowButton)) {
                leftArrowButton.setImage("imgs/leftArrowButtonPressed.png");
                if (mousePressed) {
                    if (helpPageNumber > 1) {
                        helpPageNumber--;
                        helpPage.setImage("imgs/helpPage"+helpPageNumber+".png");
                        mousePressed = false;
                    }
                }
            }
            else {
                leftArrowButton.setImage("imgs/leftArrowButton.png");
            }
        }
        else if (showGameModes) {
            if (mouseHitBox.touching(easyButton)) {
                easyButton.setImage("imgs/easyButtonPressed.png");
                if (mousePressed) {
                    gameMode = "Easy";
                    showGameModes = false;
                    gameOver = false;
                    robotSpawnFactor = 1;
                }
            }
            else {
                easyButton.setImage("imgs/easyButton.png");
            }
            if (mouseHitBox.touching(mediumButton)) {
                mediumButton.setImage("imgs/mediumButtonPressed.png");
                if (mousePressed) {
                    gameMode = "Medium";
                    showGameModes = false;
                    gameOver = false;
                    robotSpawnFactor = 4;
                }
            }
            else {
                mediumButton.setImage("imgs/mediumButton.png");
            }
            if (mouseHitBox.touching(hardButton)) {
                hardButton.setImage("imgs/hardButtonPressed.png");
                if (mousePressed) {
                    gameMode = "Hard";
                    showGameModes = false;
                    gameOver = false;
                    robotSpawnFactor = 10;
                }
            }
            else {
                hardButton.setImage("imgs/hardButton.png");
            }
        }
        else if (!gameOver) {
            if (frameDifTime >= 1000000000/24) {
                frameLastTime = System.nanoTime();
                frameDifTime = 0;

                if (player.getDirection().equals("right")) {
                    player.setImage("imgs/survivor_idleRight.png");
                }
                else {
                    player.setImage("imgs/survivor_idleLeft.png");
                }
                if (dPressed && xChange > -445) {
                    xChange+=-2;
                    player.setDirection("right");
                    if (playerF < 3) {
                        playerF++;
                        player.setImage("imgs/survivor_runningF"+playerF+"Right.png");
                    }
                    else {
                        playerF = 1;
                        player.setImage("imgs/survivor_runningF"+playerF+"Right.png");
                    }
                }
                if (aPressed && xChange < 45) {
                    xChange+=2;
                    player.setDirection("left");
                    if (playerF < 3) {
                        playerF++;
                        player.setImage("imgs/survivor_runningF"+playerF+"Left.png");
                    }
                    else {
                        playerF = 1;
                        player.setImage("imgs/survivor_runningF"+playerF+"Left.png");
                    }
                }
                if (spacePressed && player.getAmmo() > 0) {
                    player.changeAmmo(-1);
                    Bullet newBullet = new Bullet(player.getXLoc()+4-xChange, player.getYLoc() + 4 + (int)(Math.random() * 3));
                    bullets.add(newBullet);
                    if (player.getDirection().equals("right")) {
                        newBullet.setDirection("right");
                        if (xChange <= 45) {
                            xChange+=1;
                        }
                    }
                    else {
                        newBullet.setDirection("left");
                        if (xChange >= -445) {
                            xChange+=-1;
                        }
                    }
                }

                if (ammoBoxDifTime >= 1000*10) {
                    ammoBoxLastTime = System.currentTimeMillis();
                    ammoBoxDifTime = 0;
                    AmmoBox newAmmoBox;

                    int xLoc = (int)(Math.random()*450);
                    if (xLoc + xChange < 0 || xLoc + xChange > 100) {
                        ammoBoxes.add(newAmmoBox = new AmmoBox(xLoc, 80));
                        newAmmoBox.setXLoc(newAmmoBox.getXLocRel() + xChange);
                    }
                }
                if (robotDifTime >= 1000/robotSpawnFactor) {
                    robotLastTime = System.currentTimeMillis();
                    robotDifTime = 0;
                    Robot newRobot;

                    int xLoc = (int)(Math.random()*450);
                    if (xLoc + xChange < 0 || xLoc + xChange > 100) {
                        robots.add(newRobot = new Robot(xLoc, 80));
                        newRobot.setXLoc(newRobot.getXLocRel() + xChange);
                    }
                }

                //world
                for (int r = 0; r < world.length; r ++) {
                    for (int c = 0; c < world[0].length; c ++) {
                        if (world[r][c] != null) {
                            Figure f = world[r][c];
                            f.setXLoc(f.getXLocRel() + xChange);
                        }
                    }
                }

                //bullets
                for (int a = 0; a < bullets.size(); a++) {
                    Bullet bullet = bullets.get(a);
                    bullet.setXLoc(bullet.getXLocRel() + xChange);
                    if (bullet.getDirection().equals("right")) {
                        bullet.changeXLocRel(5);
                    }
                    else if (bullet.getDirection().equals("left")) {
                        bullet.changeXLocRel(-5);
                    }
                    if (bullet.getXLoc() > 100 || bullet.getXLoc() < 0) {
                        bullets.remove(a);
                    }
                    bullet.setXLoc(bullet.getXLocRel() + xChange);
                }

                //robots
                for (int a = 0; a < robots.size(); a++) {
                    Robot robot = robots.get(a);
                    robot.setXLoc(robot.getXLocRel() + xChange);
                    if (robot.getXLoc() > player.getXLoc() + 9) {
                        robot.changeXLocRel(-3);
                        if (robot.getImageSource().equals("imgs/robot_runningF1Left.png")) {
                            robot.setImage("imgs/robot_runningF2Left.png");
                        }
                        else if (robot.getImageSource().equals("imgs/robot_runningF2Right.png")) {
                            robot.setImage("imgs/robot_runningF3Left.png");
                        }
                        else if (robot.getImageSource().equals("imgs/robot_runningF3Right.png")) {
                            robot.setImage("imgs/robot_runningF1Left.png");
                        }
                        else {
                            robot.setImage("imgs/robot_runningF1Left.png");
                        }
                    }
                    if (robot.getXLoc() < player.getXLoc() + 3) {
                        robot.changeXLocRel(+3);
                        if (robot.getImageSource().equals("imgs/robot_runningF1Right.png")) {
                            robot.setImage("imgs/robot_runningF2Right.png");
                        }
                        else if (robot.getImageSource().equals("imgs/robot_runningF2Right.png")) {
                            robot.setImage("imgs/robot_runningF3Right.png");
                        }
                        else if (robot.getImageSource().equals("imgs/robot_runningF3Right.png")) {
                            robot.setImage("imgs/robot_runningF1Right.png");
                        }
                        else {
                            robot.setImage("imgs/robot_runningF1Right.png");
                        }
                    }
                    for (int b = 0; b < bullets.size(); b++) {
                        Bullet bullet = bullets.get(b);
                        if (robot.touching(bullet)) {
                            bullets.remove(b);
                            b--;
                            robots.remove(a);
                            a--;
                            player.changeScore(1);
                            return;
                        }
                    }
                    if (robot.touching(player)) {
                        player.changeHealth(-1);
                    }
                    robot.setXLoc(robot.getXLocRel() + xChange);
                }

                //ammo boxes
                for (int a = 0; a < ammoBoxes.size(); a++) {
                    AmmoBox ammoBox = ammoBoxes.get(a);
                    ammoBox.setXLoc(ammoBox.getXLocRel() + xChange);
                    if (ammoBox.touching(player)) {
                        ammoBoxes.remove(a);
                        a--;
                        player.setAmmo(100);
                    }
                    ammoBox.setXLoc(ammoBox.getXLocRel() + xChange);
                }

                if (player.getHealth() <= 0) {
                    frameLastTime = System.nanoTime();
                    frameDifTime = 0;
                    ammoBoxLastTime = System.currentTimeMillis();
                    ammoBoxDifTime = 0;
                    robotLastTime = System.currentTimeMillis();
                    robotDifTime = 0;
                    bullets.clear();
                    robots.clear();
                    ammoBoxes.clear();
                    player.setHealth(500);
                    player.setAmmo(100);
                    xChange = 0;
                    gameOver = true;
                    resetLastTime = System.currentTimeMillis();
                }
            }
        }
        else {
            long nowTime = System.currentTimeMillis();
            resetDifTime = nowTime - resetLastTime;
            if (resetDifTime >= 1000 * 5) {
                showMainMenu = true;
                resetDifTime = 0;
                player.setScore(0);
            }
        }

    }

    public void render(Graphics g) {
        Image bufferImage = createImage(100, 100);
        Graphics b = bufferImage.getGraphics();

        //background
        Figure background = new Figure(0, 0, "imgs/background_sky.png");
        b.drawImage(background.getImage(),
            background.getXLoc(),
            background.getYLoc(),
            background.getWidth(),
            background.getHeight(),
            this);
        //world
        for (int r = 0; r < world.length; r ++) {
            for (int c = 0; c < world[0].length; c ++) {
                if (world[r][c] != null) {
                    Figure f = world[r][c];
                    b.drawImage(f.getImage(),
                        f.getXLoc(),
                        f.getYLoc(),
                        f.getWidth(),
                        f.getHeight(),
                        this);
                }
            }
        }
        if(showMainMenu) {
            for (Figure elements: mainMenu) {
                b.drawImage(elements.getImage(),
                    elements.getXLoc(),
                    elements.getYLoc(),
                    elements.getWidth(),
                    elements.getHeight(),
                    this);
            }
        }
        else if (showHelp) {
            for (Figure elements: helpMenu) {
                b.drawImage(elements.getImage(),
                    elements.getXLoc(),
                    elements.getYLoc(),
                    elements.getWidth(),
                    elements.getHeight(),
                    this);
            }
        }
        else if (showGameModes) {
            for (Figure elements: gamemodeMenu) {
                b.drawImage(elements.getImage(),
                    elements.getXLoc(),
                    elements.getYLoc(),
                    elements.getWidth(),
                    elements.getHeight(),
                    this);
            }
        }
        else if (!gameOver) {

            //figures
            for (Figure ammoBox: ammoBoxes) {
                b.drawImage(ammoBox.getImage(),
                    ammoBox.getXLoc(),
                    ammoBox.getYLoc(),
                    ammoBox.getWidth(),
                    ammoBox.getHeight(),
                    this);
            }
            for (Figure bullet: bullets) {
                b.drawImage(bullet.getImage(),
                    bullet.getXLoc(),
                    bullet.getYLoc(),
                    bullet.getWidth(),
                    bullet.getHeight(),
                    this);
            }

            //player
            b.drawImage(player.getImage(),
                player.getXLoc(),
                player.getYLoc(),
                player.getWidth(),
                player.getHeight(),
                this);

            //health
            b.setColor(Color.RED);
            b.fillRect(player.getXLoc(), player.getYLoc() - 3, 10, 2);
            b.setColor(Color.GREEN);
            b.fillRect(player.getXLoc(), player.getYLoc() - 3, (int)((player.getHealth()/500.00)*10), 2);

            //mouse hit box
            //b.drawImage(mouseHitBox.getImage(),
            //    mouseHitBox.getXLoc(),
            //    mouseHitBox.getYLoc(),
            //    mouseHitBox.getWidth(),
            //    mouseHitBox.getHeight(),
            //    this);    

            //robots
            for (Figure robot: robots) {
                b.drawImage(robot.getImage(),
                    robot.getXLoc(),
                    robot.getYLoc(),
                    robot.getWidth(),
                    robot.getHeight(),
                    this);
            }

        }
        else {
            b.setColor(Color.WHITE);
            b.setFont(new Font("Arial", Font.PLAIN, 10));
            b.drawString("You Died ", 23, 35);
            b.drawString("Your Score: "+player.getScore(), 13, 50);
            b.drawString("Resetting in... "+(5-resetDifTime/1000), 10, 65);
        }

        //drawing whole image
        g.drawImage(bufferImage, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, this);

        //displaying stats
        if (onePressed && !gameOver) {
            displayStatsOne(g);
        }
        else if (twoPressed && !gameOver) {
            displayStatsTwo(g);
        }
        else if (!gameOver && !showMainMenu && !showHelp && !showGameModes) {
            //score
            b.setColor(Color.WHITE);
            b.setFont(new Font("Arial", Font.PLAIN, 10));
            if (player.getScore() < 10) {
                b.drawString("Score:      " + player.getScore(), 43, 10);
            }
            else if (player.getScore() < 100) {
                b.drawString("Score:    " + player.getScore(), 43, 10);
            }
            else if (player.getScore() < 1000) {
                b.drawString("Score:  " + player.getScore(), 43, 10);
            }
            else {
                b.drawString("Score:"+player.getScore(), 43, 10);
            }
            if (player.getAmmo() < 10) {
                b.drawString("Ammo:     " + player.getAmmo(), 43, 20);
            }
            else if (player.getAmmo() < 100) {
                b.drawString("Ammo:   " + player.getAmmo(), 43, 20);
            }
            else if (player.getAmmo() < 1000) {
                b.drawString("Ammo: " + player.getAmmo(), 43, 20);
            }
            g.drawImage(bufferImage, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, this);
        }
    }

    public void displayStatsOne(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        int xValue = 0, yValue = 0;
        for (int y = 0; y <= 800; y ++) {
            if (y%80 == 0) {
                g.drawString(xValue+"", 0, y+40);
                xValue += 10;
            }
        }
        for (int x = 0; x <= 800; x ++) {
            if (x%80 == 0) {
                g.drawString(yValue+"", x, 40);
                yValue += 10;                
            }
        }
    }

    public void displayStatsTwo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        g.drawString("Health: "+player.getHealth(), 0, 40);
        g.drawString("Ammo: "+player.getAmmo(), 0, 80);
        g.drawString("Score: "+player.getScore(), 0, 120);
        g.drawString("xChange: " + xChange, 0, 160);
        g.drawString("Game Mode: " + gameMode, 0, 200);
    }

    public void keyPressed(KeyEvent evt) {
        keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_1) {
            onePressed = !onePressed;
            twoPressed = false;
        }
        if (keyCode == KeyEvent.VK_2) {
            twoPressed = !twoPressed;
            onePressed = false;
        }
        if (keyCode == KeyEvent.VK_A) {
            aPressed = true;
        }
        if (keyCode == KeyEvent.VK_D) {
            dPressed = true;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    public void keyReleased(KeyEvent evt) {
        keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (keyCode == KeyEvent.VK_D) {
            dPressed = false;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

    public void keyTyped(KeyEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseReleased(MouseEvent evt) {
        mousePressed = false;
    }

    public void mousePressed(MouseEvent evt) {
        mousePressed = true;
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void mouseMoved(MouseEvent evt) {
        mouseHitBox.setXLoc(evt.getX()/SCALE);
        mouseHitBox.setYLoc(evt.getY()/SCALE);
    }

    public void mouseDragged(MouseEvent evt) {
    }
}