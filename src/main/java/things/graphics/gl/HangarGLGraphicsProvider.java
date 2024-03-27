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

import org.joml.Matrix4f;
import things.graphics.HangarGraphicsProvider;
import things.graphics.HangarOffscreenBuffer;
import things.graphics.gl.abstractions.*;
import things.utils.ListUtils;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class HangarGLGraphicsProvider extends HangarGraphicsProvider {
    public static final int CIRCLE_POINTS = 16;

    private final ArrayList<HangarGLAction> glActions = new ArrayList<>();
    private final HashMap<Image, GLTexture> generatedTextures = new HashMap<>();

    private GLVertexArray glVertexArray;
    private GLBuffer glBuffer;
    private final RenderTarget renderTarget;

    private boolean isGraphicsPrepared = false;

    private static GLShaderProgram spriteShaderProgram;
    private static boolean isShaderCompiled = false;

    public HangarGLGraphicsProvider(RenderTarget renderTarget) {
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
    public void clipRect(int x, int y, int width, int height) {
        // TODO: write method logic
    }

    @Override
    public void setClip(int x, int y, int width, int height) {
        glActions.add(() -> glScissor(x, y, width,  height));
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        final int x1f = x1 + getTranslateX();
        final int y1f = y1 + getTranslateY();
        final int x2f = x2 + getTranslateX();
        final int y2f = y2 + getTranslateY();

        glActions.add(() -> {
            renderTarget.use();

            glBuffer.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    x1f, y1f, 0.0f, 0.0f, r, g, b, a, 1.0f,
                    x2f, y2f, 1.0f, 0.0f, r, g, b, a, 1.0f,
            });

            glVertexArray.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            glDrawArrays(GL_LINES, 0, 2);
            glUseProgram(0);
        });
    }

    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color, boolean isFilled) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        final int x1f = x1 + getTranslateX();
        final int y1f = y1 + getTranslateY();
        final int x2f = x2 + getTranslateX();
        final int y2f = y2 + getTranslateY();
        final int x3f = x3 + getTranslateX();
        final int y3f = y3 + getTranslateY();

        if (isFilled) {
            glActions.add(() -> {
                renderTarget.use();

                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_BLEND);

                glBuffer.setBufferData(new float[]{
                        // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                        x1f, y1f, 0.0f, 0.0f, r, g, b, a, 1.0f,
                        x2f, y2f, 0.0f, 0.0f, r, g, b, a, 1.0f,
                        x3f, y3f, 0.0f, 0.0f, r, g, b, a, 1.0f,
                });

                glVertexArray.bind();
                spriteShaderProgram.use();
                spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

                glDrawArrays(GL_TRIANGLES, 0, 3);
                glDisable(GL_BLEND);
                glUseProgram(0);
            });
        }
        else {
            // TODO: implement it
        }
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, Color color, boolean isFilled) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        final int xf = x + getTranslateX();
        final int yf = y + getTranslateY();

        if (isFilled) {
            glActions.add(() -> {
                renderTarget.use();

                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_BLEND);

                glBuffer.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    xf, yf, 0, 0, r, g, b, a, 1,
                    xf + width, yf, 1, 0, r, g, b, a, 1,
                    xf + width, yf + height, 1, 1, r, g, b, a, 1,
                    xf, yf, 0, 0, r, g, b, a, 1,
                    xf + width, yf + height, 1, 1, r, g, b, a, 1,
                    xf, yf + height, 0, 1, r, g, b, a, 1,
                });

                glVertexArray.bind();
                spriteShaderProgram.use();
                spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

                glDrawArrays(GL_TRIANGLES, 0, 6);
                glDisable(GL_BLEND);
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
        x += getTranslateX();
        y += getTranslateY();

        float halfWidth = (float) width / 2;
        float halfHeight = (float) height / 2;

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

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
                    x + halfWidth, y + halfHeight, 0.0f, 0.0f, r, g, b, a, 1.0f,
                    prevX, prevY, 0.0f, 0.0f, r, g, b, a, 1.0f,
                    currX, currY, 0.0f, 0.0f, r, g, b, a, 1.0f));

            prevX = currX;
            prevY = currY;
        }

        if (isFilled) {
            glActions.add(() -> {
                renderTarget.use();

                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_BLEND);

                glBuffer.setBufferData(ListUtils.toArray(points));

                glVertexArray.bind();
                spriteShaderProgram.use();
                spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

                glDrawArrays(GL_TRIANGLE_FAN, 0, CIRCLE_POINTS * 3);
                glDisable(GL_BLEND);
                glUseProgram(0);
            });
        }
        else {
            // TODO: implement it
        }
    }

    public void drawPolygon(int[] xPoints, int xOffset, int[] yPoints, int yOffset, int nPoints, Color color, boolean isFilled) {
        switch (nPoints) {
            case 3 -> drawTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[2], yPoints[2], color, isFilled);
            case 4 -> {
                // TODO: optimize it
                drawTriangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[2], yPoints[2], color, isFilled);
                drawTriangle(xPoints[0], yPoints[0], xPoints[2], yPoints[2], xPoints[3], yPoints[3], color, isFilled);
            }
            default -> {
                // TODO: triangulate polygon
            }
        }

    }

    @Override
    public void drawString(String str, int x, int y, int anchor, Color color) throws NullPointerException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void drawImage(Image img, int x, int y) throws IllegalArgumentException, NullPointerException {
        int width = img.getWidth();
        int height = img.getHeight();

        final int xf = x + getTranslateX();
        final int yf = y + getTranslateY();

        if (!generatedTextures.containsKey(img)) {
            glActions.add(() -> {
                var texture = new GLTexture(img.convertToByteBuffer(), width, height);
                generatedTextures.put(img, texture);
            });
        }

        glActions.add(() -> {
            renderTarget.use();

            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_BLEND);

            glBuffer.setBufferData(new float[]{
                    // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                    xf, yf, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                    xf + width, yf, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                    xf + width, yf + height, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                    xf, yf, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                    xf + width, yf + height, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                    xf, yf + height, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
            });

            glVertexArray.bind();
            spriteShaderProgram.use();
            spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 320, 0));

            generatedTextures.get(img).bind(GL_TEXTURE0);
            glDrawArrays(GL_TRIANGLES, 0, 6);
            glDisable(GL_BLEND);
            glUseProgram(0);
        });
    }

    @Override
    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException {
        // TODO: write method logic
    }

    @Override
    public void copyArea(int x_src, int y_src, int width, int height, int x_dest, int y_dest, int anchor) throws IllegalStateException, IllegalArgumentException {
        // TODO: write method logic
    }

    @Override
    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) throws ArrayIndexOutOfBoundsException, NullPointerException {
        // TODO: write method logic
    }

    @Override
    public void paintOffscreenBuffer(HangarOffscreenBuffer offscreenBuffer) {
        if (offscreenBuffer instanceof HangarGLOffscreenBuffer glOffscreenBuffer) {
            var offscreenRenderTarget = glOffscreenBuffer.getRenderTarget();
            if (!offscreenRenderTarget.isInitialized()) {
                glActions.add(offscreenRenderTarget::initialize);
            }

            if (offscreenBuffer.getGraphicsProvider() instanceof HangarGLGraphicsProvider glOffscreenGraphicsProvider) {
                glActions.addAll(glOffscreenGraphicsProvider.glActions);
                glOffscreenGraphicsProvider.glActions.clear();
            }

            glActions.add(() -> {
                renderTarget.use();
                glBuffer.setBufferData(new float[]{
                        // 2x POSITION | 2x UV | 4x COLOR | 1x isIgnoreSprite
                        0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                        240.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                        240.0f, 320.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                        240.0f, 320.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                        0.0f, 320.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
                });

                glVertexArray.bind();
                spriteShaderProgram.use();
                spriteShaderProgram.setUniform("projectionMatrix", new Matrix4f().ortho2D(0, 240, 0, 320));

                offscreenRenderTarget.getTexture().bind(GL_TEXTURE0);
                glDrawArrays(GL_TRIANGLES, 0, 6);
                glDisable(GL_BLEND);
                glUseProgram(0);
            });
        }
    }
}