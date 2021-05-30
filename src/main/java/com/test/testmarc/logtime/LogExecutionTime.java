package com.test.testmarc.logtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation permettant d'afficher le temps de process, le démarrage et l'arret des méthodes annotées,
 * pour ce test on annote uniquement les méthodes du service utilisateur, ce comportement peux être étendu au reste de l'application
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {
}
