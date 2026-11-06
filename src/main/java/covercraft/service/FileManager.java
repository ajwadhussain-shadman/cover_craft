/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.service;

import covercraft.model.CoverPage;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileManager {

    private static File lastDirectory = null;

    private FileManager() {
    }
    
    /**
 * Shows an "open" dialog for images.
 * Returns null if the user cancels.
 */
public static File chooseImageFile(Component parent) {

    JFileChooser chooser = new JFileChooser();

    chooser.setDialogTitle("Select University Logo");

    chooser.setAcceptAllFileFilterUsed(false);

    chooser.addChoosableFileFilter(
            new FileNameExtensionFilter(
                    "Image files (PNG, JPG, GIF, BMP)",
                    "png",
                    "jpg",
                    "jpeg",
                    "gif",
                    "bmp"
            )
    );

    if (lastDirectory != null) {
        chooser.setCurrentDirectory(lastDirectory);
    }

    if (chooser.showOpenDialog(parent)
            != JFileChooser.APPROVE_OPTION) {

        return null;
    }

    lastDirectory = chooser.getCurrentDirectory();

    return chooser.getSelectedFile();
}
    
    

    /**
     * Shows a "save" dialog for PDFs.
     * Returns null if the user cancels.
     */
 
    public static File choosePdfSaveFile(
            Component parent,
            String suggestedName) {

        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle(
                "Save Cover Page as PDF"
        );

        chooser.setAcceptAllFileFilterUsed(false);

        chooser.setFileFilter(
                new FileNameExtensionFilter(
                        "PDF document (*.pdf)",
                        "pdf"
                )
        );

        if (lastDirectory != null) {
            chooser.setCurrentDirectory(lastDirectory);
        }

        chooser.setSelectedFile(
                new File(
                        chooser.getCurrentDirectory(),
                        suggestedName
                )
        );

        while (true) {

            if (chooser.showSaveDialog(parent)
                    != JFileChooser.APPROVE_OPTION) {

                return null;
            }

            File file =
                    ensurePdfExtension(
                            chooser.getSelectedFile()
                    );

            if (file.exists()) {

                int answer =
                        JOptionPane.showConfirmDialog(
                                parent,
                                file.getName()
                                + " already exists.\n"
                                + "Do you want to replace it?",
                                "Confirm Replace",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );

                if (answer != JOptionPane.YES_OPTION) {
                    continue;
                }
            }

            lastDirectory =
                    chooser.getCurrentDirectory();

            return file;
        }
    }

    public static File ensurePdfExtension(File file) {

        if (file.getName()
                .toLowerCase()
                .endsWith(".pdf")) {

            return file;
        }

        return new File(
                file.getPath() + ".pdf"
        );
    }

    public static String suggestFileName(CoverPage page) {

        String base =
                page.getDocumentType().getDisplayName()
                + "_"
                + page.getCourse().getCode()
                + "_"
                + page.getStudent().getName();

        return safeFileName(base) + ".pdf";
    }

    public static String safeFileName(String text) {

        String cleaned =
                text.replaceAll(
                        "[^A-Za-z0-9._-]+",
                        "_"
                );

        cleaned =
                cleaned.replaceAll(
                        "^_+|_+$",
                        ""
                );

        return cleaned.isEmpty()
                ? "CoverPage"
                : cleaned;
    }
}
