package m79.tools.logview.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.Function;

/**
 * Created by Hober on 2017.03.21..
 */
public class UIUtil {

    public static final Color COLOR_TEXT = new Color(255, 130, 4);

    public static final Dimension HSPLIT = new Dimension(5, 0);

    public static final Dimension HGAP = new Dimension(Integer.MAX_VALUE, 0);

    public static JPanel addHSplit(JPanel box) {
        box.add(Box.createRigidArea(HSPLIT));
        return box;
    }

    public static JPanel addHGap(JPanel box) {
        box.add(Box.createRigidArea(HGAP));
        return box;
    }

    public static JPanel createWrapper(JComponent c, int top, int left, int bottom, int right) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        wrapper.add(c, BorderLayout.CENTER);
        return wrapper;
    }

    public static JPanel createLineBox() {
        JPanel lBox = new JPanel();
        lBox.setLayout(new BoxLayout(lBox, BoxLayout.LINE_AXIS));
        return lBox;
    }

}
