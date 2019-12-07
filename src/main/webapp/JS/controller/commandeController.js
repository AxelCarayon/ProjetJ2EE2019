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
                            var processedTemplate = Mustache.render(template, {lignes: result });
                            $('#pageContentProfil').html(processedTemplate);	
                        },
                error: function(){console.log("erreur");}
            });		
    }
}
