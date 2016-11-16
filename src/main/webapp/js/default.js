"use strict";

jQuery.ajaxSettings.traditional = true;
jQuery.validator.setDefaults({
	ignore : ".ignore"
});

(function($) {
	$._edit = function(options) {
		var opts = $.extend(true, {}, $._edit.defaults, options);
		var url = $._url(opts.url, opts.params);
		opts.message = "<iframe src='{0}' style='height:{1}px'/>".format(url, opts.height);

		var timestep = 0;

		opts.buttons = {
			OK : {
				label : "保存",
				className : "btn-primary",
				callback : function() {
					var $this = $(this);
					var close = false;

					if (timestep < new Date().getTime() - 5000) {
						timestep = new Date().getTime();

						$this.find("iframe")._save({
							url : opts.saveUrl,
							async : false,
							success : function(result) {
								close = opts.success.apply($this, arguments);
							},
							failed : function(result) {
								close = opts.failed.apply($this, arguments);
							}
						});

						timestep = 0;
					}

					return close;
				}
			},
			onEscape : {
				label : "取消",
				className : "btn-default",
				callback : function() {
					return opts.cancel.apply(this, arguments);
				}
			}
		};

		$._dialog(opts);
	};

	$._edit.defaults = {
		height : 100,
		success : function() {
		},
		failed : function() {
			return false;
		},
		cancel : function() {
		}
	};

	$._delete = function(options) {
		var opts = $.extend(true, {}, $._delete.defaults, options);
		opts.params.ids = opts.ids;

		if (opts.ids.length == 0) {
			$._notify({
				message : "请勾选要删除的记录!",
				className : "warning"
			});

			return;
		}

		$._confirm({
			title : "确认",
			message : "是否要删除当前记录?",
			callback : function(result) {
				if (result) {
					$._ajax($.extend({}, opts, {
						success : function(result) {
							opts.success.apply(this, arguments);

							$._notify({
								message : "删除成功!",
								className : "success"
							});
						},
						failed : function(result) {
							opts.failed.apply(this, arguments);

							$._notify({
								message : "删除失败!",
								className : "error"
							});
						}
					}));
				}
			}
		});
	};

	$._delete.defaults = {
		ids : [],
		params : {},
		success : function() {
		},
		failed : function() {
		}
	};

	$._save = function(options) {
		var opts = $.extend(true, {}, $._save.defaults, options);

		if (opts.formId == null) {
			opts.formId = "form";
		} else {
			opts.formId = "#" + opts.formId;
		}

		$(opts.formId)._ajaxSubmit($.extend({}, opts, {
			success : function(result) {
				$._notify({
					message : "保存成功!",
					className : "success"
				});

				opts.success.apply(this, arguments);
			},
			failed : function(result) {
				$._notify({
					message : "保存失败!",
					className : "error"
				});

				opts.failed.apply(this, arguments);
			}
		}));
	};

	$._save.defaults = {
		success : function() {
		},
		failed : function() {
		}
	};

	$._valid = function() {
		if ($("form").size() > 0) {
			return $("form").valid();
		}

		return true;
	};

	$.fn._save = function(options) {
		var opts = $.extend(true, {}, $.fn._save.defaults, options);

		if (this[0].contentWindow.$ && this[0].contentWindow.$._save) {
			this[0].contentWindow.$._save(opts);
		}
	};

	$.fn._save.defaults = {};

	$.fn._valid = function() {
		if (this[0].contentWindow.$ && this[0].contentWindow.$._valid) {
			return this[0].contentWindow.$._valid();
		}

		return false;
	};

	$.fn._dictSelect = function(options) {
		$(this).each(function() {
			var opts = $.extend(true, {}, $.fn._dictSelect.defaults, options);
			var $this = $(this);
			opts.params.type = opts.type;
			opts.params.parentKey = opts.parentKey;
			$this._ajaxSelect(opts);
		});
	};

	$.fn._dictSelect.defaults = {
		url : "/sys/dictDictionary",
		parentParam : "parentKey",
		params : {}
	};

	$.fn._dictCheckbox = function(options) {
		$(this).each(function() {
			var opts = $.extend(true, {}, $.fn._dictCheckbox.defaults, options);
			var $this = $(this);
			opts.params.type = opts.type;
			opts.params.parentKey = opts.parentKey;
			$this._ajaxCheckbox(opts);
		});
	};

	$.fn._dictCheckbox.defaults = {
		url : "/sys/dictDictionary",
		parentParam : "parentKey",
		params : {}
	};

})(jQuery);

$(function() {
	$.validator.setDefaults({
		highlight : function(element) {
			$(element).closest(".form-group").addClass("has-error");
		},
		unhighlight : function(element) {
			$(element).closest(".form-group").removeClass("has-error");
		},
		errorElement : "span",
		errorClass : "help-block",
		errorPlacement : function(error, element) {
			if (element.parent(".input-group").length) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});

	$(document).on("focus", ":input", function() {
		$(this).attr("autocomplete", "off");
	});

	$("form").validate();

	$(window).bind("load resize", function() {
		var height = $(window).height();
		height -= $("#wrapper").height();
		height += $("#auto-min-height").height();
		$("#auto-min-height").css("min-height", height + "px");
	});
});