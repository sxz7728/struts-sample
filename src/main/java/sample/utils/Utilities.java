package sample.utils;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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

	@SuppressWarnings("unchecked")
	public static <T> List<T> getValues(Collection<?> rows, String name) {
		List<T> values = Lists.newArrayList();

		try {
			for (Object row : rows) {
				Object value = PropertyUtils.getProperty(row, name);

				if (value != null) {
					values.add((T) value);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return values;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getDistinctValues(Collection<?> rows, String name) {
		Set<T> values = Sets.newHashSet();

		try {
			for (Object row : rows) {
				Object value = PropertyUtils.getProperty(row, name);

				if (value != null) {
					values.add((T) value);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return Lists.newArrayList(values);
	}
}
