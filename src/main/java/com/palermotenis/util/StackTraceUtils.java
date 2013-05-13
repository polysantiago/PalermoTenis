package com.palermotenis.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Utilities for creating and converting stack traces
 */
@SuppressWarnings("unchecked")
public abstract class StackTraceUtils {

    private static Class<? extends Exception> SERVLET_EXCEPTION = null;

    static {
        // support for javax.servlet.ServletException depends on availability
        try {
            SERVLET_EXCEPTION = (Class<? extends Exception>) Class.forName("javax.servlet.ServletException");
        } catch (Throwable t) {
            SERVLET_EXCEPTION = null;
        }
    }

    /**
     * Returns the Stacktrace of a Throwable.
     * 
     * @param ex
     *            a Throwable
     * @return Stacktrace
     */
    public static String toString(Throwable ex) {
        return getStackTrace(ex);
    }

    /**
     * Returns the Stacktrace of a Throwable.
     * 
     * @param throwable
     *            a Throwable
     * @return Stacktrace
     */
    public static String getStackTrace(Throwable throwable) {
        if (throwable == null) {
            return "[no throwable == no stacktrace]";
        }
        if (throwable instanceof SQLException) {
            return getStackTrace((SQLException) throwable);
        }
        if (throwable instanceof InvocationTargetException) {
            return getStackTrace((InvocationTargetException) throwable);
        }
        if (SERVLET_EXCEPTION != null && SERVLET_EXCEPTION.isAssignableFrom(throwable.getClass())) {
            return getStackTraceFromServletException(throwable);
        }
        return printStackTrace(throwable);
    }

    /**
     * Returns the Stacktrace of an <code>InvocationTargetException</code> That means also the Stacktrace of a potential
     * toggled Exception.
     * 
     * @param exception
     *            an InvocationTargetException
     * @return Stacktrace
     */
    public static String getStackTrace(InvocationTargetException exception) {
        if (exception == null) {
            return "[no exception == no stacktrace]";
        }
        StringBuffer theTrace = new StringBuffer();
        theTrace.append(printStackTrace(exception));
        if (exception.getTargetException() != null) {
            return theTrace.append(getStackTrace(exception.getTargetException())).toString();
        }
        return theTrace.toString();
    }

    /**
     * Returns the Stacktrace of a <code>SQLException</code> That means also the Stacktrace of a potential toggled
     * Exception.
     * 
     * @param sqlException
     *            an SQLException
     * @return Stacktrace
     */
    public static String getStackTrace(SQLException sqlException) {
        if (sqlException == null) {
            return "[no exception == no stacktrace]";
        }
        StringBuffer theTrace = new StringBuffer();
        theTrace.append(printStackTrace(sqlException));
        if (sqlException.getNextException() != null) {
            return theTrace.append(getStackTrace(sqlException.getNextException())).toString();
        }
        return theTrace.toString();
    }

    /**
     * Returns the Stacktrace of a <code>ServletException</code> That means also the Stacktrace of a potential toggled
     * Exception.
     * 
     * @param servletException
     *            a ServletException
     * @return Stacktrace
     */
    private static String getStackTraceFromServletException(Throwable servletException) {
        if (servletException == null) {
            return "[no exception == no stacktrace]";
        }
        StringBuffer theTrace = new StringBuffer();
        theTrace.append(printStackTrace(servletException));
        Throwable cause = ExceptionUtils.getRootCause(servletException);
        if (cause != null) {
            return theTrace.append(getStackTrace(cause)).toString();
        }
        return theTrace.toString();
    }

    /**
     * Returns the Stacktrace of a <code>Throwables</code> without the toggled Exception.
     * 
     * @param throwable
     *            a Throwable
     * @return Stacktrace
     */
    public static String printStackTrace(Throwable throwable) {
        if (throwable != null) {
            ByteArrayOutputStream theStream = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintWriter(theStream, true));
            return theStream.toString();
        }
        return "";
    }

    /**
     * @return the trace of the current stack
     */
    public static String getCurrentStackTrace() {
        try {
            throw new Exception("problem locator");
        } catch (Exception ex) {
            return printStackTrace(ex);
        }
    }

    /**
     * Gets the stack trace for the specified thread
     * 
     * @param thread
     *            the thread to analyze
     * @return the trace of the threads stack
     */
    public static String getStackTrace(Thread thread) {
        StringBuilder stringBuilder = new StringBuilder();
        if (thread == null) {
            stringBuilder.append("[no thread given]");
        } else {
            stringBuilder.append("Thread=\"");
            stringBuilder.append(thread.getName());
            stringBuilder.append(" \"in threadGroup=\"");
            stringBuilder.append(thread.getThreadGroup().getName());
            stringBuilder.append("\" state=");
            stringBuilder.append(thread.getState().name());
            stringBuilder.append(" alive=");
            stringBuilder.append(thread.isAlive());
            stringBuilder.append(" deamon=");
            stringBuilder.append(thread.isDaemon());
            stringBuilder.append(" interrupted=");
            stringBuilder.append(thread.isInterrupted());
            stringBuilder.append(" id=");
            stringBuilder.append(thread.getId());
            stringBuilder.append(" priority=");
            stringBuilder.append(thread.getPriority());

            stringBuilder.append("\nStacktrace:\n");

            StackTraceElement[] stackTraceElements = thread.getStackTrace();
            if (stackTraceElements != null) {
                if (stackTraceElements.length > 0) {
                    for (StackTraceElement s : stackTraceElements) {
                        stringBuilder.append("\t");
                        stringBuilder.append(s.toString());
                        stringBuilder.append("\n");
                    }
                } else {
                    stringBuilder.append("\tempty stacktrace");
                }
            } else {
                stringBuilder.append("\tnot available");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Takes a given hierarchy of Throwables and converts them to the specified type. This is useful if e.g. exceptions
     * need to be serialized to a client which does not have the correct exception classes.
     * 
     * @param t
     *            the throwable to convert. If it has 'cause' exceptions, these are converted as well
     * @param targetClass
     *            the new subclass of Throwable the exceptions should be converted to. The class must have a constructor
     *            which takes a string and a throwable.
     * @return the converted throwable
     * @throws SystemException
     *             if the conversion failed. In that case, the original exception is added as a cause of this exception.
     */
    public static <T extends Throwable> T convertThrowable(Throwable t, Class<T> targetClass) {
        try {
            Constructor<T> constructor = targetClass.getConstructor(String.class, Throwable.class);
            return (T) convertThrowable(t, constructor, 0);
        } catch (Exception e) {
            throw new RuntimeException("Could not convert exception due to \"" + e.getMessage()
                    + "\". Setting original exception as cause.", t);
        }
    }

    private static final int MAX_EXCEPTION_DEPTH = 10;
    private static final String CONVERSION_HINT = " [converted from %s]";

    private static <T extends Throwable> Throwable convertThrowable(Throwable source, Constructor<T> constructor,
            int currentDepth) throws IllegalArgumentException, InstantiationException, IllegalAccessException,
            InvocationTargetException {
        if (currentDepth >= MAX_EXCEPTION_DEPTH) {
            return null;
        }
        Throwable causeCopy = null;
        Throwable cause = ExceptionUtils.getCause(source);
        if (cause != null) {
            causeCopy = convertThrowable(cause, constructor, currentDepth + 1);
        }
        String message = source.getMessage() + String.format(CONVERSION_HINT, source.getClass().getName());
        Throwable result = constructor.newInstance(message, causeCopy);
        result.setStackTrace(source.getStackTrace());
        return result;
    }
}
