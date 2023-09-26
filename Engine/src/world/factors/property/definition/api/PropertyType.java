package world.factors.property.definition.api;

public enum PropertyType {
    DECIMAL {

        public Integer convert(Object value) {
            // if the value is 75.0, convert it to 75
            // if the value is 75.5, do not convert it
            if (value instanceof Float) {
                if ((float) value % 1 == 0) {
                    return ((Float) value).intValue();
                }
            }
            if (!(value instanceof Integer)) {
                throw new IllegalArgumentException("value " + value + " is not of a DECIMAL type (expected Decimal class)");
            }
            return (Integer) value;
        }

        public boolean isMyType(String value) {
            return value.matches("-?\\d+");
        }
    }, BOOLEAN {

        public Boolean convert(Object value) {
            if (!(value instanceof Boolean)) {
                throw new IllegalArgumentException("value " + value + " is not of a BOOLEAN type (expected Boolean class)");
            }
            return (Boolean) value;
        }
        public boolean isMyType(String value) {
               return value.matches("true|false");
        }
    }, FLOAT {

        public Float convert(Object value) {
            // if the value is an integer, convert it to float
            if (value instanceof Integer) {
                return ((Integer) value).floatValue();
            }
            if (!(value instanceof Float)) {
                throw new IllegalArgumentException("value " + value + " is not of a FLOAT type (expected Float class)");
            }
            return (Float) value;
        }
        public boolean isMyType(String value) {
            return value.matches("-?\\d+(\\.\\d+)?");
        }
    }, STRING {

        public String convert(Object value) {
            if (!(value instanceof String)) {
                throw new IllegalArgumentException("value " + value + " is not of a STRING type (expected String class)");
            }
            return (String) value;
        }
        public boolean isMyType(String value) {
            return true;
        }
    };

    public abstract <T> T convert(Object value);
    public abstract boolean isMyType(String value);
}