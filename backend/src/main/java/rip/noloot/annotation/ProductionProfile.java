package rip.noloot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

import rip.noloot.util.AppConstants;

@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Profile(AppConstants.PRODUCTION_PROFILE)
public @interface ProductionProfile {}