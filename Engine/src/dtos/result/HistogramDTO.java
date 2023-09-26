package dtos.result;

import java.util.Map;

public class HistogramDTO {
    private final Map<Object, Integer> histogram;

    public HistogramDTO(Map<Object, Integer> histogram) {
        this.histogram = histogram;
    }

    public Map<Object, Integer> getHistogram() {
        return histogram;
    }
}
