<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<s:if test="properties.DEV_MODE">
	<link href="<%=request.getContextPath()%>/css/jquery.treegrid.css"
		rel="stylesheet" type="text/css"></link>
	<script src="<%=request.getContextPath()%>/js/jquery.treegrid.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery.treegrid.bootstrap3.js"
		type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.scrolltable.js"
		type="text/javascript"></script>
</s:if>
<s:else>
	<link href="<%=request.getContextPath()%>/css/jquery.treegrid.css"
		rel="stylesheet" type="text/css"></link>
	<script src="<%=request.getContextPath()%>/js/jquery.treegrid.min.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery.treegrid.bootstrap3.js"
		type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.scrolltable.js"
		type="text/javascript"></script>
</s:else>

<script type="text/javascript">
	"use strict";

	(function($) {
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

				for (var j = 0; j < columns.length; ++j) {
					var column = columns[j];
					var $td = $("<td></td>");

					if (column.formatter != null) {
						var formatter = opts.formatters[column.formatter];
						$td.append(formatter(e, e[column.id], index));
					} else if (e[column.id] != null) {
						$td.append(e[column.id]);
					}

					$tr.append($td);
				}

				$tbody.append($tr);

				if (e.children && e.children.length > 0) {
					appendRows($this, e.children, opts, id, index);
				}
			});
		}

		var methods = {};

		methods.init = function(options) {
			var opts = jQuery.extend({}, $.fn._datagrid.defaults, options);
			var $this = $(this);
			var columns = [];

			if (opts.formatters.index == null) {
				opts.formatters.index = function(row, value, index) {
					return "&nbsp;&nbsp;" + index;
				};
			}

			$this.find("th").each(function(i, e) {
				columns.push(eval("({" + $(e).data("column") + "})"));
			});

			$this.scrolltable(opts);

			var $tbody = $this.children("tbody").find("tbody");

			$this.data("_datagrid", {
				opts : opts,
				columns : columns,
				$tbody : $tbody
			});

			methods.reload.apply(this);
		};

		methods.reload = function(params) {
			var $this = $(this);
			var opts = $this.data("_datagrid").opts;
			var $tbody = $this.data(namespace).$tbody;

			if (params != null) {
				opts.params = params;
			}

			$._ajax($.extend({}, opts, {
				success : function(result) {
					var data = opts.load(result);
					$tbody.empty();
					appendRows($this, data.rows, opts);

					if (opts.tree) {
						$this.treegrid(opts);
					}
				}
			}));
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
			formatters : {},
			commands : {},
			load : function(result) {
				return result.data;
			}
		};
	})(jQuery);

	
</script>