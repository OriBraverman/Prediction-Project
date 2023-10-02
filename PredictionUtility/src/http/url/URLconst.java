package http.url;

public class URLconst {
    public static final String BASE_URL = "http://localhost:8080/PredictionWebServer";
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String LOGOUT_URL = BASE_URL + "/logout";
    public static final String LOAD_XML_SRC = "/loadXML";
    public static final String LOAD_XML_URL = BASE_URL + LOAD_XML_SRC;
    public static final String FETCH_WORLDS_DETAILS_SRC = "/fetchWorldsDetails";
    public static final String FETCH_WORLDS_DETAILS_URL = BASE_URL + FETCH_WORLDS_DETAILS_SRC;
}
