/* global Mustache */

$(document).ready(
    function () {
        showProduitByCategorie(1);
        showCategorieForNav();
        showCategorieForNavMob();
 
        $(document).on('click', '.elemtMenuCat', function () {
            showProduitByCategorie($(this).attr('id'));
        });
    }
);

function showProduitByCategorie(cat) {
    $.ajax({
            url: "ProduitsCategorieServlet",
            data: {"action":"dispo","categorie": cat},
            dataType: "json",
            success: 
                    function(result) {
                            var template = $('#templateShowProduits').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {produits: result });
                            $('#displayProd').html(processedTemplate);	
                    },
            error: showError
    });				
}

function showCategorieForNav() {
    $.ajax({
            url: "CategorieServlet",
            dataType: "json",
            success: 
                    function(result) {
                            var template = $('#templateMenuCat').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {categories: result });
                            $('#displayMenu').html(processedTemplate);	
                    },
            error: showError
    });				
}

function showCategorieForNavMob() {
    $.ajax({
            url: "CategorieServlet",
            dataType: "json",
            success: 
                    function(result) {
                            var template = $('#templateMenuMobCat').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {categories: result });
                            $('#displayMenuMob').html(processedTemplate);	
                    },
            error: showError
    });				
}

function showError(xhr, status, message) {
        alert("Erreur: " + status + " : " + message);
}