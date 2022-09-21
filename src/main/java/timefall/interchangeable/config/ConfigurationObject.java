package timefall.interchangeable.config;

public class ConfigurationObject {
    public String[][] EquivalenceClasses;

    public ConfigurationObject(String[][] equivalenceClasses) {
        this.EquivalenceClasses = equivalenceClasses;
    }

    public String[][] getEquivalenceClasses() {
        return EquivalenceClasses;
    }
}
