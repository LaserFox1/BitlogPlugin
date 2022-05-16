package main;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.model.fileset.FileSet;
import org.apache.maven.shared.model.fileset.util.FileSetManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tools.ObjectBuilder;
import tools.ToolBox;
import tools.Writer;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Mojo(name = "test-extractor", defaultPhase = LifecyclePhase.COMPILE)
public class TestExtractor extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter
    private FileSet fileset;

    public void execute() throws MojoExecutionException, MojoFailureException {

        FileSetManager fileSetManager = new FileSetManager();
        String[] includedFiles = fileSetManager.getIncludedFiles(fileset);

        ToolBox.resetFile("output.json");
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        for (String s : includedFiles) {
            arr.add(ObjectBuilder.buildFeature(s).JSONize());
        }

        obj.put("Features", arr);
        try {
            URL url = new URL("https://bitlogdev-ad0fe.web.app/");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.write("Resource content");
            out.close();
            httpCon.getInputStream();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Writer.write(obj.toJSONString());
    }
}



