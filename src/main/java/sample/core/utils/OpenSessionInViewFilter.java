package sample.core.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class OpenSessionInViewFilter extends org.springframework.orm.hibernate4.support.OpenSessionInViewFilter {

	private static final String INCLUDE_SUFFIXS = "includeSuffixs";

	private static String[] includeSuffixs = {};

	@Override
	protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		String extension = FilenameUtils.getExtension(path);

		if (StringUtils.isEmpty(extension)) {
			return false;
		}

		for (String suffix : includeSuffixs) {
			if (StringUtils.equals(suffix, extension))
				return false;
		}

		return true;
	}

	@Override
	protected void initFilterBean() throws ServletException {
		String includeSuffixStr = getFilterConfig().getInitParameter(INCLUDE_SUFFIXS);

		if (!StringUtils.isEmpty(includeSuffixStr)) {
			includeSuffixs = includeSuffixStr.split(",");
		}
	}
}
