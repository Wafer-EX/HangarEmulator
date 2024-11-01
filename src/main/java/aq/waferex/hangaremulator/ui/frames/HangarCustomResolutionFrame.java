/*
 * Copyright 2024 Wafer EX
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

package aq.waferex.hangaremulator.ui.frames;

import aq.waferex.hangaremulator.HangarState;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("FieldCanBeLocal")
public class HangarCustomResolutionFrame extends JFrame {
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton applyButton = new JButton("Apply");

    private final GridBagConstraints formConstraints = new GridBagConstraints();
    private final JTextField widthTextField = new JTextField();
    private final JTextField heightTextField = new JTextField();

    public HangarCustomResolutionFrame() {
        super("Set custom resolution");

        widthTextField.setText(String.valueOf(HangarState.getGraphicsSettings().getResolution().width));
        heightTextField.setText(String.valueOf(HangarState.getGraphicsSettings().getResolution().height));

        cancelButton.addActionListener(e -> setVisible(false));
        applyButton.addActionListener(e -> {
            try {
                int width = Integer.parseInt(widthTextField.getText());
                int height = Integer.parseInt(heightTextField.getText());

                HangarState.getGraphicsSettings().setResolution(new Dimension(width, height));
                setVisible(false);
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Width and height should be integer values.", "Resolution setting error!", JOptionPane.ERROR_MESSAGE);
            }
        });

        var formPanel = new JPanel(new GridBagLayout());
        formConstraints.insets.set(0, 0, 4, 4);
        formPanel.add(new JLabel("Width:"), formConstraints);

        formConstraints.fill = GridBagConstraints.HORIZONTAL;
        formConstraints.weightx = 1.0f;
        formConstraints.gridx = 1;
        formConstraints.insets.set(0, 4, 4, 0);
        formPanel.add(widthTextField, formConstraints);

        formConstraints.fill = GridBagConstraints.NONE;
        formConstraints.weightx = 0.0f;
        formConstraints.gridx = 0;
        formConstraints.gridy = 1;
        formConstraints.insets.set(4, 0, 0, 4);
        formPanel.add(new JLabel("Height:"), formConstraints);

        formConstraints.fill = GridBagConstraints.HORIZONTAL;
        formConstraints.weightx = 1.0f;
        formConstraints.gridx = 1;
        formConstraints.insets.set(4, 4, 0, 0);
        formPanel.add(heightTextField, formConstraints);

        formConstraints.fill = GridBagConstraints.BOTH;
        formConstraints.insets.set(0, 0, 0, 0);
        formConstraints.gridy = 2;
        formConstraints.weighty = 1.0f;
        formPanel.add(Box.createGlue(), formConstraints);
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        var buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(applyButton);
        buttonsPanel.add(cancelButton);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonsPanel, BorderLayout.SOUTH);

        this.getRootPane().setDefaultButton(applyButton);
        this.setPreferredSize(new Dimension(280, 160));
        this.setResizable(false);

        pack();
        revalidate();
    }
}