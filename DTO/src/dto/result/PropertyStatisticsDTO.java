package dto.result;

public class PropertyStatisticsDTO {
    private PropertyConstistencyDTO propertyConsistencyDTO;
    private PropertyAvaregeValueDTO propertyAvaregeValueDTO;
    private HistogramDTO histogramDTO;

    public PropertyStatisticsDTO(PropertyConstistencyDTO propertyConsistencyDTO, PropertyAvaregeValueDTO propertyAvaregeValueDTO, HistogramDTO histogramDTO) {
        this.propertyConsistencyDTO = propertyConsistencyDTO;
        this.propertyAvaregeValueDTO = propertyAvaregeValueDTO;
        this.histogramDTO = histogramDTO;
    }

    public PropertyConstistencyDTO getPropertyConsistencyDTO() {
        return propertyConsistencyDTO;
    }

    public PropertyAvaregeValueDTO getPropertyAvaregeValueDTO() {
        return propertyAvaregeValueDTO;
    }

    public HistogramDTO getHistogramDTO() {
        return histogramDTO;
    }
}
