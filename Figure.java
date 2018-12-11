import java.awt.Image;
import java.awt.Component;
import java.io.*;
import javax.imageio.*;

/**
 * Name: Figure.java
 * Authors: Al Bondad, Anand Batbaatar
 * Description: Class used to make simple elements in the game.
 */
public class Figure extends Component
{
    int xLoc, xLocRel, yLoc, yLocRel, width, height;
    String source;
    Image texture;
    
    /**
     * constructs a new Figure object
     * 
     * @param newXLoc starting value for xLoc
     * @param newYLoc starting value for yLoc
     * @param newTexture path to image
     */
    public Figure(int newXLoc, int newYLoc, String newTexture) {
        xLoc = newXLoc;
        yLoc = newYLoc;
        xLocRel = newXLoc;
        yLocRel = newYLoc;
        source = newTexture;
        
        try {
            texture = ImageIO.read(new File(newTexture));
			width = texture.getWidth(this);
			height = texture.getHeight(this);            
        }
        catch (IOException e) {
            
        }
        
        
    }
    
    /**
     * returns Figure object's xLoc
     * 
     * @return Figure object's xLoc
     */
    public int getXLoc() {
        return xLoc;
    }
    /**
     * sets value for Figure object's xLoc
     * 
     * @param newXLoc new value for Figure object's xLoc
     */
    public void setXLoc(int newXLoc) {
        xLoc = newXLoc;
    }
    /**
     * adds a value to the Figure object's current xLoc
     * 
     * @param newXLoc value added to Figure object's current xLoc
     */
    public void changeXLoc(int newXLoc) {
        xLoc += newXLoc;
    }
    
    /**
     * returns Figure object's xLocRel
     * 
     * @return Figure object's xLocRel
     */
    public int getXLocRel() {
        return xLocRel;
    }
    /**
     * sets value for Figure object's xLocRel
     * 
     * @param newXLocRel new value for Figure object's xLocRel
     */
    public void setXLocRel(int newXLocRel) {
        xLocRel = newXLocRel;
    }
    /**
     * adds a value to the Figure object's current xLocRel
     * 
     * @param newXLocRel value added to Figure object's current xLocRel
     */
    public void changeXLocRel(int newXLocRel) {
        xLocRel += newXLocRel;
    }
    
    /**
     * returns Figure object's yLoc
     * 
     * @return Figure object's yLoc
     */
    public int getYLoc() {
        return yLoc;
    }
    /**
     * sets value for Figure object's yLoc
     * 
     * @param newYLoc new value for Figure object's yLoc
     */
    public void setYLoc(int newYLoc){ 
        yLoc = newYLoc;
    }
    /**
     * adds a value to the Figure object's current yLoc
     * 
     * @param newYLoc value added to Figure object's current yLoc
     */
    public void changeYLoc(int newYLoc) {
        yLoc += newYLoc;
    }
    
    /**
     * returns Figure object's yLocRel
     * 
     * @return Figure object's yLocRel
     */
    public int getYLocRel() {
        return yLocRel;
    }
    /**
     * sets value for Figure object's yLocRel
     * 
     * @param newYLocRel new value for Figure object's yLocRel
     */
    public void setYLocRel(int newYLocRel){ 
        yLoc = newYLocRel;
    }
    /**
     * adds a value to the Figure object's current yLocRel
     * 
     * @param newYLocRel value added to Figure object's current yLocRel
     */
    public void changeYLocRel(int newYLocRel) {
        yLocRel += newYLocRel;
    }
    
    /**
     * returns Figure object's texture
     * 
     * @return Figure object's texture
     */
    public Image getImage() {
        return texture;
    }
    /**
     * returns Figure object's texture's file path
     * 
     * @return Figure object's texture's file path
     */
    public String getImageSource() {
        return source;
    }
    /**
     * returns Figure object's texture's width
     * 
     * @return Figure object's texture's width
     */
        public int getWidth() {
        return width;
    }
    /**
     * returns Figure object's texture's height
     * 
     * @return Figure object's texture's height
     */
    public int getHeight() {
        return height;
    }
    /**
     * sets Figure object's texture
     * 
     * @param newSource new file path for Figure object's texture
     */
    public void setImage(String newSource) {
        source = newSource;
                    
        try {
            texture = ImageIO.read(new File(newSource));
        }
        catch (IOException e) {
            
        }
        
        width = texture.getWidth(this);
        height = texture.getHeight(this);        
    }
    
    /**
     * checks if Figure object is touchgin a certain Figure object
     * 
     * @param otherFigure the Figure object that this Figure object is being checked for touching
     */
    public boolean touching(Figure otherFigure) {
        if (
            ((xLoc >= otherFigure.getXLoc() &&
            xLoc <= otherFigure.getXLoc() + otherFigure.getWidth()) ||
            
            (xLoc+width >= otherFigure.getXLoc() && 
            xLoc+width <= otherFigure.getXLoc() + otherFigure.getWidth())) 
            
            &&
            
            ((yLoc >= otherFigure.getYLoc() && 
            yLoc <= otherFigure.getYLoc() + otherFigure.getHeight()) ||
            
            (yLoc+height >= otherFigure.getYLoc() && 
            yLoc+height <= otherFigure.getYLoc() + otherFigure.getHeight()))
            
            ||
            
            ((otherFigure.getXLoc() >= xLoc &&
            otherFigure.getXLoc() <=  xLoc + width) ||
            
            (otherFigure.getXLoc() + otherFigure.getWidth() >= xLoc && 
            otherFigure.getXLoc() + otherFigure.getWidth() <= width))
            
            &&
            
            ((otherFigure.getYLoc() >= yLoc && 
            otherFigure.getYLoc() <= yLoc + height) ||
            
            (otherFigure.getYLoc()+otherFigure.getHeight() >= yLoc && 
            otherFigure.getYLoc()+otherFigure.getHeight() <= yLoc + height))
            ) {
                return true;
            }
        return false;
    }
}
