<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<s:head />
<%@ include file="/include/sidebar.html"%>

<script type="text/javascript">
	$(function() {
		$("#sidebar")._sidebar({
			params : {
				moduleId : 1
			}
		});

		$("#logout").click(function() {
			$._ajax({
				url : "logout",
				success : function(result) {
					if (result.data) {
						$._location("index");
					}
				}
			});
		});
	});
</script>
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="main">struts-sample</a>
			</div>
			<!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-right">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i
						class="fa fa-envelope fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-messages">
						<li>&nbsp;</li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>所有消息</strong>
								<i class="fa fa-angle-right"></i>
						</a></li>

					</ul> <!-- /.dropdown-messages --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-tasks fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-tasks">
						<li>&nbsp;</li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>所有任务</strong>
								<i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-tasks --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-bell fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-alerts">
						<li>&nbsp;</li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>所有通知</strong>
								<i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-alerts --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#"><i class="fa fa-user fa-fw"></i>用户信息</a></li>
						<li><a href="#"><i class="fa fa-gear fa-fw"></i>设置</a></li>
						<li class="divider"></li>
						<li><a id="logout" href="javascript:void(0)"><i
								class="fa fa-sign-out fa-fw"></i> 退出</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->
			<div id="sidebar"></div>
		</nav>

		<div id="page-wrapper">
			<iframe id="main" name="main"></iframe>
		</div>
		<!-- /#page-wrapper -->

	</div>
</body>
</html>