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
                            var processedTemplate = Mustache.render(template, {ths: tab,title:"Bon de commande"});
                            $('#pageContentProfil').html(processedTemplate);
                            
                            var template = $('#templateLigneCommandeUser').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {commandes: result });
                            $('#tbody').html(processedTemplate);
                            
                            $('.prixTotalCommande').each(function(){
                                var code = $(this).attr('id');
                                afficherTotalCommande(code,$(this));
                            });
                        },
                error: showError
            });		
    }
}
function afficherTotalCommande(id,place){
    $.ajax({
        url: "../PrixCommandeServlet",
        xhrFields: {
            withCredentials: true
        },
        data: {"code":id},
        dataType: "json",
        success: 
                function(result) {
                   var template = $('#templatePrixTotalCommandeUser').html();
                    Mustache.parse(template);
                    var processedTemplate = Mustache.render(template, {prix: result });
                    console.log("place :"+place);
                    place.html(processedTemplate);
                },
        error: showError
    });		
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
                            console.log(result);
                            var template = $('#templateTable').html();
                            Mustache.parse(template);
                            var tab = [{titre:"Produit"},{titre:"Catégorie"},{titre:"Prix unitaire"},{titre:"Promo"},{titre:"Quantité"},{titre:"Total"}];
                            var processedTemplate = Mustache.render(template, {ths:tab,id:id,title:"Détails de la commande n°"+id});
                            $('#pageContentProfil').html(processedTemplate);	
                            
                            var template = $('#templateTbodyLigneCommandeUser').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {lignes: result });
                            $('#tbody').html(processedTemplate);
                        },
                error: showError
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
                error: showError
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
                error: showError
            });		
    }
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
        alert("Erreur: " + status + " : " + message);
}