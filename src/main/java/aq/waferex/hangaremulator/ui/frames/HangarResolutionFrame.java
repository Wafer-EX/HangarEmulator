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
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

@SuppressWarnings("FieldCanBeLocal")
public class HangarResolutionFrame extends JFrame {
    private final JButton applyButton = new JButton("Apply");
    private final JButton cancelButton = new JButton("Cancel");

    private final GridBagConstraints formConstraints = new GridBagConstraints();
    private final NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
    private final JFormattedTextField widthTextField;
    private final JFormattedTextField heightTextField;

    public HangarResolutionFrame() {
        super("Set custom resolution");

        numberFormatter.setAllowsInvalid(false);
        widthTextField = new JFormattedTextField(numberFormatter);
        widthTextField.setValue(HangarState.getGraphicsSettings().getResolution().width);
        heightTextField = new JFormattedTextField(numberFormatter);
        heightTextField.setValue(HangarState.getGraphicsSettings().getResolution().height);

        applyButton.addActionListener(e -> {
            long width = (long) widthTextField.getValue();
            long height = (long) heightTextField.getValue();

            HangarState.getGraphicsSettings().setResolution(new Dimension((int) width, (int) height));
            setVisible(false);
        });
        cancelButton.addActionListener(e -> setVisible(false));

        var formPanel = new JPanel(new GridBagLayout());
        formConstraints.insets.set(0, 0, 4, 4);
        formPanel.add(new JLabel("Width:"), formConstraints);

        formConstraints.gridx = 1;
        formConstraints.insets.set(0, 4, 4, 0);
        formPanel.add(widthTextField, formConstraints);

        formConstraints.gridx = 0;
        formConstraints.gridy = 1;
        formConstraints.insets.set(4, 0, 0, 4);
        formPanel.add(new JLabel("Height:"), formConstraints);

        formConstraints.gridx = 1;
        formConstraints.insets.set(4, 4, 0, 0);
        formPanel.add(heightTextField, formConstraints);

        formConstraints.insets.set(0, 0, 0, 0);
        formConstraints.gridx = 2;
        formConstraints.gridy = 2;
        formConstraints.weightx = 1.0f;
        formConstraints.weighty = 1.0f;
        formPanel.add(Box.createGlue(), formConstraints);
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        var buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(applyButton);
        buttonsPanel.add(cancelButton);

        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonsPanel, BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(240, 160));
        this.setResizable(false);

        pack();
        revalidate();
    }
}