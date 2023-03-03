onclickBtnVisualizzaVerifiche = function() {
    var form = $("#formVisualizzaVerifiche");
    var login = $("#inputLoginVisualizzaVerifiche").val();
    var password = $("#inputPasswordVisualizzaVerifiche").val();
    
    
    //$(form).submit();
    $.ajax({
		data: {login: login, password: password},
		url: "arpamip/ajaxControlloPassword.jsp",
		success: function(data) {
			if (data.verifiche == undefined) {
				$("<div>La password inserita non e' corretta</div>").dialog();
			}
			else if (data.verifiche == 0) {
				$("<div>Nessuna verifica trovata</div>").dialog();
			}
			else {
				$(form).submit();
			}
		},
		error: function(data) {
			$("<div>Errore nel verificare le credenziali</div>").dialog();
		}
	});
};


$(document).ready(function() {
	//devo controllare se l'utente ha inserito la password
	//se l'utente ha inserito la password e si e' loggato correttamente
	//allora gli faccio vedere la lista delle verifiche
	var checkInterval = "1000";
	
	var func = function() {
		////console.debug("CONTROLLO UTENTE CONNESSO");
		var loggedUser = false;
		var iframeAjxp = frames[0] //$("#iframeAjxp");
		if (iframeAjxp) {
			if (iframeAjxp.ajaxplorer) {
				if (iframeAjxp.ajaxplorer.user) {
					var userid = iframeAjxp.ajaxplorer.user.id;
					if (userid!="guest" && userid!="ad___min") {
						loggedUser = userid;
					}
				}
			}
		}
		
		////console.debug("CONTROLLO UTENTE CONNESSO, UTENTE TROVATO: " + loggedUser);
		
		if (!loggedUser) {
			window.setTimeout(func, checkInterval);
		}
		else {
            var iframeVisualizzaVerifiche = $("#iframeVisualizzaVerificheUtente");
            iframeVisualizzaVerifiche.attr("src", "arpamip/visualizzaVerificheUtente.jsp?login=" + loggedUser)
		}
	};
	
	window.setTimeout(func, checkInterval);

    /////////////////////////////////////////////////////////////////////////////////////////////////	
    
    //devo visualizzare il widget di login, altrimenti l'utente entra come guest
	//e potrebbe non capire subito che deve fare il login per vedere i propri file
	var checkIntervalLogin = "500";
	var funcLogin = function() {
		if (frames[0].ajaxplorer) {
			frames[0].ajaxplorer.actionBar.fireAction('login');
		} else {
			window.setTimeout(funcLogin, checkIntervalLogin);
		}
	}
	window.setTimeout(funcLogin, checkIntervalLogin);
    
});