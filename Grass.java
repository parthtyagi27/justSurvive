/**
 * Name: Figure.java
 * Authors: Al Bondad, Anand Batbaatar
 * Description: Class used to make grass Figures.
 */
public class Grass extends Figure{
    
    /**
     * constructs new Grass object
     * 
     * @param newXLoc starting value for xLoc
     * @param newYLoc starting value for yLoc
     * @param newTexture path to image
     */
    public Grass(int newXLoc, int newYLoc) {
        super(newXLoc, newYLoc, "imgs/grass.png");
    }
    
}