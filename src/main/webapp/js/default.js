(function($) {
	$._openModify = function(options) {
		var opts = jQuery.extend({}, $._openModify.defaults, options);
	};

	$._openModify.defaults = {

	};

	$._save = function(options) {
		var opts = jQuery.extend({}, $._save.defaults, options);
	};

	$._save.defaults = {

	};

	$._delete = function(options) {
		var opts = jQuery.extend({}, $._delete.defaults, options);
	};

	$._delete.defaults = {

	};

	$.fn._selectDict = function(options) {
		var opts = jQuery.extend({}, $._selectDict.defaults, options);
		var $this = $(this);

		opts.params = {
			type : opts.type
		};

		$this._ajaxSelect(opts);
	};

	$.fn._selectDict.defaults = {
		url : "/system/dictionaryDict"
	};

})(jQuery);

$(function() {
	$.validator.setDefaults({
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		},
		errorElement : 'span',
		errorClass : 'help-block',
		errorPlacement : function(error, element) {
			if (element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});

	$("form").validate();
});