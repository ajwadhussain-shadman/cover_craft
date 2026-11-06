/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.template;
import covercraft.model.CoverPage;
import java.awt.Color;
/**
 *
 * @author max2
 */
public class TextStyle {
     private final String family;
    private final boolean bold;
    private final boolean italic;
    private final double size;
    private final Color color;
    public TextStyle(String family, boolean bold, boolean italic, double size, Color color) {
        this.family = family;
        this.bold = bold;
        this.italic = italic;
        this.size = size;
        this.color = color;
    }
    public String getFamily() {
        return family;
    }
    public boolean isSerif() {
        return CoverPage.FONT_SERIF.equals(family);
    }
    public boolean isBold() {
        return bold;
    }
    public boolean isItalic() {
        return italic;
    }
    public double getSize() {
        return size;
    }
    public Color getColor() {
        return color;
    }
}
