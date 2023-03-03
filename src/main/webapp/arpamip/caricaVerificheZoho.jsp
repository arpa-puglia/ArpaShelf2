<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="it.puglia.arpa.arpashelf.arpamip.service.ArpaMipService"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
		//TEST CODE
		//String testMe = "P.IVA Azienda, Codice Verifica, Stato Verifica, Tecnico Incaricato, Data Richiesta, Data Validazione, Data Rifiuto, Data Accettazione, Data Pagamento, Data Verifica, Data Chiusura, Importo Totae|12345678901,12001258,Chiusa con Oneri Aggiuntivi,Rocco F.,09-02-2012,null,null,null,null,09-02-2012,null,1280.0|05172820721,12003113,Inserita,Raffaele Marrese,10-02-2012,23-02-2012,null,23-02-2012,22-02-2012,24-02-2012,23-02-2012,1000.0|12345678901,12005543,Chiusa,Germinario,10-02-2012,null,null,null,22-02-2012,20-02-2012,23-02-2012,340.67|05830420724,12000125,Chiusa,G. Fasano,20-02-2012,null,null,null,null,21-02-2012,23-02-2012,2500.0|05172820721,12008804,Sospesa,Germinario,21-02-2012,null,null,null,null,14-03-2012,null,1300.45|";
		//InputStream testIs = new ByteArrayInputStream(testMe.getBytes());
		//new ArpaMipService().elabolaVerificheZoho(testIs);
		//END
		
		//PROD CODE
		InputStream requestIs = request.getInputStream();
		new ArpaMipService().elabolaVerificheZoho(requestIs);
%>
      
</body>
</html>