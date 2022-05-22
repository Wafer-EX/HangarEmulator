import things.*;

import javax.swing.*;
import java.io.File;
import java.net.URISyntaxException;

public class HangarEmulator {
    public static void main(String[] args) throws URISyntaxException {
        var hangarFrame = HangarFrame.getInstance();

        if (args.length > 0 && new File(args[0]).isFile()) {
            try {
                MIDletLoader.loadMIDlet(args[0]);
                MIDletLoader.startLoadedMIDlet();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            hangarFrame.setLabel(new JLabel("Please select a file."));
        }

        var programFile = new File(HangarEmulator.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        HangarState.setProgramFile(programFile);
        hangarFrame.setVisible(true);
    }
}