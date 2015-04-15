<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<s:if test="properties.DEV_MODE">
	<link href="<%=request.getContextPath()%>/css/jquery.bootgrid.css"
		rel="stylesheet" type="text/css"></link>
	<script src="<%=request.getContextPath()%>/js/jquery.bootgrid.js"
		type="text/javascript"></script>
</s:if>
<s:else>
	<link href="<%=request.getContextPath()%>/css/jquery.bootgrid.min.css"
		rel="stylesheet" type="text/css"></link>
	<script src="<%=request.getContextPath()%>/js/jquery.bootgrid.min.js"
		type="text/javascript"></script>
</s:else>

<script id="bgSearchTemplate" type="text/template">
<div class="search form-group form-inline">
	<label class="control-label">{0}</label>{1}
</div>
</script>

<script id="bgButtonTemplate" type="text/template">
<button class="btn btn-default">{0}</button>
</script>

<script id="bgCommandsTemplate" type="text/template">
<button type="button" class="btn btn-xs btn-default command-edit" data-id="{{=it.row.id}}">
	<span class="fa fa-pencil"></span>
</button> 
<button type="button" class="btn btn-xs btn-default command-delete" data-id="{{=it.row.id}}">
	<span class="fa fa-trash-o"></span>
</button>
</script>

<script type="text/javascript">
	(function($) {
		var searchTemplate = $("#bgSearchTemplate").html();
		var buttonTemplate = $("#bgButtonTemplate").html();
		var commandsTemplate = doT.template($("#bgCommandsTemplate").html());

		var methods = {};

		function autoHeight($footer) {
			var $parent = $footer.parent();
			var $prev = $footer.prev();
			$prev.height(0);
			var height = $parent.offset().top + $parent.height();
			height -= $footer.offset().top + $footer.height();
			$prev.height(height);
		}

		methods.init = function(options) {
			var opts = jQuery.extend({}, $.fn._bootgrid.defaults, options);
			var $this = $(this);
			var id = $this.attr("id");
			opts.url = $._url(opts.url);

			if (opts.formatters.serialNo == null) {
				opts.formatters.serialNo = function(column, row) {
					return ++this.options.start;
				};
			}

			if (opts.formatters.commands == null) {
				opts.formatters.commands = function(column, row) {
					if (opts.commandsTemplate) {
						return opts.commandsTemplate({
							row : row
						});
					}

					return "";
				};
			}

			$this.on("initialize.rs.jquery.bootgrid", function(e) {
				var grid = $this.data(".rs.jquery.bootgrid");

				if (opts.saveRowCount && $.cookie(id + "-row-count") > 0) {
					grid.rowCount = $.cookie(id + "-row-count");
				}
			});

			$this.on("initialized.rs.jquery.bootgrid", function(e) {
				var header = $("#" + id + "-header");
				var grid = $this.data(".rs.jquery.bootgrid");
				var params = grid.options.params;

				$.each(opts.searches, function(key, value) {
					var search = $(searchTemplate.format(key, value));

					search.find("input,select").each(function(i, e) {
						$e = $(e);

						if ($e.attr("name")) {
							params[$e.attr("name")] = $(e).val();

							$e.on("change", function() {
								params[$e.attr("name")] = $(e).val();
								grid.current = 1;
								$this.bootgrid("reload");
							});
						}
					});

					header.find(".actionBar").prepend(search);
				});

				$.each(opts.buttons, function(key, value) {
					var button = $(buttonTemplate.format(key));
					button.on("click", value);
					header.find(".actions").prepend(button);
				});

				var $footer = $("#" + id + "-footer");
				$footer.before("<p></p>");

				if (opts.autoHeight) {
					$(window).resize(function() {
						autoHeight($footer);
					});
				}
			});

			$this.on("loaded.rs.jquery.bootgrid", function(e) {
				var grid = $this.data(".rs.jquery.bootgrid");

				$.each(opts.commands, function(key, value) {
					$this.find("." + key).on("click", value);
				});

				if (opts.autoHeight) {
					autoHeight($("#" + id + "-footer"));
				}

				if (opts.saveRowCount) {
					$.cookie(id + "-row-count", grid.rowCount, {
						expires : 30
					});
				}
			});

			$this.bootgrid(opts);
		};

		$.fn._bootgrid = function(method) {
			if (methods[method]) {
				return methods[method].apply(this, Array.prototype.slice.call(
						arguments, 1));
			} else if (typeof method === "object" || !method) {
				return methods.init.apply(this, arguments);
			} else {
				$(this).bootgrid.apply(this, arguments);
			}
		};

		$.fn._bootgrid.defaults = {
			ajax : true,
			sorting : false,
			columnSelection : false,
			rowCount : [ 10, 25, 50 ],
			searches : {},
			buttons : {},
			commands : {},
			commandsTemplate : commandsTemplate,
			formatters : {},
			params : {},
			autoHeight : true,
			saveRowCount : true,
			requestHandler : function(request) {
				var current = request.current;
				var rowCount = request.rowCount;
				var start = (current - 1) * rowCount;

				this.current = current;
				this.start = start;
				request.start = start;
				request.length = rowCount;

				if (!String.isNullOrEmpty(request.searchPhrase)) {
					request.queryName = "%" + request.searchPhrase + "%";
				}

				return $.extend({}, request, this.params);
			},
			responseHandler : function(response) {
				if (response.success) {
					var data = response.data;
					data.current = this.current;
					data.total = data.count;
					return data;
				} else {
					$._notify({
						message : response.error,
						className : "error"
					});
					return {
						rows : [],
						total : 0
					};
				}
			}
		};
	})(jQuery);

	
</script>