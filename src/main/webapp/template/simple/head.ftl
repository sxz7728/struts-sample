<#if properties.DEV_MODE>

<link href="${base}/css/bootstrap.css" rel="stylesheet" type="text/css"></link>
<link href="${base}/css/bootstrap-theme.css" rel="stylesheet"
	type="text/css"></link>
<link href="${base}/css/jquery.bootgrid.css" rel="stylesheet"
	type="text/css"></link>
<link href="${base}/css/jquery-ui-1.10.0.custom.css" rel="stylesheet"
	type="text/css"></link>
<link href="${base}/css/sb-admin-2.css" rel="stylesheet" type="text/css">
<link href="${base}/font-awesome-4.1.0/css/font-awesome.css"
	rel="stylesheet" type="text/css">

<link href="${base}/css/default.css" rel="stylesheet" type="text/css"></link>

<script src="${base}/js/jquery-1.11.1.js" type="text/javascript"></script>
<script src="${base}/js/bootstrap.js" type="text/javascript"></script>
<script src="${base}/js/jquery.validate.js" type="text/javascript"></script>
<script src="${base}/js/jquery.form.js" type="text/javascript"></script>
<script src="${base}/js/jquery.bootgrid.js" type="text/javascript"></script>
<script src="${base}/js/jquery-ui.js" type="text/javascript"></script>
<script src="${base}/js/bootbox.js" type="text/javascript"></script>
<script src="${base}/js/notify-combined.js" type="text/javascript"></script>
<script src="${base}/js/doT.js"></script>

<script src="${base}/js/localization/messages_zh.js"></script>
<script src="${base}/js/localization/jquery.ui.datepicker-zh-CN.js"
	type="text/javascript"></script>

<script src="${base}/js/utils.js"></script>
<script src="${base}/js/default.js"></script>

<#else>

<link href="${base}/css/bootstrap.min.css" rel="stylesheet"
	type="text/css"></link>
<link href="${base}/css/bootstrap-theme.min.css" rel="stylesheet"
	type="text/css"></link>
<link href="${base}/css/jquery.bootgrid.min.css" rel="stylesheet"
	type="text/css"></link>
<link href="${base}/css/jquery-ui-1.10.0.custom.css" rel="stylesheet"
	type="text/css"></link>
<link href="${base}/css/sb-admin-2.css" rel="stylesheet" type="text/css">
<link href="${base}/font-awesome-4.1.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<link href="${base}/css/default.css" rel="stylesheet" type="text/css"></link>

<script src="${base}/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${base}/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${base}/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="${base}/js/jquery.form.js" type="text/javascript"></script>
<script src="${base}/js/jquery.bootgrid.min.js" type="text/javascript"></script>
<script src="${base}/js/jquery-ui.min.js" type="text/javascript"></script>
<script src="${base}/js/bootbox.min.js" type="text/javascript"></script>
<script src="${base}/js/notify-combined.min.js" type="text/javascript"></script>
<script src="${base}/js/doT.min.js"></script>

<script src="${base}/js/localization/messages_zh.js"></script>
<script src="${base}/js/localization/jquery.ui.datepicker-zh-CN.min.js"
	type="text/javascript"></script>

<script src="${base}/js/utils.js"></script>
<script src="${base}/js/default.js"></script>

</#if>

<script type="text/javascript">
	var globals = {
		APP_NAME : '${base}',
		DEV_MODE : '${properties.DEV_MODE?string}'
	};
</script>