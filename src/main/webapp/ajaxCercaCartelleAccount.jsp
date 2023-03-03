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
String idApplicazione = request.getParameter("idApplicazione");
String iDisplayLength = request.getParameter("iDisplayLength");

List<String[]> cartelle = new ArpaShelfService().getCartelleAccountByIdApplicazione(idApplicazione, request.getParameterMap());
long numTotaleCartelle = new ArpaShelfService().getNumeroTotaleCartelleAccountByIdApplicazione(idApplicazione);

JSONObject jsonResponse = new JSONObject();
jsonResponse.put("aaData", cartelle);
jsonResponse.put("iTotalRecords", numTotaleCartelle);
jsonResponse.put("iTotalDisplayRecords", numTotaleCartelle);
//jsonResponse.put("sEcho", 1);

out.println(jsonResponse.toString());
%>
