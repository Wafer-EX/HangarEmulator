package things;

import javax.microedition.lcdui.Canvas;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class HangarMouseListener extends MouseInputAdapter {
    private final HangarPanel hangarPanel;

    public HangarMouseListener(HangarPanel hangarPanel) {
        super();
        this.hangarPanel = hangarPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (hangarPanel.getDisplayable() instanceof Canvas canvas) {
            canvas.pointerPressed(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (hangarPanel.getDisplayable() instanceof Canvas canvas) {
            canvas.pointerReleased(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (hangarPanel.getDisplayable() instanceof Canvas canvas) {
            canvas.pointerDragged(e.getX(), e.getY());
        }
    }
}