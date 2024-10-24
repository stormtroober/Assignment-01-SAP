package layered.model;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;


@AnalyzeClasses(packages = "sap.ass01.layered")
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Presentation").definedBy("sap.ass01.layered.presentation..")
            .layer("Business").definedBy( "sap.ass01.layered.services..", "sap.ass01.layered.domain..")
            .layer("Persistence").definedBy("sap.ass01.layered.persistence..")
            .layer("Database").definedBy("sap.ass01.layered.database..", "sap.ass01.layered.config..")

            // Ensuring that no layer accesses the Presentation layer
            .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()

            // Business layer can only be accessed by Presentation and Plugin layers
            .whereLayer("Business").mayOnlyBeAccessedByLayers("Presentation")

            // Persistence layer can only be accessed by Business and Plugin layers
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Business")
            // Infrastructure layer can only be accessed by the Persistence layer
            .whereLayer("Database").mayOnlyBeAccessedByLayers("Persistence");

    @ArchTest
    public static final ArchRule no_cycles = slices()
            .matching("sap.ass01.layered.(*)..")
            .should().beFreeOfCycles();
}
