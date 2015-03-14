package sample.core.utils;

import org.apache.commons.lang.StringUtils;

public class ColumnUtils {
	public static final String COLUMN = " {0} as {1} ";

	public static final String DICT_VALUE = " (select t1.dictValue from SysDict t1 where t1.dictKey = {0} and t1.dictType = '{1}') as {2} ";

	public static final String USER_NAME = " (select t1.userName from SysUser t1 where t1.id = {0}) as {1} ";

	public static String column(String name) {
		int index = StringUtils.indexOf(name, ".");
		String alias = index != -1 ? StringUtils.substring(name, index) : name;
		return Utilities.format(COLUMN, name, alias);
	}

	public static String column(String name, String alias) {
		return Utilities.format(COLUMN, name, alias);
	}

	public static String dictValue(String name, String dictType) {
		int index = StringUtils.indexOf(name, ".");
		String alias = (index != -1 ? StringUtils.substring(name, index) : name)
				+ "Name";
		return Utilities.format(DICT_VALUE, name, dictType, alias);
	}

	public static String dictValue(String name, String alias, String dictType) {
		return Utilities.format(DICT_VALUE, name, dictType, alias);
	}

	public static String userName(String name, String alias) {
		return Utilities.format(USER_NAME, name, alias);
	}

}
