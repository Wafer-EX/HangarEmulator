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

package aq.waferex.hangaremulator.ui.components.wrappers;

import aq.waferex.hangaremulator.HangarState;
import aq.waferex.hangaremulator.enums.ScalingModes;
import aq.waferex.hangaremulator.utils.CanvasWrapperUtils;
import aq.waferex.hangaremulator.utils.SystemUtils;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;
import org.joml.Matrix4f;
import org.lwjgl.opengl.awt.AWTGLCanvas;

import javax.microedition.lcdui.Canvas;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

import static aq.waferex.hangaremulator.utils.CanvasWrapperUtils.getScreenImageProjectionMatrix;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL30.*;

public class HangarCanvasWrapper extends HangarWrapper {
    private final Canvas canvas;
    private final javax.microedition.lcdui.Graphics meGraphics;

    private final HangarOpenGLCanvas openGLCanvas;
    private Timer serialCallTimer = new Timer();

    public HangarCanvasWrapper(Canvas canvas) {
        super(new CardLayout());
        this.canvas = canvas;
        this.meGraphics = new javax.microedition.lcdui.Graphics((Graphics2D) canvas.getScreenImage().getGraphics());

        openGLCanvas = new HangarOpenGLCanvas(canvas);
        openGLCanvas.setFocusable(false);
        openGLCanvas.setPreferredSize(this.getPreferredSize());
        this.add(openGLCanvas);
    }

