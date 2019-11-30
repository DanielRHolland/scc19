package src;

public class UserDatum {
    private final String key;
    private final String value;
    public UserDatum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
