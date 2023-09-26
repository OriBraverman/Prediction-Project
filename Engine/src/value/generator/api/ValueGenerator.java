package value.generator.api;

public interface ValueGenerator<T> {
    T generateValue();
    boolean isRandomInit();
}
