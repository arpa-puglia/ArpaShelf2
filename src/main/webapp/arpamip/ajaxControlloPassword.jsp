<%@page import="it.puglia.arpa.arpashelf.arpamip.service.ArpaMipService"%>
<%@page import="it.puglia.arpa.arpashelf.hbm.Cartella"%>
<%@page import="org.json.JSONObject"%>
<%@page import="it.puglia.arpa.service.common.Applicazione"%>
<%@page import="it.puglia.arpa.arpashelf.hbm.ApplicazioneExt"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="it.puglia.arpa.arpashelf.service.ArpaShelfService"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String login = request.getParameter("login");
String password = request.getParameter("password");

Long numVerifiche = new ArpaMipService().getNumeroTotaleVerificheUtente(login, password);

JSONObject jsonApp = new JSONObject();
jsonApp.put("verifiche", numVerifiche);

out.println(jsonApp.toString());
%>