/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.ui;

/**
 *
 * @author max2
 */
import covercraft.template.Surface;
import covercraft.template.TextStyle;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
/** A Surface that draws on the screen with Java2D. */
public class SwingSurface implements Surface {
    private static final FontRenderContext MEASURE = new FontRenderContext(null, true, true);
    private final Graphics2D g;
    private final double width;
    private final double height;
    public SwingSurface(Graphics2D g, double width, double height) {
        this.g = g;
        this.width = width;
        this.height = height;
    }
    @Override
    public double getWidth() {
        return width;
    }
@Override
    public double getHeight() {
        return height;
    }
    private Font toFont(TextStyle st) {
        int style = (st.isBold() ? Font.BOLD : 0) | (st.isItalic() ? Font.ITALIC : 0);
        String name = st.isSerif() ? Font.SERIF : Font.SANS_SERIF;
        return new Font(name, style, 1).deriveFont((float) st.getSize());
    }
    @Override
    public double textWidth(String text, TextStyle style) {
        return toFont(style).getStringBounds(text, MEASURE).getWidth();
    }
    @Override
    public void drawText(String text, double x, double y, TextStyle style) {
        if (text == null || text.isEmpty()) {
            return;
        }
        g.setFont(toFont(style));
        g.setColor(style.getColor());
        g.drawString(text, (float) x, (float) y);
    }
    @Override
    public void drawLine(double x1, double y1, double x2, double y2, double thickness, Color color) {
        g.setColor(color);
        g.setStroke(new BasicStroke((float) thickness));
        g.draw(new Line2D.Double(x1, y1, x2, y2));
    }
    @Override
    public void drawRect(double x, double y, double w, double h, double thickness, Color color) {
        g.setColor(color);
        g.setStroke(new BasicStroke((float) thickness));
        g.draw(new Rectangle2D.Double(x, y, w, h));
    }
    @Override
    public void fillRect(double x, double y, double w, double h, Color color) {
        g.setColor(color);
        g.fill(new Rectangle2D.Double(x, y, w, h));
    }
    @Override
    public void drawImage(BufferedImage image, double x, double y, double w, double h) {
        AffineTransform placement = new AffineTransform();
        placement.translate(x, y);
        placement.scale(w / image.getWidth(), h / image.getHeight());
        g.drawImage(image, placement, null);
    }
}
