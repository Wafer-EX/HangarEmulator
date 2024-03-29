/*
 * Copyright 2024 Wafer EX
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

package javax.microedition.sensor;

public class MeasurementRange {
    private final double smallest;
    private final double largest;
    private final double resolution;

    public MeasurementRange(double smallest, double largest, double resolution) {
        this.smallest = smallest;
        this.largest = largest;
        this.resolution = resolution;
    }

    public double getLargestValue() {
        return largest;
    }

    public double getResolution() {
        return resolution;
    }

    public double getSmallestValue() {
        return smallest;
    }
}