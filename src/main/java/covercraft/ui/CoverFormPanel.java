/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package covercraft.ui;
import covercraft.model.DocumentType;
import covercraft.model.Course;
import covercraft.model.CoverPage;
import covercraft.model.Student;
import covercraft.model.Teacher;
import covercraft.model.University;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import covercraft.service.FileManager;
import covercraft.service.ImageManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 *
 * @author max2
 */
public class CoverFormPanel extends javax.swing.JPanel implements Scrollable {
    
      private static final DateTimeFormatter INPUT_DATE =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
private final MainFrame mainFrame;
private String logoPath = "";

    /**
     * Creates new form CoverFormPanel
     */
    public CoverFormPanel( MainFrame mainFrame) {
        this.mainFrame= mainFrame;
        initComponents();
        setupControls();
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return orientation == SwingConstants.VERTICAL ? visibleRect.height : visibleRect.width;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return getParent() != null && getParent().getWidth() > getPreferredSize().width;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
    private void setupControls(){
        Theme.apply(this);
        Theme.styleTitle(lblFormTitle, 22f);
        Theme.styleSection(lblUniversityHeading);
        Theme.styleSection(lblCourseHeading);
        Theme.styleSection(lblAssignmentHeading);
        Theme.styleSection(lblTeacherHeading);
        Theme.styleSection(lblStudentHeading);
        Theme.styleMuted(lblRequiredNote);
        DocumentType[] types=DocumentType.values();
        String[] names= new String[types.length];
        for(int i=0;i<types.length;i++){
            names[i]=types[i].getDisplayName();
        }
         cmbDocumentType.setModel(new DefaultComboBoxModel<>(names));
        txtSubmissionDate.setToolTipText("Format: dd/MM/yyyy, for example 23/09/2026");
        resetForm();
    }
public void startNewCover(){
   resetForm();
}
private JTextField[] allTextFields() {
return new JTextField[]{
            txtUniversityName, txtDepartment,
            txtCourseName, txtCourseCode,
            txtAssignmentTitle, txtAssignmentNumber, txtSubmissionDate,
            txtTeacherName, txtTeacherDesignation,
            txtStudentName, txtStudentId, txtBatch
        };
    }
 
private DocumentType getSelectedDocumentType() {
int index = cmbDocumentType.getSelectedIndex();
if (index < 0) {
            index = 0;
        }
return DocumentType.values()[index];
    }

private void applyDocumentType() {
DocumentType type = getSelectedDocumentType();
        lblAssignmentHeading.setText(type.getHeading());
        lblAssignmentTitle.setText(type.getTitleLabel() + " *");
        lblAssignmentNumber.setText(type.getNumberLabel());
    }
private void resetForm() {
for (JTextField field : allTextFields()) {
            field.setText("");
        }
        cmbDocumentType.setSelectedIndex(0);
        txtSubmissionDate.setText(LocalDate.now().format(INPUT_DATE));
        applyDocumentType();
        txtUniversityName.requestFocusInWindow();
    }
private LocalDate parseDate(String text) {
try {
return LocalDate.parse(text.trim(), INPUT_DATE);
        } 
catch (DateTimeParseException ex) {
return null; // the text is not a valid date
        }
    }

public CoverPage buildCoverPage(){
    String universityName=txtUniversityName.getText().trim();
    String department = txtDepartment.getText().trim();
    String courseName = txtCourseName.getText().trim();
    String courseCode = txtCourseCode.getText().trim();
String assignmentTitle = txtAssignmentTitle.getText().trim();
String assignmentNumber = txtAssignmentNumber.getText().trim();
String teacherName = txtTeacherName.getText().trim();
String teacherDesignation = txtTeacherDesignation.getText().trim();
String teacherUniversity= txtTeacherUniversity.getText().trim();
String studentName = txtStudentName.getText().trim();
String studentId = txtStudentId.getText().trim();
String batch = txtBatch.getText().trim();
LocalDate date = parseDate(txtSubmissionDate.getText());
if (date == null) {
            date = 
LocalDate.now();
        }
University university = new University(universityName, department, logoPath);
Course course = new Course(courseName, courseCode);
Teacher teacher = new Teacher(teacherName, teacherDesignation,teacherUniversity);
Student student = new Student(studentName, studentId, batch,"");
return new CoverPage(getSelectedDocumentType(), university, course, teacher, student,
                assignmentTitle, assignmentNumber, date);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFormTitle = new javax.swing.JLabel();
        lblDocType = new javax.swing.JLabel();
        cmbDocumentType = new javax.swing.JComboBox<>();
        lblUniversityHeading = new javax.swing.JLabel();
        lblUniversityName = new javax.swing.JLabel();
        txtUniversityName = new javax.swing.JTextField();
        lblDepartment = new javax.swing.JLabel();
        txtDepartment = new javax.swing.JTextField();
        lblCourseHeading = new javax.swing.JLabel();
        lblCourseName = new javax.swing.JLabel();
        txtCourseName = new javax.swing.JTextField();
        lblCourseCode = new javax.swing.JLabel();
        txtCourseCode = new javax.swing.JTextField();
        lblAssignmentHeading = new javax.swing.JLabel();
        lblAssignmentTitle = new javax.swing.JLabel();
        lblAssignmentNumber = new javax.swing.JLabel();
        lblSubmissionDate = new javax.swing.JLabel();
        txtAssignmentTitle = new javax.swing.JTextField();
        txtAssignmentNumber = new javax.swing.JTextField();
        txtSubmissionDate = new javax.swing.JTextField();
        btnToday = new javax.swing.JButton();
        lblTeacherHeading = new javax.swing.JLabel();
        lblTeacherName = new javax.swing.JLabel();
        txtTeacherName = new javax.swing.JTextField();
        lblTeacherDesignation = new javax.swing.JLabel();
        txtTeacherDesignation = new javax.swing.JTextField();
        lblStudentHeading = new javax.swing.JLabel();
        lblStudentName = new javax.swing.JLabel();
        txtStudentName = new javax.swing.JTextField();
        lblStudentId = new javax.swing.JLabel();
        txtStudentId = new javax.swing.JTextField();
        lblBatch = new javax.swing.JLabel();
        txtBatch = new javax.swing.JTextField();
        lblRequiredNote = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnDemoData = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblLogoPreview = new javax.swing.JLabel();
        btnUploadLogo = new javax.swing.JButton();
        btnRemoveLogo = new javax.swing.JButton();
        lblLogoName = new javax.swing.JLabel();
        lblTeacherUniversity = new javax.swing.JLabel();
        txtTeacherUniversity = new javax.swing.JTextField();

        lblFormTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblFormTitle.setText("Create Cover Page");

        lblDocType.setText("Document Type ");

        cmbDocumentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Assignment", "Lap Report", "Projecct Report" }));
        cmbDocumentType.addActionListener(this::cmbDocumentTypeActionPerformed);

        lblUniversityHeading.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUniversityHeading.setText("UNIVERSITY");

        lblUniversityName.setText("University Name *");

        txtUniversityName.addActionListener(this::txtUniversityNameActionPerformed);

        lblDepartment.setText("Department");

        lblCourseHeading.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCourseHeading.setText("COURSE");

        lblCourseName.setText("Course Name*");

        lblCourseCode.setText("Course Code*");

        txtCourseCode.addActionListener(this::txtCourseCodeActionPerformed);

        lblAssignmentHeading.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAssignmentHeading.setText("ASSIGNMENT");

        lblAssignmentTitle.setText("Assignemt Title*");

        lblAssignmentNumber.setText("Assignment No");

        lblSubmissionDate.setText("Submission Date*");

        txtAssignmentTitle.addActionListener(this::txtAssignmentTitleActionPerformed);

        btnToday.setText("TODAY");
        btnToday.addActionListener(this::btnTodayActionPerformed);

        lblTeacherHeading.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTeacherHeading.setText("TEACHER");

        lblTeacherName.setText(" Name *");

        txtTeacherName.addActionListener(this::txtTeacherNameActionPerformed);

        lblTeacherDesignation.setText("Designation");

        lblStudentHeading.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblStudentHeading.setText(" STUDENT");

        lblStudentName.setText(" Name *");

        lblStudentId.setText(" Student ID *");

        lblBatch.setText("session");

        lblRequiredNote.setText("*Required");

        btnBack.setText("← Home");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        btnNext.setText("Preview →");
        btnNext.addActionListener(this::btnNextActionPerformed);

        btnDemoData.setText("Auto Fill Demo");
        btnDemoData.addActionListener(this::btnDemoDataActionPerformed);

        jLabel1.setText("Logo");

        btnUploadLogo.setText("Upload...");
        btnUploadLogo.addActionListener(this::btnUploadLogoActionPerformed);

        btnRemoveLogo.setText("Remove");
        btnRemoveLogo.addActionListener(this::btnRemoveLogoActionPerformed);

        lblLogoName.setText("No logo selected");

        lblTeacherUniversity.setText("University");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUniversityHeading)
                            .addComponent(lblAssignmentHeading)
                            .addComponent(lblFormTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblAssignmentTitle)
                                        .addGap(38, 38, 38))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAssignmentNumber)
                                            .addComponent(lblSubmissionDate))
                                        .addGap(26, 26, 26)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAssignmentNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                    .addComponent(txtAssignmentTitle)
                                    .addComponent(txtSubmissionDate)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblDocType)
                                            .addComponent(lblUniversityName)
                                            .addComponent(lblDepartment)
                                            .addComponent(lblCourseName)
                                            .addComponent(lblCourseCode)
                                            .addComponent(jLabel1))
                                        .addGap(36, 36, 36))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblCourseHeading)
                                        .addGap(89, 89, 89)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cmbDocumentType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtUniversityName)
                                        .addComponent(txtDepartment)
                                        .addComponent(txtCourseName)
                                        .addComponent(txtCourseCode))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(lblLogoName))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnUploadLogo)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnRemoveLogo)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(lblLogoPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTeacherHeading)
                                    .addComponent(lblStudentHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblRequiredNote)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblTeacherName)
                                                .addComponent(lblTeacherDesignation)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(lblStudentId)
                                                    .addComponent(lblStudentName))
                                                .addComponent(lblBatch)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addComponent(lblTeacherUniversity)))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtTeacherName, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                                .addComponent(txtTeacherDesignation)
                                                .addComponent(txtStudentName)
                                                .addComponent(txtStudentId)
                                                .addComponent(txtBatch)
                                                .addComponent(txtTeacherUniversity)))))
                                .addGap(71, 71, 71))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnToday)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDemoData)
                        .addGap(42, 42, 42)
                        .addComponent(btnNext)
                        .addGap(130, 130, 130))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jLabel1)
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblStudentHeading)
                                    .addComponent(lblCourseHeading))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCourseName)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblStudentName)
                                        .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(txtCourseName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCourseCode)
                            .addComponent(txtCourseCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblStudentId)
                                .addComponent(txtStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblBatch)
                                .addGap(2, 2, 2)
                                .addComponent(lblAssignmentHeading)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAssignmentTitle)
                                    .addComponent(txtAssignmentTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAssignmentNumber)
                                    .addComponent(txtAssignmentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSubmissionDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnToday)
                                    .addComponent(lblSubmissionDate)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblRequiredNote)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnNext)
                                    .addComponent(btnDemoData)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnBack)
                                    .addComponent(btnReset)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblLogoPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFormTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmbDocumentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDocType))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblUniversityHeading)
                                    .addComponent(lblTeacherHeading))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUniversityName)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtUniversityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTeacherName)
                                        .addComponent(txtTeacherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDepartment)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTeacherDesignation)
                                        .addComponent(txtTeacherDesignation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(btnUploadLogo)
                                            .addComponent(btnRemoveLogo))
                                        .addGap(11, 11, 11)
                                        .addComponent(lblLogoName))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTeacherUniversity)
                                        .addComponent(txtTeacherUniversity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(105, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtUniversityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUniversityNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUniversityNameActionPerformed

    private void txtCourseCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCourseCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCourseCodeActionPerformed

    private void txtAssignmentTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssignmentTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssignmentTitleActionPerformed

    private void txtTeacherNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTeacherNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeacherNameActionPerformed

    private void btnTodayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodayActionPerformed
        // TODO add your handling code here:
         txtSubmissionDate.setText(LocalDate.now().format(INPUT_DATE));
    }//GEN-LAST:event_btnTodayActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        mainFrame.showHome();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        int answer = JOptionPane.showConfirmDialog(this,"Clear every field?", "Reset", JOptionPane.YES_NO_OPTION);
        if(answer==JOptionPane.YES_OPTION){
            resetForm();
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
         mainFrame.submitCover(buildCoverPage());
    }//GEN-LAST:event_btnNextActionPerformed

    private void cmbDocumentTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDocumentTypeActionPerformed
        // TODO add your handling code here:
        applyDocumentType();
    }//GEN-LAST:event_cmbDocumentTypeActionPerformed

    private void btnDemoDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDemoDataActionPerformed
        // TODO add your handling code here:
         // Document type
    cmbDocumentType.setSelectedItem("Project Report");

    // University
    txtUniversityName.setText("Sylhet Engineering College");
    txtDepartment.setText("Computer Science & Engineering");

    // Course
    txtCourseName.setText("Object Oriented Programming Language Sessional");
    txtCourseCode.setText("CSE 0613 2134");

    // Assignment
    txtAssignmentTitle.setText("JAVA Project");
    txtAssignmentNumber.setText("01");

    // Date
    txtSubmissionDate.setText(
        LocalDate.now().format(INPUT_DATE)
    );

    // Teacher
    txtTeacherName.setText("Nayan Kumar Nath");
    txtTeacherDesignation.setText("Lecturer");
    txtTeacherUniversity.setText("Sylhet Engineering College");

    // Student
    txtStudentName.setText("Azwad Hussain Shadman");
    txtStudentId.setText("2023331504");
    txtBatch.setText("2023-24");
    

    // Update the assignment labels
    applyDocumentType();
    File logoFile = new File(
        "src/main/resources/images/sec_logo.png"
);

