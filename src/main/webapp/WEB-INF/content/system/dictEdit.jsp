<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<s:head />
<style type="text/css">
#wrapper {
	height: 90%
}

.panel {
	height: 100%
}
</style>
</head>
<body>
	<div id="wrapper">
		<div class="panel panel-default">
			<div class="panel-body">
				<s:form role="form">
					<s:hidden name="id"></s:hidden>
					<s:hidden name="type"></s:hidden>
					<dl class="form-group dl-horizontal">
						<dt>
							<label class="control-label" for="sysDict.dictKey">字典键</label>
						</dt>
						<dd>
							<s:textfield name="sysDict.dictKey"
								cssClass="form-control required" maxlength="10" />
						</dd>
					</dl>
					<dl class="form-group dl-horizontal">
						<dt>
							<label class="control-label" for="sysDict.dictValue">字典值</label>
						</dt>
						<dd>
							<s:textfield name="sysDict.dictValue"
								cssClass=" form-control required" maxlength="50" />
						</dd>
					</dl>
					<dl class="form-group dl-horizontal">
						<dt>
							<label class="control-label" for="sysDict.sequence">顺序</label>
						</dt>
						<dd>
							<s:textfield name="sysDict.sequence"
								cssClass="form-control digits" maxlength="10" />
						</dd>
					</dl>
				</s:form>
			</div>
		</div>
	</div>
	<!-- /#wrapper -->
</body>

</html>
