<%@page import="it.puglia.arpa.arpashelf.service.ArpaShelfService"
%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="java.util.Enumeration"
%><%
String idApplicazione = request.getParameter("idApplicazione");
String codiceAzienda = request.getParameter("codiceazienda");
String _ip = request.getParameter("ip");
String _formID = request.getParameter("formID");
String _submission_id = request.getParameter("submission_id");

new ArpaShelfService().inviaMailRecuperoPassword(idApplicazione, codiceAzienda);

%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Recupero Password</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<!-- css -->
	<link type="text/css" href="css/ArpaShelf.css" rel="stylesheet" />
</head>
<body>
Gentile Utente,<br>
la richiesta &egrave; stata recepita.
</body>
</html>