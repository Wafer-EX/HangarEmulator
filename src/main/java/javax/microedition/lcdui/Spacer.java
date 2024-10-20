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

package javax.microedition.lcdui;

// TODO: use this in form
public class Spacer extends Item {
    public Spacer(int minWidth, int minHeight) {
        setMinimumSize(minWidth, minHeight);
    }

    public void setMinimumSize(int minWidth, int minHeight) {
        if (minWidth < 0 || minHeight < 0) {
            throw new IllegalArgumentException();
        }
    }

    public void addCommand(Command cmd) {
        throw new IllegalStateException();
    }

    public void setDefaultCommand(Command cmd) {
        throw new IllegalStateException();
    }

    public void setLabel(String label) {
        throw new IllegalStateException();
    }
}