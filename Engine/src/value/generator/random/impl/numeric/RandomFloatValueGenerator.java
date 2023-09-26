package value.generator.random.impl.numeric;

public class RandomFloatValueGenerator extends AbstractNumericRandomGenerator<Float>{
    public RandomFloatValueGenerator(Float from, Float to) {
        super(from, to);
    }

    @Override
    public Float generateValue() {
        return from + random.nextFloat() * (to - from);
    }
}
