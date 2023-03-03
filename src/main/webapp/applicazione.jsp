<%@page import="it.puglia.arpa.arpashelf.exception.CartellaEsistenteException"%>
<%@page import="it.puglia.arpa.arpashelf.exception.LoginEsistenteException"%>
<%@page import="it.puglia.arpa.arpashelf.util.ArpaShelfConfig"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="it.puglia.arpa.arpashelf.service.ArpaShelfService"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Applicazione</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<!-- css -->
	<link type="text/css" href="css/jquery/start/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
	<link type="text/css" href="css/ArpaShelf.css" rel="stylesheet" />
	
	<!-- js -->
	<script type="text/javascript" src="js/jquery/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.validate.messages_it.js"></script>
	
	<%
	String ID_APPLICAZIONE = request.getParameter("idApplicazione");
	%>
	
	<script type="text/javascript">
		var ID_APPLICAZIONE_MIP = <%=ID_APPLICAZIONE_MIP%>;
		
		$(document).ready(function() {
			$('input[type=submit]').button();
			$('#alert').dialog({width: 500});
			$('#error').dialog({width: 500});
			$('#formCreaCartellaAccount').validate();
			$('#formEliminaCartellaAccount').validate({
				submitHandler: function() {
					var idApplicazione = <%=ID_APPLICAZIONE%>;
					var login = $("#formEliminaLogin").val();
					
					$.ajax({
						data: {login: login, idApplicazione: idApplicazione},
						url: "ajaxCartellaAccount.jsp",
						success: function(data) {
							var nomeCartella = data.nomeCartella;

							if (typeof nomeCartella != 'undefined') { //controllo che esiste la cartella per l'account
								var testoConfirm = "Stai eliminando il fascicolo " + nomeCartella + " (" + login + "). Sei sicuro?";
								if (idApplicazione == ID_APPLICAZIONE_MIP) {
									testoConfirm = "Stai eliminando il fascicolo della ditta " + nomeCartella + " (" + login + "). Sei sicuro?";
								}
								
								if (confirm(testoConfirm)) {
									//faccio l'unbind del submit, altrimenti ritorno in questo submit handler
									$('#formEliminaCartellaAccount').unbind('submit');
									//ora faccio il submit classico del form
									$('#formEliminaCartellaAccount').submit();
								}
							}
							else {
								var testoAlert = "Account non presente, impossibile eliminare";
								if (idApplicazione == ID_APPLICAZIONE_MIP) {
									testoAlert = "Ditta non presente, impossibile eliminare";
								}
								alert(testoAlert);
							}
							
						},
						error: function(data) {
							alert("Errore nel caricare i dati della cartella utente");
						}
					});
				}
			});
			$('#tabs').tabs();
		});
	</script>
</head>
<body>

