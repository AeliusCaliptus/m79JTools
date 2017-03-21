package m79.tools.logview;

import m79.tools.logview.connect.Connector;
import m79.tools.logview.textfinder.Match;
import m79.tools.logview.textfinder.TextFinder;
import m79.tools.logview.util.UISerializer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import static m79.tools.logview.util.UIUtil.*;

/**
 * Created by Hober on 2017.03.21..
 */
public class LogView extends JPanel {

    private JTextArea logText;
    private JTextField patternField;
    private JTextField addressField;
    private JCheckBox caseSensBox;
    private TextFinder finder;

    public LogView() {
        init();
    }

    public void fetchFromAddress(String url) {
        try {
            addressField.setText(url);
            setLog(Connector.getHTML(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLog(String log) {
        logText.setText(log);
        finder.invalidate();
    }

    public void search() {
        System.out.println("Searching for '" + patternField.getText() + "'");
        finder.find(logText.getText(), patternField.getText());
    }

    public void findNext(boolean forward) {
        if (finder.isVirgin()) search();
        Match m = finder.directedCircularNext(forward);
        logText.setSelectionStart(m.startIndex);
        logText.setSelectionEnd(m.endIndex);
        logText.getCaret().setSelectionVisible(true);
    }

    public String getAddress() {
        return addressField.getText();
    }

    private void init() {
        setLayout(new BorderLayout());
        add(new JScrollPane(logText = new JTextArea()), BorderLayout.CENTER);
        logText.setForeground(COLOR_TEXT);
        logText.setEditable(false);
        add(createSouth(), BorderLayout.SOUTH);
        add(createNorth(), BorderLayout.NORTH);
        finder = new TextFinder();
    }

    private JPanel createSouth() {
        JPanel sBox = createLineBox();
        addHSplit(sBox).add(createWrapper(patternField = new JTextField(20), 5, 0, 5, 0));
        patternField.setMinimumSize(new Dimension(200, 0));
        addHSplit(sBox).add(caseSensBox = new JCheckBox("case"));
        JButton findBtn = new JButton("find");
        findBtn.addActionListener(e -> search());
        addHSplit(sBox).add(findBtn);
        addHGap(sBox);
        return sBox;
    }

    private JPanel createNorth() {
        JPanel nBox = createLineBox();
        addHSplit(nBox).add(new JLabel("Address:"));
        addHSplit(nBox).add(addressField = new JTextField(40));
        return nBox;
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(new com.bulenkov.darcula.DarculaLaf());

        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(3);

            LogView lv = new LogView();
            lv.fetchFromAddress("http://127.0.0.1:8080");
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
                        //F3 pressed
                        if (e.getKeyCode() == 114 && e.getID() == KeyEvent.KEY_PRESSED) {
                            lv.findNext(!e.isAltDown());
                        }
                        return false;
                    });

            f.setContentPane(lv);
            f.pack();
            UISerializer uis = new UISerializer(f, lv);
            uis.deserialize();
            f.setVisible(true);
        });

    }

}
