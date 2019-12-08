$(document).ready(
    function () {
        
        $(document).on('click', '.nav-link-cat', function () {
            afficheListeProduit($(this).attr('id'),$(this).text());
        });
        $(document).on('click', '.modifierProd', function () {
            afficherDetailsProduit($(this).attr('id'));
        });
});

function displayCat(){
    $.ajax({
            url: "../CategorieServlet",
            dataType: "json",
            success: 
                    function(result) {
                            var template = $('#').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {categories: result });
                            $('#').html(processedTemplate);	
                    },
            error: showError
    });		
}

function afficheListeProduit(id,cat){
    if (localStorage.getItem('acces') === 'admin'){
        $.ajax({
                url: "../ProduitsCategorieServlet",
                xhrFields: {
                    withCredentials: true
                },
                data: {"categorie":id},
                dataType: "json",
                success: 
                        function(result) {
                            var template = $('#templateTable').html();
                            Mustache.parse(template);
                            var tab = [{titre:"Réference"},{titre:"Libelle"},{titre:"En stock"},{titre:"commandé"},{titre:"réapro"},{titre:"Prix"},{titre:"Indisponible"}];
                            var processedTemplate = Mustache.render(template, {ths:tab,title:"Produit de la catégorie: "+cat});
                            $('#pageContentProfil').html(processedTemplate);	
                            
                            var template = $('#templateTbodyLigneProduitAdmin').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {lignes: result });
                            $('#tbody').html(processedTemplate);
                        },
                error: showError
            });		
    }
}

function afficherDetailsProduit(id){
    if (localStorage.getItem('acces') === 'admin'){
        $.ajax({
                url: "../ProduitServlet",
                xhrFields: {
                    withCredentials: true
                },
                data: {"reference":id},
                dataType: "json",
                success: 
                        function(result) {
                            var template = $('#templateFormProduitForAdmin').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {produit:result});
                            $('#pageContentProfil').html(processedTemplate);	
                        },
                error: showError
            });		
    }
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
        alert("Erreur: " + status + " : " + message);
}