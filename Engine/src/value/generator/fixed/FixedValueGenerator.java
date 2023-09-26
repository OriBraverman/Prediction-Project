package value.generator.fixed;

import value.generator.api.ValueGenerator;

import java.io.Serializable;

public class FixedValueGenerator<T> implements ValueGenerator<T>, Serializable {

    private final T fixedValue;

    public FixedValueGenerator(T fixedValue) {
        this.fixedValue = fixedValue;
    }

    @Override
    public T generateValue() {
        return fixedValue;
    }

    @Override
    public boolean isRandomInit() {
        return false;
    }
}
