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

package things.utils;

public final class MatrixUtils {
    public static float[] createOrthographicOffCenter(float left, float right, float bottom, float top, float near, float far) {
        return new float[] {
                2.0f / (right - left), 0.0f, 0.0f, -((right + left) / (right - left)),
                0.0f, 2.0f / (top - bottom), 0.0f, -((top + bottom) / (top - bottom)),
                0.0f, 0.0f, -2.0f / (far - near), (far + near) / (far - near),
                0.0f, 0.0f, 0.0f, 1.0f
        };
    }
}