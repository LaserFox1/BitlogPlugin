package tools;

import classes.Feature;
import classes.Scenario;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObjectBuilderTest {
    @Test
    public void ensureBuildFeatureCreatesValidFeature(){
        Feature f1 = ObjectBuilder.buildFeature("test1.feature");
        assertFalse(f1.getFeatureName().isEmpty());
        assertFalse(f1.getFeatureName().isBlank());
        assertFalse(f1.getDescription().isEmpty());
        assertFalse(f1.getDescription().isBlank());
        assertNotNull(f1.getScenarios());
        assertFalse(f1.getScenarios().isEmpty());

        for(Scenario s1 : f1.getScenarios()){
            assertFalse(s1.getScenarioName().isBlank());
            assertFalse(s1.getScenarioName().isEmpty());
            assertFalse(s1.getGiven().isBlank());
            assertFalse(s1.getGiven().isEmpty());
            assertFalse(s1.getWhen().isBlank());
            assertFalse(s1.getWhen().isEmpty());
            assertFalse(s1.getThen().isBlank());
            assertFalse(s1.getThen().isEmpty());

        }
    }
}
