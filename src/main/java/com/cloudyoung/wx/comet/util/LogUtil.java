package com.cloudyoung.wx.comet.util;

import java.io.Serializable;
import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil implements Serializable {

    private static final long serialVersionUID = 3993187523235317784L;

    private static final Logger LOGGER = LogManager.getLogger(LogUtil.class);

    public static void info(Logger logger, String message, Object... args) {
        try {
            logger.info(LogUtil.messageFormat(message, args));
        } catch (Exception e) {
        }
    }

    public static void warn(Logger logger, String message, Object... args) {
        try {
            logger.warn(LogUtil.messageFormat(message, args));
        } catch (Exception e) {
        }
    }

    public static void debug(Logger logger, String message, Object... args) {
        try {
            logger.debug(LogUtil.messageFormat(message, args));
        } catch (Exception e) {
        }
    }

    public static void error(Logger logger, Exception exception, String message, Object... args) {
        try {
            logger.error(LogUtil.messageFormat(message, args), exception);
        } catch (Exception e) {
        }
    }

    public static void error(Logger logger, String message, Object... args) {
        try {
            logger.error(LogUtil.messageFormat(message, args));
        } catch (Exception e) {
        }
    }

    public static void warn(Logger logger, Exception exception, String message, Object... args) {
        try {
            logger.warn(LogUtil.messageFormat(message, args), exception);
        } catch (Exception e) {
        }
    }

    /**
     * 格式化消息信息
     *
     * @param message
     * @param args
     * @return消息信息 x
     */
    private static String messageFormat(String message, Object... args) {
        try {
            if (StringUtils.isEmpty(message)) {
                return "";
            }
            String str = MessageFormat.format(message, args);
            return str;
        } catch (Exception e) {
            LOGGER.error("Log日志工具类messageFormat(String message,Object... args)发生异常:", e);
        }
        return "";
    }

}
