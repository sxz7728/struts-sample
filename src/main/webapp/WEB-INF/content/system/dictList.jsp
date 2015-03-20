<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<s:head />
<%@ include file="/include/commands.html"%>
<script type="text/javascript">
	$(function() {
		$("#grid-data")._bootgrid({
			url : "dictDatagrid",
			commandsTemplate : commandsTemplate
		});
	});
</script>

</head>
<body>
	<div id="wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">字典维护</h1>
			</div>
			<!-- /.col-lg-12 -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<table id="grid-data"
								class="table table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th data-column-id="seq" data-sortable="false"
											data-formatter="seq">序号</th>
										<th data-column-id="dictKey" data-sortable="false">字典键</th>
										<th data-column-id="dictValue" data-sortable="false">字典值</th>
										<th data-column-id="sequence" data-sortable="false">顺序</th>
										<th data-column-id="commands" data-sortable="false"
											data-formatter="commands">操作</th>
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
