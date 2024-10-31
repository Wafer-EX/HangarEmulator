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

package aq.waferex.hangaremulator.ui.components;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("FieldCanBeLocal")
public class HangarVirtualKeyboard extends JPanel {
    private final JButton leftFuncButton = new JButton("L");
    private final JButton upButton = new JButton("↑");
    private final JButton rightFuncButton = new JButton("R");
    private final JButton leftButton = new JButton("←");
    private final JButton okButton = new JButton("OK");
    private final JButton rightButton = new JButton("→");
    private final JButton dButton = new JButton("D");
    private final JButton downButton = new JButton("↓");
    private final JButton cButton = new JButton("C");

    private final JButton oneButton = new JButton("1");
    private final JButton twoButton = new JButton("2");
    private final JButton threeButton = new JButton("3");
    private final JButton fourButton = new JButton("4");
    private final JButton fiveButton = new JButton("5");
    private final JButton sixButton = new JButton("6");
    private final JButton sevenButton = new JButton("7");
    private final JButton eightButton = new JButton("8");
    private final JButton nineButton = new JButton("9");
    private final JButton starButton = new JButton("*");
    private final JButton zeroButton = new JButton("0");
    private final JButton signButton = new JButton("#");

    public HangarVirtualKeyboard() {
        super(new GridLayout(8, 3, 2, 2));

        // TODO: finish it

        this.add(leftFuncButton);
        this.add(upButton);
        this.add(rightFuncButton);
        this.add(leftButton);
        this.add(okButton);
        this.add(rightButton);
        this.add(dButton);
        this.add(downButton);
        this.add(cButton);
        this.add(Box.createGlue());
        this.add(Box.createGlue());
        this.add(Box.createGlue());
        this.add(oneButton);
        this.add(twoButton);
        this.add(threeButton);
        this.add(fourButton);
        this.add(fiveButton);
        this.add(sixButton);
        this.add(sevenButton);
        this.add(eightButton);
        this.add(nineButton);
        this.add(starButton);
        this.add(zeroButton);
        this.add(signButton);
    }
}