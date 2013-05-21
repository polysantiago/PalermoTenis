package com.palermotenis.commandline.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.palermotenis.commandline.exceptions.InitializationException;
import com.palermotenis.commandline.services.StartupService;
import com.palermotenis.util.StackTraceUtils;

public class StartupServiceImpl implements StartupService {

    private static final Logger logger = Logger.getLogger(StartupServiceImpl.class);

    private AbstractApplicationContext context;

    @Override
    public void init(String applicationName, String... springConfigFileNames) {
        try {
            initLogging(applicationName);
            initSpring(applicationName, springConfigFileNames);
        } catch (InitializationException ex) {
            String error = "can't init application " + applicationName + ", reason: " + ex;
            String fullError = error + ", StackTrace: " + StackTraceUtils.toString(ex);
            System.err.println("init() - " + fullError);
            throw ex;
        } catch (Exception ex) {
            String error = "can't init application " + applicationName + ", reason: " + ex;
            String fullError = error + ", StackTrace: " + StackTraceUtils.toString(ex);
            System.err.println("init() - " + fullError);
            throw new InitializationException(error, ex);
        }

    }

    private void initSpring(String applicationName, String[] springConfigFileNames) {
        // Filter out non existing config files
        List<String> existingConfigFileNames = new ArrayList<String>();
        for (String configFile : springConfigFileNames) {
            if (exists(configFile)) {
                existingConfigFileNames.add(configFile);
            } else {
                logger.warn("initSpring() - can't find config file " + configFile + " inside the classpath");
            }
        }

        if (existingConfigFileNames.isEmpty()) {
            throw new InitializationException("no spring config files found!");
        }

        String[] configFilesArray = existingConfigFileNames.toArray(new String[existingConfigFileNames.size()]);
        context = new ClassPathXmlApplicationContext(configFilesArray);
    }

    private boolean exists(String configFile) {
        ClassPathResource classPathResource = new ClassPathResource(configFile);
        try {
            return classPathResource.getInputStream() != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void initLogging(String applicationName) {
        BasicConfigurator.configure();
    }

    @Override
    public void destroy() {
        if (context != null) {
            context.destroy();
        }
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return context;
    }

}
