<%@ page language="java" pageEncoding="UTF-8"%>

<link
	href="<%=request.getContextPath()%>/css/plugins/metisMenu/metisMenu.min.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/js/plugins/metisMenu/metisMenu.min.js"></script>

<script id="sidebarTemplate" type="text/template">
<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			<li class="sidebar-search">
				<div class="input-group custom-search-form">
					<input type="text" class="form-control" placeholder="Search...">
					<span class="input-group-btn">
						<button class="btn btn-default" type="button">
							<i class="fa fa-search"></i>
						</button>
					</span>
				</div> <!-- /input-group -->
			</li>
			{{~ it.rows : row1}}
				{{var url = !String.isNullOrEmpty(row1.url) ? row1.url : "javascript:void(0);";}}
				<li><a href="{{=url}}" target="{{=it.target}}"><i class="{{=row1.cssClass||''}}"></i> {{=row1.name}}
				{{? row1.children.length > 0}}<span class="fa arrow"></span>{{?}}</a>
				{{? row1.children.length > 0}}
					<ul class="nav nav-second-level">
					{{~ row1.children : row2}}
						{{var url = !String.isNullOrEmpty(row2.url) ? row2.url : "javascript:void(0);";}}
						<li><a href="{{=url}}" target="{{=it.target}}">{{=row2.name}}
						{{? row2.children.length > 0}}<span class="fa arrow"></span>{{?}}</a>
						{{? row2.children.length > 0}}
							<ul class="nav nav-third-level">
							{{~ row2.children : row3}}
								{{var url = !String.isNullOrEmpty(row3.url) ? row3.url : "javascript:void(0);";}}
								<li><a href="{{=url}}" target="{{=it.target}}">{{=row3.name}}</a></li>
							{{~}}
							</ul> <!-- /.nav-third-level -->
						{{?}}
						</li>
					{{~}}
					</ul> <!-- /.nav-second-level -->
				{{?}}
				</li>
			{{~}}
		</ul>
	</div>
	<!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->
</script>

<script type="text/javascript">
	"use strict";

	(function($) {
		var template = doT.template($("#sidebarTemplate").html());

		$.fn._sidebar = function(options) {
			var $this = $(this);
			var opts = $.extend(true, {}, $.fn._sidebar.defaults, options);

			$._ajax($.extend({}, opts, {
				success : function(result) {
					$this.hide().html(template({
						rows : $._tree(result.data.rows),
						target : opts.target
					})).fadeIn(opts.dual);
					
					$("#side-menu").metisMenu();

					$this.find("a").each(function(i, e) {
						e.click();

						if ($(e).attr("href") != "javascript:void(0);") {
							return false;
						}
					});
				}
			}));

			$this.on("click", "a", function() {
				if ($(this).attr("href") != "javascript:void(0);") {
					$this.find("a").removeClass("active");
					$(this).addClass("active");
				}
			});
		};

		$.fn._sidebar.defaults = {
			url : "/sidebar",
			params : {},
			target : "mainFrame",
			dual : 0
		};
	})(jQuery);
</script>