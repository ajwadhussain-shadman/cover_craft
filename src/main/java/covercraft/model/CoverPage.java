/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.model;
import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
/**
 *
 * @author max2
 */
public class CoverPage {
    public static final String FONT_SERIF="Serif";
    public static final String FONT_SANS="Sans";
    
    private final DocumentType documentType;
    private final University university;
    private final Course course;
    private final Teacher teacher;
    private final Student student;
    private final String assignmentTitle;
    private final String assignmentNumber;
    private final LocalDate submissionDate;
    
    // customization settings
     private String fontFamily = FONT_SERIF;
    private Color accentColor = new Color(0x22334D);
    private String datePattern = "d MMMM yyyy";
    
       public CoverPage(DocumentType documentType, University university, Course course,
            Teacher teacher, Student student, String assignmentTitle,
            String assignmentNumber, LocalDate submissionDate) {
        this.documentType = documentType;
        this.university = university;
        this.course = course;
        this.teacher = teacher;
        this.student = student;
        this.assignmentTitle = assignmentTitle;
        this.assignmentNumber = assignmentNumber;
        this.submissionDate = submissionDate;
    }
        public DocumentType getDocumentType() {
        return documentType;
    }
    public University getUniversity() {
        return university;
    }
    public Course getCourse() {
        return course;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public Student getStudent() {
        return student;
    }
    public String getAssignmentTitle() {
        return assignmentTitle;
    }
    public String getAssignmentNumber() {
        return assignmentNumber;
    }
    /**
     *
     * @return
     */
    public  LocalDate getSubmissionDate() {
        return submissionDate;
    }
    public String getFormattedDate() {
        return submissionDate.format(DateTimeFormatter.ofPattern(datePattern, Locale.ENGLISH));
    }
    public String getFontFamily() {
        return fontFamily;
    }
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }
    public Color getAccentColor() {
        return accentColor;
    }
    public void setAccentColor(Color accentColor) {
        this.accentColor = accentColor;
    }
    public String getDatePattern() {
        return datePattern;
    }
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }
}
