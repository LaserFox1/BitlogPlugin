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
                    String[] s2 = s1.trim().split(":\\s+");
                    if (s2.length > 1) {
                        switch (s2[0]) {
                            case "Feature" -> obj.put("FeatureName", s2[1]);
                            case "Scenario" -> {
                                JSONObject scenObj = new JSONObject();
                                scenObj.put("Scenario", s2[1]);

                                ArrayList<String> scenArr = new ArrayList<>();
                                int temp = arr.indexOf(s1);
                                scenArr.add(arr.get(temp + 1));
                                scenArr.add(arr.get(temp + 2));
                                scenArr.add(arr.get(temp + 3));

                                scenObj.put("Syntax", makeObject(scenArr));
                                jarr.add(scenObj);
                            }
                        }
                    } else {
                        s2 = s1.split("\\s+");
                        String s3 = "";
                        for (int i = 1; i < s2.length; i++) {
                            s3 = s3.concat(s2[i]);
                            if ((i + 1 < s2.length)) {
                                s3 = s3.concat(" ");
                            }
                        }
                        obj.put("Description", s3);
                    }
                }
            }
            obj.put("Scenarios", jarr);

            topJarr.add(obj);
        }
        topObj.put("Features", topJarr);
        topObj.put("Project", project.getGroupId() + ":" + project.getArtifactId());
        topObj.put("Version", project.getVersion());
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

    private static JSONObject makeObject(ArrayList<String> arr){
        JSONObject obj = new JSONObject();

        for(String s1 : arr){
            String[] s2 = s1.trim().split("\\s+");
            String s3 = "";
            for (int i = 1; i < s2.length; i++) {
                s3 = s3.concat(s2[i]);
                if ((i + 1 < s2.length)) {
                    s3 = s3.concat(" ");
                }
            }
            switch (s2[0]){
                case "Given" -> obj.put("Given", s3);
                case "When" -> obj.put("When", s3);
                case "Then" -> obj.put("Then", s3);
             }
        }
        return obj;
    }
}
