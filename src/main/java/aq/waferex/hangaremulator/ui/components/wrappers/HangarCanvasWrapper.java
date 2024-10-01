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
import aq.waferex.hangaremulator.ui.listeners.HangarMouseListener;
import aq.waferex.hangaremulator.utils.CanvasWrapperUtils;
import aq.waferex.hangaremulator.utils.SystemUtils;
import aq.waferex.hangaremulator.utils.microedition.ImageUtils;
import org.joml.Matrix4f;
import org.lwjgl.opengl.awt.AWTGLCanvas;

import javax.microedition.lcdui.Canvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

import static aq.waferex.hangaremulator.utils.CanvasWrapperUtils.getScreenImageProjectionMatrix;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL30.*;

public class HangarCanvasWrapper extends JPanel {
    protected final Canvas canvas;
    private Runnable callSerially;
    private Timer serialCallTimer = new Timer();

    protected Dimension bufferScale;
    protected double bufferScaleFactor = 1.0;
    protected Point bufferPosition = new Point(0, 0);

    private final HangarOpenGLCanvas openGLCanvas;

    public HangarCanvasWrapper(Canvas canvas) {
        super(new CardLayout());
        this.canvas = canvas;

        openGLCanvas = new HangarOpenGLCanvas();
        openGLCanvas.setFocusable(false);
        openGLCanvas.setPreferredSize(this.getPreferredSize());
        this.add(openGLCanvas);
        // TODO: refactor below, remove unused and etc

        var resolution = HangarState.getGraphicsSettings().getResolution();
        // TODO: initialize it in different place
        HangarState.setScreenImage(ImageUtils.createCompatibleImage(resolution.width, resolution.height));

        var mouseListener = new HangarMouseListener(this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var graphicsSettings = HangarState.getGraphicsSettings();
                if (graphicsSettings.getScalingMode() == ScalingModes.ChangeResolution) {
                    float scalingInUnits = SystemUtils.getScalingInUnits();
                    int realWidth = (int) (getWidth() * scalingInUnits);
                    int realHeight = (int) (getHeight() * scalingInUnits);
                    graphicsSettings.setResolution(new Dimension(realWidth, realHeight));
                }
                updateBufferTransformations();
            }
        });

        this.updateBufferTransformations();
        this.refreshSerialCallTimer();
    }

    public void setCallSerially(Runnable runnable) {
        this.callSerially = runnable;
    }

    public void refreshSerialCallTimer() {
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

    public Rectangle getDisplayedArea() {
        var graphicsSettings = HangarState.getGraphicsSettings();
        var resolution = graphicsSettings.getResolution();

        int width = (int) (resolution.width * bufferScaleFactor);
        int height = (int) (resolution.height * bufferScaleFactor);
        return new Rectangle(bufferPosition.x, bufferPosition.y, width, height);
    }

    public double getScaleFactor() {
        return bufferScaleFactor;
    }

    public void updateBufferTransformations() {
        var graphicsSettings = HangarState.getGraphicsSettings();
        var resolution = graphicsSettings.getResolution();

        bufferScaleFactor = CanvasWrapperUtils.getBufferScaleFactor(this, resolution.width, resolution.height);
        float scalingInUnits = SystemUtils.getScalingInUnits();

        int newWidth = (int) (resolution.width * bufferScaleFactor);
        int newHeight = (int) (resolution.height * bufferScaleFactor);
        bufferScale = new Dimension(newWidth, newHeight);

        bufferPosition.x = (int) ((getWidth() * scalingInUnits) / 2 - bufferScale.width / 2);
        bufferPosition.y = (int) ((getHeight() * scalingInUnits) / 2 - bufferScale.height / 2);
    }

//    @Override
//    public void paintComponent(Graphics graphics) {
//        super.paintComponent(graphics);
//
//        var graphics2d = (Graphics2D) graphics;
//        var transform = graphics2d.getTransform();
//        transform.setToScale(1.0, 1.0);
//        graphics2d.setTransform(transform);
//
//        var graphicsSettings = HangarState.getGraphicsSettings();
//        var screenImage = HangarState.getScreenImage();
//
//        if (screenImage != null) {
//            var graphicsWithHints = HangarState.applyAntiAliasing(screenImage.getGraphics());
//            if (graphicsSettings.getCanvasClearing()) {
//                graphicsWithHints.clearRect(0, 0, screenImage.getWidth(), screenImage.getHeight());
//            }
//            // TODO: use graphics with hints or setup these settings in graphics object?
//            canvas.paint(new javax.microedition.lcdui.Graphics(screenImage));
//            graphics2d.drawImage(screenImage, bufferPosition.x, bufferPosition.y, bufferScale.width, bufferScale.height, null);
//        }
//    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        var screenImage = HangarState.getScreenImage();
        if (screenImage != null) {
            var graphicsWithHints = HangarState.applyAntiAliasing(screenImage.getGraphics());
            if (HangarState.getGraphicsSettings().getCanvasClearing()) {
                graphicsWithHints.clearRect(0, 0, screenImage.getWidth(), screenImage.getHeight());
            }

            canvas.paint(new javax.microedition.lcdui.Graphics(graphicsWithHints));
            openGLCanvas.render();
        }
    }

    private static final class HangarOpenGLCanvas extends AWTGLCanvas {
        private int vertexArrayObject;
        private int vertexBufferObject;
        private int shaderProgram;
        private int texture;

        private final float[] vertices = {
                // Left top
                0.0f, 0.0f, 0.0f, 0.0f,
                // Right bottom
                240.0f, 320.0f, 1.0f, 1.0f,
                // Left bottom
                0.0f, 320.0f, 0.0f, 1.0f,

                // Left top
                0.0f, 0.0f, 0.0f, 0.0f,
                // Right top
                240.0f, 0.0f, 1.0f, 0.0f,
                // Right bottom
                240.0f, 320.0f, 1.0f, 1.0f,
        };

        public HangarOpenGLCanvas() {
            super();
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
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

            glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 4, 0);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * 4, 2 * 4);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            String vertexShaderSource = """
                    #version 330 core
                    layout (location = 0) in vec2 position;
                    layout (location = 1) in vec2 uv;
                    
                    uniform mat4 projectionMatrix;
                    
                    out vec2 UV;
                    
                    void main() {
                        UV = uv;
                        gl_Position = projectionMatrix * vec4(position.x, position.y, 0.0, 1.0);
                    }
                    """;

            String fragmentShaderSource = """
                    #version 330 core
                    out vec4 FragColor;
                    
                    in vec2 UV;
                    
                    uniform sampler2D sprite;
                    
                    void main() {
                        FragColor = texture(sprite, UV);
                    }
                    """;

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
            var screenImage = HangarState.getScreenImage();
            var screenImageBuffer = convertToByteBuffer(HangarState.getScreenImage());

            var scalingInUnits = SystemUtils.getScalingInUnits();
            int viewportWidth = (int) (getSize().width * scalingInUnits);
            int viewportHeight = (int) (getSize().height * scalingInUnits);

            glBindFramebuffer(GL_FRAMEBUFFER, 0);
            glViewport(0, 0, viewportWidth, viewportHeight);
            glClear(GL_COLOR_BUFFER_BIT);

            glBindVertexArray(vertexArrayObject);
            glUseProgram(shaderProgram);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, screenImage.getWidth(), screenImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, screenImageBuffer);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            int location = glGetUniformLocation(shaderProgram, "projectionMatrix");
            float[] data = new float[16];
            Matrix4f projectionMatrix = getScreenImageProjectionMatrix(viewportWidth, viewportHeight, screenImage.getWidth(), screenImage.getHeight());
            glUniformMatrix4fv(location, false, projectionMatrix.get(data));

            glDrawArrays(GL_TRIANGLES, 0, 6);
            swapBuffers();
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