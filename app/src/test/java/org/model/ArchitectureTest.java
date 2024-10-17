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
            .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
            .whereLayer("Business").mayOnlyBeAccessedByLayers("Presentation")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Business");


}
