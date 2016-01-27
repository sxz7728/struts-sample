<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<s:head />
<%@ include file="/include/datagrid.jsp"%>

<script type="text/javascript">
	"use strict";

	function edit(id) {
		$._edit({
			title : id == null ? "新增字典" : "编辑字典",
			url : "dictEdit",
			params : {
				id : id,
				type : "${type}"
			},
			saveUrl : "dictSave",
			height : 300,
			success : function() {
				if (id == null) {
					$(this).find("iframe")._refresh();
					return false;
				} else {
					$("#datagrid")._bootgrid("reload");
					return true;
				}
			},
			cancel : function() {
				$("#datagrid")._bootgrid("reload");
			}
		});
	}

	function del(id) {
		$._delete({
			url : "dictDelete",
			params : {
				id : id
			},
			success : function() {
				$("#datagrid")._bootgrid("reload");
			}
		});
	}

	$(function() {
		$("#datagrid")._datagrid({
			url : "dictDatagrid",
			
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
						<div id="auto-min-height" class="panel-body">
							<div class="table-header container-fluid">
								<div class="row">
									<div class="col-sm-12 actionBar">
										<div class="btn-group">
											<button id="add" type="button" class="btn btn-default">新增</button>

											<button id="refresh" type="button" class="btn btn-default">
												刷新</button>
										</div>
									</div>
								</div>
							</div>

							<table id="datagrid" class="table table-hover table-striped">
								<thead>
									<tr>
										<th data-column="formatter:'checkbox',width:'50px'"></th>
										<th data-column="id:'dictKey'">字典键</th>
										<th data-column="id:'dictValue'">字典值</th>
										<th data-column="id:'sequence'">顺序</th>
										<th data-column="id:'commands',width:'100px'">操作</th>
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
