import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.model.fileset.FileSet;
import org.apache.maven.shared.model.fileset.util.FileSetManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tools.Reader;
import tools.Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
        /*List features = project.getTestResources();
        System.out.println(features.toString());*/
        FileSetManager fileSetManager = new FileSetManager();
        String[] includedFiles = fileSetManager.getIncludedFiles(fileset);
        resetFile();
        JSONObject topObj = new JSONObject();
        JSONArray topJarr = new JSONArray();
        for (String s : includedFiles) {
            ArrayList<String> arr = Reader.read("src/test/resources/features/" + s);
            JSONObject obj = new JSONObject();
            JSONArray jarr = new JSONArray();
            boolean isScenario = false;
            for (String s1 : arr) {
                if (!s1.equals("")) {
                    if (!isScenario) {
                        String[] s2 = s1.trim().split(":\\s+");
                        if (s2.length > 1) {
                            switch (s2[0]) {
                                case "Feature" -> obj.put("FeatureName", s2[1]);
                                case "Scenario" -> {
                                    obj.put("ScenarioName", s2[1]);
                                    isScenario = true;
                                }
                            }
                        }
                        else{
                            s2 = s1.split("\\s+");
                            String s3 = "";
                            for(int i = 1; i < s2.length; i++){
                                s3 = s3.concat(s2[i]);
                                if((i+1<s2.length)){
                                    s3 = s3.concat(" ");
                                }
                            }
                            obj.put("Description", s3);
                        }
                    }
                    else{
                        String[] s2 = s1.trim().split("\\s+");
                        String s3 = "";
                        for(int i = 1; i < s2.length; i++){
                            s3 = s3.concat(s2[i]);
                            if((i+1<s2.length)){
                                s3 = s3.concat(" ");
                            }
                        }
                        switch (s2[0]) {
                            case "Given" -> jarr.add("Given: " + s3);
                            case "When" -> jarr.add("When: " + s3);
                            case "Then" -> jarr.add("Then: " + s3);
                        }
                    }
                }
            }
            obj.put("Syntax", jarr);

            topJarr.add(obj.toJSONString());
        }
        topObj.put("Features: ", topJarr);
        Writer.write(topObj.toJSONString());
    }

    private static void resetFile() {
        File myObj = new File("output.json");
        try {
            if (!myObj.createNewFile()) {
                FileWriter fwOb = new FileWriter("output.json", false);
                PrintWriter pwOb = new PrintWriter(fwOb, false);
                pwOb.flush();
                pwOb.close();
                fwOb.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
