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

<script id="tgCommandsTemplate" type="text/template">
<button type="button" class="btn btn-xs btn-default command-add" data-id="{{=it.row.id}}">
	<span class="fa fa-plus"></span>
</button> 
<button type="button" class="btn btn-xs btn-default command-edit" data-id="{{=it.row.id}}">
	<span class="fa fa-pencil"></span>
</button> 
<button type="button" class="btn btn-xs btn-default command-delete" data-id="{{=it.row.id}}">
	<span class="fa fa-trash-o"></span>
</button>
</script>

<script type="text/javascript">
	(function($) {
		var commandsTemplate = doT.template($("#tgCommandsTemplate").html());

		function appendRows(rows, columns, $tbody, opts, parentId, parentSerial) {
			$.each(rows, function(i, e) {
				var id = e[opts.idColumn];
				var serial = parentSerial ? parentSerial + "-" + (i + 1)
						: (i + 1);

				var $tr = $("<tr></tr>");
				$tr.addClass("treegrid-" + id);

				if (parentId != null) {
					$tr.addClass("treegrid-parent-" + parentId);
				}

				for (var j = 0; j < columns.length; ++j) {
					var column = columns[j];

					if (column.formatter != null) {
						var formatter = opts.formatters[column.formatter];
						$tr.append("<td>" + formatter(e, e[column.id], serial)
								+ "</td>");
					} else if (e[column.id] != null) {
						$tr.append("<td>" + e[column.id] + "</td>");
					} else {
						$tr.append("<td></td>");
					}
				}

				$tbody.append($tr);

				if (e.children && e.children.length > 0) {
					appendRows(e.children, columns, $tbody, opts, id, serial);
				}
			});
		}

		var methods = {};

		methods.init = function(options) {
			var opts = jQuery.extend({}, $.fn._treegrid.defaults, options);
			var $this = $(this);
			var columns = [];

			if (opts.formatters.serialNo == null) {
				opts.formatters.serialNo = function(row, value, serialNo) {
					return "&nbsp;&nbsp;" + serialNo;
				};
			}

			if (opts.formatters.commands == null) {
				opts.formatters.commands = function(row, value, serialNo) {
					if (opts.commandsTemplate) {
						return opts.commandsTemplate({
							row : row
						});
					}

					return "";
				};
			}

			$this.find("th").each(function(i, e) {
				columns.push(eval("({" + $(e).data("column") + "})"));
			});

			$this.data("_treegrid", {
				opts : opts,
				columns : columns
			});

			methods.reload.apply(this);
		};

		methods.reload = function(params) {
			var $this = $(this);
			var opts = $this.data("_treegrid").opts;
			var columns = $this.data("_treegrid").columns;

			if (params != null) {
				opts.params = params;
			}

			$._ajax($.extend({}, opts, {
				success : function(result) {
					$tbody = $("<tbody></tbody>");

					appendRows(opts.load(result), columns, $tbody, opts);

					$.each(opts.commands, function(key, value) {
						$tbody.find("." + key).on("click", value);
					});

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
			} else if (typeof method === "object" || !method) {
				return methods.init.apply(this, arguments);
			} else {
				$(this).treegrid.apply(this, arguments);
			}
		};

		$.fn._treegrid.defaults = {
			idColumn : "id",
			saveState : true,
			formatters : {},
			commands : {},
			commandsTemplate : commandsTemplate,
			load : function(result) {
				return $._tree({
					rows : result.data.rows
				});
			}
		};
	})(jQuery);

	
</script>