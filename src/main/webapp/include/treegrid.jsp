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
</s:if>
<s:else>
	<link href="<%=request.getContextPath()%>/css/jquery.treegrid.css"
		rel="stylesheet" type="text/css"></link>
	<script src="<%=request.getContextPath()%>/js/jquery.treegrid.min.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/js/jquery.treegrid.bootstrap3.js"
		type="text/javascript"></script>
</s:else>

<script type="text/javascript">
	(function($) {
		function appendRows(rows, columns, $tbody, opts, parentId) {
			$.each(rows, function(i, e) {
				var id = e[opts.idColumn];

				var $tr = $("<tr></tr>");
				$tr.addClass("treegrid-" + id);

				if (parentId != null) {
					$tr.addClass("treegrid-parent-" + parentId);
				}

				for (var j = 0; j < columns.length; ++j) {
					var column = columns;

					if (column.formatter) {
						$tr.append("<td>" + column.formatter(e, e[column.id])
								+ "</td>");
					} else if (e[column.id]) {
						$tr.append("<td>" + e[column.id] + "</td>");
					} else {
						$tr.append("<td></td>");
					}
				}

				$tbody.appand($tr);

				if (e.children && e.children.length > 0) {
					appendRows(e.children, columns, $tbody, id);
				}
			});
		}

		var methods = {};

		methods.init = function(options) {
			var opts = jQuery.extend({}, $.fn._bootgrid.defaults, options);
			var $this = $(this);
			var columns = [];

			$this.find("th").each(function(i, e) {
				columns.push(eval("({" + $(e).data("column") + "})"));
			});

			$this.data("_treegrid", {
				opts : opts,
				columns : columns
			});

			methods.reload.apply(this);
		};

		methods.reload = function() {
			var $this = $(this);
			var opts = $this.data("_treegrid").opts;
			var columns = $this.data("_treegrid").columns;

			$._ajax($.extend({}, opts, {
				success : function(result) {
					$tbody = $("<tbody></tbody>");

					appendRows(opts.load(result.data), columns, opts);

					$this.find("tbody").remove();
					$this.append($tbody);
					$this.treegrid(opts);
				}
			}));
		};

		$.fn._treegrid = function(method) {
			if (methods[method]) {
				return methods[method].apply(this, Array.prototype.slice.call(
						arguments, 1));
			} else if (typeof method === 'object' || !method) {
				return methods.initTree.apply(this, arguments);
			} else {
				$(this).treegrid.apply(this, arguments);
			}
		};

		$.fn._treegrid.defaults = {
			idColumn : "id",
			load : function(data) {
				return data;
			}
		};
	})(jQuery);

	
</script>