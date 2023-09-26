package value.generator.random.impl.numeric;

public class RandomIntegerValueGenerator extends AbstractNumericRandomGenerator<Integer> {

    public RandomIntegerValueGenerator(Integer from, Integer to) {
        super(from, to);
    }

    @Override
    public Integer generateValue() {
        return from + random.nextInt(to - from);
    }
}
