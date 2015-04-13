<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<s:head />
<%@ include file="/include/bootgrid.jsp"%>

<script id="typeTemplate" type="text/template">
<s:select name="type" list="findDict(DictUtils.DICT_TYPE)" listKey="key"
	cssClass="form-control" cssStyle="width: 120px;" listValue="value"></s:select>
</script>

<script type="text/javascript">
	function edit(id) {
		$._edit({
			title : id == null ? "新增字典" : "编辑字典",
			url : "dictEdit",
			params : {
				id : id,
				type : $("[name=type]").val()
			},
			saveUrl : "dictSave",
			height : 300,
			success : function() {
				if (id == null) {
					$(this).find("iframe")._refresh();
					return false;
				} else {
					$("#grid-data")._bootgrid("reload");
					return true;
				}
			},
			cancel : function() {
				$("#grid-data")._bootgrid("reload");
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
				$("#grid-data")._bootgrid("reload");
			}
		});
	}

	$(function() {
		$("#datagrid")._bootgrid({
			url : "dictDatagrid",
			searches : {
				"类型" : $("#typeTemplate").html()
			},
			buttons : {
				"新增" : function() {
					edit();
				}
			},
			commands : {
				"command-edit" : function() {
					edit($(this).data("id"));
				},

				"command-delete" : function() {
					del($(this).data("id"));
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
							<table id="datagrid"
								class="table table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th data-column-id="serialNo" data-formatter="serialNo"
											style="width: 100px">序号</th>
										<th data-column-id="dictKey">字典键</th>
										<th data-column-id="dictValue">字典值</th>
										<th data-column-id="sequence">顺序</th>
										<th data-column-id="commands" data-formatter="commands"
											style="width: 100px">操作</th>
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
