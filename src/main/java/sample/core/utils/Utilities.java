package sample.core.utils;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

public class Utilities {
	public static String format(String str, Object... args) {
		return MessageFormat.format(str.replaceAll("'", "''"), args);
	}

	public static boolean isValidId(Integer id) {
		return id != null && id > 0;
	}

	public static boolean getYesNo(String str) {
		return StringUtils.equals(str, DictUtils.YES);
	}

	public static String getYesNo(boolean b) {
		return b ? DictUtils.YES : DictUtils.NO;
	}

}
