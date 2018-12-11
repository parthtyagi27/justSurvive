/**
 * Name: Figure.java
 * Authors: Al Bondad, Anand Batbaatar
 * Description: Class used to make player Figures.
 */
public class Player extends Figure{
    private int health, ammo, score;
    String direction;
    
    /**
     * constructs new Player object
     * 
     * @param newXLoc starting value for xLoc
     * @param newYLoc starting value for yLoc
     * @param newTexture path to image
     */
    public Player(int newXLoc, int newYLoc) {
        super(newXLoc, newYLoc, "imgs/survivor_idleRight.png");
        health = 0;
        ammo = 0;
        score = 0;
        direction = "right";
    }
    
    /**
     * returns the current Player object's health
     * 
     * @return current Player object's health
     */
    public int getHealth() {
        return health;
    }
    /**
     * sets the health for the Player object's
     * 
     * @param newHealth new value for Player object's health
     */
    public void setHealth(int newHealth) {
        health = newHealth;
    }
    /**
     * adds a value to the Player object's current health
     * 
     * @param newHealth value added to Player object's current health
     */
    public void changeHealth(int newHealth) {
        health += newHealth;
    }
    
    /**
     * returns the Player object's current ammo amount
     * 
     * @return Player object's current ammo amount
     */
    public int getAmmo() {
        return ammo;
    }
    /**
     * sets the Player object's amount of ammo for the
     * 
     * @param newAmmo new value for Player object's ammo
     */
    public void setAmmo(int newAmmo) {
        ammo = newAmmo;
    }
    /**
     * adds a value to the Player object's current ammo amount
     * 
     * @param newAmmo value added to Player object's current ammo amount
     */
    public void changeAmmo(int newAmmo) {
        ammo += newAmmo;
    }
    
    /**
     * returns the current Player object's score
     * 
     * @return current Player object's score
     */
    public int getScore() {
        return score;
    }
    /**
     * sets the Player object's score
     * 
     * @param newScore new value for Player object's score
     */
    public void setScore(int newScore) {
        score = newScore;
    }
    /**
     * adds a value to the Player object's current score
     * 
     * @param newScore value added to current Player object's score
     */
    public void changeScore(int newScore) {
        score += newScore;
    }
    
    /**
     * returns the current Player object's direction
     * 
     * @return current Player object's direction
     */
    public String getDirection() {
        return direction;
    }
    /**
     * sets the Player object's direction
     * 
     * @param newDirection new value for Player object's direction
     */
    public void setDirection(String newDirection) {
        direction = newDirection;
    }
    
}