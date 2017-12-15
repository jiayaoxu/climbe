package cn.newcode.climb.LogUtil;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MLogger {
	public static Logger log=(Logger) LogManager.getLogger(MLogger.class);
	public static void info(String msg) {
		log.info(msg);
	}
	public static void warn(String msg) {
		log.warn(msg);
	}
	public static void error(Throwable e) {
		log.error("", e);

	}
}
