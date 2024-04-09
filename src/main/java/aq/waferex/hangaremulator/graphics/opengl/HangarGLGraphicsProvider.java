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

package aq.waferex.hangaremulator.graphics.opengl;

import aq.waferex.hangaremulator.graphics.HangarImage;
import aq.waferex.hangaremulator.graphics.swing.HangarSwingImage;
import org.joml.Matrix4f;
import aq.waferex.hangaremulator.graphics.HangarGraphicsProvider;
import aq.waferex.hangaremulator.graphics.HangarOffscreenBuffer;
import aq.waferex.hangaremulator.graphics.opengl.abstractions.*;
import aq.waferex.hangaremulator.utils.ListUtils;

import javax.microedition.lcdui.Font;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL33.*;

public class HangarGLGraphicsProvider extends HangarGraphicsProvider {
    public static final int CIRCLE_POINTS = 16;

    private final ArrayList<HangarGLAction> glActions = new ArrayList<>();
    private final HashMap<HangarImage, GLTexture> generatedTextures = new HashMap<>();

    private final RenderTarget renderTarget;

    private static GLShaderProgram spriteShaderProgram;
    private GLVertexArray glSpriteVertexArray;
    private GLBuffer glSpriteBuffer;

    private static GLShaderProgram shapeShaderProgram;
    private GLVertexArray glShapeVertexArray;
    private GLBuffer glShapeBuffer;

    private static boolean shadersAreCompiled = false;
    private boolean isGraphicsPrepared = false;

