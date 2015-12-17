package sample.core.utils;

import java.util.List;
import java.util.Map;

public class Datagrid {
	private List<Map<String, ?>> rows;

	private Integer count;

	public List<Map<String, ?>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, ?>> rows) {
		this.rows = rows;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
