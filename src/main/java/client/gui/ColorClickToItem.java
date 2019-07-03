package client.gui;
import java.awt.*;
import java.awt.image.*;

/**
 * Class to manage clicks on the map
 * allows to dsitnguish the color of teh cliccked point
 */
public class ColorClickToItem{
    private BufferedImage image;
    private Rectangle imageBounds;

    /**
     *
     * @param image imagin from which take the color
     */
    public ColorClickToItem(BufferedImage image) {
        this.image = image;
        imageBounds = new Rectangle(0, 0, image.getWidth(), image.getHeight());
    }

    /**
     * When click is made this method is called in the present class in main
     * @param position is the point that say the cliccked one in the map
     * @return the color of the map, shadow of the clicked point
     */
    public Color getItemForClick(Point position) {
        return getPixelColor(position);
    }

    /**
     * Given a point in the map, it says the corresponding color to the coordinates
     * @param p
     * @return
     */
    private Color getPixelColor(Point p) {
        if(imageBounds.contains(p)){
            return new Color(image.getRGB(p.x, p.y)) ;
        }
        return null;
    }
}
