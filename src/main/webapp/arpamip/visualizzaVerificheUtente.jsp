<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="it.puglia.arpa.arpashelf.arpamip.hbm.Verifica"%>
<%@page import="java.util.List"%>
<%@page import="it.puglia.arpa.arpashelf.arpamip.service.ArpaMipService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Verifiche utente</title>
	
	<!-- css -->
	<link type="text/css" href="../css/jquery/start/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
	<link type="text/css" href="../css/ArpaShelf.css" rel="stylesheet" />
			
	<!-- js -->
	<script type="text/javascript" src="../js/jquery/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="../js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="../js/jquery/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/jquery/jquery.validate.messages_it.js"></script>
	<script type="text/javascript" src="../js/jquery/jquery.dataTables.min.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			$('#tableVerifiche').dataTable( {
				"bJQueryUI": true,
				"bProcessing": false,
				"bfilter": false,
				"bServerSide": true,
				"sAjaxSource": "visualizzaVerificheUtenteAjax.jsp?login=<%=request.getParameter("login")%>&password=<%=request.getParameter("password")%>",
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
				
		});
	</script>
	
	<style>
		TABLE {
			border-bottom: 1px solid gray;
			border-right: 1px solid gray;
			width: 100%;
			font-size: 110%;
		}
		TH, TD {
			border-top: 1px solid gray;
			border-left: 1px solid gray;
			padding: 3px;
		}
		.titoloTabella {
			font-weight:bold;
			padding:0px;
			font-size:110%;
		}
		#tableVerifiche_wrapper {
			margin-top:10px;
			margin-bottom:10px;
		}
		.linkLegenda {
			padding: 0px;
		}
	</style>

</head>
<body>
<div class="titoloTabella">Sintesi Verifiche Ditta <%=request.getParameter("login")%> alla data <%=new SimpleDateFormat("dd/MM/yyyy").format(new Date())%></div>
<%
Long numVer = new ArpaMipService().getNumeroTotaleVerificheUtente(request.getParameter("login"), request.getParameter("password"));

if (numVer!=null) {
	if (numVer.longValue() > 0) { 
	%>
		<table id="tableVerifiche" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<th>Codice Verifica</th>
					<th>Stato Verifica</th>
					<th>Tecnico Incaricato</th>
					<th>Data Richiesta</th>
					<th>Data Validazione</th>
					<th>Data Rifiuto</th>
					<th>Data Accettazione</th>
					<th>Data Pagamento</th>
					<th>Data Verifica</th>
					<th>Data Chiusura</th>
					<th>Importo Totale</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		
		<a class="linkLegenda" href="http://arpamip.weebly.com/legenda_stati.html" target="_blank">Legenda Stati Verifica</a>
	<% } else { %>
		Nessuna verifica trovata
	<% } %>
<% } else { %>
	La password inserita non &egrave; corretta
<% } %>

</body>
</html>