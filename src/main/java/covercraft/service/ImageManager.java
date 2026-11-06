/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.service;

/**
 *
 * @author max2
 */
import covercraft.model.CoverPage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ImageManager {
    private ImageManager() {
    }
    /** Loads an image file and shrinks it so its longest side is at most maxSide pixels. */
    public static BufferedImage load(String path, int maxSide) throws IOException {
        File file = new File(path);
        if (!file.isFile()) {
            throw new IOException("The file does not exist: " + path);
        }
        BufferedImage original = ImageIO.read(file);
        if (original == null) {
            throw new IOException("This is not a supported image. Please use PNG, JPG, GIF or BMP.");
        }
        return scaleDown(original, maxSide);
    }
    public static BufferedImage scaleDown(BufferedImage source, int maxSide) {
        int width = source.getWidth();
        int height = source.getHeight();
        int longest = Math.max(width, height);
        if (longest <= maxSide) {
            return source;
 }
        double scale = (double) maxSide / longest;
        int newWidth = Math.max(1, (int) Math.round(width * scale));
        int newHeight = Math.max(1, (int) Math.round(height * scale));
        BufferedImage result = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(source, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return result;
    }
    /** Quietly loads the logo of a cover page. Returns null if there is none or it cannot be read. */
    public static BufferedImage loadLogo(CoverPage page) {
        if (!page.getUniversity().hasLogo()) {
            return null;
        }
        try {
            return load(page.getUniversity().getLogoPath(), 800);
        } catch (IOException ex) {
            return null;
        }
    }
}