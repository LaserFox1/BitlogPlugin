import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.model.fileset.FileSet;
import org.apache.maven.shared.model.fileset.util.FileSetManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tools.*;
import tools.Reader;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "test-extractor", defaultPhase = LifecyclePhase.COMPILE)
public class TestExtractor extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter
    private FileSet fileset;

    @Parameter
    private FileSet[] filesets;

    public void execute() throws MojoExecutionException, MojoFailureException {

        FileSetManager fileSetManager = new FileSetManager();
        String[] includedFiles = fileSetManager.getIncludedFiles(fileset);
        ToolBox.resetFile("output.json");
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        for (String s : includedFiles) {
            arr.add(buildFeature(s).JSONize());
        }
        obj.put("Features", arr);
        Writer.write(obj.toJSONString());
    }



    private Scenario buildScenario(List<String> arr){
        return new Scenario(
                ToolBox.concat(arr.get(0), "Scenario:"),
                ToolBox.concat(arr.get(1)),
                ToolBox.concat(arr.get(2)),
                ToolBox.concat(arr.get(3))
        );
    }

    private Feature buildFeature(String s){

        List<String> arr = Reader.read("src/test/resources/features/" + s);

        List<Scenario> scenarios = new ArrayList<>();
        String featureName = "";
        StringBuilder description = new StringBuilder();
        boolean isDescription = false;

        for(String line : arr){
            List<String> tokens = ToolBox.tokenize(line);
            String keyword = tokens.get(0);
            if(isDescription){
                if(line.isBlank()||line.isEmpty()){
                    break;
                }
                description.append(ToolBox.concat(line));
            }
            switch (keyword){
                case "Feature:" -> {
                    featureName = ToolBox.concat(line, "Feature:");
                    isDescription = true;
                }
                case "Scenario:" -> {
                    scenarios.add(buildScenario(arr.subList(arr.indexOf(line), arr.indexOf(line)+4)));
                    isDescription = false;
                }
            }
        }
        return new Feature(featureName, description.toString(), scenarios);
    }
}
