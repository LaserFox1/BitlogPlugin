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

import static tools.ToolBox.defaultSet;

@Mojo(name = "test-extractor", defaultPhase = LifecyclePhase.COMPILE)
public class TestExtractor extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter
    private FileSet fileset;

    @Parameter(defaultValue = "http://10.91.59.80:8070")
    private String httpTargetURI;


    public void execute() throws MojoExecutionException, MojoFailureException {

        FileSetManager fileSetManager = new FileSetManager();
        String[] includedFiles = fileSetManager.getIncludedFiles(fileset != null ? fileset : defaultSet());

        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        obj.put("Project", project.getId());
        obj.put("Version", project.getVersion());
        for (String s : includedFiles) {
            arr.add(ObjectBuilder.buildFeature(s).JSONize());
        }
        obj.put("Features", arr);

        HTTPPost.post(obj, httpTargetURI);
    }
}



