function afficheListeCommandeUser(){
    if (localStorage.getItem('acces') === 'true'){
        $.ajax({
                url: "../CommandesClientServlet",
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                success: 
                        function(result) {
                            var template = $('#templateListeCommandeUser').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {commandes: result });
                            $('#pageContentProfil').html(processedTemplate);	
                        },
                error: function(){console.log("erreur");}
            });		
    }
}

function afficheLigneCommande(id){
    if (localStorage.getItem('acces') === 'true'){
        $.ajax({
                url: "../LignesCommandeServlet",
                xhrFields: {
                    withCredentials: true
                },
                data: {"commande":id},
                dataType: "json",
                success: 
                        function(result) {
                            var template = $('#templateListeLigne').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {lignes: result ,id:id});
                            $('#pageContentProfil').html(processedTemplate);	
                        },
                error: function(){console.log("erreur");}
            });		
    }
}

function modifierQteProdLigne(prod){
    var idcom = $('#numCom');
    if (localStorage.getItem('acces') === 'true'){
        $.ajax({
                url: "../LignesCommandeServlet",
                xhrFields: {
                    withCredentials: true
                },
                data: {"commande":idcom,"produit":prod,"quantite":qte},
                dataType: "json",
                success: 
                        function(result) {
                            
                        },
                error: function(){console.log("erreur");}
            });		
    }
}

function suppProdLigne(prod){
    var idcom = $('#numCom');
    if (localStorage.getItem('acces') === 'true'){
        $.ajax({
                url: "../LignesCommandeServlet",
                xhrFields: {
                    withCredentials: true
                },
                data: {"commande":idcom,"produit":prod},
                dataType: "json",
                success: 
                        function(result) {
                            afficheLigneCommande(idcom);
                        },
                error: function(){console.log("erreur");}
            });		
    }
}