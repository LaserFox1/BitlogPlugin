package tools;

import classes.Feature;
import classes.Scenario;

import java.util.ArrayList;
import java.util.List;

public class ObjectBuilder {

    private static Scenario buildScenario(List<String> arr) {
        return new Scenario(
                ToolBox.concat(arr.get(0), "Scenario:"),
                ToolBox.concat(arr.get(1)),
                ToolBox.concat(arr.get(2)),
                ToolBox.concat(arr.get(3))
        );
    }

    public static Feature buildFeature(String s) {

        List<String> arr = Reader.read("src/test/resources/features/" + s);

        String featureName = "";
        StringBuilder description = new StringBuilder();
        boolean isDescription = false;
        List<Scenario> scenarios = new ArrayList<>();

        for (String line : arr) {
            List<String> tokens = ToolBox.tokenize(line);
            if (line.isEmpty() || line.isBlank())
                continue;

            String keyword = tokens.get(0);
            if (keyword.equals("Given") || keyword.equals("When") || keyword.equals("Then"))
                continue;

            switch (keyword) {
                case "Feature:" -> {
                    featureName = ToolBox.concat(line, "Feature:");
                    isDescription = true;
                }
                case "Scenario:" -> {
                    scenarios.add(buildScenario(arr.subList(arr.indexOf(line), arr.indexOf(line) + 4)));
                    isDescription = false;
                }
            }
            if (arr.indexOf(line) > 0 && isDescription) {
                description.append(ToolBox.concat(line));
            }
        }
        return new Feature(featureName, description.toString(), scenarios);
    }
}
