/* global Mustache */

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
                            var template = $('#templateTable').html();
                            Mustache.parse(template);
                            var tab = [{titre:"Numéro"},{titre:"Saisie le"},{titre:"Envoyée le"},{titre:"Adresse"},{titre:"Port"},{titre:"Total"}];
                            var processedTemplate = Mustache.render(template, {ths: tab });
                            $('#pageContentProfil').html(processedTemplate);
                            
                            var template = $('#templateLigneCommandeUser').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {commandes: result });
                            $('#tbody').html(processedTemplate);
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
                            var template = $('#templateTable').html();
                            Mustache.parse(template);
                            var tab = [{titre:"Produit"},{titre:"Catégorie"},{titre:"Prix unitaire"},{titre:"Promo"},{titre:"Quantité"},{titre:"Total"}];
                            var processedTemplate = Mustache.render(template, {ths:tab,id:id});
                            $('#pageContentProfil').html(processedTemplate);	
                            
                            var template = $('#templateTbodyLigneCommandeUser').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {lignes: result });
                            $('#tbody').html(processedTemplate);
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