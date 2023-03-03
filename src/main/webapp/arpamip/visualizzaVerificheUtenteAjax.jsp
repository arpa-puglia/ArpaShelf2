<%@page import="it.puglia.arpa.arpashelf.arpamip.service.ArpaMipService"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.util.List"%>
<%@page import="it.puglia.arpa.arpashelf.hbm.Cartella"%>
<%@page import="org.json.JSONObject"%>
<%@page import="it.puglia.arpa.service.common.Applicazione"%>
<%@page import="it.puglia.arpa.arpashelf.hbm.ApplicazioneExt"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="it.puglia.arpa.arpashelf.service.ArpaShelfService"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String iDisplayLength = request.getParameter("iDisplayLength");

List<String[]> verifiche = new ArpaMipService().getVerificheUtente(request.getParameter("login"), request.getParameter("password"), request.getParameterMap());
long numTotaleVerifiche = new ArpaMipService().getNumeroTotaleVerificheUtente(request.getParameter("login"), request.getParameter("password"));

JSONObject jsonResponse = new JSONObject();
jsonResponse.put("aaData", verifiche);
jsonResponse.put("iTotalRecords", numTotaleVerifiche);
jsonResponse.put("iTotalDisplayRecords", numTotaleVerifiche);

out.println(jsonResponse.toString());
%>
