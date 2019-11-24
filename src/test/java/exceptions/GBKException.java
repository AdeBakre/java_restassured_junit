package exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;

/**
 * General purpose exception , when framework catch some exception but don't have special type for it than throw this one
 */

public class GBKException extends RuntimeException {

    public GBKException(Exception e, Class clazz) {
        super(e);
        if (!(e instanceof GBKException)) {
            LogManager.getLogger(clazz.getSimpleName()).error(ExceptionUtils.getMessage(e));
        }
    }

    public GBKException(String errorMsg, Class clazz) {
        super(errorMsg);
        LogManager.getLogger(clazz.getSimpleName()).error(errorMsg);
    }

}
