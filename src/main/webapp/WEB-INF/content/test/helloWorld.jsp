<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<s:head />
<script>
	$(function() {
		$("#dialog-confirm").dialog({
			resizable : false,
			width : 300,
			height : 300,
			modal : true,
			buttons : {
				"保存" : function() {
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});
	});
</script>
</head>
<body>
	<div id="wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">hello world</h1>
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /#wrapper -->

	<div id="dialog-confirm" title="Empty the recycle bin?">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>These items will
			be permanently deleted and cannot be recovered. Are you sure?
		</p>
	</div>
</body>
</html>
