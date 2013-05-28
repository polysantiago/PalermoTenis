<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta content="text/html; charset=iso-8859-1" http-equiv="Content-Type"/>
		<meta content="es-AR" http-equiv="Content-Language"/>

		<title><tiles:insertAttribute name="title" ignore="true" /></title>

		<script src="<s:url value="/js/lib/AC_RunActiveContent.js" />" type="text/javascript"></script>
		
		<link type="text/css" rel="stylesheet" href="/css/min/jquery-main.css" />
		<link type="text/css" rel="stylesheet" href="/css/min/main.css" />
		
		<link type="image/x-icon" rel="shortcut icon" href="/images/favicon.ico" />
		<link type="image/x-icon" rel="icon" href="/images/favicon.ico" />
		
		<tiles:importAttribute name="css" ignore="true"/>
		<s:iterator value="#attr.css" var="href">
			<link href="<s:property value="#href"/>" rel="stylesheet" type="text/css" />
		</s:iterator>
	</head>
	<body>
		<div id="todo">
			<tiles:insertAttribute name="header"/>
			<tiles:insertAttribute name="body" ignore="true"/>
		</div>
		
		<tiles:useAttribute name="data" />
		<script src="<s:url value="/js/lib/require.min.js" />" type="text/javascript"></script>
		
		<tiles:importAttribute name="require" ignore="true" />
		<script type="text/javascript">
			<s:if test="#attr.require != null">
				require(["/js/common.js"], function() {
					require(["${require}"]);	
				});
			</s:if>
			<s:else>
				require(["/js/common.js"]);
			</s:else>
		</script>
	</body>
</html>