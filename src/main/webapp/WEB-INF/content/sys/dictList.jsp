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

	function search() {
		$("#datagrid")._datagrid("reload", {
			queryName : $("#queryName").val()
		}, 1);
	}

	function refresh() {
		$("#datagrid")._datagrid("reload");
	}

	function edit(row) {
		$._edit({
			title : row ? "编辑${typeName}" : "新增${typeName}",
			url : "dictEdit",
			params : {
				id : row ? row.id : null,
				type : "${type}"
			},
			saveUrl : "dictSave",
			height : 300,
			success : function() {
				if (row) {
					refresh();
					return true;
				} else {
					$(this).find("iframe")._refresh();
					return false;
				}
			},
			cancel : function() {
				refresh();
			}
		});
	}

	function del(rows) {
		var ids = $.map(rows, function(e, i) {
			return e.id;
		});

		$._delete({
			url : "dictDelete",
			ids : ids,
			success : function() {
				refresh();
			}
		});
	}

	$(function() {
		$("#datagrid")._datagrid({
			url : "dictDatagrid",
			params : {
				type : "${type}"
			},
			formatters : {
				commands : function(row, value, index) {
					var $edit = $("<button type='button' class='btn btn-default btn-xs'></button>");
					$edit.html("<span class='glyphicon glyphicon glyphicon-edit'></span>");
					$edit.click(function() {
						edit(row);
					});

					var $del = $("<button type='button' class='btn btn-default btn-xs'></button>");
					$del.html("<span class='glyphicon glyphicon glyphicon-trash'></span>");
					$del.click(function() {
						del([ row ]);
					});

					return [ $edit, "&nbsp;", $del ];
				}
			}
		});

		$("#add").click(function() {
			edit();
		});

		$("#del").click(function() {
			del($("#datagrid")._datagrid("getChecked"));
		});

		$("#search").click(function() {
			search();
		});

		$("#queryName").keyup(function(e) {
			if (e.keyCode == 13) {
				search();
			}
		});

		$("#refresh").click(function() {
			refresh();
		});
	});
</script>

</head>
<body>
	<div id="wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">${typeName}维护</h1>
			</div>
			<!-- /.col-lg-12 -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div id="auto-min-height" class="panel-body">
							<div class="table-header container-fluid">
								<div class="row">
									<div class="col-sm-12 actionBar">
										<div class="search">
											<div class="input-group">
												<input type="text" id="queryName" class="form-control">
												<span class="input-group-btn">
													<button class="btn btn-default" type="button" id="search">
														<span class='glyphicon glyphicon glyphicon-search'></span>
													</button>
												</span>
											</div>
										</div>
										<div class="btn-group">
											<button id="add" type="button" class="btn btn-default">新增</button>
											<button id="del" type="button" class="btn btn-default">删除</button>
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
										<th data-column="id:'dictKey',width:'30%'">字典键</th>
										<th data-column="id:'dictValue'">字典值</th>
										<th data-column="id:'sequence',width:'30%'">顺序</th>
										<th data-column="formatter:'commands',width:'100px'">操作</th>
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
