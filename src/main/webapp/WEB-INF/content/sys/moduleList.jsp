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

	function moduleEdit(id) {
		$._edit({
			title : id == null ? "新增模块" : "编辑模块",
			url : "moduleEdit",
			params : {
				id : id
			},
			saveUrl : "moduleSave",
			height : 300,
			success : function() {
				if (id == null) {
					$(this).find("iframe")._refresh();
					return false;
				} else {
					$("#datagrid")._datagrid("reload");
					return true;
				}
			},
			cancel : function() {
				$("#datagrid")._datagrid("reload");
			}
		});
	}

	function moduleDel(ids) {
		$._delete({
			url : "moduleDelete",
			ids : ids,
			success : function() {
				$("#datagrid")._datagrid("reload");
			}
		});
	}

	function menuEdit(id, moduleId, parentId) {
		$._edit({
			title : id == null ? "新增菜单" : "编辑菜单",
			url : "menuEdit",
			params : {
				id : id,
				moduleId : moduleId,
				parentId : parentId,
			},
			saveUrl : "menuSave",
			height : 300,
			success : function() {
				if (id == null) {
					$(this).find("iframe")._refresh();
					return false;
				} else {
					$("#datagrid")._datagrid("reload");
					return true;
				}
			},
			cancel : function() {
				$("#datagrid")._datagrid("reload");
			}
		});
	}

	function menuDel(ids) {
		$._delete({
			url : "menuDelete",
			ids : ids,
			success : function() {
				$("#datagrid")._datagrid("reload");
			}
		});
	}

	$(function() {
		$("#datagrid")._datagrid({
			url : "moduleDatagrid",
			tree : true,
			load : function(result) {
				var modules = result.data.modules.rows;

				$.each(modules, function(i, e) {
					e._id = e.id;
					e.id = "module-" + e.id;
				});

				var menus = result.data.menus.rows;

				$.each(menus, function(i, e) {
					e._moduleId = e.moduleId;
					e.moduleId = "module-" + e.moduleId;
					e.trStyle = "color:#006699";
				});

				var rows = $._tree(menus);
				rows = $._tree(modules.concat(rows), {
					parentId : "moduleId"
				});

				return {
					rows : rows
				};
			},

			formatters : {
				commands : function(row, value, index) {
					var $add = $("<button type='button' class='btn btn-default btn-xs'></button>");
					$add.html("<span class='glyphicon glyphicon glyphicon-plus'></span>");

					$add.click(function() {
						if (row.id > 0) {
							menuEdit(null, row._moduleId, row.id);
						} else {
							menuEdit(null, row._id);
						}
					});

					var $edit = $("<button type='button' class='btn btn-default btn-xs'></button>");
					$edit.html("<span class='glyphicon glyphicon glyphicon-edit'></span>");

					$edit.click(function() {
						if (row.id > 0) {
							menuEdit(row.id);
						} else {
							moduleEdit(row._id);
						}
					});

					var $del = $("<button type='button' class='btn btn-default btn-xs'></button>");
					$del.html("<span class='glyphicon glyphicon glyphicon-trash'></span>");

					$del.click(function() {
						if (row.id > 0) {
							menuDel(row.id);
						} else {
							moduleDel(row._id);
						}
					});

					return [ $add, "&nbsp;", $edit, "&nbsp;", $del ];
				}
			}

		});

		$("#moduleAdd").click(function() {
			moduleEdit();
		});

		$("#moduleDel").click(function() {
			var ids = $("#datagrid")._datagrid("getChecked", function(row) {
				return !(row.id > 0) ? row._id : null;
			});

			moduleDel(ids);
		});

		$("#menuDel").click(function() {
			var ids = $("#datagrid")._datagrid("getChecked", function(row) {
				return row.id > 0 ? row.id : null;
			});

			menuDel(ids);
		});

		$("#refresh").click(function() {
			$("#datagrid")._datagrid("reload");
		});
	});
</script>

</head>
<body>
	<div id="wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">模块维护</h1>
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
											<button id="moduleAdd" type="button" class="btn btn-default">新增模块</button>
											<button id="moduleDel" type="button"
												class="btn btn-default">删除模块</button>
											<button id="menuDel" type="button" class="btn btn-default">删除菜单</button>
											<button id="refresh" type="button" class="btn btn-default">刷新</button>
										</div>
									</div>
								</div>
							</div>

							<table id="datagrid" class="table table-hover table-striped">
								<thead>
									<tr>
										<th data-column="formatter:'checkbox',width:'50px'"></th>
										<th data-column="id:'name',width:'20%'">名称</th>
										<th data-column="id:'url'">链接</th>
										<th data-column="id:'cssClass',width:'20%'">样式</th>
										<th data-column="id:'sequence',width:'20%'">顺序</th>
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