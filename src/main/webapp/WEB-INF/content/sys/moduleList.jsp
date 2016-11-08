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

	function refresh() {
		$("#datagrid")._datagrid("reload");
	}

	function moduleEdit(row) {
		$._edit({
			title : row ? "编辑模块" : "新增模块",
			url : "moduleEdit",
			params : {
				id : row ? row.moduleId : null
			},
			saveUrl : "moduleSave",
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

	function moduleDel(rows) {
		var ids = $.map(rows, function(e, i) {
			if (!(e.id > 0)) {
				return e.moduleId;
			}
		});

		$._delete({
			url : "moduleDelete",
			ids : ids,
			success : function() {
				refresh();
			}
		});
	}

	function menuEdit(row, moduleId, parentId) {
		$._edit({
			title : row ? "编辑菜单" : "新增菜单",
			url : "menuEdit",
			params : {
				id : row ? row.id : null,
				moduleId : moduleId,
				parentId : parentId,
			},
			saveUrl : "menuSave",
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

	function menuDel(rows) {
		var ids = $.map(rows, function(e, i) {
			if (e.id > 0) {
				return e.menuId;
			}
		});

		$._delete({
			url : "menuDelete",
			ids : ids,
			success : function() {
				refresh();
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
					e.moduleId = e.id;
					e.id = "module-" + e.id;
				});

				var menus = result.data.menus.rows;

				$.each(menus, function(i, e) {
					e.menuId = e.id;
					e.trStyle = "color:#006699";

					if (e.parentId == null) {
						e.parentId = "module-" + e.moduleId;
					}
				});

				return {
					rows : $._tree(modules.concat(menus))
				};
			},

			formatters : {
				commands : function(row, value, index) {
					var $add = $("<button type='button' class='btn btn-default btn-xs'></button>");
					$add.html("<span class='glyphicon glyphicon glyphicon-plus'></span>");
					$add.click(function() {
						menuEdit(null, row.moduleId, row.menuId);
					});

					var $edit = $("<button type='button' class='btn btn-default btn-xs'></button>");
					$edit.html("<span class='glyphicon glyphicon glyphicon-edit'></span>");
					$edit.click(function() {
						if (row.id > 0) {
							menuEdit(row);
						} else {
							moduleEdit(row);
						}
					});

					var $del = $("<button type='button' class='btn btn-default btn-xs'></button>");
					$del.html("<span class='glyphicon glyphicon glyphicon-trash'></span>");

					$del.click(function() {
						if (row.id > 0) {
							menuDel([ row ]);
						} else {
							moduleDel([ row ]);
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
			moduleDel($("#datagrid")._datagrid("getChecked"));
		});

		$("#menuDel").click(function() {
			menuDel($("#datagrid")._datagrid("getChecked"));
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
											<button id="moduleDel" type="button" class="btn btn-default">删除模块</button>
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