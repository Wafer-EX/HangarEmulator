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

package things.graphics.lwjgl.abstractions;

import org.lwjgl.opengl.GL46;

public class FramebufferObject {
    private final int identifier;

    public FramebufferObject() {
        identifier = GL46.glGenFramebuffers();
        bind();
    }

    public void attachTexture(TextureObject texture, int attachment) {
        GL46.glFramebufferTexture2D(GL46.GL_FRAMEBUFFER, attachment, GL46.GL_TEXTURE_2D, texture.getIdentifier(), 0);
    }

    public void bind() {
        GL46.glBindFramebuffer(GL46.GL_FRAMEBUFFER, identifier);
    }
}