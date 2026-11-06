/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package covercraft.template;
import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 *
 * @author max2
 */
public interface Surface {
     double A4_WIDTH = 595.28;
    double A4_HEIGHT = 841.89;
    double getWidth();
    double getHeight();
    double textWidth(String text, TextStyle style);
    /** Draws text with its baseline at y and its left edge at x. */
    void drawText(String text, double x, double y, TextStyle style);
    void drawLine(double x1, double y1, double x2, double y2, double thickness, Color color);
    void drawRect(double x, double y, double w, double h, double thickness, Color color);
    void fillRect(double x, double y, double w, double h, Color color);
    void drawImage(BufferedImage image, double x, double y, double w, double h);
}
