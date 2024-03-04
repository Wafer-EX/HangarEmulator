/*
 * Copyright 2022-2024 Wafer EX
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

package javax.microedition.lcdui;

import java.util.ArrayList;

public class Alert extends Screen {
    public static final int FOREVER = -2;
    public static final Command DISMISS_COMMAND = new Command("", Command.OK, 0);

    private String title;
    private String alertText;
    private Image alertImage;
    private AlertType alertType;
    private int timeout = FOREVER;
    private final ArrayList<Command> commandList = new ArrayList<>();
    private CommandListener commandListener;

    public Alert(String title) {
        this(title, null, null, null);
    }

    public Alert(String title, String alertText, Image alertImage, AlertType alertType) {
        this.title = title;
        this.alertText = alertText;
        this.alertImage = alertImage;
        this.alertType = alertType;
        commandList.add(DISMISS_COMMAND);
    }

    public int getDefaultTimeout() {
        return FOREVER;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int time) throws IllegalArgumentException {
        if (time < 0 && time != FOREVER) {
            throw new IllegalArgumentException();
        }
        timeout = time;
    }

    public AlertType getType() {
        return alertType;
    }

    public void setType(AlertType type) {
        alertType = type;
    }

    public String getString() {
        return alertText;
    }

    public void setString(String str) {
        alertText = str;
    }

    public Image getImage() {
        return alertImage;
    }

    public void setImage(Image img) {
        alertImage = img;
    }

    // TODO: write method logic
    //public void setIndicator(Gauge indicator) { }

    // TODO: write method logic
    //public Gauge getIndicator() { }

    @Override
    public void addCommand(Command cmd) throws NullPointerException {
        if (cmd == null) {
            throw new NullPointerException();
        }
        else {
            if (commandList.size() == 1 && commandList.get(0) == DISMISS_COMMAND) {
                commandList.clear();
            }
            commandList.add(cmd);
        }
    }

    @Override
    public void removeCommand(Command cmd) {
        commandList.remove(cmd);
        if (commandList.isEmpty()) {
            commandList.add(DISMISS_COMMAND);
        }
    }

    @Override
    public void setCommandListener(CommandListener l) {
        commandListener = l;
    }
}