if (logoFile.exists()) {

    try {

        BufferedImage thumbnail =
                ImageManager.load(
                        logoFile.getAbsolutePath(),
                        64
                );

        lblLogoPreview.setIcon(
                new ImageIcon(thumbnail)
        );

        lblLogoName.setText(
                logoFile.getName()
        );

        logoPath =
                logoFile.getAbsolutePath();

    } catch (IOException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Default logo could not be loaded:\n"
                + ex.getMessage(),
                "Logo problem",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
    }//GEN-LAST:event_btnDemoDataActionPerformed

    private void btnUploadLogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadLogoActionPerformed
        // TODO add your handling code here:
           File file = FileManager.chooseImageFile(this);

    if (file == null) {
        return; // user cancelled
    }

    try {

        BufferedImage thumbnail =
                ImageManager.load(
                        file.getAbsolutePath(),
                        64
                );

        lblLogoPreview.setIcon(
                new ImageIcon(thumbnail)
        );

        lblLogoName.setText(
                file.getName()
        );

        logoPath =
                file.getAbsolutePath();

    } catch (IOException ex) {

        JOptionPane.showMessageDialog(
                this,
                "That file cannot be used as a logo.\n"
                + ex.getMessage(),
                "Invalid image",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnUploadLogoActionPerformed

    private void btnRemoveLogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveLogoActionPerformed
        // TODO add your handling code here:
        clearLogo();
    }//GEN-LAST:event_btnRemoveLogoActionPerformed
private void clearLogo() {

    logoPath = "";

    lblLogoPreview.setIcon(null);

    lblLogoName.setText(
            "No logo selected"
    );
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDemoData;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnRemoveLogo;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnToday;
    private javax.swing.JButton btnUploadLogo;
    private javax.swing.JComboBox<String> cmbDocumentType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAssignmentHeading;
    private javax.swing.JLabel lblAssignmentNumber;
    private javax.swing.JLabel lblAssignmentTitle;
    private javax.swing.JLabel lblBatch;
    private javax.swing.JLabel lblCourseCode;
    private javax.swing.JLabel lblCourseHeading;
    private javax.swing.JLabel lblCourseName;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblDocType;
    private javax.swing.JLabel lblFormTitle;
    private javax.swing.JLabel lblLogoName;
    private javax.swing.JLabel lblLogoPreview;
    private javax.swing.JLabel lblRequiredNote;
    private javax.swing.JLabel lblStudentHeading;
    private javax.swing.JLabel lblStudentId;
    private javax.swing.JLabel lblStudentName;
    private javax.swing.JLabel lblSubmissionDate;
    private javax.swing.JLabel lblTeacherDesignation;
    private javax.swing.JLabel lblTeacherHeading;
    private javax.swing.JLabel lblTeacherName;
    private javax.swing.JLabel lblTeacherUniversity;
    private javax.swing.JLabel lblUniversityHeading;
    private javax.swing.JLabel lblUniversityName;
    private javax.swing.JTextField txtAssignmentNumber;
    private javax.swing.JTextField txtAssignmentTitle;
    private javax.swing.JTextField txtBatch;
    private javax.swing.JTextField txtCourseCode;
    private javax.swing.JTextField txtCourseName;
    private javax.swing.JTextField txtDepartment;
    private javax.swing.JTextField txtStudentId;
    private javax.swing.JTextField txtStudentName;
    private javax.swing.JTextField txtSubmissionDate;
    private javax.swing.JTextField txtTeacherDesignation;
    private javax.swing.JTextField txtTeacherName;
    private javax.swing.JTextField txtTeacherUniversity;
    private javax.swing.JTextField txtUniversityName;
    // End of variables declaration//GEN-END:variables
}
