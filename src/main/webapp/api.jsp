<%@page import="org.apache.log4j.Logger"%><%@
page import="java.util.Enumeration"%><%@
page import="java.util.Map"%><%@
page import="java.util.HashMap"%><%@
page import="it.puglia.arpa.arpashelf.util.ArpaShelfConfig"%><%@
page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%><%
	// !!!!!                                                       !!!!!
	// ......FARE ATTENZIONE A NON MANDARE IN OUTPUT NESSUN ACCAPO......
	// !!!!!                                                       !!!!!
	String metodo = request.getParameter("metodo");
	String idApplicazione = request.getParameter("idApplicazione");
	String login = request.getParameter("login");
	String nomeFile = request.getParameter("nomeFile");
	String url = request.getParameter("url");
	String protocolId = request.getParameter("protocolId");
	String secret = request.getParameter("secret");
	String htmlToPdf = ( request.getParameter("HtmlToPdf")!=null && request.getParameter("HtmlToPdf").equals("true") )?"true":"false";
	String htmlToPdf2 = ( request.getParameter("HtmlToPdf2")!=null && request.getParameter("HtmlToPdf2").equals("true") )?"true":"false";
	String secretConf = ArpaShelfConfig.getInstance().getProperty(ArpaShelfConfig.PROP_KEY_AUTH_SECRET);
	
	Logger log = Logger.getLogger("it.arpa.puglia.arpashelf.jsp.api");
	
	//faccio il log dei parametri in ingresso
	Enumeration<String> en = request.getParameterNames();
	while (en.hasMoreElements()) {
		String parName = en.nextElement();
		log.debug("PARAM [" + parName + "]=[" + request.getParameter(parName) + "]");
	}
	
	//controllo il secret
	if (secret==null || !secret.equals(secretConf)) {
		throw new Exception(">>>>>>>>>>> SECRET NON CORRETTO <<<<<<<<<<<<<<");
	}
	
	if (metodo!=null) {

		it.puglia.arpa.arpashelf.service.ArpaShelfService shelfSvc = new it.puglia.arpa.arpashelf.service.ArpaShelfService();
		
		/////////////////////////////////////////////////////// GET PASSWORD
		if (metodo.equals("get_password")) {
			if (idApplicazione!=null && login!=null) {
				int idApplicazioneInt = Integer.parseInt(idApplicazione); 
				String pass = shelfSvc.getPasswordAccountInApplicazione(idApplicazioneInt, login);
				
				if (pass!=null) {
					out.print(pass);
				} else {
					out.print(" ");
				}
			} else {
				throw new Exception("idApplicazione e login obbligatori");
			}
		}
		/////////////////////////////////////////////////////// GOOGLE URL SHORTENER
		else if (metodo.equals("google_url_shortener")) {
			if (url!=null) {
				String retVal = shelfSvc.googleUrlShortener(url);
				out.print(retVal);
			} else {
				out.print("url obbligatorio");
			}
		}
		/////////////////////////////////////////////////////// PUT FILE
		else if (metodo.equals("put_file")) {
			if (idApplicazione!=null && login!=null && nomeFile!=null && url!=null) {
				int idApplicazioneInt = Integer.parseInt(idApplicazione); 
				String retVal = shelfSvc.putFile(url, idApplicazioneInt, login, nomeFile, htmlToPdf);
				log.info("PUTFILE RETURN: [" + retVal + "]");
				out.print(retVal);
			} else {
				out.print("idApplicazione, login, url, nomeFile obbligatori");
			}
		}
		/////////////////////////////////////////////////////// PUT FILE UPDATE
		else if (metodo.equals("put_file_upd")) {
			if (idApplicazione!=null && login!=null && nomeFile!=null && url!=null) {
				int idApplicazioneInt = Integer.parseInt(idApplicazione); 
				String retVal = shelfSvc.putFileUpd(url, idApplicazioneInt, login, nomeFile, htmlToPdf);
				out.print(retVal);
			}
			else {
				out.print("idApplicazione e login obbligatori");
			}
		}
		/////////////////////////////////////////////////////// PUT FILE FROM PROTOCOL
		else if (metodo.equals("put_file_from_protocol")) {
			if (idApplicazione!=null && login!=null && nomeFile!=null && protocolId!=null
				&& request.getParameter("UserName")!=null
				&& request.getParameter("Password")!=null
				) {
				int idApplicazioneInt = Integer.parseInt(idApplicazione);
				String UserName = request.getParameter("UserName");
				String Password = request.getParameter("Password");
				String retVal = shelfSvc.putFileFromProtocol(UserName, Password, protocolId, idApplicazioneInt, login, nomeFile);
				log.info("PUTFILEFROMPROTOCOL RETURN: [" + retVal + "]");
				out.print(retVal);
			} else {
				out.print("idApplicazione, login, protocolId, nomeFile, UserName, Password obbligatori");
			}
		}
		/////////////////////////////////////////////////////// GET FILE
		else if (metodo.equals("get_file")) {
			if (idApplicazione!=null && login!=null && nomeFile!=null) {
				int idApplicazioneInt = Integer.parseInt(idApplicazione); 
				String retVal = shelfSvc.getFile(idApplicazioneInt, login, nomeFile);
				out.print(retVal);
			}
			else {
				out.print("idApplicazione, login, nomeFile obbligatori");
			}
		}
		/////////////////////////////////////////////////////// VERIFY FILE
		else if (metodo.equals("verify_file")) {
			if (idApplicazione!=null && login!=null && nomeFile!=null) {
				int idApplicazioneInt = Integer.parseInt(idApplicazione); 
				String retVal = shelfSvc.verifyFile(idApplicazioneInt, login, nomeFile);
				log.info("VERIFYFILE RETURN: [" + retVal + "]");
				out.print(retVal);
			}
			else {
				out.print("idApplicazione, login, nomeFile obbligatori");
			}
		}
		/////////////////////////////////////////////////////// PROTOCOL FILE
		else if (metodo.equals("protocol_file")) {
/*
url => <url_del_file_da_caricare>
PARAMETRI PROTOCOLLO
Subject => '__Oggetto__'
Note => '__note__'
BusinessUnitID => 10000
SourceID => 30000
CategoryID => 340000
TitolarioID => 200000
TypeID => 10000000
SenderUORs => 1,2,3,4....       <<<<<< ELENCO UO SEPARATE DA VIRGOLA
RecipientUORs => 5,6,7.......   <<<<<< ELENCO UO SEPARATE DA VIRGOLA
ShowCase => true
IsSubscribed =>true,
Inventory => 123,
IsPublic => false,
FileName => 'test.txt',
FileType => 2,
IsSigned => false,
CheckOriginality => false,
/////////questi non stavano nel file della documentazione ma sono obbligatori
ProtocolDocument => false,
SignDate => '2012-12-04T00:00:00'
			*/
			
			if (url!=null
				//parametri login
				&& request.getParameter("UserName")!=null
				&& request.getParameter("Password")!=null
				//
				&& request.getParameter("Subject")!=null
				&& request.getParameter("Note")!=null
				&& request.getParameter("BusinessUnitID")!=null
				&& request.getParameter("SourceID")!=null
				&& request.getParameter("CategoryID")!=null
				&& request.getParameter("TitolarioID")!=null
				&& request.getParameter("TypeID")!=null
				&& request.getParameter("SenderUORs")!=null // ELENCO UO SEPARATE DA VIRGOLA
				&& request.getParameter("RecipientUORs")!=null // ELENCO UO SEPARATE DA VIRGOLA
				&& request.getParameter("ShowCase")!=null
				&& request.getParameter("IsSubscribed")!=null
				&& request.getParameter("Inventory")!=null
				&& request.getParameter("IsPublic")!=null
				&& request.getParameter("FileName")!=null
				&& request.getParameter("FileType")!=null
				&& request.getParameter("IsSigned")!=null
				&& request.getParameter("CheckOriginality")!=null
				/////////questi non stavano nel file della documentazione ma sono obbligatori
				&& request.getParameter("ProtocolDocument")!=null
				&& request.getParameter("SignDate")!=null
			) {
				Map<String, Object> parametriProtocollo = new HashMap<String,Object>();
				
				//parametri login
				parametriProtocollo.put("UserName", request.getParameter("UserName"));
				parametriProtocollo.put("Password", request.getParameter("Password"));
				//
				parametriProtocollo.put("Subject", request.getParameter("Subject"));
				parametriProtocollo.put("Note", request.getParameter("Note"));
				parametriProtocollo.put("BusinessUnitID", request.getParameter("BusinessUnitID"));
				parametriProtocollo.put("SourceID", request.getParameter("SourceID"));
				parametriProtocollo.put("CategoryID", request.getParameter("CategoryID"));
				parametriProtocollo.put("TitolarioID", request.getParameter("TitolarioID"));
				parametriProtocollo.put("TypeID", request.getParameter("TypeID"));
				parametriProtocollo.put("SenderUORs", request.getParameter("SenderUORs"));
				parametriProtocollo.put("RecipientUORs", request.getParameter("RecipientUORs"));
				parametriProtocollo.put("ShowCase", request.getParameter("ShowCase")); //boolean
				parametriProtocollo.put("IsSubscribed", request.getParameter("IsSubscribed")); //boolean
				parametriProtocollo.put("Inventory", request.getParameter("Inventory"));
				parametriProtocollo.put("IsPublic", request.getParameter("IsPublic")); //boolean
				parametriProtocollo.put("FileName", request.getParameter("FileName"));
				parametriProtocollo.put("FileType", request.getParameter("FileType"));
				parametriProtocollo.put("IsSigned", request.getParameter("IsSigned")); //boolean
				parametriProtocollo.put("CheckOriginality", request.getParameter("CheckOriginality")); //boolean
				/////////questi non stavano nel file della documentazione ma sono obbligatori
				parametriProtocollo.put("ProtocolDocument", request.getParameter("ProtocolDocument")); //boolean
				parametriProtocollo.put("SignDate", request.getParameter("SignDate")); //deve avere questo formato: 2012-12-04T00:00:00
				
				/////////indirizzo mittente/destinatario (custom address)
				parametriProtocollo.put("A_Address", request.getParameter("A_Address"));
				parametriProtocollo.put("A_BusinessUnit", request.getParameter("A_BusinessUnit"));
				parametriProtocollo.put("A_City", request.getParameter("A_City"));
				parametriProtocollo.put("A_Email", request.getParameter("A_Email"));
				parametriProtocollo.put("A_OrganizationCode", request.getParameter("A_OrganizationCode"));
				parametriProtocollo.put("A_OrganizationName", request.getParameter("A_OrganizationName"));
				parametriProtocollo.put("A_Phone", request.getParameter("A_Phone"));
				parametriProtocollo.put("A_ZIPCode", request.getParameter("A_ZIPCode"));
				
				String[] urls = new String[]{url};
				String[] htmlToPdfs = new String[]{htmlToPdf};
				if (request.getParameter("url2") != null) {
					urls = new String[]{url, request.getParameter("url2")};
					htmlToPdfs = new String[]{htmlToPdf, htmlToPdf2};
				}
				
				String retVal = shelfSvc.protocolFile(urls, htmlToPdfs, parametriProtocollo);
				log.info("PROTOCOLFILE RETURN: [" + retVal + "]");
				out.print(retVal);
			} else {
				out.print("url, " +
					"UserName, Password, Subject, Note, BusinessUnitID, SourceID, CategoryID, TitolarioID, TypeID, SenderUORs, RecipientUORs, "+
					"ShowCase, IsSubscribed, Inventory, IsPublic, FileName, FileType, IsSigned, CheckOriginality, " +
					"ProtocolDocument, SignDate OBBLIGATORI");
			}
		}
		/////////////////////////////////////////////////////// JOTFORMPOSTURLMAIL
		else if (metodo.equals("jotform_post_url_mail_zoho")) {
			String retVal = shelfSvc.jotformPostUrlMailZoho(request.getParameterMap());
			out.print(retVal);
		}
		
		
	}
	else {
		throw new Exception("parametro metodo mancante");
	}
%>