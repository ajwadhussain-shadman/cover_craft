/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package covercraft;
import covercraft.ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
public class CoverCraft {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            
        }
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}