    public void refreshSerialCallTimer(Runnable callSerially) {
        serialCallTimer.cancel();
        serialCallTimer.purge();
        serialCallTimer = new Timer();

        var frameRateInMilliseconds = HangarState.frameRateInMilliseconds();
        if (frameRateInMilliseconds >= 0) {
            serialCallTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (callSerially != null) {
                        callSerially.run();
                    }
                }
            }, 0, frameRateInMilliseconds);
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        refreshScreenImageResolution();

        var screenImage = canvas.getScreenImage();
        var screenImageGraphics = (Graphics2D) canvas.getScreenImage().getGraphics();

        if (HangarState.getGraphicsSettings().getScreenClearing()) {
            screenImageGraphics.clearRect(0, 0, screenImage.getWidth(), screenImage.getHeight());
        }

        meGraphics.setGraphics2D(screenImageGraphics);
        canvas.paint(meGraphics);
        openGLCanvas.render();
    }

    private void refreshScreenImageResolution() {
        var screenImage = canvas.getScreenImage();
        int expectedWidth = canvas.getWidth();
        int expectedHeight = canvas.getHeight();

        if (HangarState.getGraphicsSettings().getScalingMode() == ScalingModes.ChangeResolution) {
            var scalingInUnits = SystemUtils.getScalingInUnits();
            expectedWidth = (int) (getSize().width * scalingInUnits);
            expectedHeight = (int) (getSize().height * scalingInUnits);
        }

        if (expectedWidth != screenImage.getWidth() || expectedHeight != screenImage.getHeight()) {
            var newScreenImage = ImageUtils.createCompatibleImage(expectedWidth, expectedHeight);
            canvas.setScreenImage(newScreenImage);
            canvas.sizeChanged(expectedWidth, expectedHeight);
        }
    }

    private static final class HangarOpenGLCanvas extends AWTGLCanvas {
        private final Canvas canvas;

        private int vertexArrayObject;
        private int vertexBufferObject;
        private int shaderProgram;

        private int texture;
        private int textureWidth;
        private int textureHeight;

        public HangarOpenGLCanvas(Canvas canvas) {
            super();

            this.canvas = canvas;
            this.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (HangarState.getInputSettings().getTouchscreenInput()) {
                        var point = getConvertedPoint(e.getX(), e.getY());
                        canvas.pointerPressed(point.x, point.y);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (HangarState.getInputSettings().getTouchscreenInput()) {
                        var point = getConvertedPoint(e.getX(), e.getY());
                        canvas.pointerReleased(point.x, point.y);
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (HangarState.getInputSettings().getTouchscreenInput()) {
                        var point = getConvertedPoint(e.getX(), e.getY());
                        canvas.pointerDragged(point.x, point.y);
                    }
                }

                private Point getConvertedPoint(int mouseX, int mouseY) {
                    var scalingInUnits = SystemUtils.getScalingInUnits();

                    int viewportWidth = (int) (getSize().width * scalingInUnits);
                    int viewportHeight = (int) (getSize().height * scalingInUnits);

                    int screenImageWidth = canvas.getScreenImage().getWidth();
                    int screenImageHeight = canvas.getScreenImage().getHeight();

                    return CanvasWrapperUtils.convertMousePointToScreenImage(mouseX, mouseY, screenImageWidth, screenImageHeight, viewportWidth, viewportHeight, scalingInUnits);
                }
            });
        }

        @Override
        public void initGL() {
            createCapabilities();

            int red = getBackground().getRed();
            int green = getBackground().getGreen();
            int blue = getBackground().getBlue();
            int alpha = getBackground().getAlpha();
            glClearColor(red / 255.0f, green / 255.0f, blue / 255.0f, alpha / 255.0f);

            vertexArrayObject = glGenVertexArrays();
            glBindVertexArray(vertexArrayObject);

            vertexBufferObject = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);

            glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 4, 0);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * 4, 2 * 4);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            CharSequence vertexShaderSource = readShaderFile("/shaders/screenimage.vert");
            CharSequence fragmentShaderSource = readShaderFile("/shaders/screenimage.frag");
            assert fragmentShaderSource != null;
            assert vertexShaderSource != null;

            int vertexShader = glCreateShader(GL_VERTEX_SHADER);
            glShaderSource(vertexShader, vertexShaderSource);
            glCompileShader(vertexShader);

            int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
            glShaderSource(fragmentShader, fragmentShaderSource);
            glCompileShader(fragmentShader);

            shaderProgram = glCreateProgram();
            glAttachShader(shaderProgram, vertexShader);
            glAttachShader(shaderProgram, fragmentShader);
            glLinkProgram(shaderProgram);

            glDetachShader(shaderProgram, vertexShader);
            glDetachShader(shaderProgram, fragmentShader);
            glDeleteShader(vertexShader);
            glDeleteShader(fragmentShader);

            texture = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texture);
            glActiveTexture(GL_TEXTURE0);
        }

        @Override
        public void paintGL() {
            var screenImage = canvas.getScreenImage();
            var screenImageBuffer = convertToByteBuffer(canvas.getScreenImage());

            var scalingInUnits = SystemUtils.getScalingInUnits();
            int viewportWidth = (int) (getSize().width * scalingInUnits);
            int viewportHeight = (int) (getSize().height * scalingInUnits);

            glBindFramebuffer(GL_FRAMEBUFFER, 0);
            glViewport(0, 0, viewportWidth, viewportHeight);
            glClear(GL_COLOR_BUFFER_BIT);

            if (screenImage.getWidth() != textureWidth || screenImage.getHeight() != textureHeight) {
                glBufferData(GL_ARRAY_BUFFER, getScreenImageVertices(screenImage.getWidth(), screenImage.getHeight()), GL_STATIC_DRAW);
                textureWidth = screenImage.getWidth();
                textureHeight = screenImage.getHeight();
            }

            glBindVertexArray(vertexArrayObject);
            glUseProgram(shaderProgram);

            // TODO: call paintGL in x fps by timer, don't convert it until canvas.paint haven't called
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, screenImage.getWidth(), screenImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, screenImageBuffer);
            if (HangarState.getGraphicsSettings().getInterpolation()) {
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            }
            else {
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            }

            int location = glGetUniformLocation(shaderProgram, "projectionMatrix");
            float[] data = new float[16];
            Matrix4f projectionMatrix = getScreenImageProjectionMatrix(viewportWidth, viewportHeight, screenImage.getWidth(), screenImage.getHeight());
            glUniformMatrix4fv(location, false, projectionMatrix.get(data));

            glDrawArrays(GL_TRIANGLES, 0, 6);
            swapBuffers();
        }

        private static CharSequence readShaderFile(String name) {
            // TODO: improve code quality?
            try {
                var resource = HangarCanvasWrapper.class.getResourceAsStream(name);
                var bufferedReader = new BufferedReader(new InputStreamReader(resource));
                var stringBuilder = new StringBuilder();
                var line = bufferedReader.readLine();

                while (line != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                    line = bufferedReader.readLine();
                }
                return stringBuilder.toString();
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }

        private static float[] getScreenImageVertices(int imageWidth, int imageHeight) {
            return new float[] {
                    0.0f, 0.0f, 0.0f, 0.0f,
                    imageWidth, imageHeight, 1.0f, 1.0f,
                    0.0f, imageHeight, 0.0f, 1.0f,
                    0.0f, 0.0f, 0.0f, 0.0f,
                    imageWidth, 0.0f, 1.0f, 0.0f,
                    imageWidth, imageHeight, 1.0f, 1.0f,
            };
        }

        private static ByteBuffer convertToByteBuffer(BufferedImage image) {
            int[] pixels = image.getRGB(0, 0, image.getData().getWidth(), image.getData().getHeight(), null, 0, image.getData().getWidth());
            ByteBuffer buffer = ByteBuffer.allocateDirect(pixels.length * 4);
            for (int pixel : pixels) {
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
            buffer.flip();
            return buffer;
        }
    }
}