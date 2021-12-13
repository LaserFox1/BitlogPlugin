import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.model.fileset.FileSet;
import org.apache.maven.shared.model.fileset.util.FileSetManager;
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
        String[] includedFiles = fileSetManager.getIncludedFiles( fileset );
        File myObj = new File("output.txt");
        try {
            if(!myObj.createNewFile()){
                FileWriter fwOb = new FileWriter("output.txt", false);
                PrintWriter pwOb = new PrintWriter(fwOb, false);
                pwOb.flush();
                pwOb.close();
                fwOb.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String s : includedFiles){
            ArrayList<String> arr = Reader.read("src/test/resources/features/"+s);
            for(String s1 : arr){
                Writer.write(s1);
                Writer.write("\n");
            }
            Writer.write("\n");
        }
    }
}
