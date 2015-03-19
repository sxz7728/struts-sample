(function($) {
	$._url = function(url) {
		return url.charAt(0) == '/' ? globals.APP_NAME + url : url;
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
		var opts = jQuery.extend({}, $._confirm.defaults, options);
	};

	$._confirm.defaults = {

	};

	$._openDialog = function(options) {
		var opts = jQuery.extend({}, $._openDialog.defaults, options);
	};

	$._openDialog.defaults = {

	};

	$._ajax = function(options) {
		var opts = jQuery.extend({}, $._ajax.defaults, options);

		opts.url = $._url(opts.url);
		opts.data = opts.params;
		opts.dataType = "json";

		$.ajax(jQuery.extend({}, opts, {
			success : function(result) {
				if (result.success) {
					opts.success(result);
				} else {
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

	$.fn._data = function() {
		return {};
	};

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
					opts.success(result);
				} else {
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
		}
	};

	$.fn._jsonSelect = function(options) {
		var opts = jQuery.extend({}, $.fn._jsonSelect.defaults, options);
		var $this = $(this);

		$this.find("option[value != '']").remove();

		$.each(opts.data, function(i, val) {
			$this.append("<option value='" + val.key + "'>" + val.value
					+ "</option>");
		});

		$this.val(opts.value);

		if ($this.find("option:selected").size() == 0) {
			$this.find("option:first").prop('selected', 'selected');
		}
	};

	$.fn._jsonSelect.defaults = {
		data : [],
		value : ''
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
		$(this).bootgrid(opts);
	};

	$.fn._bootgrid.defaults = {
		ajax : true,
		responseHandler : function(response) {
			if (response.success) {
				return {
					current : 1,
					rowCount : 10,
					rows : response.data.rows,
					total : response.data.count
				};
			} else {
				$._notify({
					message : response.error
				});
				return {
					rows : [],
					total : 0
				};
			}

		}
	};
})(jQuery);
