<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<s:head />
</head>
<body>
	<div id="wrapper">
		<div class="panel panel-default">
			<div id="auto-min-height" class="panel-body">
				<s:form role="form" cssClass="form-horizontal">
					<s:hidden name="id"></s:hidden>
					<s:hidden name="type"></s:hidden>
					<div class="form-group">
						<label class="control-label col-xs-2 required"
							for="sysDict.dictKey">字典键</label>
						<div class="col-xs-10">
							<s:textfield name="sysDict.dictKey"
								cssClass="form-control required" maxlength="10" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2 required"
							for="sysDict.dictValue">字典值</label>
						<div class="col-xs-10">
							<s:textfield name="sysDict.dictValue"
								cssClass="form-control required" maxlength="50" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-xs-2" for="sysDict.sequence">顺序</label>
						<div class="col-xs-10">
							<s:textfield name="sysDict.sequence"
								cssClass="form-control digits" maxlength="10" />
						</div>
					</div>
				</s:form>
			</div>
		</div>
	</div>
	<!-- /#wrapper -->
</body>
</html>
