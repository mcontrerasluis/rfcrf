package sat.gob.mx.agsc;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("sat.gob.mx.agsc");

        noClasses()
            .that()
                .resideInAnyPackage("sat.gob.mx.agsc.service..")
            .or()
                .resideInAnyPackage("sat.gob.mx.agsc.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..sat.gob.mx.agsc.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
