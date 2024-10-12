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

public final class RangeCondition implements Condition {
    private final double lowerLimit;
    private final String lowerOp;
    private final double upperLimit;
    private final String upperOp;

    public RangeCondition(double lowerLimit, String lowerOp, double upperLimit, String upperOp) {
        if (lowerLimit > upperLimit) {
            throw new IllegalArgumentException();
        }
        if (lowerLimit == upperLimit && !(lowerOp.equals(Condition.OP_GREATER_THAN_OR_EQUALS) && upperOp.equals(Condition.OP_LESS_THAN_OR_EQUALS))) {
            throw new IllegalArgumentException();
        }

        this.lowerLimit = lowerLimit;
        this.lowerOp = lowerOp;
        this.upperLimit = upperLimit;
        this.upperOp = upperOp;
    }

    public final double getLowerLimit() {
        return lowerLimit;
    }

    public final java.lang.String getLowerOp() {
        return lowerOp;
    }

    public final double getUpperLimit() {
        return upperLimit;
    }

    public final java.lang.String getUpperOp() {
        return upperOp;
    }

    // TODO: check it
    @Override
    public boolean isMet(double value) {
        boolean comparedToLowerLimit = switch (lowerOp) {
            case Condition.OP_EQUALS -> value == lowerLimit;
            case Condition.OP_GREATER_THAN -> value > lowerLimit;
            case Condition.OP_GREATER_THAN_OR_EQUALS -> value >= lowerLimit;
            case Condition.OP_LESS_THAN -> value < lowerLimit;
            case Condition.OP_LESS_THAN_OR_EQUALS -> value <= lowerLimit;
            default -> throw new IllegalStateException();
        };

        boolean comparedToUpperLimit = switch (upperOp) {
            case Condition.OP_EQUALS -> value == upperLimit;
            case Condition.OP_GREATER_THAN -> value > upperLimit;
            case Condition.OP_GREATER_THAN_OR_EQUALS -> value >= upperLimit;
            case Condition.OP_LESS_THAN -> value < upperLimit;
            case Condition.OP_LESS_THAN_OR_EQUALS -> value <= upperLimit;
            default -> throw new IllegalStateException();
        };

        return comparedToLowerLimit && comparedToUpperLimit;
    }

    @Override
    public boolean isMet(Object value) {
        return false;
    }
}