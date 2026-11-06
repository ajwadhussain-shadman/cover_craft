/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.service;

/**
 *
 * @author max2
 */
import covercraft.template.Surface;
import covercraft.template.TextStyle;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * A Surface that draws into a PDF page using PDFBox.
 */
public class PdfSurface implements Surface {

    private final PDDocument document;
    private final PDPageContentStream out;
    private final double width;
    private final double height;

    private final Map<String, PDFont> fonts = new HashMap<>();

    public PdfSurface(
            PDDocument document,
            PDPage page,
            PDPageContentStream out) {

        this.document = document;
        this.out = out;

        this.width = page.getMediaBox().getWidth();
        this.height = page.getMediaBox().getHeight();
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    private PDFont fontFor(TextStyle st) {

        String key = st.getFamily()
                + st.isBold()
                + st.isItalic();

        PDFont font = fonts.get(key);

        if (font == null) {
            font = new PDType1Font(pickFont(st));
            fonts.put(key, font);
        }

        return font;
    }

    private Standard14Fonts.FontName pickFont(TextStyle st) {

        if (st.isSerif()) {

            if (st.isBold() && st.isItalic()) {
                return Standard14Fonts.FontName.TIMES_BOLD_ITALIC;
            }

            if (st.isBold()) {
                return Standard14Fonts.FontName.TIMES_BOLD;
            }

            if (st.isItalic()) {
                return Standard14Fonts.FontName.TIMES_ITALIC;
            }

            return Standard14Fonts.FontName.TIMES_ROMAN;
        }

        if (st.isBold() && st.isItalic()) {
            return Standard14Fonts.FontName.HELVETICA_BOLD_OBLIQUE;
        }

        if (st.isBold()) {
            return Standard14Fonts.FontName.HELVETICA_BOLD;
        }

        if (st.isItalic()) {
            return Standard14Fonts.FontName.HELVETICA_OBLIQUE;
        }

        return Standard14Fonts.FontName.HELVETICA;
    }

    @Override
    public double textWidth(String text, TextStyle style) {

        try {
            return fontFor(style).getStringWidth(text)
                    / 1000.0
                    * style.getSize();

        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void drawText(
            String text,
            double x,
            double y,
            TextStyle style) {

        if (text == null || text.isEmpty()) {
            return;
        }

        try {

            out.setNonStrokingColor(style.getColor());

            out.beginText();

            out.setFont(
                    fontFor(style),
                    (float) style.getSize()
            );

            /*
             * Surface uses TOP-LEFT coordinates.
             * PDFBox uses BOTTOM-LEFT coordinates.
             *
             * So we convert:
             *
             * pdfY = pageHeight - y
             */
            out.newLineAtOffset(
                    (float) x,
                    (float) (height - y)
            );

            out.showText(text);

            out.endText();

        } catch (IOException ex) {

            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void drawLine(
            double x1,
            double y1,
            double x2,
            double y2,
            double thickness,
            Color color) {

        try {

            out.setStrokingColor(color);

            out.setLineWidth(
                    (float) thickness
            );

            out.moveTo(
                    (float) x1,
                    (float) (height - y1)
            );

            out.lineTo(
                    (float) x2,
                    (float) (height - y2)
            );

            out.stroke();

        } catch (IOException ex) {

            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void drawRect(
            double x,
            double y,
            double w,
            double h,
            double thickness,
            Color color) {

        try {

            out.setStrokingColor(color);

            out.setLineWidth(
                    (float) thickness
            );

            out.addRect(
                    (float) x,
                    (float) (height - y - h),
                    (float) w,
                    (float) h
            );

            out.stroke();

        } catch (IOException ex) {

            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void fillRect(
            double x,
            double y,
            double w,
            double h,
            Color color) {

        try {

            out.setNonStrokingColor(color);

            out.addRect(
                    (float) x,
                    (float) (height - y - h),
                    (float) w,
                    (float) h
            );

            out.fill();

        } catch (IOException ex) {

            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void drawImage(
            BufferedImage image,
            double x,
            double y,
            double w,
            double h) {

        try {

            PDImageXObject pdImage =
                    LosslessFactory.createFromImage(
                            document,
                            image
                    );

            out.drawImage(
                    pdImage,
                    (float) x,
                    (float) (height - y - h),
                    (float) w,
                    (float) h
            );

        } catch (IOException ex) {

            throw new UncheckedIOException(ex);
        }
    }
}