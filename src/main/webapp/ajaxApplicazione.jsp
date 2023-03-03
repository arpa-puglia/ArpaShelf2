<%@page import="org.json.JSONObject"%>
<%@page import="it.puglia.arpa.service.common.Applicazione"%>
<%@page import="it.puglia.arpa.arpashelf.hbm.ApplicazioneExt"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="it.puglia.arpa.arpashelf.service.ArpaShelfService"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String idApplicazione = request.getParameter("idApplicazione");
ApplicazioneExt app = new ArpaShelfService().getApplicazione(idApplicazione);

JSONObject jsonApp = new JSONObject();
jsonApp.put("testoLogin", app.getTestoLogin());
jsonApp.put("html", app.getHtml());

out.println(jsonApp.toString());
%>