<%
String SECRET = ArpaShelfConfig.getInstance().getProperty(ArpaShelfConfig.PROP_KEY_AUTH_SECRET);
boolean authenticated = false;
if (request.getParameter("secret")!=null && request.getParameter("secret").equals(SECRET)) {
	session.setAttribute("__AUTH_OK__", "yes");
	authenticated = true;
}
if (session.getAttribute("__AUTH_OK__")!=null) {
	authenticated = true;
}
//controllo se sono loggato correttamente
if (authenticated) {
	%>
	<!-- UTENTE AUTENTICATO SU BOX.NET -->
	
	<%gestisciAzione(request, out, ID_APPLICAZIONE);%>
	
	<div id="tabs">
		
		<%
		String labelCartellaAccount = "folder account";
		String labelCartella = "folder";
		String labelLogin = "Login";
		if(ID_APPLICAZIONE_MIP.equals(ID_APPLICAZIONE)){
			labelCartellaAccount = "fascicolo ditta";
			labelCartella = "fascicolo";
			labelLogin = "Partita IVA Ditta";
		}%>
	
		<ul>
			<li><a href="#tabs-1" class="shelf-admin-tab-title">Crea <%=labelCartellaAccount%></a></li>
			<li><a href="#tabs-2" class="shelf-admin-tab-title">Elimina <%=labelCartellaAccount%></a></li>
		</ul>
	
		<div id="tabs-1">
			<form id="formCreaCartellaAccount" action="applicazione.jsp">
				<!-- CREAZIONE CARTELLA ACCOUNT -->
				<input type="hidden" name="azione" value="creaCartellaAccount">
				<input type="hidden" name="idApplicazione" value="<%=ID_APPLICAZIONE%>">
				
				<div class="campo_container">
					<span class="etichetta">Nome <%=labelCartella%>:</span> 
					<input type="text" name="account_nome" class="required campo">
				</div>
				
				<div class="campo_container">
					<span class="etichetta"><%=labelLogin%>:</span>
					<input type="text" name="account_login" class="required campo">
				</div>
				
				<div class="campo_container">
					<span class="etichetta">Password:</span>
					<input type="text" name="account_password" class="required campo">
				</div>
				
				<div class="campo_container">
					<span class="etichetta">Mail:</span>
					<input name="account_mail" class="required email campo">
				</div>
				
				<input type="submit" value="crea"/>
			</form>
		</div>
		
		<div id="tabs-2">
			<form id="formEliminaCartellaAccount" action="applicazione.jsp">
				<!-- ELIMINAZIONE CARTELLA ACCOUNT -->
				<input type="hidden" name="azione" value="eliminaCartellaAccount">
				<input type="hidden" name="idApplicazione" value="<%=ID_APPLICAZIONE%>">
				
				<div class="campo_container">
					<span class="etichetta"><%=labelLogin%>:</span>
					<input id="formEliminaLogin" type="text" name="account_login" class="required campo">
				</div>
				
				<input type="submit" value="elimina"/>
				
			</form>
		</div>
		
	</div>
	
<% } %>
</body>
</html>

<%!
//FUNZIONI
public static final String ID_APPLICAZIONE_MIP="5";

public static void gestisciAzione(HttpServletRequest request, JspWriter out, String idApplicazione) throws Exception {
	try {
		String azione = request.getParameter("azione");
		if (azione!=null) {
			if (azione.equals("creaCartellaAccount")) {
				String publicName = new ArpaShelfService().creaCartellaAccountInApplicazione(idApplicazione, request.getParameter("account_login"), request.getParameter("account_nome"), request.getParameter("account_password"), request.getParameter("account_mail"));
				out.println("<div id=\"alert\">");
				
					out.println("Folder creato correttamente, codice condivisione: ");
					out.println("<span style=\"color:green;font-weight:bold;\">" + publicName + "</span>");
					
				out.println("</div>");
			}
			else if (azione.equals("eliminaCartellaAccount")) {
				new ArpaShelfService().eliminaCartellaAccountInApplicazione(idApplicazione, request.getParameter("account_login"));
				out.println("<div id=\"alert\">Folder eliminato correttamente</div>");
			}
		}
	}
	catch(LoginEsistenteException e1) {
		if (idApplicazione.equals(ID_APPLICAZIONE_MIP)) {
			out.println("<div id=\"error\" style=\"font-weight:bold;color:red;\">");
			out.println("Attenzione: nome fascicolo o codice pratica gia' esistenti. Si prega di modificare");
			out.println("</div>");
		}
		else {
			outEccezioneGenerica(out, e1);
		}
	}
	catch(CartellaEsistenteException e2) {
		if (idApplicazione.equals(ID_APPLICAZIONE_MIP)) {
			out.println("<div id=\"error\" style=\"font-weight:bold;color:red;\">");
			out.println("Attenzione: nome fascicolo o codice pratica gia' esistenti. Si prega di modificare");
			out.println("</div>");
		} else {
			outEccezioneGenerica(out, e2);
		}
	}
	catch(Exception e) {
		outEccezioneGenerica(out, e);
	}
}
	
public static void outEccezioneGenerica(JspWriter out, Exception e) throws Exception {
	out.println("<div id=\"error\" style=\"font-weight:bold;color:red;\">ERRORE: ");
	out.println(e.getLocalizedMessage());
	
		out.println("<!--");
		e.printStackTrace(new PrintWriter(out));
		out.println("-->");
	
	out.println("</div>");
}





%>