/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package covercraft.model;

public enum DocumentType {

    ASSIGNMENT(
        "Assignment",
        "ASSIGNMENT",
        "Assignment Title",
        "Assignment No."
    ),

    LAB_REPORT(
        "Lab Report",
        "LAB REPORT",
        "Experiment Title",
        "Experiment No."
    ),

    PROJECT_REPORT(
        "Project Report",
        "PROJECT REPORT",
        "Project Title",
        "Project No."
    );

    private final String displayName;
    private final String heading;
    private final String titleLabel;
    private final String numberLabel;

    DocumentType(
        String displayName,
        String heading,
        String titleLabel,
        String numberLabel
    ) {
        this.displayName = displayName;
        this.heading = heading;
        this.titleLabel = titleLabel;
        this.numberLabel = numberLabel;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getHeading() {
        return heading;
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    public String getNumberLabel() {
        return numberLabel;
    }
}