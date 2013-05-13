package com.palermotenis.commandline;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.StopWatch;

import com.google.common.collect.ImmutableList;
import com.palermotenis.commandline.services.StartupService;
import com.palermotenis.commandline.services.impl.StartupServiceImpl;

public abstract class AbstractCommandLine {

    protected Logger log = Logger.getLogger(getClass());

    // exit codes to use with runWithin(ApplicationContext spring)
    public static final int EXIT_ERROR = 3;
    public static final int EXIT_WARNING = 2;
    public static final int EXIT_VERSION = 1;
    public static final int EXIT_SUCCESS = 0;
    // exit codes used in this class
    public static final int EXIT_RUNTIME_EXCEPTION = -1;
    public static final int EXIT_EXCEPTION = -2;

    @Autowired
    private JpaTransactionManager jtaTransactionManager;

    private StartupService startupService;
    private TransactionStatus transaction;

    public final void run(String[] args) {
        final StopWatch watch = new StopWatch(getClass().getName());
        watch.start();

        startSpring(getApplicationName(), getConfigLocations());

        int exitCode = EXIT_SUCCESS;
        try {
            exitCode = runWithin(startupService.getApplicationContext());
        } catch (RuntimeException rte) {
            exitCode = handleRuntimeException(rte);
        } catch (Exception ex) {
            exitCode = handleException(ex);
        } finally {
            stopSpring();
            watch.stop();
            log.info(String.format("<%s> took %f s to run", this, watch.getTotalTimeSeconds()));
        }
        exit(exitCode);
    }

    protected void startSpring(String applicationName, String... springConfigFileNames) {
        startupService = new StartupServiceImpl();
        startupService.init(applicationName, springConfigFileNames);
        // enable autowiring for this object
        getContext().getAutowireCapableBeanFactory().autowireBeanProperties(this,
            AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
    }

    protected void stopSpring() {
        startupService.destroy();
        startupService = null;
    }

    protected abstract int runWithin(ApplicationContext springContext);

    protected String[] getConfigLocations() {
        return new String[]
            { "spring/applicationContext-business.xml" };
    }

    protected String getApplicationName() {
        return "palermotenis";
    }

    public ApplicationContext getContext() {
        return startupService.getApplicationContext();
    }

    protected void exit(int exitCode) {
        log.info(String.format("<%s> exiting with code %d", this, exitCode));
        System.exit(exitCode);
    }

    protected int handleException(Exception exception) {
        log.error("unhandled exception: " + exception, exception);
        return EXIT_EXCEPTION;
    }

    protected int handleRuntimeException(RuntimeException rte) {
        log.error("unhandled runtime exception: " + rte, rte);
        return EXIT_RUNTIME_EXCEPTION;
    }

    protected TransactionStatus getTransaction() {
        return transaction;
    }

    protected void startTransaction(int attribute) {
        List<RollbackRuleAttribute> rollbackRules = new ImmutableList.Builder<RollbackRuleAttribute>()
            .add(new NoRollbackRuleAttribute(NoResultException.class))
            .add(new NoRollbackRuleAttribute(EntityNotFoundException.class))
            .build();
        TransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute(attribute, rollbackRules);
        transaction = jtaTransactionManager.getTransaction(transactionAttribute);
    }

    protected void startTransaction() {
        startTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    protected void commitTransaction() {
        jtaTransactionManager.commit(transaction);
    }

    protected void rollBackTransaction() {
        jtaTransactionManager.rollback(transaction);
    }

}
