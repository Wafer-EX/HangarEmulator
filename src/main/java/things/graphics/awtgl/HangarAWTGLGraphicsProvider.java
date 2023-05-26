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

package things.graphics.awtgl;

import things.graphics.HangarGraphicsProvider;
import things.graphics.HangarOffscreenBuffer;
import things.utils.microedition.ImageUtils;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class HangarAWTGLGraphicsProvider implements HangarGraphicsProvider {
    private final ArrayList<HangarGLAction> glActions;
    private int translateX = 0, translateY = 0;
    private Color color;
    private final Rectangle clip;
    protected int frameBufferId;
    protected int frameBufferTextureId;

    public HangarAWTGLGraphicsProvider() {
        this(0);
        SwingUtilities.invokeLater(() -> glActions.add(() -> {
            frameBufferId = glGenFramebuffers();
            glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);

            frameBufferTextureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, frameBufferTextureId);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 240, 320, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

            glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, frameBufferTextureId, 0);
        }));
    }

    public HangarAWTGLGraphicsProvider(int renderBufferId) {
        //this.awtglCanvas = awtglCanvas;
        this.glActions = new ArrayList<>();
        this.color = new Color(0);
        this.clip = new Rectangle(0, 0, 240, 320);
        this.frameBufferId = renderBufferId;
    }

    public ArrayList<HangarGLAction> getGLActions() {
        return glActions;
    }

    @Override
    public void translate(int x, int y) {
        // TODO: add translation to actions
        translateX += x;
        translateY += y;
    }

    @Override
    public int getTranslateX() {
        return translateX;
    }

    @Override
    public int getTranslateY() {
        return translateY;
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
        color = new Color(RGB, false);
    }

    @Override
    public void setGrayScale(int value) throws IllegalArgumentException {

    }

    @Override
    public Font getFont() {
        return Font.getDefaultFont();
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
        return clip.x;
    }

    @Override
    public int getClipY() {
        return clip.y;
    }

    @Override
    public int getClipWidth() {
        return clip.width;
    }

    @Override
    public int getClipHeight() {
        return clip.height;
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {

    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        // TODO: fix it
        //clip.setBounds(x, y, width, height);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        System.out.println("drawLine");
        glActions.add(() -> {
            glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);

            glBegin(GL_LINES);
            glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
            glVertex2f(x1, y1);
            glVertex2f(x2, y2);
            glEnd();
        });
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        glActions.add(() -> {
            glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);

            glBegin(GL_QUADS);
            glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
            glVertex2f(x, y);
            glVertex2f(x + width, y);
            glVertex2f(x + width, y + height);
            glVertex2f(x, y + height);
            glEnd();
        });
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        System.out.println("drawRect");
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
        int width = img.getWidth();
        int height = img.getHeight();
        int alignedX = ImageUtils.alignX(img.getWidth(), x, anchor);
        int alignedY = ImageUtils.alignY(img.getHeight(), y, anchor);
        var buffer = img.convertToByteBuffer();

        glActions.add(() -> {
            glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);

            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
            glEnable(GL_SCISSOR_TEST);

            int textureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureId);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glScissor(clip.x, clip.y, clip.width, clip.height);
            glDepthMask(true);

            glBegin(GL_QUADS);
            glColor3f(1, 1, 1);
            glTexCoord2f(0, 0);
            glVertex2f(alignedX, alignedY);
            glTexCoord2f(1, 0);
            glVertex2f(alignedX + width, alignedY);
            glTexCoord2f(1, 1);
            glVertex2f(alignedX + width, alignedY + height);
            glTexCoord2f(0, 1);
            glVertex2f(alignedX, alignedY + height);
            glEnd();

            glDisable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
            glDisable(GL_SCISSOR_TEST);
            glDeleteTextures(textureId);
        });
    }

    @Override
    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException {
        System.out.println("drawRegion");
    }

    @Override
    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException {
        System.out.println("copyArea");
    }

    @Override
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.println("fillTriangle");
    }

    @Override
    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException {
        System.out.println("drawRGB");
    }

    @Override
    public int getDisplayColor(int color) {
        return color;
    }

    @Override
    public void setARGBColor(int argbColor) {
        this.color = new Color(argbColor, true);
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

    @Override
    public void paintOffscreenBuffer(HangarOffscreenBuffer offscreenBuffer) {
        if (offscreenBuffer.getGraphicsProvider() instanceof HangarAWTGLGraphicsProvider awtglGraphicsProvider) {
            glActions.add(() -> {
                glEnable(GL_TEXTURE_2D);
                glBindTexture(GL_TEXTURE_2D, awtglGraphicsProvider.frameBufferTextureId);

                glBegin(GL_QUADS);
                glColor3f(1, 1, 1);
                glTexCoord2f(0, 0);
                glVertex2f(0, 0);
                glTexCoord2f(1, 0);
                glVertex2f(240, 0);
                glTexCoord2f(1, 1);
                glVertex2f(240, 320);
                glTexCoord2f(0, 1);
                glVertex2f(0, 320);
                glEnd();
            });
        }
    }
}