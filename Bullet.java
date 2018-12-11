/**
 * Name: Figure.java
 * Authors: Al Bondad, Anand Batbaatar
 * Description: Class used to make bullet Figures.
 */
public class Bullet extends Figure{
    String direction;
    
    /**
     * constructs new Bullets object
     * 
     * @param newXLoc starting value for xLoc
     * @param newYLoc starting value for yLoc
     * @param newTexture path to image
     */
    public Bullet(int newXLoc, int newYLoc) {
        super(newXLoc, newYLoc, "imgs/bullet.png");
        direction = "right";
    }
    
    /**
     * returns the current Bullet object's direction
     * 
     * @return current Bullet object's direction
     */
    public String getDirection() {
        return direction;
    }
    /**
     * sets the Bullet object's direction
     * 
     * @param newDirection new value for Bullet object's direction
     */
    public void setDirection(String newDirection) {
        direction = newDirection;
    }
    
}