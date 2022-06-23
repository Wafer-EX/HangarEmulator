package things;

import things.utils.HangarPanelUtils;

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
            var point = HangarPanelUtils.panelPointToCanvas(hangarPanel, e.getX(), e.getY());
            canvas.pointerPressed(point.x, point.y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (hangarPanel.getDisplayable() instanceof Canvas canvas) {
            var point = HangarPanelUtils.panelPointToCanvas(hangarPanel, e.getX(), e.getY());
            canvas.pointerReleased(point.x, point.y);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (hangarPanel.getDisplayable() instanceof Canvas canvas) {
            var point = HangarPanelUtils.panelPointToCanvas(hangarPanel, e.getX(), e.getY());
            canvas.pointerDragged(point.x, point.y);
        }
    }
}