jQuery.ajaxSettings.traditional = true;
jQuery.validator.setDefaults({
	ignore : ".ignore"
});

(function($) {
	$._edit = function(options) {
		var opts = jQuery.extend({}, $._edit.defaults, options);
		opts.url = $._url(opts.url);

		opts.buttons = {
			"保存" : function() {
				$this = top.$(this);
				frame = $this.find("iframe")[0];
				frame.contentWindow.$("form")._ajaxSubmit({
					url : opts.saveUrl,
					success : function(result) {
						$._notify({
							message : "保存成功!"
						});
						opts.success.apply($this, arguments);
					},
					failed : function(result) {
						$._notify({
							message : "保存失败!"
						});
						opts.failed.apply($this, arguments);
					}
				});
			},
			"取消" : function() {
				$this = top.$(this);
				opts.cancel.apply($this, arguments);
				$this.dialog("close");
			}
		};

		top.$._dialog(opts);
	};

	$._edit.defaults = {
		success : function() {
		},
		failed : function() {
		},
		cancel : function() {
		}
	};

	$._delete = function(options) {
		var opts = jQuery.extend({}, $._delete.defaults, options);

		
		$._ajax(jQuery.extend({}, opts, {
			success : function(result) {
				$._notify({
					message : "删除成功!"
				});
				opts.success.apply(this, arguments);
			},
			failed : function(result) {
				$._notify({
					message : "删除失败!"
				});
				opts.failed.apply(this, arguments);
			}
		}));
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

	$(document).on('focus', ':input', function() {
		$(this).attr('autocomplete', 'off');
	});

	$("form").validate();
});