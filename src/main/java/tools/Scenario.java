package tools;

public class Scenario {
    String Given;
    String When;
    String Then;

    public Scenario(String given, String when, String then) {
        Given = given;
        When = when;
        Then = then;
    }

    public String getGiven() {
        return Given;
    }

    public void setGiven(String given) {
        Given = given;
    }

    public String getWhen() {
        return When;
    }

    public void setWhen(String when) {
        When = when;
    }

    public String getThen() {
        return Then;
    }

    public void setThen(String then) {
        Then = then;
    }
}
