package com.caelcs;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class DependencyRuleTest {

  private static final String ROOT_PACKAGE = "com.caelcs";
  private static final String MODEL_PACKAGE = "model";
  private static final String APPLICATION_PACKAGE = "application";
  private static final String PORT_PACKAGE = "application.port";
  private static final String SERVICE_PACKAGE = "application.service";
  private static final String APPLICATION_DTO_PACKAGE = "application.dto";

  private static final String ADAPTER_PACKAGE = "adapter";
  private static final String BOOTSTRAP_PACKAGE = "bootstrap";

  @Test
  void checkDependencyRule() {
    String importPackages = ROOT_PACKAGE + "..";
    JavaClasses classesToCheck = new ClassFileImporter().importPackages(importPackages);

    checkNoDependencyFromTo(MODEL_PACKAGE, APPLICATION_PACKAGE, classesToCheck);
    checkNoDependencyFromTo(MODEL_PACKAGE, ADAPTER_PACKAGE, classesToCheck);
    checkNoDependencyFromTo(MODEL_PACKAGE, BOOTSTRAP_PACKAGE, classesToCheck);
    checkNoDependencyFromTo(MODEL_PACKAGE, APPLICATION_DTO_PACKAGE, classesToCheck);

    checkNoDependencyFromTo(APPLICATION_PACKAGE, ADAPTER_PACKAGE, classesToCheck);
    checkNoDependencyFromTo(APPLICATION_PACKAGE, BOOTSTRAP_PACKAGE, classesToCheck);

    checkNoDependencyFromTo(PORT_PACKAGE, SERVICE_PACKAGE, classesToCheck);

    checkNoDependencyFromTo(ADAPTER_PACKAGE, SERVICE_PACKAGE, classesToCheck);
    checkNoDependencyFromTo(ADAPTER_PACKAGE, BOOTSTRAP_PACKAGE, classesToCheck);

    checkNoDependencyFromTo(APPLICATION_DTO_PACKAGE, ADAPTER_PACKAGE, classesToCheck);
  }

  private void checkNoDependencyFromTo(
      String fromPackage, String toPackage, JavaClasses classesToCheck) {
    noClasses()
        .that()
        .resideInAPackage(fullyQualified(fromPackage))
        .should()
        .dependOnClassesThat()
        .resideInAPackage(fullyQualified(toPackage))
        .check(classesToCheck);
  }

  private String fullyQualified(String packageName) {
    return ROOT_PACKAGE + '.' + packageName + "..";
  }
}
