package http.url;

public enum Client {
    USER, ADMIN, UNAUTHORIZED;
    public static Client getClientTypeFromString(String str) {
        if (str == null) {
            return UNAUTHORIZED;
        }
        switch (str) {
            case "USER":
                return USER;
            case "ADMIN":
                return ADMIN;
            default:
                return UNAUTHORIZED;
        }
    }
    public String getClientType() {
        switch (this) {
            case USER:
                return "USER";
            case ADMIN:
                return "ADMIN";
            default:
                return "";
        }
    }
}
