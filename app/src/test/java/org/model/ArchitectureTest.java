package org.model;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;


@AnalyzeClasses(packages = "sap.ass01.layered")
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Presentation").definedBy("sap.ass01.layered.ui..")
            .layer("Business").definedBy("sap.ass01.layered.domain..", "sap.ass01.layered.services..")
            .layer("Persistence").definedBy("sap.ass01.layered.persistence..")
            .layer("Plugin").definedBy("sap.ass01.layered.plugin..")  // Plugin layer

            // Ensuring that no layer accesses the Presentation layer
            .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()

            // Business layer can only be accessed by Presentation and Plugin layers
            .whereLayer("Business").mayOnlyBeAccessedByLayers("Presentation", "Plugin")

            // Persistence layer can only be accessed by Business and Plugin layers
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Business", "Plugin")

            // Plugin layer can be accessed by Presentation layer for dynamic UI behavior, if needed
            .whereLayer("Plugin").mayOnlyBeAccessedByLayers("Business", "Presentation");


}
