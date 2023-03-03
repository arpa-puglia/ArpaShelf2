<%@page import="it.puglia.arpa.arpashelf.hbm.Cartella"%>
<%@page import="org.json.JSONObject"%>
<%@page import="it.puglia.arpa.service.common.Applicazione"%>
<%@page import="it.puglia.arpa.arpashelf.hbm.ApplicazioneExt"%>
<%@page import="it.puglia.arpa.arpashelf.service.ArpaShelfService"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String idApplicazione = request.getParameter("idApplicazione");
String login = request.getParameter("login");

String token = (String)session.getAttribute("auth_token");

Cartella cartAccApp = new ArpaShelfService().getCartellaAccountInApplicazione(idApplicazione, login);

JSONObject jsonApp = new JSONObject();
jsonApp.put("nomeCartella", cartAccApp!=null?cartAccApp.getNome():null);

out.println(jsonApp.toString());
%>