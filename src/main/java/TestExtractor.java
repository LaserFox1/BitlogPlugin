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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        resetFile();
        JSONObject topObj = new JSONObject();
        JSONArray topJarr = new JSONArray();
        for (String s : includedFiles) {

            int phase = 0;
            String phaseIdent = "Feature";
            List<Object> arr = Reader.read("src/test/resources/features/" + s);
            JSONObject obj = new JSONObject();
            JSONArray jarr = new JSONArray();

            Integer[] scenarios = filter(arr, "Scenario");
            Integer[] givens = filter(arr, "Given");
            Integer[] whens = filter(arr, "When");
            Integer[] thens = filter(arr, "Then");
            boolean isDesc = false;
            for (int i = 0; i < arr.size(); i++) {
                if(i > scenarios[0]){
                    if(!isDesc){
                        if(arr.get(i).equals('\n')){
                            isDesc = true;
                        }
                    }
                }
            }
            System.out.println(obj.toJSONString());


        }


        /*topJarr.add(obj.toJSONString());
    }
        topObj.put("Features: ",topJarr);
        Writer.write(topObj.toJSONString());*/
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

    private static Integer[] filter(List<Object> arr, String keyword) {
        List<Integer> tempList = new ArrayList<Integer>();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(keyword))
                tempList.add(i);
        }
        return tempList.toArray(Integer[]::new);
    }
}
