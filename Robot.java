/**
 * Name: Figure.java
 * Authors: Al Bondad, Anand Batbaatar
 * Description: Class used to make robot Figures.
 */
public class Robot extends Figure {
    private int health;
    String direction;
    
    /**
     * constructs new Robot object
     * 
     * @param newXLoc starting value for xLoc
     * @param newYLoc starting value for yLoc
     * @param newTexture path to image
     */
    public Robot(int newXLoc, int newYLoc) {
        super(newXLoc, newYLoc, "imgs/robot_runningF1Right.png");
        health = 0;
        direction = "right";
    }
    
    /**
     * returns the current Robot object's health
     * 
     * @return current Robot object's health
     */
    public int getHealth() {
        return health;
    }
    /**
     * sets the health for the Robot object's
     * 
     * @param newHealth new value for Robot object's health
     */
    public void setHealth(int newHealth) {
        health = newHealth;
    }
    /**
     * adds a value to the Robot object's current health
     * 
     * @param newHealth value added to Robot object's current health
     */
    public void changeHealth(int newHealth) {
        health += newHealth;
    }
    
    /**
     * returns the current Robot object's direction
     * 
     * @return current Robot object's direction
     */
    public String getDirection() {
        return direction;
    }
    /**
     * sets the Robot object's direction
     * 
     * @param newDirection new value for Robot object's direction
     */
    public void setDirection(String newDirection) {
        direction = newDirection;
    }
}