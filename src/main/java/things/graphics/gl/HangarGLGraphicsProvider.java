/*
 * Copyright 2023-2024 Wafer EX
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

package things.graphics.gl;

import com.nokia.mid.ui.DirectGraphics;
import org.joml.Matrix4f;
import things.HangarState;
import things.graphics.HangarGraphicsProvider;
import things.graphics.HangarOffscreenBuffer;
import things.graphics.gl.abstractions.*;
import things.graphics.swing.HangarSwingOffscreenBuffer;
import things.utils.ListUtils;
import things.utils.microedition.ImageUtils;
import things.utils.nokia.DirectGraphicsUtils;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class HangarGLGraphicsProvider implements HangarGraphicsProvider {
    public static final int CIRCLE_POINTS = 16;

    private final ArrayList<HangarGLAction> glActions;
    private int translateX = 0, translateY = 0;
    private Color color;
    private final Rectangle clip;
    private DirectGraphics directGraphics;

    protected FramebufferObject frameBuffer;
    protected TextureObject frameBufferTexture;

    private VertexArrayObject vertexArrayObject;
    private VertexBufferObject bufferObject;

    private boolean isGraphicsPrepared = false;

    private static ShaderProgram spriteShaderProgram;
    private static boolean isShaderCompiled = false;

    public HangarGLGraphicsProvider() {
        this(FramebufferObject.getScreen());
        var profile = HangarState.getProfileManager().getCurrentProfile();
        int width = profile.getResolution().width;
        int height = profile.getResolution().height;

        glActions.add(() -> {
            frameBuffer = new FramebufferObject();

            frameBufferTexture = new TextureObject(width, height, GL_RGB, GL_RGB, GL_UNSIGNED_BYTE);
            frameBufferTexture.setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            frameBufferTexture.setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            frameBuffer.attachTexture(frameBufferTexture, GL_COLOR_ATTACHMENT0);
        });
    }

    public HangarGLGraphicsProvider(FramebufferObject frameBuffer) {
        this.glActions = new ArrayList<>();
        this.color = new Color(0);
        this.clip = new Rectangle(0, 0, 240, 320);
        this.frameBuffer = frameBuffer;

        glActions.add(() -> {
            if (!isShaderCompiled) {
                spriteShaderProgram = new ShaderProgram("/shaders/sprite.vert", "/shaders/sprite.frag");
                isShaderCompiled = true;
            }
            if (!isGraphicsPrepared) {
                bufferObject = new VertexBufferObject(GL_ARRAY_BUFFER, null);

                vertexArrayObject = new VertexArrayObject();
                vertexArrayObject.VertexAttribPointer(0, 2, GL_FLOAT, false, 9 * 4, 0);
                vertexArrayObject.VertexAttribPointer(1, 2, GL_FLOAT, false, 9 * 4, 2 * 4);
                vertexArrayObject.VertexAttribPointer(2, 4, GL_FLOAT, false, 9 * 4, 4 * 4);
                vertexArrayObject.VertexAttribPointer(3, 1, GL_FLOAT, false, 9 * 4, 8 * 4);

                isGraphicsPrepared = true;
            }
        });
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
                    color = new Color(argbColor, true);
                }

                @Override
                public void drawImage(Image img, int x, int y, int anchor, int manipulation) throws IllegalArgumentException, NullPointerException {
                    if (img == null) {
                        throw new NullPointerException();
                    }
                    var image = new Image(DirectGraphicsUtils.manipulateImage(img.getSEImage(), manipulation), true);
                    HangarGLGraphicsProvider.this.drawImage(image, x, y, anchor);
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
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;
                    float a = color.getAlpha() / 255f;

                    float[] points = new float[nPoints * 9];
                    for (int i = 0; i < nPoints; i++) {
                        int index = i * 9;
                        points[index] = xPoints[i];
                        points[index + 1] = yPoints[i];
                        points[index + 2] = 0.0f;
                        points[index + 3] = 0.0f;
                        points[index + 4] = r;
                        points[index + 5] = g;
                        points[index + 6] = b;
                        points[index + 7] = a;
                        points[index + 8] = 1.0f;
                    }

                    glActions.add(() -> {
                        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer.getIdentifier());

                        bufferObject.setBufferData(points);

                        glEnable(GL_BLEND);
                        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

                        vertexArrayObject.bind();
                        spriteShaderProgram.use();
                        spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

                        glDrawArrays(GL_TRIANGLE_FAN, 0, nPoints);
                        glDisable(GL_BLEND);
                        glUseProgram(0);
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
        color = new Color(red, green, blue);
    }

    @Override
    public void setColor(int RGB) {
        color = new Color(RGB, false);
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
        // TODO: write method logic
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        glActions.add(() -> {
            glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer.getIdentifier());

            bufferObject.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    x1, y1, 0, 0, r, g, b, 1, 1,
                    x2, y2, 1, 0, r, g, b, 1, 1,
            });

            vertexArrayObject.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            glDrawArrays(GL_LINES, 0, 2);
            glUseProgram(0);
        });
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        glActions.add(() -> {
            glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer.getIdentifier());

            bufferObject.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    x, y, 0, 0, r, g, b, 1, 1,
                    x + width, y, 1, 0, r, g, b, 1, 1,
                    x + width, y + height, 1, 1, r, g, b, 1, 1,
                    x, y, 0, 0, r, g, b, 1, 1,
                    x + width, y + height, 1, 1, r, g, b, 1, 1,
                    x, y + height, 0, 1, r, g, b, 1, 1,
            });

            vertexArrayObject.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            glDrawArrays(GL_TRIANGLES, 0, 6);
            glUseProgram(0);
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
        float halfWidth = (float) width / 2;
        float halfHeight = (float) height / 2;

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        // TODO: use startAngle and arcAngle here
        float deltaAngle = ((float) Math.PI * 2) / CIRCLE_POINTS;
        float angle = 0;

        float prevX = (float) Math.sin(angle) * halfWidth + x + halfWidth;
        float prevY = (float) Math.cos(angle) * halfHeight + y + halfHeight;

        var points = new ArrayList<Float>();

        for (int i = 0; i < CIRCLE_POINTS; i++) {
            angle += deltaAngle;
            float currX = (float) Math.sin(angle) * halfWidth + x + halfWidth;
            float currY = (float) Math.cos(angle) * halfHeight + y + halfHeight;

            points.addAll(List.of(
                    x + halfWidth, y + halfHeight, 0.0f, 0.0f, r, g, b, 1.0f, 1.0f,
                    prevX, prevY, 0.0f, 0.0f, r, g, b, 1.0f, 1.0f,
                    currX, currY, 0.0f, 0.0f, r, g, b, 1.0f, 1.0f));

            prevX = currX;
            prevY = currY;
        }

        glActions.add(() -> {

            glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer.getIdentifier());

            bufferObject.setBufferData(ListUtils.toArray(points));

            vertexArrayObject.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            glDrawArrays(GL_TRIANGLE_FAN, 0, CIRCLE_POINTS * 3);
            glUseProgram(0);
        });
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
        var imageBuffer = img.convertToByteBuffer();

        glActions.add(() -> {
            glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer.getIdentifier());

            bufferObject.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    alignedX, alignedY, 0, 0, 1, 1, 1, 1, 0,
                    alignedX + width, alignedY, 1, 0, 1, 1, 1, 1, 0,
                    alignedX + width, alignedY + height, 1, 1, 1, 1, 1, 1, 0,
                    alignedX, alignedY, 0, 0, 1, 1, 1, 1, 0,
                    alignedX + width, alignedY + height, 1, 1, 1, 1, 1, 1, 0,
                    alignedX, alignedY + height, 0, 1, 1, 1, 1, 1, 0,
            });

            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_BLEND);

            vertexArrayObject.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("sprite", 0);
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            // TODO: use abstraction
            int textureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureId);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glBindTexture(GL_TEXTURE_2D, textureId);
            glActiveTexture(GL_TEXTURE0);

            glDrawArrays(GL_TRIANGLES, 0, 6);
            glDisable(GL_BLEND);
            glUseProgram(0);
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
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        glActions.add(() -> {
            glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer.getIdentifier());

            bufferObject.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    x1, y1, 0, 0, r, g, b, 1, 1,
                    x2, y2, 0, 0, r, g, b, 1, 1,
                    x3, y3, 0, 0, r, g, b, 1, 1,
            });

            vertexArrayObject.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            glDrawArrays(GL_TRIANGLES, 0, 3);
            glUseProgram(0);
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
        if (offscreenBuffer instanceof HangarGLOffscreenBuffer lwjglOffscreenBuffer) {
            var graphicsProvider = (HangarGLGraphicsProvider) lwjglOffscreenBuffer.getGraphicsProvider();

            glActions.addAll(graphicsProvider.glActions);
            glActions.add(() -> {
                glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer.getIdentifier());

                bufferObject.setBufferData(new float[]{
                        // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                        -1, -1, 0, 0, 1, 1, 1, 1, 0,
                        1, -1, 1, 0, 1, 1, 1, 1, 0,
                        1, 1, 1, 1, 1, 1, 1, 1, 0,
                        -1, 1, 0, 1, 1, 1, 1, 1, 0,
                        -1, -1, 0, 0, 1, 1, 1, 1, 0,
                        1, 1, 1, 1, 1, 1, 1, 1, 0,
                        -1, 1, 0, 1, 1, 1, 1, 1, 0,
                });

                vertexArrayObject.bind();
                spriteShaderProgram.use();
                spriteShaderProgram.setUniform("sprite", 0);
                spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f());

                glBindTexture(GL_TEXTURE_2D, graphicsProvider.frameBufferTexture.getIdentifier());
                glActiveTexture(GL_TEXTURE0);

                glDrawArrays(GL_TRIANGLES, 0, 6);
                glUseProgram(0);
            });
            graphicsProvider.glActions.clear();
        }
        else if (offscreenBuffer instanceof HangarSwingOffscreenBuffer swingOffscreenBuffer) {
            drawImage(new Image(swingOffscreenBuffer.getBufferedImage(), false), 0, 0, 0);
        }
    }
}