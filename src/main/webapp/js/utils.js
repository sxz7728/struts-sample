"use strict";

String.isNullOrEmpty = function(str) {
	return str == undefined || str == null || str == "";
};

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
			url = Globals.APP_NAME + url;
		} else if (url.indexOf("http") != 0) {
			var href = window.location.href;
			url = href.substring(0, href.lastIndexOf("/") + 1) + url;
		}

		if (params != null) {
			url += url.indexOf("?") < 0 ? "?" : "&";
			url += $.param(params);
		}

		return url;
	};

	$._refresh = function(url, params) {
		if (url) {
			window.location.href = $._url(url, params);
		} else {
			window.location.reload();
		}
	};

	$._notify = function(options) {
		var opts = $.extend(true, {}, $._notify.defaults, options);
		top.$.notify(opts.message, opts);
	};

	$._notify.defaults = {
		autoHideDelay : 3000,
		className : "info",
	};

	$._confirm = function(options) {
		var opts = $.extend(true, {}, $._confirm.defaults, options);
		top.bootbox.confirm(opts);
	};

	$._confirm.defaults = {
		buttons : {
			confirm : {
				label : "确认",
				className : "btn-primary"
			},
			cancel : {
				label : "取消",
				className : "btn-default"
			}
		},
	};

	$._ajax = function(options) {
		var opts = $.extend(true, {}, $._ajax.defaults, options);

		opts.url = $._url(opts.url);
		opts.data = opts.params;
		opts.dataType = "json";

		$.ajax($.extend({}, opts, {
			success : function(result) {
				if (result.success) {
					opts.success.apply(this, arguments);
				} else {
					opts.failed.apply(this, arguments);

					$._notify({
						message : result.error,
						className : "error"
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

	$._tree = function(rows, options) {
		var opts = $.extend(true, {}, $._tree.defaults, options);
		var tree = [];
		var map = {};

		$.each(rows, function(i, e) {
			map[e[opts.id]] = e;

			if (e[opts.children] == null) {
				e[opts.children] = [];
			}
		});

		$.each(rows, function(i, e) {
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
		var opts = $.extend(true, {}, $._dialog.defaults, options);
		top.bootbox.dialog(opts);
	};

	$._dialog.defaults = {};

	$.fn._refresh = function(url, params) {
		var $this = $(this);

		if (url) {
			$this.attr("src", $._url(url, params));
		} else {
			$this.attr("src", $this.attr("src"));
		}
	};

	$.fn._autoHeight = function() {
		$(this).each(function() {
			var $this = $(this);
			var $current = $(this);

			$this.parents().each(function(i, e) {
				var bottom = $current.offset().top + $current.outerHeight(true);

				$(e).children().each(function(i, e) {
					if ($(e).offset().top > bottom) {
						bottom = Math.max(bottom, $(e).offset().top + $(e).outerHeight(true));
					}
				});

				var height = $(e).offset().top + $(e).height() - bottom;

				if (height > 10) {
					$this.height(Math.floor($this.height() + height));
				}

				$current = $(e);
			});
		});
	};

	$.fn._ajaxSubmit = function(options) {
		var opts = $.extend(true, {}, $.fn._ajaxSubmit.defaults, options);
		var $this = $(this);

		opts.url = $._url(opts.url);
		opts.data = opts.params;
		opts.dataType = "json";

		$this.ajaxSubmit($.extend({}, opts, {
			beforeSubmit : function(arr, $form, options) {
				return $form.valid();
			},
			success : function(result) {
				if (result.success) {
					opts.success.apply(this, arguments);
				} else {
					opts.failed.apply(this, arguments);

					$._notify({
						message : result.error,
						className : "error"
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
		var opts = $.extend(true, {}, $.fn._jsonSelect.defaults, options);
		var $this = $(this);

		$this.find("option[value != '']").remove();

		$.each(opts.data, function(i, e) {
			$this.append("<option value='" + e.key + "'>" + e.value + "</option>");
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
		var opts = $.extend(true, {}, $.fn._ajaxSelect.defaults, options);
		var $this = $(this);
	};

	$.fn._ajaxSelect.defaults = {};
})(jQuery);
