package value.generator.random.api;

import value.generator.api.ValueGenerator;

import java.io.Serializable;
import java.util.Random;

public abstract class AbstractRandomValueGenerator<T> implements ValueGenerator<T>, Serializable {
    protected final Random random;

    protected AbstractRandomValueGenerator() {
        random = new Random();
    }

    @Override
    public boolean isRandomInit() {
        return true;
    }
}
