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

package javax.microedition.lcdui;

import java.util.ArrayList;
import java.util.List;

public class Form extends Screen {
    private final ArrayList<Item> items = new ArrayList<>();

    public Form(String title) {
        this.setTitle(title);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Form(String title, Item[] items) {
        this(title);
        this.items.addAll(List.of(items));
    }

    public int append(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        items.add(item);
        return items.size() - 1;
    }

    public int append(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        items.add(new StringItem(null, str));
        return items.size() - 1;
    }

    public int append(Image img) {
        if (img == null) {
            throw new NullPointerException();
        }
        items.add(new ImageItem(null, img, ImageItem.LAYOUT_DEFAULT, null));
        return items.size() - 1;
    }

    public void insert(int itemNum, Item item) {
        items.add(itemNum, item);
    }

    public void delete(int itemNum) {
        items.remove(itemNum);
    }

    public void deleteAll() {
        items.clear();
    }

    public void set(int itemNum, Item item) {
        items.set(itemNum, item);
    }

    public Item get(int itemNum) {
        return (Item) items.get(itemNum);
    }

    public void setItemStateListener(ItemStateListener iListener) {
        // TODO: write method logic
    }

    public int size() {
        return items.size();
    }

    public int getWidth() {
        // TODO: write method logic
        return 0;
    }

    public int getHeight() {
        // TODO: write method logic
        return 0;
    }
}