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

public class HangarGLGraphicsProvider extends HangarGraphicsProvider {
    public static final int CIRCLE_POINTS = 16;

    private final ArrayList<HangarGLAction> glActions;
    private final Rectangle clip;
    private DirectGraphics directGraphics;

    protected RenderTarget renderTarget;

    private GLVertexArray glVertexArray;
    private GLBuffer glBuffer;

    private boolean isGraphicsPrepared = false;

    private static GLShaderProgram spriteShaderProgram;
    private static boolean isShaderCompiled = false;

    public HangarGLGraphicsProvider() {
        this(null);
        var profile = HangarState.getProfileManager().getCurrentProfile();
        int width = profile.getResolution().width;
        int height = profile.getResolution().height;

        glActions.add(() -> {
            renderTarget = new RenderTarget(width, height);
        });
    }

    public HangarGLGraphicsProvider(RenderTarget renderTarget) {
        this.glActions = new ArrayList<>();
        this.clip = new Rectangle(0, 0, 240, 320);
        this.renderTarget = renderTarget;

        glActions.add(() -> {
            if (!isShaderCompiled) {
                spriteShaderProgram = new GLShaderProgram("/shaders/sprite.vert", "/shaders/sprite.frag");
                isShaderCompiled = true;
            }
            if (!isGraphicsPrepared) {
                glBuffer = new GLBuffer(GL_ARRAY_BUFFER, null);

                glVertexArray = new GLVertexArray();
                glVertexArray.VertexAttribPointer(0, 2, GL_FLOAT, false, 9 * 4, 0);
                glVertexArray.VertexAttribPointer(1, 2, GL_FLOAT, false, 9 * 4, 2 * 4);
                glVertexArray.VertexAttribPointer(2, 4, GL_FLOAT, false, 9 * 4, 4 * 4);
                glVertexArray.VertexAttribPointer(3, 1, GL_FLOAT, false, 9 * 4, 8 * 4);

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
                // TODO: remove this from here
                private Color color;

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
                        if (renderTarget != null) {
                            renderTarget.use();
                        }
                        else {
                            RenderTarget.bindDefault(240, 320);
                        }

                        glBuffer.setBufferData(points);

                        glEnable(GL_BLEND);
                        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

                        glVertexArray.bind();
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
    public int getGrayScale() {
        // TODO: write method logic
        return 0;
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
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        glActions.add(() -> {
            if (renderTarget != null) {
                renderTarget.use();
            }
            else {
                RenderTarget.bindDefault(240, 320);
            }

            glBuffer.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    x1, y1, 0, 0, r, g, b, 1, 1,
                    x2, y2, 1, 0, r, g, b, 1, 1,
            });

            glVertexArray.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            glDrawArrays(GL_LINES, 0, 2);
            glUseProgram(0);
        });
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, Color color, boolean isFilled) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        if (isFilled) {
            glActions.add(() -> {
            if (renderTarget != null) {
                renderTarget.use();
            }
            else {
                RenderTarget.bindDefault(240, 320);
            }

            glBuffer.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    x, y, 0, 0, r, g, b, 1, 1,
                    x + width, y, 1, 0, r, g, b, 1, 1,
                    x + width, y + height, 1, 1, r, g, b, 1, 1,
                    x, y, 0, 0, r, g, b, 1, 1,
                    x + width, y + height, 1, 1, r, g, b, 1, 1,
                    x, y + height, 0, 1, r, g, b, 1, 1,
            });

            glVertexArray.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            glDrawArrays(GL_TRIANGLES, 0, 6);
            glUseProgram(0);
            });
        }
        else {
            // TODO: implement it
        }
    }

    @Override
    public void drawRoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight, Color color, boolean isFilled) {
        // TODO: write method logic
    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color, boolean isFilled) {
        float halfWidth = (float) width / 2;
        float halfHeight = (float) height / 2;

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

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

        if (isFilled) {
            glActions.add(() -> {
            if (renderTarget != null) {
                renderTarget.use();
            }
            else {
                RenderTarget.bindDefault(240, 320);
            }

            glBuffer.setBufferData(ListUtils.toArray(points));

            glVertexArray.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            glDrawArrays(GL_TRIANGLE_FAN, 0, CIRCLE_POINTS * 3);
            glUseProgram(0);
            });
        }
        else {
            // TODO: implement it
        }
    }

    @Override
    public void drawString(String str, int x, int y, int anchor, Color color) throws NullPointerException, IllegalArgumentException {
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
            if (renderTarget != null) {
                renderTarget.use();
            }
            else {
                RenderTarget.bindDefault(240, 320);
            }

            glBuffer.setBufferData(new float[]{
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

            glVertexArray.bind();
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
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        glActions.add(() -> {
            if (renderTarget != null) {
                renderTarget.use();
            }
            else {
                RenderTarget.bindDefault(240, 320);
            }

            glBuffer.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    x1, y1, 0, 0, r, g, b, 1, 1,
                    x2, y2, 0, 0, r, g, b, 1, 1,
                    x3, y3, 0, 0, r, g, b, 1, 1,
            });

            glVertexArray.bind();
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
    public void paintOffscreenBuffer(HangarOffscreenBuffer offscreenBuffer) {
        if (offscreenBuffer instanceof HangarGLOffscreenBuffer lwjglOffscreenBuffer) {
            var graphicsProvider = (HangarGLGraphicsProvider) lwjglOffscreenBuffer.getGraphicsProvider();

            glActions.addAll(graphicsProvider.glActions);
            glActions.add(() -> {
                if (renderTarget != null) {
                    renderTarget.use();
                }
                else {
                    RenderTarget.bindDefault(240, 320);
                }

                glBuffer.setBufferData(new float[]{
                        // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                        -1, -1, 0, 0, 1, 1, 1, 1, 0,
                        1, -1, 1, 0, 1, 1, 1, 1, 0,
                        1, 1, 1, 1, 1, 1, 1, 1, 0,
                        -1, 1, 0, 1, 1, 1, 1, 1, 0,
                        -1, -1, 0, 0, 1, 1, 1, 1, 0,
                        1, 1, 1, 1, 1, 1, 1, 1, 0,
                        -1, 1, 0, 1, 1, 1, 1, 1, 0,
                });

                glVertexArray.bind();
                spriteShaderProgram.use();
                spriteShaderProgram.setUniform("sprite", 0);
                spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f());

                glBindTexture(GL_TEXTURE_2D, graphicsProvider.renderTarget.getTexture().getIdentifier());
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