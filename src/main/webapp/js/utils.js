String.prototype.format = function(args) {
	var result = this;
	if (arguments.length > 0) {
		if (arguments.length == 1 && typeof (args) == "object") {
			for ( var key in args) {
				if (args[key] != undefined) {
					var reg = new RegExp("({" + key + "})", "g");
					result = result.replace(reg, args[key]);
				}
			}
		} else {
			for (var i = 0; i < arguments.length; i++) {
				if (arguments[i] != undefined) {
					var reg = new RegExp("({[" + i + "]})", "g");
					result = result.replace(reg, arguments[i]);
				}
			}
		}
	}
	return result;
};

(function($) {
	$._url = function(url, params) {
		if (url.charAt(0) == "/") {
			url = globals.APP_NAME + url;
		} else {
			if (url.indexOf("http") != 0) {
				var href = window.location.href;
				url = href.substring(0, href.lastIndexOf("/") + 1) + url;
			}
		}

		if (params != null) {
			url += url.indexOf("?") < 0 ? "?" : "&";
			url += $.param(params);
		}

		return url;
	};

	$._location = function(url) {
		window.location.href = $._url(url);
	};

	$._notify = function(options) {
		var opts = jQuery.extend({}, $._notify.defaults, options);
	};

	$._notify.defaults = {

	};

	$._confirm = function(options) {

	};

	$._confirm.defaults = {

	};

	$._ajax = function(options) {
		var opts = jQuery.extend({}, $._ajax.defaults, options);

		opts.url = $._url(opts.url);
		opts.data = opts.params;
		opts.dataType = "json";

		$.ajax(jQuery.extend({}, opts, {
			success : function(result) {
				if (result.success) {
					opts.success.apply(this, arguments);
				} else {
					opts.failed.apply(this, arguments);

					$._notify({
						message : result.error
					});
				}
			}
		}));
	};

	$._ajax.defaults = {
		type : "post",
		params : {},
		success : function() {
		},
		failed : function() {
		}
	};

	$._autoHeight = function() {

	};

	$._tree = function(options) {
		var opts = jQuery.extend({}, $._tree.defaults, options);
		var tree = [];
		var map = {};

		$.each(opts.rows, function(i, e) {
			map[e[opts.id]] = e;
			e[opts.children] = [];
		});

		$.each(opts.rows, function(i, e) {
			if (e[opts.parentId] != null && map[e[opts.parentId]] != null) {
				map[e[opts.parentId]][opts.children].push(e);
			} else {
				tree.push(e);
			}
		});

		return tree;
	};

	$._tree.defaults = {
		id : "id",
		parentId : "parentId",
		children : "children"
	};

	$._dialog = function(options) {
		/*
		 * var opts = jQuery.extend({}, $._dialog.defaults, options);
		 * 
		 * var id = "dlg" + new Date().getTime(); var $dlg = $("<div id='{0}'><iframe
		 * style='height: 99%;' /></div>" .format(id)); var frame =
		 * $dlg.find("iframe"); var url = $._url(opts.url, opts.params);
		 * frame.attr("src", url);
		 * 
		 * $dlg.appendTo(document.body); $dlg.dialog(jQuery.extend({}, opts, {
		 * close : function() { opts.close.apply(this, arguments);
		 * $dlg.remove(); } }));
		 */

		var opts = jQuery.extend({}, $._dialog.defaults, options);
		var url = $._url(opts.url, opts.params);
		opts.message = "<iframe src='{0}' />".format(url);

		bootbox.dialog(opts);
	};

	$._dialog.defaults = {};

	$.fn._ajaxSubmit = function(options) {
		var opts = jQuery.extend({}, $.fn._ajaxSubmit.defaults, options);
		var $this = $(this);

		opts.url = $._url(opts.url);
		opts.data = opts.params;
		opts.dataType = "json";

		$this.ajaxSubmit(jQuery.extend({}, opts, {
			beforeSubmit : function(arr, $form, options) {
				return $form.valid();
			},
			success : function(result) {
				if (result.success) {
					opts.success.apply(this, arguments);
				} else {
					opts.failed.apply(this, arguments);

					$._notify({
						message : result.error
					});
				}
			}
		}));
	};

	$.fn._ajaxSubmit.defaults = {
		type : "post",
		params : {},
		success : function() {
		},
		failed : function() {
		}
	};

	$.fn._jsonSelect = function(options) {
		var opts = jQuery.extend({}, $.fn._jsonSelect.defaults, options);
		var $this = $(this);

		$this.find("option[value != '']").remove();

		$.each(opts.data, function(i, e) {
			$this.append("<option value='" + e.key + "'>" + e.value
					+ "</option>");
		});

		$this.val(opts.value);

		if ($this.find("option:selected").size() == 0) {
			$this.find("option:first").prop("selected", "selected");
		}
	};

	$.fn._jsonSelect.defaults = {
		data : [],
		value : ""
	};

	$.fn._ajaxSelect = function(options) {
		var opts = jQuery.extend({}, $.fn._ajaxSelect.defaults, options);
		var $this = $(this);

		opts.success = function(result) {
			opts.data = result;
			$this._jsonSelect(opts);
		};
	};

	$.fn._ajaxSelect.defaults = {};

	$.fn._bootgrid = function(options) {
		var opts = jQuery.extend({}, $.fn._bootgrid.defaults, options);
		opts.url = $._url(opts.url);
		opts.formatters.seq = function(column, row) {
			return ++this.options.start;
		};
		opts.formatters.commands = function(column, row) {
			if (this.options.commandsTemplate) {
				return this.options.commandsTemplate({
					row : row
				});
			}

			return "";
		};

		$(this).bootgrid(opts);
	};

	$.fn._bootgrid.defaults = {
		ajax : true,
		sorting : false,
		columnSelection : false,
		rowCount : [ 10, 25, 50 ],
		requestHandler : function(request) {
			var current = request.current;
			var rowCount = request.rowCount;
			var start = (current - 1) * rowCount;

			this.current = current;
			this.start = start;
			request.start = start;
			request.length = rowCount;

			if (request.searchPhrase != null) {
				request.queryName = "%" + request.searchPhrase + "%";
			}
			return request;
		},
		responseHandler : function(response) {
			if (response.success) {
				var data = response.data;
				data.current = this.current;
				data.total = data.count;
				return data;
			} else {
				$._notify({
					message : response.error
				});
				return {
					rows : [],
					total : 0
				};
			}
		},
		formatters : {}
	};
})(jQuery);
