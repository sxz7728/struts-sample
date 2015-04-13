<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<s:head />
<%@ include file="/include/treegrid.jsp"%>

<script type="text/javascript">
	$(function() {
		$("#datagrid")._treegrid({
			url : "menuDatagrid",
			params : {
				moduleId : 1
			}
		});
	});
</script>

</head>
<body>
	<div id="wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">菜单维护</h1>
			</div>
			<!-- /.col-lg-12 -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<table id="datagrid" class="table tree">
								<thead>
									<tr>
										<th data-column="id:'serialNo',formatter:'serialNo'"
											style="width: 200px">序号</th>
										<th data-column="id:'name'">名称</th>
										<th data-column="id:'url'">链接</th>
										<th data-column="id:'cssClass'">样式</th>
										<th data-column="id:'sequence'">顺序</th>
										<th data-column="id:'commands'" style="width: 100px">操作</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /#wrapper -->
</body>
</html>