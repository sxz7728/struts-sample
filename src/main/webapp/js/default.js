jQuery.ajaxSettings.traditional = true;
jQuery.validator.setDefaults({
	ignore : ".ignore"
});

(function($) {
	$._edit = function(options) {
		var opts = $.extend({}, $._edit.defaults, options);
		var url = $._url(opts.url, opts.params);
		opts.message = "<iframe src='{0}' style='height:{1}px'/>".format(url,
				opts.height);

		opts.buttons = {
			OK : {
				label : "保存",
				className : "btn-primary",
				callback : function() {
					var close = false;

					$this = $(this);
					frame = $this.find("iframe")[0];
					frame.contentWindow.$("form")._ajaxSubmit({
						url : opts.saveUrl,
						async : false,
						success : function(result) {
							close = opts.success.apply($this[0], arguments);

							$._notify({
								message : "保存成功!",
								className : "success"
							});
						},
						failed : function(result) {
							close = opts.failed.apply($this[0], arguments);

							$._notify({
								message : "保存失败!",
								className : "error"
							});
						}
					});
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
		var opts = $.extend({}, $._delete.defaults, options);

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
		success : function() {
		},
		failed : function() {
		}
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
});