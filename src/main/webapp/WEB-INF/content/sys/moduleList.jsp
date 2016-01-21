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
	function edit(id, parentId) {
		$._edit({
			title : id == null ? "新增菜单" : "编辑菜单",
			url : "menuEdit",
			params : {
				moduleId : $("#module").val(),
				id : id,
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

	function del(id) {
		$._delete({
			url : "menuDelete",
			params : {
				id : id
			},
			success : function() {
				$("#datagrid")._datagrid("reload");
			}
		});
	}

	$(function() {
		$("#datagrid")._datagrid({
			url : "moduleDatagrid",
			load : function(result) {
				var modules = result.data.modules;
				var menus = result.data.menus;
				return modules;
			},
			commands : {
				"command-add" : function() {
					edit(null, $(this).data("id"));
				},
				"command-edit" : function() {
					edit($(this).data("id"));
				},
				"command-delete" : function() {
					del($(this).data("id"));
				}
			}
		});

		$("#add").click(function() {
			edit();
		});

		$("#refresh").click(function() {
			$("#datagrid")._datagrid("reload");
		});

		$("#expand").click(function() {
			$("#datagrid")._datagrid("expandAll");
		});

		$("#collapse").click(function() {
			$("#datagrid")._datagrid("collapseAll");
		});

		$("#module").change(function() {
			$("#datagrid")._datagrid("reload", {
				moduleId : $("#module").val()
			});
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
										<th data-column="id:'index',formatter:'index',width:'200px'">序号</th>
										<th data-column="id:'name',width:'20%'">名称</th>
										<th data-column="id:'url',width:'20%'">链接</th>
										<th data-column="id:'cssClass',width:'20%'">样式</th>
										<th data-column="id:'sequence',width:'20%'">顺序</th>
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