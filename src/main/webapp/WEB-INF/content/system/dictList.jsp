<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<s:head />
<%@ include file="/include/bootgrid.html"%>
<script type="text/javascript">
	function edit(id) {
		var title = id == null ? "新建字典" : "编辑字典";

		$._edit({
			title : title,
			url : "dictEdit",
			params : {
				id : id
			},
			saveUrl : "dictSave",
			height : 300,
			success : function() {
				if (id == null) {
					$(this).find("iframe")._refresh();
					return false;
				} else {
					$("#grid-data").bootgrid("reload");
					return true;
				}
			},
			cancel : function() {
				$("#grid-data").bootgrid("reload");
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
				$("#grid-data").bootgrid("reload");
			}
		});
	}

	$(function() {
		$("#grid-data").on("loaded.rs.jquery.bootgrid", function(e) {
			$(this).find(".command-edit").on("click", function(e) {
				edit($(this).data("id"));
			});

			$(this).find(".command-delete").on("click", function(e) {
				del($(this).data("id"));
			});

		});

		$("#grid-data")._bootgrid({
			url : "dictDatagrid",
			commandsTemplate : commandsTemplate,
			buttons : {
				"新增" : function() {
					edit();
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
										<th data-column-id="seq" data-formatter="seq"
											data-header-css-class="seq">序号</th>
										<th data-column-id="dictKey">字典键</th>
										<th data-column-id="dictValue">字典值</th>
										<th data-column-id="sequence">顺序</th>
										<th data-column-id="commands" data-header-css-class="commands"
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
