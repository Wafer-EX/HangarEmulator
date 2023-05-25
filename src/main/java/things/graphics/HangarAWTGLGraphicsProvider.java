/*
 * Copyright 2023 Kirill Lomakin
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

package things.graphics;

import things.graphics.awtgl.HangarGLAction;
import things.graphics.awtgl.HangarAWTGLCanvas;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class HangarAWTGLGraphicsProvider implements HangarGraphicsProvider {
    private HangarAWTGLCanvas awtglCanvas;
    private final ArrayList<HangarGLAction> glActions;
    private Color color;

    public HangarAWTGLGraphicsProvider(HangarAWTGLCanvas awtglCanvas) {
        this.awtglCanvas = awtglCanvas;
        this.glActions = new ArrayList<>();
        this.color = Color.BLACK;
    }

    public ArrayList<HangarGLAction> getGLActions() {
        return glActions;
    }

    @Override
    public void translate(int x, int y) {

    }

    @Override
    public int getTranslateX() {
        return 0;
    }

    @Override
    public int getTranslateY() {
        return 0;
    }

    @Override
    public int getColor() {
        return color.getRGB();
    }

    @Override
    public int getRedComponent() {
        return color.getRed();
    }

    @Override
    public int getGreenComponent() {
        return color.getGreen();
    }

    @Override
    public int getBlueComponent() {
        return color.getBlue();
    }

    @Override
    public int getGrayScale() {
        return 0;
    }

    @Override
    public void setColor(int red, int green, int blue) throws IllegalArgumentException {
        color = new Color(red, green, blue);
    }

    @Override
    public void setColor(int RGB) {
        color = new Color(RGB);
    }

    @Override
    public void setGrayScale(int value) throws IllegalArgumentException {

    }

    @Override
    public Font getFont() {
        return null;
    }

    @Override
    public void setStrokeStyle(int style) throws IllegalArgumentException {

    }

    @Override
    public int getStrokeStyle() {
        return 0;
    }

    @Override
    public void setFont(Font font) {

    }

    @Override
    public int getClipX() {
        return 0;
    }

    @Override
    public int getClipY() {
        return 0;
    }

    @Override
    public int getClipWidth() {
        return 0;
    }

    @Override
    public int getClipHeight() {
        return 0;
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {

    }

    @Override
    public void setClip(int x, int y, int width, int height) {

    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        glActions.add(() -> {
            glBegin(GL_QUADS);
            glColor3f(color.getRed(), color.getGreen(), color.getBlue());
            glVertex2f(x, y);
            glVertex2f(x + width, y);
            glVertex2f(x + width, y + height);
            glVertex2f(x, y + height);
            glEnd();
        });
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {

    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    @Override
    public void drawString(String str, int x, int y, int anchor) throws NullPointerException, IllegalArgumentException {

    }

    @Override
    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) throws StringIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {

    }

    @Override
    public void drawChar(char character, int x, int y, int anchor) throws IllegalArgumentException {

    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {

    }

    @Override
    public void drawImage(Image img, int x, int y, int anchor) throws IllegalArgumentException, NullPointerException {

    }

    @Override
    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException {

    }

    @Override
    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {

    }

    @Override
    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException {

    }

    @Override
    public int getDisplayColor(int color) {
        return 0;
    }

    @Override
    public void setARGBColor(int argbColor) {

    }

    @Override
    public void drawPolygon(int[] x, int[] y, int numberOfPoints) {

    }

    @Override
    public void fillPolygon(int[] x, int[] y, int numberOfPoints) {

    }

    @Override
    public int getAlphaComponent() {
        return 0;
    }
}