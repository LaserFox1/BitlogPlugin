package classes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Scenario {

    String scenarioName;
    String given;
    String when;
    String then;


    public Scenario(String scenarioName, String given, String when, String then) {
        this.scenarioName = scenarioName;
        this.given = given;
        this.when = when;
        this.then = then;
    }
    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }
    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getThen() {
        return then;
    }

    public void setThen(String then) {
        this.then = then;
    }

    public JSONObject JSONize(){
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        obj.put("Scenario", scenarioName);

        JSONObject givenObj = new JSONObject();
        givenObj.put("Given", given);
        arr.add(givenObj);

        JSONObject whenObj = new JSONObject();
        whenObj.put("When", when);
        arr.add(whenObj);

        JSONObject thenObj = new JSONObject();
        thenObj.put("Then", then);
        arr.add(thenObj);

        obj.put("Syntax", arr);
        return obj;
    }
}
