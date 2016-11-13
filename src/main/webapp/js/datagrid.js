"use strict";

(function($) {
	var namespace = "_datagrid";
	var checkboxClass = "treegrid-checkbox";

	function appendRows($this, rows, parentId, parentIndex) {
		var opts = $this.data(namespace).opts;
		var columns = $this.data(namespace).columns;
		var $tbody = $this.data(namespace).$tbody;

		for (var i = 0; i < rows.length; ++i) {
			var row = rows[i];
			var id = row[opts.id];
			var index = parentIndex ? parentIndex + "-" + (i + 1) : (i + 1);

			var $tr = $("<tr></tr>");
			$tr.addClass("treegrid-" + id);
			$tr.data(namespace, row);

			if (parentId != null) {
				$tr.addClass("treegrid-parent-" + parentId);
			}

			if (row.trClass != null) {
				$tr.addClass(row.trClass);
			}

			if (row.trStyle != null) {
				$tr.attr("style", row.trStyle);
			}

			for (var j = 0; j < columns.length; ++j) {
				var column = columns[j];
				var $td = $("<td></td>");

				if (column.width != null) {
					$td.css("width", column.width);
				}

				if (column.formatter != null) {
					var formatter = opts.formatters[column.formatter];
					$td.append(formatter(row, row[column.id], index));
				} else if (row[column.id] != null) {
					$td.append(row[column.id]);
				}

				$tr.append($td);
			}

			$tr.click(function() {
				opts.clickRow.call($this, row, index);
			});

			$tbody.append($tr);

			if (row.children && row.children.length > 0) {
				appendRows($this, row.children, id, index);
			}
		}
	}

	var methods = {};

	methods.init = function(options) {
		var opts = $.extend(true, {}, $.fn._datagrid.defaults, options);
		var $this = $(this);
		var columns = [];

		var $div = $("<div></div>");
		var $table = $("<table></table>");
		var $tbody = $("<tbody></tbody>");
		var $pagination = $("<div></div>");

		if (opts.formatters.index == null) {
			opts.formatters.index = function(row, value, index) {
				return "&nbsp;&nbsp;" + index;
			};
		}

		if (opts.formatters.checkbox == null) {
			opts.formatters.checkbox = function(row, value, index) {
				var $checkbox = $("<input type='checkbox' />");
				$checkbox.addClass(checkboxClass);
				return $checkbox;
			};
		}

		$this.find("th").each(function(i, e) {
			var $e = $(e);
			var column = eval("({" + $e.data("column") + "})");

			if (column.width != null) {
				$e.css("width", column.width);
			}

			if (column.formatter == "checkbox") {
				var $checkbox = $("<input type='checkbox' />");

				$checkbox.click(function() {
					$tbody.find("." + checkboxClass).prop("checked", $checkbox.prop("checked"));
				});

				$tbody.on("change", "." + checkboxClass, function() {
					$checkbox.prop("checked", $tbody.find("." + checkboxClass).not(":checked").size() == 0);

					var $current = $(this).closest("tr");
					var $parent = $current.treegrid("getParentNode");

					while ($parent != null) {
						var $checkboxs = $parent.treegrid("getChildNodes").find("." + checkboxClass);
						$parent.find("." + checkboxClass).prop("checked", $checkboxs.not(":checked").size() == 0);
						$parent = $parent.treegrid("getParentNode");
					}

					var $next = $current.next();
					var depth = $current.treegrid("getDepth");
					var checked = $(this).prop("checked");

					while ($next.size() > 0 && depth < $next.treegrid("getDepth")) {
						$next.find("." + checkboxClass).prop("checked", checked);
						$next = $next.next();
					}
				});

				$e.append($checkbox);
			}

			columns.push(column);
		});

		$div.append($table);
		$div.css("overflow-y", "auto");

		$table.append($tbody);
		$table.attr("class", $this.attr("class"));

		$this.after($div);
		$this.css("margin-bottom", "-1px");
		$div.after($pagination);

		$this.data(namespace, {
			opts : opts,
			columns : columns,
			$tbody : $tbody,
			$pagination : $pagination
		});

		if (opts.pagination && !opts.tree) {
			$pagination.bootpag({
				total : 1,
				page : 1,
				maxVisible : 1
			}).on('page', function(event, num) {
				methods.reload.apply($this, [ null, num ]);
			});
		}

		if (opts.height != null) {
			$div.css("height", opts.height);
		} else {
			$div._autoHeight();
		}

		methods.reload.apply(this);
	};

	methods.reload = function(params, pageStart, pageSize) {
		var $this = $(this);
		var opts = $this.data(namespace).opts;
		var columns = $this.data(namespace).columns;
		var $tbody = $this.data(namespace).$tbody;
		var $pagination = $this.data(namespace).$pagination;

		$.extend(opts.params, params);

		if (pageStart) {
			opts.pageStart = pageStart;
		}

		if (pageSize) {
			opts.pageSize = pageSize;
		}

		if (opts.pagination && !opts.tree) {
			opts.params.start = (opts.pageStart - 1) * opts.pageSize;
			opts.params.length = opts.pageSize;
		}

		$._ajax($.extend({}, opts, {
			success : function(result) {
				var data = opts.load(result);

				$tbody.empty();
				appendRows($this, data.rows);

				if ($tbody.html() == "") {
					$tbody.append($("<tr><td colspan=" + (columns.length - 1) + ">&nbsp;<td/></tr>"));
				}

				if (opts.tree) {
					$tbody.parent().treegrid(opts);
				} else if (opts.pagination) {
					var total = parseInt(data.count / opts.pageSize) + 1;

					$pagination.bootpag({
						total : total,
						page : opts.pageStart,
						maxVisible : 10
					});
				}
			}
		}));
	};

	methods.getChecked = function() {
		var $this = $(this);
		var $tbody = $this.data(namespace).$tbody;

		return $tbody.find("." + checkboxClass + ":checked").map(function(i, e) {
			return $(e).closest("tr").data(namespace);
		}).get();
	};

	$.fn._datagrid = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === "object" || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$(this).treegrid.apply(this, arguments);
		}
	};

	$.fn._datagrid.defaults = {
		id : "id",
		tree : false,
		saveState : true,
		treeColumn : 1,
		formatters : {},
		pagination : true,
		pageStart : 1,
		pageSize : 10,
		load : function(result) {
			return result.data;
		},
		clickRow : function() {
		}
	};
})(jQuery);