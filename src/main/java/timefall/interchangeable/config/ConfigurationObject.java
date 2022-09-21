package timefall.interchangeable.config;

import java.util.Map;

public class ConfigurationObject {
    private final String[][] EquivalenceClasses;
    private final Map<String, String[]> Substitutions;
    private final Map<String, String[]> RecipeSpecificEquivalenceClasses;

    public ConfigurationObject(String[][] equivalenceClasses, Map<String, String[]> substitutions, Map<String, String[]> recipeSpecificEquivalenceClasses) {
        this.EquivalenceClasses = equivalenceClasses;
        this.Substitutions = substitutions;
        this.RecipeSpecificEquivalenceClasses = recipeSpecificEquivalenceClasses;
    }

    public String[][] getEquivalenceClasses() {
        return EquivalenceClasses;
    }

    public Map<String, String[]> getSubstitutions() {
        return Substitutions;
    }

    public Map<String, String[]> getRecipeSpecificEquivalenceClasses() {
        return RecipeSpecificEquivalenceClasses;
    }
}
