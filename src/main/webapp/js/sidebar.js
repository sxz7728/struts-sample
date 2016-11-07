"use strict";

(function($) {
	var js = document.scripts;
	js = js[js.length - 1].src.substring(0, js[js.length - 1].src.lastIndexOf("/") + 1);

	var template = null;

	$.ajax({
		url : js + "sidebar.html",
		async : false,
		dataType : "text",
		success : function(result) {
			template = doT.template(result);
		}
	});

	$.fn._sidebar = function(options) {
		var $this = $(this);
		var opts = $.extend(true, {}, $.fn._sidebar.defaults, options);

		$._ajax($.extend({}, opts, {
			success : function(result) {
				$this.hide().html(template({
					rows : $._tree(result.data.rows),
					target : opts.target
				})).fadeIn(0);

				$("#side-menu").metisMenu();

				$this.find("a").each(function(i, e) {
					e.click();

					if ($(e).attr("href") != "javascript:void(0);") {
						$(e).addClass("active");
						return false;
					}
				});
			}
		}));
	};

	$.fn._sidebar.defaults = {
		url : "/sidebar",
		params : {},
		target : "mainFrame"
	};
})(jQuery);