    public HangarGLGraphicsProvider(RenderTarget renderTarget) {
        this.renderTarget = renderTarget;

        glActions.add(() -> {
            if (!isGraphicsPrepared) {
                glSpriteBuffer = new GLBuffer(GL_ARRAY_BUFFER, null);
                glSpriteVertexArray = new GLVertexArray();
                glSpriteVertexArray.VertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 4, 0);
                glSpriteVertexArray.VertexAttribPointer(1, 2, GL_FLOAT, false, 4 * 4, 2 * 4);

                glShapeBuffer = new GLBuffer(GL_ARRAY_BUFFER, null);
                glShapeVertexArray = new GLVertexArray();
                glSpriteVertexArray.VertexAttribPointer(0, 2, GL_FLOAT, false, 6 * 4, 0);
                glSpriteVertexArray.VertexAttribPointer(1, 4, GL_FLOAT, false, 6 * 4, 2 * 4);

                isGraphicsPrepared = true;
            }
        });
    }

    public static boolean getShadersAreCompiled() {
        return shadersAreCompiled;
    }

    public static void compileShaders() {
        if (shadersAreCompiled) {
            throw new IllegalStateException();
        }

        spriteShaderProgram = new GLShaderProgram("/shaders/sprite.vert", "/shaders/sprite.frag");
        shapeShaderProgram = new GLShaderProgram("/shaders/shape.vert", "/shaders/shape.frag");
        shadersAreCompiled = true;
    }

    public static GLShaderProgram getSpriteShaderProgram() {
        return spriteShaderProgram;
    }

    private Matrix4f getProjectionMatrix(boolean translate) {
        var matrix = new Matrix4f().ortho2D(0, renderTarget.getWidth(), 0, renderTarget.getHeight());
        if (translate) {
            matrix = matrix.mul(new Matrix4f().translate(getTranslateX(), getTranslateY(), 0.0f));
        }
        return matrix;
    }

    public ArrayList<HangarGLAction> getGLActions() {
        return glActions;
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
    public void setClip(int x, int y, int width, int height) {
        final int translateX = getTranslateX();
        final int translateY = getTranslateY();
        glActions.add(() -> glScissor(x + translateX, y + translateY, width,  height));
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        // TODO: check it
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        final Matrix4f projectionMatrix = getProjectionMatrix(true);
        glActions.add(() -> {
            renderTarget.use();

            glShapeBuffer.setBufferData(new float[]{
                    // 2x POSITION | 4x COLOR
                    x1, y1, r, g, b, a,
                    x2, y2, r, g, b, a,
            });

            glShapeVertexArray.bind();
            shapeShaderProgram.use();
            shapeShaderProgram.setUniform("projectionMatrix", projectionMatrix);

            glDrawArrays(GL_LINES, 0, 2);
            glUseProgram(0);
        });
    }

    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color, boolean isFilled) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        final Matrix4f projectionMatrix = getProjectionMatrix(true);
        if (isFilled) {
            glActions.add(() -> {
                renderTarget.use();

                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_BLEND);

                glShapeBuffer.setBufferData(new float[]{
                        // 2x POSITION | 4x COLOR
                        x1, y1, r, g, b, a,
                        x2, y2, r, g, b, a,
                        x3, y3, r, g, b, a,
                });

                glShapeVertexArray.bind();
                shapeShaderProgram.use();
                shapeShaderProgram.setUniform("projectionMatrix", projectionMatrix);

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

        final Matrix4f projectionMatrix = getProjectionMatrix(true);
        if (isFilled) {
            glActions.add(() -> {
                renderTarget.use();

                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_BLEND);

                glShapeBuffer.setBufferData(new float[]{
                        // 2x POSITION | 4x COLOR
                        x, y, r, g, b, a,
                        x + width, y, r, g, b, a,
                        x + width, y + height, r, g, b, a,
                        x, y, r, g, b, a,
                        x + width, y + height, r, g, b, a,
                        x, y + height, r, g, b, a,
                });

                glShapeVertexArray.bind();
                shapeShaderProgram.use();
                shapeShaderProgram.setUniform("projectionMatrix", projectionMatrix);

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
                    // 2x POSITION | 4x COLOR
                    x + halfWidth, y + halfHeight, r, g, b, a,
                    prevX, prevY, r, g, b, a,
                    currX, currY, r, g, b, a
            ));

            prevX = currX;
            prevY = currY;
        }

        final Matrix4f projectionMatrix = getProjectionMatrix(true);
        if (isFilled) {
            glActions.add(() -> {
                renderTarget.use();

                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_BLEND);

                glShapeBuffer.setBufferData(ListUtils.toArray(points));

                glShapeVertexArray.bind();
                shapeShaderProgram.use();
                shapeShaderProgram.setUniform("projectionMatrix", projectionMatrix);

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
    public void drawImage(HangarImage img, int x, int y) throws IllegalArgumentException, NullPointerException {
        int width = img.getWidth();
        int height = img.getHeight();
        final Matrix4f projectionMatrix = getProjectionMatrix(true);

        // TODO: render HangarGLImage instead of this
        if (img instanceof HangarSwingImage swingImage) {
            glActions.add(() -> {
                renderTarget.use();

                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_BLEND);

                glSpriteBuffer.setBufferData(new float[]{
                        // 2x POSITION | 2x UV
                        x, y, 0.0f, 0.0f,
                        x + width, y, 1.0f, 0.0f,
                        x + width, y + height, 1.0f, 1.0f,
                        x, y, 0.0f, 0.0f,
                        x + width, y + height, 1.0f, 1.0f,
                        x, y + height, 0.0f, 1.0f,
                });

                glSpriteVertexArray.bind();
                spriteShaderProgram.use();
                spriteShaderProgram.setUniform("projectionMatrix", projectionMatrix);

                var texture = generatedTextures.get(img);
                if (texture == null) {
                    texture = new GLTexture(swingImage.convertToByteBuffer(), width, height);
                    generatedTextures.put(img, texture);
                }

                texture.bind(GL_TEXTURE0);
                glDrawArrays(GL_TRIANGLES, 0, 6);
                glDisable(GL_BLEND);
                glUseProgram(0);
            });
        }
    }

    @Override
    public void drawRegion(HangarImage src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) throws IllegalArgumentException, NullPointerException {
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
                glSpriteBuffer.setBufferData(new float[]{
                        // 2x POSITION | 2x UV
                        0.0f, 0.0f, 0.0f, 0.0f,
                        renderTarget.getWidth(), 0.0f, 1.0f, 0.0f,
                        renderTarget.getWidth(), renderTarget.getHeight(), 1.0f, 1.0f,
                        0.0f, 0.0f, 0.0f, 0.0f,
                        renderTarget.getWidth(), renderTarget.getHeight(), 1.0f, 1.0f,
                        0.0f, renderTarget.getHeight(), 0.0f, 1.0f,
                });

                glSpriteVertexArray.bind();
                spriteShaderProgram.use();
                spriteShaderProgram.setUniform("projectionMatrix", getProjectionMatrix(false));

                offscreenRenderTarget.getTexture().bind(GL_TEXTURE0);
                glDrawArrays(GL_TRIANGLES, 0, 6);
                glDisable(GL_BLEND);
                glUseProgram(0);
            });
        }
    }
}