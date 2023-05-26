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

import com.nokia.mid.ui.DirectGraphics;
import things.HangarState;
import things.graphics.HangarGraphicsProvider;
import things.graphics.HangarOffscreenBuffer;
import things.graphics.swing.HangarSwingOffscreenBuffer;
import things.utils.microedition.ImageUtils;
import things.utils.nokia.DirectGraphicsUtils;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class HangarAWTGLGraphicsProvider implements HangarGraphicsProvider {
    private final ArrayList<HangarGLAction> glActions;
    private int translateX = 0, translateY = 0;
    private Color color;
    private final Rectangle clip;
    protected int frameBufferId;
    protected int frameBufferTextureId;
    private DirectGraphics directGraphics;

    public HangarAWTGLGraphicsProvider() {
        this(0);
        var profile = HangarState.getProfileManager().getCurrentProfile();
        int width = profile.getResolution().width;
        int height = profile.getResolution().height;

        glActions.add(() -> {
            frameBufferId = glGenFramebuffers();
            glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);

            frameBufferTextureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, frameBufferTextureId);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, frameBufferTextureId, 0);
        });
    }

    public HangarAWTGLGraphicsProvider(int renderBufferId) {
        this.glActions = new ArrayList<>();
        this.color = new Color(0);
        this.clip = new Rectangle(0, 0, 240, 320);
        this.frameBufferId = renderBufferId;
    }

    public ArrayList<HangarGLAction> getGLActions() {
        return glActions;
    }

    @Override
    public DirectGraphics getDirectGraphics(Graphics graphics) {
        if (directGraphics == null) {
            directGraphics = new DirectGraphics() {
                @Override
                public void setARGBColor(int argbColor) {
                    glActions.add(() -> color = new Color(argbColor, true));
                }

                @Override
                public void drawImage(Image img, int x, int y, int anchor, int manipulation) throws IllegalArgumentException, NullPointerException {
                    if (img == null) {
                        throw new NullPointerException();
                    }
                    var image = new Image(DirectGraphicsUtils.manipulateImage(img.getSEImage(), manipulation), true);
                    HangarAWTGLGraphicsProvider.this.drawImage(image, x, y, anchor);
                }

                @Override
                public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
                    setARGBColor(argbColor);
                    // TODO: write method logic
                }

                @Override
                public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int argbColor) {
                    setARGBColor(argbColor);
                    // TODO: write method logic
                }

                @Override
                public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
                    setARGBColor(argbColor);
                    // TODO: write method logic
                }

                @Override
                public void fillPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, int argbColor) throws NullPointerException, ArrayIndexOutOfBoundsException {
                    setARGBColor(argbColor);
                    glActions.add(() -> {
                        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);
                        glEnable(GL_BLEND);
                        glBegin(GL_POLYGON);
                        glColor4b((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) color.getAlpha());
                        for (int i = 0; i < nPoints; i++) {
                            glVertex2f(xPoints[i], yPoints[i]);
                        }
                        glEnd();
                        glDisable(GL_BLEND);
                    });
                }

                @Override
                public void drawPixels(int[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                    // TODO: write method logic
                }

                @Override
                public void drawPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                    // TODO: write method logic
                }

                @Override
                public void drawPixels(short[] pixels, boolean transparency, int offset, int scanlength, int x, int y, int width, int height, int manipulation, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                    // TODO: write method logic
                }

                @Override
                public void getPixels(int[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                    // TODO: write method logic
                }

                @Override
                public void getPixels(byte[] pixels, byte[] transparencyMask, int offset, int scanlength, int x, int y, int width, int height, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                    // TODO: write method logic
                }

                @Override
                public void getPixels(short[] pixels, int offset, int scanlength, int x, int y, int width, int height, int format) throws NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
                    // TODO: write method logic
                }

                @Override
                public int getNativePixelFormat() {
                    // TODO: write method logic
                    return 0;
                }

                @Override
                public int getAlphaComponent() {
                    return color.getAlpha();
                }
            };
        }
        return directGraphics;
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
        // TODO: write method logic
        return 0;
    }

    @Override
    public void setColor(int red, int green, int blue) throws IllegalArgumentException {
        glActions.add(() -> color = new Color(red, green, blue));
    }

    @Override
    public void setColor(int RGB) {
        glActions.add(() -> color = new Color(RGB));
    }

    @Override
    public void setGrayScale(int value) throws IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public Font getFont() {
        // TODO: write method logic
        return Font.getDefaultFont();
    }

    @Override
    public void setStrokeStyle(int style) throws IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public int getStrokeStyle() {
        // TODO: write method logic
        return 0;
    }

    @Override
    public void setFont(Font font) {
        // TODO: write method logic
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
        // TODO: write method logic
    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        // TODO: fix it
        //glActions.add(() -> clip.setBounds(x, y, width, height));
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        // TODO: fix it (line matching problems)
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
        // TODO: write method logic
    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        // TODO: write method logic
    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        // TODO: write method logic
    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        // TODO: write method logic
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        // TODO: write method logic
    }

    @Override
    public void drawString(String str, int x, int y, int anchor) throws NullPointerException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) throws StringIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        // TODO: write method logic
    }

    @Override
    public void drawChar(char character, int x, int y, int anchor) throws IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        // TODO: write method logic
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
        if (src == null) {
            throw new NullPointerException();
        }
        if (width > 0 && height > 0) {
            var imageRegion = src.getSEImage().getSubimage(x_src, y_src, width, height);
            var transformedImage = ImageUtils.transformImage(imageRegion, transform);
            x_dest = ImageUtils.alignX(transformedImage.getWidth(), x_dest, anchor);
            y_dest = ImageUtils.alignY(transformedImage.getHeight(), y_dest, anchor);

            drawImage(new Image(transformedImage, false), x_dest, y_dest, anchor);
        }
    }

    @Override
    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        glActions.add(() -> {
            glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);

            glBegin(GL_TRIANGLES);
            glColor3b((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
            glVertex2i(x1, y1);
            glVertex2i(x2, y2);
            glVertex2i(x3, y3);
            glEnd();
        });
    }

    @Override
    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (rgbData == null) {
            throw new NullPointerException();
        }
        if (width > 0 && height > 0) {
            var image = new BufferedImage(width, height, processAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, width, height, rgbData, offset, scanlength);
            drawImage(new Image(image, false), x, y, 0);
        }
    }

    @Override
    public int getDisplayColor(int color) {
        // TODO: write method logic?
        return color;
    }

    @Override
    public void paintOffscreenBuffer(HangarOffscreenBuffer offscreenBuffer) {
        if (offscreenBuffer instanceof HangarAWTGLOffscreenBuffer awtglOffscreenBuffer) {
            var graphicsProvider = (HangarAWTGLGraphicsProvider) awtglOffscreenBuffer.getGraphicsProvider();
            var profile = HangarState.getProfileManager().getCurrentProfile();
            int width = profile.getResolution().width;
            int height = profile.getResolution().height;

            glActions.addAll(graphicsProvider.glActions);
            glActions.add(() -> {
                glBindFramebuffer(GL_FRAMEBUFFER, frameBufferId);

                glEnable(GL_TEXTURE_2D);
                glBindTexture(GL_TEXTURE_2D, graphicsProvider.frameBufferTextureId);

                glBegin(GL_QUADS);
                glColor3f(1, 1, 1);
                glTexCoord2f(0, 1);
                glVertex2f(0, 0);
                glTexCoord2f(1, 1);
                glVertex2f(width, 0);
                glTexCoord2f(1, 0);
                glVertex2f(width, height);
                glTexCoord2f(0, 0);
                glVertex2f(0, height);
                glEnd();

                glDisable(GL_TEXTURE_2D);
            });
            graphicsProvider.glActions.clear();
        }
        else if (offscreenBuffer instanceof HangarSwingOffscreenBuffer swingOffscreenBuffer) {
            drawImage(new Image(swingOffscreenBuffer.additionalBuffer, false), 0, 0, 0);
        }
    }
}