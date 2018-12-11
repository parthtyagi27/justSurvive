/**
 * Name: Figure.java
 * Authors: Al Bondad, Anand Batbaatar
 * Description: Class used to make ammo box Figures.
 */
public class AmmoBox extends Figure{
    
    /**
     * constructs new AmmoBox object
     * 
     * @param newXLoc starting value for xLoc
     * @param newYLoc starting value for yLoc
     * @param newTexture path to image
     */
    public AmmoBox(int newXLoc, int newYLoc) {
        super(newXLoc, newYLoc, "imgs/ammoBox.png");
    }
    
}