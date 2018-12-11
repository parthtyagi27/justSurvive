/**
 * Name: Figure.java
 * Authors: Al Bondad, Anand Batbaatar
 * Description: Class used to make dirt Figures.
 */
public class Dirt extends Figure{
    
    /**
     * constructs new Dirt object
     * 
     * @param newXLoc starting value for xLoc
     * @param newYLoc starting value for yLoc
     * @param newTexture path to image
     */
    public Dirt(int newXLoc, int newYLoc) {
        super(newXLoc, newYLoc, "imgs/dirtGrass.png");
    }
    
}