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

public class AlertType {
    // TODO: assign a values
    public static final AlertType INFO = null;
    public static final AlertType WARNING = null;
    public static final AlertType ERROR = null;
    public static final AlertType ALARM = null;
    public static final AlertType CONFIRMATION = null;

    protected AlertType() { }

    public boolean playSound(Display display) {
        // TODO: write method logic
        return false;
    }
}