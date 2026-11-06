/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.template;

import covercraft.model.CoverPage;
import covercraft.model.DocumentType;
import covercraft.model.Student;
import covercraft.model.Teacher;
import covercraft.model.University;
import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 *
 * @author max2
 */
public class ClassicTemplate extends CoverTemplate {
    @Override
      public String getName() {
        return "Classic Academic";
    }
       @Override
    public String getDescription() {
        return "Traditional centered layout with a double border.";
    }
    @Override
    public void draw(CoverPage page, BufferedImage logo, Surface s) {
        double w = s.getWidth();
        double h = s.getHeight();
        Color accent = page.getAccentColor();
        DocumentType type = page.getDocumentType();
        University university = page.getUniversity();
        Teacher teacher = page.getTeacher();
        Student student = page.getStudent();
        // 1. Double border
        s.drawRect(28, 28, w - 56, h - 56, 1.6, accent);
        s.drawRect(34, 34, w - 68, h - 68, 0.5, accent);
        // 2. Logo, university and department
        double y = 128;
        if (logo != null) {
            drawLogoInBox(s, logo, w / 2 - 45, 70, 90, 90);
            y = 192;
        }
        y = drawCenteredWrapped(s, upper(university.getName()), y, 420, 27,
                style(page, true, false, 22, INK));
        y = drawCenteredWrapped(s, university.getDepartment(), y - 5, 420, 19,
                style(page, false, false, 14, INK));
        // 3. Document heading, number and title
        double top = Math.max(y + 46, 330);
        s.drawLine(w / 2 - 40, top - 40, w / 2 + 40, top - 40, 1.2, accent);
        drawCentered(s, type.getHeading(), top, style(page, true, false, 30, accent));
        y = top + 30;
        if (has(page.getAssignmentNumber())) {
            drawCentered(s, type.getNumberLabel() + " " + page.getAssignmentNumber(), y,
                    style(page, false, false, 13, GRAY));
            y += 30;
        }
        y = drawCenteredWrapped(s, page.getAssignmentTitle(), y + 12, 400, 27,
                style(page, false, true, 21, INK));
        // 4. Course
        y += 20;
        y = drawCenteredWrapped(s, page.getCourse().getName(), y, 400, 20,
                style(page, true, false, 16, INK));
        if (has(page.getCourse().getCode())) {
            drawCentered(s, "Course Code: " + page.getCourse().getCode(), y - 2,
                    style(page, false, false, 13, GRAY));
        }
        // 5. Submitted to / Submitted by
        double colLeft = 84;
        double colRight = w / 2 + 24;
        double colWidth = 190;
        double by = 620;
        TextStyle label = style(page, true, false, 10.5, accent);
        s.drawText("SUBMITTED TO", colLeft, by, label);
        s.drawText("SUBMITTED BY", colRight, by, label);
        s.drawLine(colLeft, by + 6, colLeft + colWidth, by + 6, 0.6, HAIRLINE);
        s.drawLine(colRight, by + 6, colRight + colWidth, by + 6, 0.6, HAIRLINE);
        TextStyle bold = style(page, true, false, 14, INK);
        TextStyle plain = style(page, false, false, 12, INK);
        double ty = by + 28;
       ty = drawWrapped(
    s,
    teacher.getName(),
    colLeft,
    ty,
    colWidth,
    17,
    bold
);

ty = drawWrapped(
    s,
    teacher.getDesignation(),
    colLeft,
    ty,
    colWidth,
    16,
    plain
);

if (has(teacher.getUniversity())) {
    drawWrapped(
        s,
        teacher.getUniversity(),
        colLeft,
        ty,
        colWidth,
        16,
        plain
    );
}

        double sy = by + 28;
        sy = drawWrapped(s, student.getName(), colRight, sy, colWidth, 17, bold);
        if (has(student.getStudentId())) {
            sy = drawWrapped(s, "ID: " + student.getStudentId(), colRight, sy, colWidth, 16, plain);
        }
        drawWrapped(s, batchSection(student), colRight, sy, colWidth, 16, plain);
        // 6. Date
        drawCentered(s, "Date of Submission: " + page.getFormattedDate(), h - 92,
                style(page, false, false, 13, INK));
    }

}
