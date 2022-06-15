/*
 * Copyright 2022 Kirill Lomakin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package things;

import javax.swing.*;
import java.awt.*;

public class HangarFrame extends JFrame {
    private static HangarFrame instance;
    private boolean hangarLabelAdded;

    private HangarFrame() {
        this.setTitle("Hangar Emulator");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new HangarMenuBar());
        this.addKeyListener(new HangarKeyListener());
    }

    public static HangarFrame getInstance() {
        if (instance == null) {
            instance = new HangarFrame();
        }
        return instance;
    }

    public void setHangarLabel() {
        hangarLabelAdded = true;
        this.add(HangarLabel.getInstance());
        this.pack();
        this.revalidate();
    }

    public void setHangarPanel() {
        var hangarPanel = HangarPanel.getInstance();
        var hangarLabel = HangarLabel.getInstance();

        if (hangarLabelAdded) {
            hangarPanel.setPreferredSize(hangarLabel.getSize());
            this.remove(hangarLabel);
        }
        else {
            hangarPanel.setPreferredSize(new Dimension(360, 360));
        }

        this.setJMenuBar(new HangarMenuBar());
        this.add(HangarPanel.getInstance());
        this.pack();
        this.revalidate();
    }
}