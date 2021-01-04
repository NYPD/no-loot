package rip.noloot.annotation;

import org.springframework.context.annotation.Profile;

import rip.noloot.util.AppConstants;

@Profile(AppConstants.TEST_PROFILE)
public @interface TestProfile {}