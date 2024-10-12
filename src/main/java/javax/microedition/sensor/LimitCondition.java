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

public final class LimitCondition implements Condition {
    private final double limit;
    private final String operator;

    public LimitCondition(double limit, String operator) {
        if (operator == null) {
            throw new NullPointerException();
        }
        this.limit = limit;
        this.operator = operator;
    }

    public final double getLimit() {
        return limit;
    }

    public final String getOperator() {
        return operator;
    }

    @Override
    public boolean isMet(double value) {
        return switch (operator) {
            case Condition.OP_EQUALS -> value == limit;
            case Condition.OP_GREATER_THAN -> value > limit;
            case Condition.OP_GREATER_THAN_OR_EQUALS -> value >= limit;
            case Condition.OP_LESS_THAN -> value < limit;
            case Condition.OP_LESS_THAN_OR_EQUALS -> value <= limit;
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public boolean isMet(Object value) {
        return false;
    }
}