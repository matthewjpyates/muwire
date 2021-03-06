<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="initcode.jsi"%>

<% 

String pagetitle=Util._t("Users"); 

%>

<html>
	<head>
<%@ include file="css.jsi"%>
<script src="js/util.js?<%=version%>" type="text/javascript"></script>
<script src="js/tables.js?<%=version%>" type="text/javascript"></script>
<script src="js/trustUsers.js?<%=version%>" type="text/javascript"></script>

	</head>
	<body onload="initTranslate(jsTranslations); initConnectionsCount(); initTrustUsers();">
<%@ include file="header.jsi"%>
	    <aside>
<%@include file="sidebar.jsi"%>    	
	    </aside>
	    <section class="main foldermain">
	    	<h3><%=Util._t("Trusted Users")%></h3>
		    <div id="table-wrapper">
				<div id="table-scroll">
					<div id="trustedUsers"></div>
				</div>
			</div>
			<hr/>
			<h3><%=Util._t("Distrusted Users")%></h3>
			<div id="refresh-link"></div>
			<div id="table-wrapper">
				<div id="table-scroll">
					<div id="distrustedUsers"></div>
				</div>
			</div>
	    </section>
	</body>
</html>
