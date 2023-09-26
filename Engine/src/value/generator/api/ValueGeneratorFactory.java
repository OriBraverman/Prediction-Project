package value.generator.api;

import value.generator.fixed.FixedValueGenerator;
import value.generator.random.impl.bool.RandomBooleanValueGenerator;
import value.generator.random.impl.numeric.RandomFloatValueGenerator;
import value.generator.random.impl.numeric.RandomIntegerValueGenerator;
import value.generator.random.impl.string.RandomStringValueGenerator;
import world.factors.property.definition.api.Range;

public interface ValueGeneratorFactory {

    static <T> ValueGenerator<T> createFixed(T value) {
        return new FixedValueGenerator<>(value);
    }

    static ValueGenerator<Boolean> createRandomBoolean() {
        return new RandomBooleanValueGenerator();
    }

    static ValueGenerator<Integer> createRandomInteger(Range range) {
        if (!(range.getFrom() instanceof Integer) || !(range.getTo() instanceof Integer)) {
            throw new IllegalArgumentException("Range must be of type Integer");
        }
        return new RandomIntegerValueGenerator((int)range.getFrom(), (int)range.getTo());
    }

    static ValueGenerator<Float> createRandomFloat(Range range) {
        if (range == null) {
            range = new Range(Float.MIN_VALUE, Float.MAX_VALUE);
        }
        if (!(range.getFrom() instanceof Float) || !(range.getTo() instanceof Float)) {
            throw new IllegalArgumentException("Range must be of type Float");
        }
        return new RandomFloatValueGenerator((float)range.getFrom(), (float)range.getTo());
    }

    static ValueGenerator<String> createRandomString() {
        return new RandomStringValueGenerator();
    }

    static ValueGenerator createRandomValueGeneratorByString(String valueGeneratorString, Range range) {
        if (valueGeneratorString.equals("decimal")) {
            return ValueGeneratorFactory.createRandomFloat(range);
        } else if (valueGeneratorString.equals("float")) {
            return ValueGeneratorFactory.createRandomFloat(range);
        } else if (valueGeneratorString.equals("string")) {
            return ValueGeneratorFactory.createRandomString();
        } else if (valueGeneratorString.equals("boolean")) {
            return ValueGeneratorFactory.createRandomBoolean();
        } else {
            throw new IllegalArgumentException("Unknown value generator type: " + valueGeneratorString);
        }
    }
}
