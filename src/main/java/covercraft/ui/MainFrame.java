/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package covercraft.ui;

/**
 *
 * @author max2
 */
import covercraft.model.CoverPage;
import covercraft.template.CoverTemplate;
import covercraft.template.ClassicTemplate;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import covercraft.service.FileManager;
import covercraft.service.ImageManager;
import covercraft.service.PDFGenerator;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.Toolkit;
import javax.swing.JOptionPane;

public class MainFrame extends javax.swing.JFrame {
    
    private static final String CARD_HOME = "home";
    private static final String CARD_FORM = "form";
    private final CardLayout cardLayout = new CardLayout();
    private final HomePanel homePanel;
    private final CoverFormPanel formPanel;
    private javax.swing.JScrollPane scrollForm;
    private final PDFGenerator pdfGenerator = new PDFGenerator();
    public MainFrame() {
        initComponents();
        setTitle("CoverCraft - Academic Cover Page Generator");
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = Math.min(980, screen.width - 40);
        int height = Math.min(740, screen.height - 80);
        setSize(width, height);
        setMinimumSize(new Dimension(750, 480));
        setLocationRelativeTo(null);
        pnlCards.setLayout(cardLayout);
        homePanel = new HomePanel(this);
        pnlCards.add(homePanel, CARD_HOME); 
        formPanel = new CoverFormPanel(this);
        scrollForm = new javax.swing.JScrollPane(formPanel);
        scrollForm.setBorder(null);
        scrollForm.getViewport().setBackground(Theme.PAPER);
        scrollForm.getVerticalScrollBar().setUnitIncrement(16);
        scrollForm.getHorizontalScrollBar().setUnitIncrement(16);
        pnlCards.add(scrollForm, CARD_FORM);
        applyTheme();
        showHome();
    }
 private void applyTheme() {
        Theme.apply(getContentPane());
        pnlHeader.setBackground(Theme.ACCENT);
        lblAppName.setForeground(Color.WHITE);
        lblAppName.setFont(lblAppName.getFont().deriveFont(Font.BOLD, 20f));
        lblAppTag.setForeground(new Color(0xC9D1DE));
        pnlStatus.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.RULE));
        lblStatus.setForeground(Theme.MUTED);
    }
  public void setStatus(String text) {
        lblStatus.setText(text);
    }
    public void showHome() {
        cardLayout.show(pnlCards, CARD_HOME);
        setStatus("Ready");
    }
    public void showForm() {
        formPanel.startNewCover();
        cardLayout.show(pnlCards, CARD_FORM);
        if (scrollForm != null) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                scrollForm.getVerticalScrollBar().setValue(0);
                scrollForm.getHorizontalScrollBar().setValue(0);
            });
        }
        setStatus("Step 1 of 3 - Enter the cover page details");
    }
    public void openProfileDialog() {
        JOptionPane.showMessageDialog(this, "Comming Soon");
    }
     public void submitCover(CoverPage page) {
         openPreview(page, new ClassicTemplate());
    }
      public void openPreview(CoverPage page, CoverTemplate template) {
        PreviewFrame frame = new PreviewFrame();
        frame.showPreview(this, page, template);
    }
      public void generatePdf(
        CoverPage page,
        CoverTemplate template,
        Component parent) {

    // 1. Load the logo if the cover has one
    BufferedImage logo = null;

    if (page.getUniversity().hasLogo()) {

        try {
            logo = ImageManager.load(
                    page.getUniversity().getLogoPath(),
                    800
            );

        } catch (IOException ex) {

            int answer = JOptionPane.showConfirmDialog(
                    parent,
                    "The logo could not be loaded:\n"
                    + ex.getMessage()
                    + "\n\nCreate the PDF without a logo?",
                    "Logo problem",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }
    }

    // 2. Ask the user where to save the PDF
    File target = FileManager.choosePdfSaveFile(
            parent,
            FileManager.suggestFileName(page)
    );

    // User clicked Cancel
    if (target == null) {
        setStatus("Save cancelled");
        return;
    }

    // 3. Check the folder
    File folder =
            target.getAbsoluteFile().getParentFile();

    if (folder == null || !folder.isDirectory()) {

        showError(
                parent,
                "The folder does not exist:\n"
                + target.getAbsolutePath()
        );

        return;
    }

    if (!folder.canWrite()) {

        showError(
                parent,
                "You do not have permission to save in:\n"
                + folder.getAbsolutePath()
        );

        return;
    }

    // 4. Generate the PDF
    try {

        pdfGenerator.generate(
                page,
                template,
                logo,
                target
        );

    } catch (IOException ex) {

        showError(
                parent,
                "The PDF could not be written:\n"
                + ex.getMessage()
        );

        return;

    } catch (IllegalArgumentException ex) {

        showError(
                parent,
                "Some text contains a character "
                + "the PDF fonts cannot print:\n"
                + ex.getMessage()
        );

        return;
    }

    // 5. Success
    setStatus("Saved " + target.getName());

    Object[] options = {
        "Open PDF",
        "Close"
    };

    int choice = JOptionPane.showOptionDialog(
            parent,
            "Your cover page was saved to:\n"
            + target.getAbsolutePath(),
            "PDF saved",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[1]
    );

    if (choice == 0) {
        openFile(target, parent);
    }
}
      private void openFile(
        File file,
        Component parent) {

    try {

        if (Desktop.isDesktopSupported()
                && Desktop.getDesktop()
                .isSupported(Desktop.Action.OPEN)) {

            Desktop.getDesktop().open(file);

        } else {

            showError(
                    parent,
                    "This computer cannot open PDFs "
                    + "from Java. Open the file manually."
            );
        }

    } catch (IOException ex) {

        showError(
                parent,
                "The PDF could not be opened:\n"
                + ex.getMessage()
        );
    }
}
      private void showError(
        Component parent,
        String message) {

    JOptionPane.showMessageDialog(
            parent,
            message,
            "CoverCraft",
            JOptionPane.ERROR_MESSAGE
    );
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlHeader = new javax.swing.JPanel();
        lblAppName = new javax.swing.JLabel();
        lblAppTag = new javax.swing.JLabel();
        pnlCards = new javax.swing.JPanel();
        pnlStatus = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblAppName.setText("CoverCraft");

        lblAppTag.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblAppTag.setForeground(new java.awt.Color(255, 255, 255));
        lblAppTag.setText("Academic Tool");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAppName, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAppTag)
                .addGap(85, 85, 85))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAppName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAppTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlCardsLayout = new javax.swing.GroupLayout(pnlCards);
        pnlCards.setLayout(pnlCardsLayout);
        pnlCardsLayout.setHorizontalGroup(
            pnlCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlCardsLayout.setVerticalGroup(
            pnlCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );

        pnlStatus.setToolTipText("status");

        javax.swing.GroupLayout pnlStatusLayout = new javax.swing.GroupLayout(pnlStatus);
        pnlStatus.setLayout(pnlStatusLayout);
        pnlStatusLayout.setHorizontalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 95, Short.MAX_VALUE)
        );
        pnlStatusLayout.setVerticalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        lblStatus.setText("Ready");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(138, 138, 138))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(574, 574, 574)
                .addComponent(lblStatus)
                .addContainerGap(729, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                .addComponent(lblStatus)
                .addGap(156, 156, 156)
                .addComponent(pnlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAppName;
    private javax.swing.JLabel lblAppTag;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnlCards;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlStatus;
    // End of variables declaration//GEN-END:variables
}
