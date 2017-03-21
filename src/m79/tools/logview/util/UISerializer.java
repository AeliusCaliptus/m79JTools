package m79.tools.logview.util;

import m79.tools.logview.LogView;

import javax.swing.JFrame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Hober on 2017.03.21..
 */
public class UISerializer {

    private JFrame parent;
    private LogView logView;

    public UISerializer(JFrame parent, LogView logView) {
        this.parent = parent;
        this.logView = logView;
        parent.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                serialize();
            }
        });
    }

    private void serialize() {
        try {
            Properties props = new Properties();
            File ini = new File(System.getProperty("user.home") + "/.logview.properties");
            ini.createNewFile();
            FileOutputStream fos = new FileOutputStream(ini);
            Rectangle bounds = parent.getBounds();
            Point location = parent.getLocation();
            String address = logView.getAddress();
            props.setProperty("frame.bounds", bounds.x + "," + bounds.y + "," + bounds.width + "," + bounds.height);
            props.setProperty("frame.location", location.x + "," + location.y);
            props.setProperty("logview.address", address);
            props.store(fos, "LogView properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserialize() {
        File ini = new File(System.getProperty("user.home") + "/.logview.properties");
        if (ini.exists()) {
            try (FileInputStream fis = new FileInputStream(ini)) {
                Properties props = new Properties();
                props.load(fis);
                String[] b = props.getProperty("frame.bounds").split(",");
                parent.setBounds(Integer.parseInt(b[0]), Integer.parseInt(b[1]), Integer.parseInt(b[2]), Integer.parseInt(b[3]));
                String[] l = props.getProperty("frame.location").split(",");
                parent.setLocation(Integer.parseInt(l[0]), Integer.parseInt(l[1]));
                logView.fetchFromAddress(props.getProperty("logview.address"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
