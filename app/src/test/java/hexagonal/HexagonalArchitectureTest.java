package hexagonal;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class HexagonalArchitectureTest {
    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("sap.ass01.hexagonal");

    @Test
    void domainLayerShouldNotDependOnApplicationLayer() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..application..");

        rule.check(importedClasses);
    }

    @Test
    void applicationLayerShouldNotDependOnAdapters() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..adapters..");

        rule.check(importedClasses);
    }

    @Test
    void adaptersShouldOnlyDependOnApplicationLayer() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage("..adapters..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("..application..", "java..", "..io..", "..infrastructure..", "..ports..");

        rule.check(importedClasses);
    }

    @Test
    void applicationLayerShouldOnlyDependOnDomainLayer() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage("..application..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("..domain..", "java..","..io..", "..ports..");

        rule.check(importedClasses);
    }

    @ArchTest
    public static final ArchRule no_cycles = slices()
            .matching("sap.ass01.hexagonal.(*)..")
            .should().beFreeOfCycles();
}
