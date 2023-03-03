<%@page import="it.puglia.arpa.arpashelf.util.ArpaShelfConfig"%>
<%@page import="com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException"%>
<%@page import="it.puglia.arpa.service.common.Applicazione"%>
<%@page import="it.puglia.arpa.arpashelf.hbm.ApplicazioneExt"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="it.puglia.arpa.arpashelf.service.ArpaShelfService"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:ajxp>
<head>
	<title>ArpaShelf - Scaffale Utente</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<!-- css -->
	<link type="text/css" href="css/jquery/start/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
	<link type="text/css" href="css/ArpaShelf.css" rel="stylesheet" />
	
	<!-- js -->
	<script type="text/javascript" src="js/jquery/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.validate.messages_it.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$('#formLogin').validate();
			$('#inputVai').button();
			$('.shelf-title').button();
			$('.shelf-error').dialog();
			/*$('#textareaCreaApplicazione').ckeditor();
			$('#alert').dialog({width: 500});
			$('#error').dialog({width: 500});
			$('#formCreaCartellaApplicazione').validate();
			$('#formEliminaCartellaApplicazione').validate();
			$('#formCreaCartellaAccount').validate();
			$('#formEliminaCartellaAccount').validate();*/
			
			//PER TESTARE IL RESIZE
			//window.resizeTo(625,580);
		});
	</script>
	
</head>
<body>
	
	<div class="shelf-container ui-widget ui-widget-content ui-corner-all">
		
		<%
		try {
		%>
		
			<%
			String idApplicazione = request.getParameter("idApplicazione");
			String login = request.getParameter("login");
			
			ApplicazioneExt app = new ArpaShelfService().getApplicazione(idApplicazione);
			Applicazione _app = app.getApplicazione();
			
			//ui-dialog ui-widget ui-widget-content ui-corner-all
			//ui-dialog-titlebar  ui-helper-clearfix
			%>
		
			<div class="shelf-title"><%=_app.getCodice()%> - FASCICOLO ELETTRONICO</div>
			
		<% 
		
			if (request.getParameter("login")==null) { 
				//VISUALIZZO IL FORM DI LOGIN (ho idApplicazione valorizzato)
				%>
				
				<form id="formLogin">
					
					
					<%=app.getHtml()%>
					
					<input type="hidden" name="idApplicazione" value="<%=idApplicazione%>"/>
					
					<!--
					  <%=app.getTestoLogin()%>: <input type="text" name="login" class="required"/>
					-->
					
					<input type="hidden" name="login" value=""/>
					<input id="inputVai" type="submit" value="Accedi al fascicolo"/>
					
				</form>
				
				<%
			}
			else {
				//VISUALIZZO IL WIDGET (ho utente e idApplicazione valorizzati)
				%>
					<iframe width="100%" height="660px" frameborder="0" id="iframeAjxp" src="<%=ArpaShelfConfig.getInstance().getProperty(ArpaShelfConfig.PROP_KEY_AJAXPLORER_URL)%>"></iframe>
				<%
				
			}
			
		} catch(Exception e) {
			out.println("<div class=\"shelf-error\">" + e.getMessage() + "</div>");
		}
		
		
		%>
		
		
		
	</div>
	<%=new ArpaShelfService().costruisciHtmlPersonalizzatoApplicazione(request.getParameter("idApplicazione"), request.getParameterMap())%>
	
</body>
</html>

