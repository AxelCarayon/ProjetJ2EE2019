/* global Mustache */

$(document).ready(
        function () {
            $(document).on('click', '.nav-link-cat', function () {
                $('h2').addClass('d-none');
                
                var template = $('#templateTable').html();
                Mustache.parse(template);
                var tab = [{titre: "Réference"}, {titre: "Libelle"},{titre: "Quantité par unité"}, {titre: "En stock"}, {titre: "commandé"}, {titre: "Prix"}, {titre: "Indisponible"}];
                var processedTemplate = Mustache.render(template, {ths: tab, title: "Produit de la catégorie: " + $(this).text(),idcat:$(this).attr('id'),titlecat:$(this).text()});
                $('#pageContentProfil').html(processedTemplate);
                
                afficheListeProduit($(this).attr('id'));
            });
            $(document).on('click', '.modifierProd', function () {
                $('h2').removeClass('d-none');
                afficherDetailsProduit($(this).attr('id'));
            });
            $(document).on('click', '.trashProd', function () {
                supprimerProduit($(this).attr('id'));
            });
            $(document).on('click', '#fromModifierProduit', function () {
                modifierProduit();
            });
            $(document).on('click', '.link-ajout-prod', function () {
                $('h2').removeClass('d-none');
                displayTitre("Ajouté un article");
                afficherFormAjoutProd();
            });
        });
        
function displayTitre(msg){
    var template = $('#templatetitreSection').html();
    Mustache.parse(template);
    var processedTemplate = Mustache.render(template, {titreSection:msg});
    $('h2').html(processedTemplate);	
}

function displayCat(t,t1) {
    $.ajax({
        url: "../CategorieServlet",
        dataType: "json",
        success:
                function (result) {
                    var template = $('#'+t).html();
                    Mustache.parse(template);
                    var processedTemplate = Mustache.render(template, {categories: result});
                    $('#'+t1).html(processedTemplate);
                },
        error: showError
    });
}

function afficheListeProduit(id) {
    if (localStorage.getItem('acces') === 'admin') {
        $.ajax({
            url: "../ProduitsCategorieServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {"categorie": id},
            dataType: "json",
            success:
                    function (result) {
                        var template = $('#templateTbodyLigneProduitAdmin').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {lignes: result});
                        $('#tbody').html(processedTemplate);
                    },
            error: showError
        });
    }
}

function afficherDetailsProduit(id) {
    if (localStorage.getItem('acces') === 'admin') {
        $.ajax({
            url: "../ProduitServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {"reference": id},
            dataType: "json",
            success:
                    function (result) {
                        displayTitre("Éditez le produit "+result.nom);
                        var template = $('#templateFormProduitForAdmin').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {produit: result});
                        $('#pageContentProfil').html(processedTemplate);
                        catForModifProd(result);
                    },
            error: showError
        });
    }
}

function catForModifProd(prod) {
    $.ajax({
        url: "../CategorieServlet",
        dataType: "json",
        success:
                function (result) {
                    catActiveProduit(prod.categorie,result);
                },
        error: showError
    });
}

function catActiveProduit(codeCat,listcat){
    $.ajax({
        url: "../CategorieServlet",
        data:{"action":"libelle","code":codeCat},
        dataType: "json",
        success:
                function (result) {
                    var template = $('#templateOptCatForModif').html();
                    Mustache.parse(template);
                    var processedTemplate = Mustache.render(template, {codeCat:codeCat, libCat:result,categories: listcat});
                    $('#categorie').html(processedTemplate);
                },
        error: showError
    });
}

function supprimerProduit(idprod) {
    if (localStorage.getItem('acces') === 'admin') {
        $.ajax({
            url: "../ProduitTrashServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {"code": idprod},
            success: function () {
                console.log("succes");
            },
            error: function () {
                alert("Erreur: produit commandé par un client.");
            }
        });
    }
}

function modifierProduit() {
    var champs = ["nom", "fournisseur", "categorie", "quantite_par_unite", "prix_unitaire", "unites_commandes", "niveau_reaprovis"];
    for (var i = 0; i < champs.length; i++) {
        if ($('#' + champs[i]).val() != "") {
            $.ajax({
                url: "../ProduitServlet",
                xhrFields: {
                    withCredentials: true
                },
                data: {"action": "update", "champ": champs[i], "data": $('#' + champs[i]).val(), "reference": $('#reference').text()},
                dataType: "json",
                success:
                        function (result) {
                            var template = $('#templateFormProduitForAdmin').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {produit: result});
                            $('#pageContentProfil').html(processedTemplate);
                            catForModifProd(result);
                        },
                error: showError
            });
        }
    }
}
function afficherFormAjoutProd(){
    var template = $('#templateFormAjoutProduit').html();
    Mustache.parse(template);
    var processedTemplate = Mustache.render(template);
    $('#pageContentProfil').html(processedTemplate);
    displayCat('templateOptCat','categorie');
    $('#fromAjouterProduit').click(ajouterProduit);
}  

function ajouterProduit(){
    var champs = ["nom", "fournisseur", "categorie", "quantite_par_unite", "prix_unitaire", "unite_en_stock"];
    var data =[];
    
    for (var i = 0; i < champs.length; i++ ) {
        if (champs[i]==="categorie"){
            data.push($('#categorie option:selected').val());
        }else data.push($('#' + champs[i]).val());
    }     
    $.ajax({
        url: "../ProduitTrashServlet",
        xhrFields: {
            withCredentials: true
        },
        data: {"action": "add", "nom": data[0],"fournisseur": data[1], "categorie": data[2], "quantite_par_unite": data[3], "prix_unitaire": data[4],"unite_en_stock": data[5] },
        success:afficherFormAjoutProd,
        error: showError
    });
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    alert("Erreur: " + status +" " + message);
}