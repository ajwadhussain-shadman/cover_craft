/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.service;

import covercraft.model.CoverPage;
import covercraft.template.CoverTemplate;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class PDFGenerator {

    /**
     * Creates a one-page A4 PDF containing the cover page.
     */
    public void generate(
            CoverPage page,
            CoverTemplate template,
            BufferedImage logo,
            File target) throws IOException {

        try (PDDocument document = new PDDocument()) {

            // Create an A4 page
            PDPage pdfPage = new PDPage(PDRectangle.A4);
            document.addPage(pdfPage);

            // Open the PDF drawing stream
            try (PDPageContentStream content =
                    new PDPageContentStream(document, pdfPage)) {

                // Connect PDFBox with our Surface interface
                PdfSurface surface =
                        new PdfSurface(document, pdfPage, content);

                // Let the selected template draw the cover
                template.draw(page, logo, surface);
            }

            // PDF metadata
            PDDocumentInformation info =
                    document.getDocumentInformation();

            info.setTitle(
                    page.getDocumentType().getDisplayName()
                    + " - "
                    + page.getCourse().getName()
            );

            info.setAuthor(
                    page.getStudent().getName()
            );

            info.setCreator("CoverCraft");

            // Save the PDF
            document.save(target);

        } catch (UncheckedIOException ex) {
            throw ex.getCause();
        }
    }

    /**
     * Checks whether the built-in PDF fonts
     * can represent the supplied text.
     */
    public static boolean isSupported(String text) {

        return Charset
                .forName("windows-1252")
                .newEncoder()
                .canEncode(text);
    }
}