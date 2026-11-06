/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.template;
import covercraft.model.CoverPage;
import covercraft.model.Student;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 *
 * @author max2
 */
public abstract class CoverTemplate {
    protected static final Color INK = new Color(0x1E1E1E);
    protected static final Color GRAY = new Color(0x6B6B6B);
    protected static final Color HAIRLINE = new Color(0xBDBDBD);
      public abstract String getName();
    public abstract String getDescription();
    public abstract void draw(CoverPage page, BufferedImage logo, Surface s);
    protected TextStyle style(CoverPage page, boolean bold, boolean italic, double size, Color color) {
        return new TextStyle(page.getFontFamily(), bold, italic, size, color);
    }
    protected boolean has(String text) {
        return text != null && !text.isEmpty();
    }
    protected String upper(String text) {
        return text == null ? "" : text.toUpperCase(Locale.ROOT);
    }
protected String batchSection(Student student) {
String result = "";
if (has(student.getBatch())) {
            result = "Session:  " + student.getBatch();
        }
if (has(student.getSection())) {
            result = result.isEmpty() ? "Section: " + student.getSection()
                    : result + 
"   Section: " + student.getSection();
        }
return result;
    }
protected void drawCentered(Surface s, String text, double y, TextStyle st) {
if (!has(text)) {
return;
        }
double x = (s.getWidth() - s.textWidth(text, st)) / 2.0;
        s.drawText(text, x, y, st);
    }
    /** Splits text into lines that are no wider than maxWidth. */
protected List<String> wrap(Surface s, String text, double maxWidth, TextStyle st) {
List<String> lines = new ArrayList<>();
if (!has(text)) {
return lines;
        }
StringBuilder current = new StringBuilder();
for (String word : text.trim().split("\\s+")) {
String candidate = current.length() == 0 ? word : current + " " + word;
if (current.length() > 0 && s.textWidth(candidate, st) > maxWidth) {
                lines.add(current.toString());
                current = new StringBuilder(word);
            } 
else {
                current = new StringBuilder(candidate);
            }
        }
if (current.length() > 0) {
            lines.add(current.toString());
        }
return lines;
    }
    /** Draws centered wrapped text. Returns the baseline where the NEXT line would go. */
protected double drawCenteredWrapped(Surface s, String text, double y, double maxWidth,
double lineHeight, TextStyle st) {
for (String line : wrap(s, text, maxWidth, st)) {
            drawCentered(s, line, y, st);
            y += lineHeight;
        }
return y;
    }
    /** Draws left-aligned wrapped text. Returns the baseline where the NEXT line would go. */
protected double drawWrapped(Surface s, String text, double x, double y, double maxWidth,
double lineHeight, TextStyle st) {
for (String line : wrap(s, text, maxWidth, st)) {
            s.drawText(line, x, y, st);
            y += lineHeight;
        }
return y;
    }
    /** Fits the logo inside a box, keeping its proportions, centered in the box. */
protected void drawLogoInBox(Surface s, BufferedImage logo, double boxX, double boxY,
double boxW, double boxH) {
if (logo == null) {
return;
        }
double scale = Math.min(boxW / logo.getWidth(), boxH / logo.getHeight());
double w = logo.getWidth() * scale;
double h = logo.getHeight() * scale;
        s.drawImage(logo, boxX + (boxW - w) / 2.0, boxY + (boxH - h) / 2.0, w, h);
    }
    
}
