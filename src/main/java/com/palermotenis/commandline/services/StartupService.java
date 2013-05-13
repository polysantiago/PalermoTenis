package com.palermotenis.commandline.services;

import org.springframework.context.ApplicationContext;

public interface StartupService {

    void init(String applicationName, String... springConfigFileNames);

    void destroy();

    ApplicationContext getApplicationContext();

}
