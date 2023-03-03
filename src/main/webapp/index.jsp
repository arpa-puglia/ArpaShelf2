<%@page import="it.puglia.arpa.arpashelf.util.ArpaShelfConfig"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="it.puglia.arpa.arpashelf.service.ArpaShelfService"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>ArpaShelf Panel</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<!-- css -->
	<link type="text/css" href="css/jquery/start/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
	<link type="text/css" href="css/ArpaShelf.css" rel="stylesheet" />
			
	<!-- js -->
	<script type="text/javascript" src="js/jquery/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.validate.messages_it.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="js/ckeditor/adapters/jquery.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$('#textareaCreaApplicazione').ckeditor();
			$('#textareaModificaApplicazione').ckeditor();
			$('input[type=submit]').button();
			$('#alert').dialog({width: 500});
			$('#error').dialog({width: 500});
			$('#formCreaCartellaApplicazione').validate();
			$('#formModificaCartellaApplicazione').validate();
			$('#formEliminaCartellaApplicazione').validate();
			$('#formCreaCartellaAccount').validate();
			$('#formEliminaCartellaAccount').validate();
			$('#tabs').tabs();
			
			$('#selectApplicazioneModificaApplicazione').change(function(){
				var idApplicazione = $('#selectApplicazioneModificaApplicazione').val();
				
				if (idApplicazione) {
					$.ajax({
						data: {idApplicazione: idApplicazione},
						url: "ajaxApplicazione.jsp",
						success: function(data) {
							$("#testoLoginModificaApplicazione").val(data.testoLogin);
							$("#textareaModificaApplicazione").val(data.html);
						},
						error: function(data) {
							alert("Errore nel caricare i dati dell'applicazione");
							$("#testoLoginModificaApplicazione").val("");
							$("#textareaModificaApplicazione").val("");
							$('#selectApplicazioneModificaApplicazione').val("");
						}
					});
				}
				else {
					$("#testoLoginModificaApplicazione").val("");
					$("#textareaModificaApplicazione").val("");
					$('#selectApplicazioneModificaApplicazione').val("");
				}
			});
			
			$('#formCercaCartelleAccount').validate({
				submitHandler: function() {
					var idApplicazione = $("#selectCercaCartelleAccount").val();
					
					if (!($('#tableCercaCartelleAccount').attr("dataTableInitialized"))) {
						$('#tableCercaCartelleAccount').dataTable( {
							"bJQueryUI": true,
							"bProcessing": false,
							"bfilter": false,
							"bServerSide": true,
							"sAjaxSource": "ajaxCercaCartelleAccount.jsp?idApplicazione=" + idApplicazione,
							"sPaginationType": "full_numbers",
							"oLanguage": {
								oPaginate: {
							            sFirst: "Inizio",
							            sLast: "Fine",
							            sNext: "Avanti",
							            sPrevious: "Indietro"            	
								},
								sEmptyTable: "Nessun risultato trovato",
								sInfo: "Trovate _TOTAL_ cartelle (da _START_ a _END_)",
								sInfoEmpty: "Nessun risultato trovato",
								sLengthMenu: "Visualizza _MENU_ righe per pagina",
								sLoadingRecords: "attendere prego...",
								sProcessing: "DataTables is currently busy",
								sSearch: "Cerca:",
								sZeroRecords: "Nessun risultato trovato"
							}
						} );
						
						
						$('#tableCercaCartelleAccount').show();
						$('#tableCercaCartelleAccount').attr("dataTableInitialized","true");
					} else {
						var oTable = $('#tableCercaCartelleAccount').dataTable();
						oSettings = oTable.fnSettings();
						oSettings.sAjaxSource = "ajaxCercaCartelleAccount.jsp?idApplicazione=" + idApplicazione;
						oTable.fnDraw();
					}
				}
			});
			
			

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
//controllo se sono loggato corretamente
if (authenticated) {
%>

	<%gestisciAzione(request, session, out);%>
		
	<%
	if (isZ(request, session)) { out.println("<div id=\"Z\" style=\"display:none\">"); }
	%>
	
	<!-- UTENTE AUTENTICATO SU BOX.NET -->
	<b>ArpaShelf Administration</b>
		
	<div id="tabs">
	
		<ul>
			<li><a href="#tabs-1" class="shelf-admin-tab-title">Crea shelf applicazione</a></li>
			<li><a href="#tabs-2" class="shelf-admin-tab-title">Modifica shelf applicazione</a></li>
			<li><a href="#tabs-3" class="shelf-admin-tab-title">Elimina shelf applicazione</a></li>
			<li><a href="#tabs-4" class="shelf-admin-tab-title">Crea folder account</a></li>
			<li><a href="#tabs-5" class="shelf-admin-tab-title">Elimina folder account</a></li>
			<li><a href="#tabs-6" class="shelf-admin-tab-title">Cerca folder account</a></li>
		</ul>
	
		<div id="tabs-1">
			<form id="formCreaCartellaApplicazione" method="post" action="index.jsp">
				<!-- CREAZIONE CARTELLA APPLICAZIONE -->
				<input type="hidden" name="azione" value="creaCartellaApplicazione">
				
				<div class="campo_container">
					<span class="etichetta">applicazione:</span>
					<select name="idApplicazione" class="required campo">
						<%=new ArpaShelfService().costruisciOptionSelectApplicazioni()%>
					</select>
				</div>
				
				<div class="campo_container">
					<span class="etichetta">testo login:</span>
					<input type="text" name="testoLogin" class="required campo">
				</div>
				
				<div><b>Testo di presentazione applicazione</b></div>
				
				<textarea name="htmlApplicazione" rows="" cols="" id="textareaCreaApplicazione"></textarea>
					
				<input type="submit" value="crea"/>
				
			</form>
		</div>
		
		<div id="tabs-2">
			<form id="formModificaCartellaApplicazione" method="post" action="index.jsp">
				<!-- MODIFICA CARTELLA APPLICAZIONE -->
				<input type="hidden" name="azione" value="modificaCartellaApplicazione">
				
				<div class="campo_container">
					<span class="etichetta">applicazione:</span>
					<select name="idApplicazione" class="required campo" id="selectApplicazioneModificaApplicazione">
						<%=new ArpaShelfService().costruisciOptionSelectApplicazioni()%>
					</select>
				</div>
				
				<div class="campo_container">
					<span class="etichetta">testo login:</span>
					<input type="text" name="testoLogin" class="required campo" id="testoLoginModificaApplicazione">
				</div>
				
				<div><b>Testo di presentazione applicazione</b></div>
				
				<textarea name="htmlApplicazione" rows="" cols="" id="textareaModificaApplicazione"></textarea>
					
				<input type="submit" value="modifica"/>
				
			</form>
		</div>
		
		<div id="tabs-3">
			<form id="formEliminaCartellaApplicazione" action="index.jsp">
				<!-- ELIMINA CARTELLA APPLICAZIONE -->
				<input type="hidden" name="azione" value="eliminaCartellaApplicazione">
				
				<div class="campo_container">
					<span class="etichetta">applicazione:</span>
					<select name="idApplicazione" class="required campo">
						<%=new ArpaShelfService().costruisciOptionSelectApplicazioni()%>
					</select>
				</div>
				
				<input type="submit" value="elimina"/>		
			</form>
		</div>
		
		<div id="tabs-4">
			<form id="formCreaCartellaAccount" action="index.jsp">
				<!-- CREAZIONE CARTELLA ACCOUNT -->
				<input type="hidden" name="azione" value="creaCartellaAccount">
				
				<div class="campo_container">
					<span class="etichetta">applicazione:</span>
					<select name="idApplicazione" class="required campo">
						<%=new ArpaShelfService().costruisciOptionSelectApplicazioni()%>
					</select>
				</div>
				
				<div class="campo_container">
					<span class="etichetta">nome folder:</span>
					<input type="text" name="account_nome" class="required campo">
				</div>
				
				<div class="campo_container">
					<span class="etichetta">login:</span>
					<input type="text" name="account_login" class="required campo">
				</div>
				
				<div class="campo_container">
					<span class="etichetta">password:</span>
					<input type="text" name="account_password" class="required campo">
				</div>
				
				<div class="campo_container">
					<span class="etichetta">mail:</span>
					<input type="text" name="account_mail" class="required email campo">
				</div>
				
				<input type="submit" value="crea"/>
			</form>
		</div>
		
		<div id="tabs-5">
			<form id="formEliminaCartellaAccount" action="index.jsp">
				<!-- ELIMINAZIONE CARTELLA ACCOUNT -->
				<input type="hidden" name="azione" value="eliminaCartellaAccount">
				
				<div class="campo_container">
					<span class="etichetta">applicazione:</span>
					<select name="idApplicazione" class="required campo">
						<%=new ArpaShelfService().costruisciOptionSelectApplicazioni()%>
					</select>
				</div>
				
				<div class="campo_container">
					<span class="etichetta">login:</span>
					<input type="text" name="account_login" class="required campo">
				</div>
				
				<input type="submit" value="elimina"/>
			</form>
		</div>
		
		<div id="tabs-6">
			<form id="formCercaCartelleAccount" action="index.jsp">
				<!-- RICERCA CARTELLA ACCOUNT -->
				<div class="campo_container">
					<input type="hidden" name="azione" value="cercaCartelleAccount">
					applicazione: <select id="selectCercaCartelleAccount" name="idApplicazione" class="required" autocomplete="off">
						<%=new ArpaShelfService().costruisciOptionSelectApplicazioni()%>
					</select>
				</div>
				<input type="submit" value="cerca"/>
				
			</form>
			
			<div style="width: 600px; margin-top: 10px">
				<table id="tableCercaCartelleAccount" style="display:none" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th>folder</th>
							<th>login</th>
							<th>password</th>
							<th>mail</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
			
		</div>
		
	</div> <!-- div tabs -->
	
	<%
	if (isZ(request, session)) { out.println("</div>"); }
	%>
	
<% } %>
	
