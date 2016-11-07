"use strict";

(function($) {
	var namespace = "_datagrid";

	function appendRows($this, rows, parentId, parentIndex) {
		var opts = $this.data(namespace).opts;
		var columns = $this.data(namespace).columns;
		var $tbody = $this.data(namespace).$tbody;

		$.each(rows, function(i, e) {
			var id = e[opts.id];
			var index = parentIndex ? parentIndex + "-" + (i + 1) : (i + 1);

			var $tr = $("<tr></tr>");
			$tr.addClass("treegrid-" + id);
			$tr.data(namespace, e);

			if (parentId != null) {
				$tr.addClass("treegrid-parent-" + parentId);
			}

			if (e.trClass != null) {
				$tr.addClass(e.trClass);
			}

			if (e.trStyle != null) {
				$tr.attr("style", e.trStyle);
			}

			for (var j = 0; j < columns.length; ++j) {
				var column = columns[j];
				var $td = $("<td></td>");

				if (column.width != null) {
					$td.css("width", column.width);
				}

				if (column.formatter != null) {
					var formatter = opts.formatters[column.formatter];
					$td.append(formatter(e, e[column.id], index));
				} else if (e[column.id] != null) {
					$td.append(e[column.id]);
				}

				$tr.append($td);
			}

			$tr.click(function() {
				opts.clickRow.call($this, e, index);
			});

			$tbody.append($tr);

			if (e.children && e.children.length > 0) {
				appendRows($this, e.children, id, index);
			}
		});
	}

	var methods = {};

	methods.init = function(options) {
		var opts = $.extend(true, {}, $.fn._datagrid.defaults, options);
		var $this = $(this);
		var columns = [];

		var $div = $("<div></div>");
		var $table = $("<table></table>");
		var $tbody = $("<tbody></tbody>");

		if (opts.formatters.index == null) {
			opts.formatters.index = function(row, value, index) {
				return "&nbsp;&nbsp;" + index;
			};
		}

		if (opts.formatters.checkbox == null) {
			opts.formatters.checkbox = function(row, value, index) {
				var $checkbox = $("<input type='checkbox' data-id='{0}' />".format(row[opts.idColumn]));
				$checkbox.addClass("checkbox");
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
					$tbody.find(".checkbox").prop("checked", $checkbox.prop("checked"));
				});

				$tbody.on("change", ".checkbox", function() {
					$checkbox.prop("checked", $tbody.find(".checkbox").not(":checked").size() == 0);

					var $current = $(this).closest("tr");
					var $parent = $current.treegrid("getParentNode");
					var $next = $current.next();
					var depth = $current.treegrid("getDepth");
					var checked = $(this).prop("checked");

					while ($parent != null) {
						var $checkboxs = $parent.treegrid("getChildNodes").find(".checkbox");
						$parent.find(".checkbox").prop("checked", $checkboxs.not(":checked").size() == 0);
						$parent = $parent.treegrid("getParentNode");
					}

					while ($next.size() > 0 && depth < $next.treegrid("getDepth")) {
						$next.find(".checkbox").prop("checked", checked);
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
		$this.data(namespace, {
			opts : opts,
			columns : columns,
			$tbody : $tbody
		});

		if (opts.height != null) {
			$div.css("height", opts.height);
		} else {
			$div._autoHeight();
		}

		methods.reload.apply(this);
	};

	methods.reload = function(params) {
		var $this = $(this);
		var opts = $this.data(namespace).opts;
		var $tbody = $this.data(namespace).$tbody;

		$.extend(opts.params, params);

		$._ajax($.extend({}, opts, {
			success : function(result) {
				var data = opts.load(result);

				$tbody.empty();
				appendRows($this, data.rows);

				if (opts.tree) {
					$tbody.parent().treegrid(opts);
				}
			}
		}));
	};

	methods.getChecked = function(callback) {
		var $this = $(this);
		var $tbody = $this.data(namespace).$tbody;

		return $tbody.find(".checkbox:checked").map(function(i, e) {
			if (callback) {
				return callback($(e).closest("tr").data(namespace));
			} else {
				return $(e).closest("tr").data(namespace);
			}
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
		load : function(result) {
			return result.data;
		},
		clickRow : function() {
		}
	};
})(jQuery);