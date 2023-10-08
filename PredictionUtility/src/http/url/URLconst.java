package http.url;

public class URLconst {
    public static final String BASE_URL = "http://localhost:8080/PredictionWebServer";
    public static final String LOGIN_SRC = "/login";
    public static final String LOGIN_URL = BASE_URL + LOGIN_SRC;
    public static final String LOGOUT_SRC = "/logout";
    public static final String LOGOUT_URL = BASE_URL + LOGOUT_SRC;
    public static final String LOAD_XML_SRC = "/loadXML";
    public static final String LOAD_XML_URL = BASE_URL + LOAD_XML_SRC;
    public static final String FETCH_WORLDS_DETAILS_SRC = "/fetchWorldsDetails";
    public static final String FETCH_WORLDS_DETAILS_URL = BASE_URL + FETCH_WORLDS_DETAILS_SRC;
    public static final String FETCH_QUEUE_MANAGEMENT_SRC = "/fetchQueueManagement";
    public static final String FETCH_QUEUE_MANAGEMENT_URL = BASE_URL + FETCH_QUEUE_MANAGEMENT_SRC;
    public static final String SET_THREADS_COUNT_SRC = "/setThreadsCount";
    public static final String SET_THREADS_COUNT_URL = BASE_URL + SET_THREADS_COUNT_SRC;
    public static final String FETCH_REQUESTS_SRC = "/fetchRequests";
    public static final String FETCH_REQUESTS_URL = BASE_URL + FETCH_REQUESTS_SRC;
    public static final String SUBMIT_REQUEST_SRC = "/submitRequest";
    public static final String SUBMIT_REQUEST_URL = BASE_URL + SUBMIT_REQUEST_SRC;
}