<%-- vedere se serve
session.setAttribute("azione", request.getParameter("azione"));
session.setAttribute("idApplicazione", request.getParameter("idApplicazione"));
session.setAttribute("account_login", request.getParameter("account_login"));
session.setAttribute("account_password", request.getParameter("account_password"));
session.setAttribute("account_nome", request.getParameter("account_nome"));
--%>

</body>
</html>

<%!
//FUNZIONI

public static void gestisciAzione(HttpServletRequest request, HttpSession session, JspWriter out) throws Exception {
	try {
		//String azione = request.getParameter("azione");
		String azione = (request.getParameter("azione")!=null)?request.getParameter("azione"):(String)session.getAttribute("azione");
		if (azione!=null) {
			if (azione.equals("creaCartellaApplicazione")) {
				new ArpaShelfService().creaCartellaApplicazione(request.getParameter("idApplicazione"), request.getParameter("htmlApplicazione"), request.getParameter("testoLogin"));
				out.println("<div id=\"alert\">Shelf applicazione creato correttamente</div>");
			}
			else if (azione.equals("modificaCartellaApplicazione")) {
				new ArpaShelfService().modificaCartellaApplicazione(request.getParameter("idApplicazione"), request.getParameter("htmlApplicazione"), request.getParameter("testoLogin"));
				out.println("<div id=\"alert\">Shelf applicazione modificata correttamente</div>");
			}
			else if (azione.equals("eliminaCartellaApplicazione")) {
				new ArpaShelfService().eliminaCartellaApplicazione(request.getParameter("idApplicazione"));
				out.println("<div id=\"alert\">Shelf applicazione eliminato correttamente</div>");
			}
			else if (azione.equals("creaCartellaAccount") || azione.equals("creaCartellaAccountZ")) {
				String _idApplicazione = (request.getParameter("idApplicazione")!=null)?request.getParameter("idApplicazione"):(String)session.getAttribute("idApplicazione");
				String _account_login = (request.getParameter("account_login")!=null)?request.getParameter("account_login"):(String)session.getAttribute("account_login");
				String _account_nome = (request.getParameter("account_nome")!=null)?request.getParameter("account_nome"):(String)session.getAttribute("account_nome");
				String _account_password =  (request.getParameter("account_password")!=null)?request.getParameter("account_password"):(String)session.getAttribute("account_password");
				String _account_mail =  (request.getParameter("account_mail")!=null)?request.getParameter("account_mail"):(String)session.getAttribute("account_mail");
				
				String publicName = new ArpaShelfService().creaCartellaAccountInApplicazione(_idApplicazione, _account_login, _account_nome, _account_password, _account_mail);
				out.println("<div id=\"alert\">");
				
					out.println("Folder account creato correttamente, codice condivisione: ");
					out.println("<span style=\"color:green;font-weight:bold;\">" + publicName + "</span>");
					
				out.println("</div>");
			}
			else if (azione.equals("eliminaCartellaAccount")) {
				new ArpaShelfService().eliminaCartellaAccountInApplicazione(request.getParameter("idApplicazione"), request.getParameter("account_login"));
				out.println("<div id=\"alert\">Folder account eliminato correttamente</div>");
			}
		}
	}
	catch(Exception e) {
		out.println("<div id=\"error\" style=\"font-weight:bold;color:red;\">ERRORE: ");
		out.println(e.getLocalizedMessage());
		
			out.println("<!--");
			e.printStackTrace(new PrintWriter(out));
			out.println("-->");
		
		out.println("</div>");
	}
}

public static boolean isZ(HttpServletRequest request, HttpSession session) throws Exception {
	if (request.getParameter("azione")!=null && request.getParameter("azione").endsWith("Z")) {
		return true;
	}
	if (session.getAttribute("azione")!=null && ((String)session.getAttribute("azione")).endsWith("Z")) {
		return true;
	}
	
	return false;
}
	





%>