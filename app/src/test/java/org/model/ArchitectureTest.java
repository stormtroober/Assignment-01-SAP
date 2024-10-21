package org.model;

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
            .layer("Presentation").definedBy("sap.ass01.layered.ui..")
            .layer("Business").definedBy( "sap.ass01.layered.services..")
            .layer("Domain").definedBy("sap.ass01.layered.domain..")
            .layer("Persistence").definedBy("sap.ass01.layered.persistence..")
            .layer("Database").definedBy("sap.ass01.layered.database..")
            .layer("Config").definedBy("sap.ass01.layered.config..")

            // Ensuring that no layer accesses the Presentation layer
            .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()

            // Business layer can only be accessed by Presentation and Plugin layers
            .whereLayer("Business").mayOnlyBeAccessedByLayers("Presentation")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Business")

            // Persistence layer can only be accessed by Business and Plugin layers
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Domain")
            // Infrastructure layer can only be accessed by the Persistence layer
            .whereLayer("Database").mayOnlyBeAccessedByLayers("Persistence")

            // Config layer can only be accessed by the Database layer
            .whereLayer("Config").mayOnlyBeAccessedByLayers("Database");

    @ArchTest
    public static final ArchRule no_cycles = slices()
            .matching("sap.ass01.layered.(*)..")
            .should().beFreeOfCycles();
}
