package tools;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class Feature {

    String featureName;
    String description;
    List<Scenario> scenarios;

    public Feature(String featureName, String description, List<Scenario> scenarios) {
        this.featureName = featureName;
        this.description = description;
        this.scenarios = scenarios;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    public JSONObject JSONize(){
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("Feature", featureName);
        obj.put("Description", description);
        for(Scenario scenario : scenarios){
            arr.add(scenario.JSONize());
        }
        obj.put("Scenarios", arr);
        return obj;
    }
}
