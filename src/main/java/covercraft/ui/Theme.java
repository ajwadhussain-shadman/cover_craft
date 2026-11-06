/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.ui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/** The colors and small styling helpers shared by every screen. */
public final class Theme {
    public static final Color PAPER = new Color(0xEEEDCE);
    public static final Color INK = new Color(0x2B2B2B);
    public static final Color ACCENT = new Color(0x22334D);
    public static final Color MUTED = new Color(0x6B675F);
    public static final Color RULE = new Color(0xCFC9BC);
    public static final Color ERROR = new Color(0xA32A2A);
    private Theme() {
    }
    /** Gives a container and every panel inside it the warm paper background. */
    public static void apply(Container root) {
        root.setBackground(PAPER);
        for (Component c : root.getComponents()) {
            if (c instanceof JPanel || c instanceof JRadioButton || c instanceof JCheckBox) {
                c.setBackground(PAPER);
            }
            if (c instanceof JPanel) {
                apply((Container) c);
            }
        }
    }
    public static void styleTitle(JLabel label, float size) {
        label.setFont(label.getFont().deriveFont(Font.BOLD, size));
        label.setForeground(ACCENT);
    }
    /** Small bold heading with a thin rule underneath. */
    public static void styleSection(JLabel label) {
        label.setFont(label.getFont().deriveFont(Font.BOLD, 12f));
        label.setForeground(ACCENT);
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, RULE));
    }
    public static void styleMuted(JLabel label) {
        label.setForeground(MUTED);
    }
}

