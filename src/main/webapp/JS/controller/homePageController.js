/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Mustache */

$(document).ready(
    function () {
        //showProduitByCategorie(1);
        showCategorieForNav();
        showCategorieForNavMob();
    }
);

function showProduitByCategorie(cat) {
    $.ajax({
            url: "ShowProduits",
            data: cat,
            dataType: "json",
            success: // La fonction qui traite les résultats
                    // La fonction qui traite les résultats
                    function(result) {
                            // Le code source du template est dans la page
                            var template = $('#templateShowProduits').html();
                            Mustache.parse(template);
                            // On combine le template avec le résultat de la requête
                            var processedTemplate = Mustache.render(template, {produits: result });
                            // On affiche le résultat dans la page
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
                            console.log(result);
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
// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
        alert("Erreur: " + status + " : " + message);
}