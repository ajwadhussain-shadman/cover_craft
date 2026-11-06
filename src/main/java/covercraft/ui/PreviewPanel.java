/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package covercraft.ui;
import covercraft.model.CoverPage;
import covercraft.service.ImageManager;
import covercraft.template.CoverTemplate;
import covercraft.template.Surface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
/**
 *
 * @author max2
 */
public class PreviewPanel extends JPanel implements Scrollable {
    private static final double PAGE_W = Surface.A4_WIDTH;
    private static final double PAGE_H = Surface.A4_HEIGHT;
    private static final int MARGIN = 14;
    private CoverPage page;
    private CoverTemplate template;
    private BufferedImage logo;

    public PreviewPanel() {
        setBackground(new Color(0xDDD8CC));
    }

    public void setContent(CoverPage page, CoverTemplate template) {
        this.page = page;
        this.template = template;
        this.logo = ImageManager.loadLogo(page);
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        int w = (int) Math.ceil(PAGE_W + 2 * MARGIN);
        int h = (int) Math.ceil(PAGE_H + 2 * MARGIN);
        return new Dimension(w, h);
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
        return getParent() != null && getParent().getHeight() > getPreferredSize().height;
    }
    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (page == null || template == null) {
        return;
    }

    Graphics2D g2 = (Graphics2D) g.create();

    try {
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        int margin = 14;

        double scale = Math.min(
            (getWidth() - 2.0 * margin) / PAGE_W,
            (getHeight() - 2.0 * margin) / PAGE_H
        );

        if (scale <= 0) {
            return;
        }

        double left =
            (getWidth() - PAGE_W * scale) / 2.0;

        double top =
            (getHeight() - PAGE_H * scale) / 2.0;

        // Page shadow
        g2.setColor(new Color(0, 0, 0, 40));

        g2.fill(
            new Rectangle2D.Double(
                left + 3,
                top + 3,
                PAGE_W * scale,
                PAGE_H * scale
            )
        );

        // White A4 page
        g2.setColor(Color.WHITE);

        g2.fill(
            new Rectangle2D.Double(
                left,
                top,
                PAGE_W * scale,
                PAGE_H * scale
            )
        );

        // Work in A4 coordinates
        g2.translate(left, top);
        g2.scale(scale, scale);

        g2.clip(
            new Rectangle2D.Double(
                0,
                0,
                PAGE_W,
                PAGE_H
            )
        );

        template.draw(
            page,
            logo,
            new SwingSurface(
                g2,
                PAGE_W,
                PAGE_H
            )
        );

    } finally {
        g2.dispose();
    }
}
}
