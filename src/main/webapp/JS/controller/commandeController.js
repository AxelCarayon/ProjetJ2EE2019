/* global Mustache */
$(document).ready(
        function () {
            $(document).on('change', '.majQte', function () {
                modifierQteProdLigne($(this).attr('id'), $(this).val());
            });
            $(document).on('click', '.trashProdCommande', function () {
                console.log($(this).attr('id'));
                suppProdLigne($(this).attr('id'));
            });
            colCommande = [{titre: "Numéro"}, {titre: "Saisie le"}, {titre: "Envoyée le"}, {titre: "Adresse"}, {titre: "Port"}, {titre: "Total"}];
            colLigne = [{titre: "Produit"}, {titre: "Catégorie"}, {titre: "Prix unitaire"}, {titre: "Promo"}, {titre: "Quantité"}, {titre: "Total"}];
        }
);
function afficheListeCommandeUser() {
    if (localStorage.getItem('acces') === 'true') {
        $.ajax({
            url: "../CommandesClientServlet",
            xhrFields: {
                withCredentials: true
            },
            dataType: "json",
            success:
                    function (result) {
                        var template = $('#templateTable').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {ths: colCommande, title: "Bon de commande"});
                        $('#pageContentProfil').html(processedTemplate);

                        var template = $('#templateLigneCommandeUser').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {commandes: result});
                        $('#tbody').html(processedTemplate);

                        $('.prixTotalCommande').each(function () {
                            var code = $(this).attr('id');
                            afficherTotalCommande(code, $(this));
                        });
                    },
            error: showError
        });
    }
}
function afficherTotalCommande(id, place) {
    $.ajax({
        url: "../PrixCommandeServlet",
        xhrFields: {
            withCredentials: true
        },
        data: {"code": id},
        dataType: "json",
        success:
                function (result) {
                    var template = $('#templatePrixTotalCommandeUser').html();
                    Mustache.parse(template);
                    var processedTemplate = Mustache.render(template, {prix: result});
                    place.html(processedTemplate);
                },
        error: showError
    });
}
function afficheLigneCommande(id) {
    if (localStorage.getItem('acces') === 'true') {
        $.ajax({
            url: "../LignesCommandeServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {"commande": id},
            dataType: "json",
            success:
                    function (result) {
                        var template = $('#templateTable').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {ths: colLigne, title: "Détails de la commande n°" + id});
                        $('#pageContentProfil').html(processedTemplate);

                        var template = $('#templateTbodyLigneCommandeUser').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {lignes: result, id: id});
                        $('#tbody').html(processedTemplate);
                    },
            error: showError
        });
    }
}

function modifierQteProdLigne(prod, qte) {
    var idcom = $('#numCom').text();
    if (localStorage.getItem('acces') === 'true') {
        $.ajax({
            url: "../LignesCommandeServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {"commande": idcom, "produit": prod, "quantite": qte, "action": "majQte"},
            dataType: "json",
            success:
                    function (result) {
                        var template = $('#templateTable').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {ths: colLigne, title: "Détails de la commande n°" + idcom});
                        $('#pageContentProfil').html(processedTemplate);

                        var template = $('#templateTbodyLigneCommandeUser').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {lignes: result, id: idcom});
                        $('#tbody').html(processedTemplate);
                    },
            error: showError
        });
    }
}

function suppProdLigne(prod) {
    var idcom = $('#numCom');
    if (localStorage.getItem('acces') === 'true') {
        $.ajax({
            url: "../LignesCommandeServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {"commande": idcom, "produit": prod, "action": "trashLigne"},
            dataType: "json",
            success:
                    function (result) {
                        var template = $('#templateTable').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {ths: colLigne, title: "Détails de la commande n°" + idcom});
                        $('#pageContentProfil').html(processedTemplate);

                        var template = $('#templateTbodyLigneCommandeUser').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {lignes: result, id: idcom});
                        $('#tbody').html(processedTemplate);
                    },
            error: showError
        });
    }
}
function suppCommande(code) {
    if (localStorage.getItem('acces') === 'true') {
        $.ajax({
            url: "../CommandesClientServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {"action": "trash", "code": code},
            dataType: "json",
            success: function (result) {
                var template = $('#templateTable').html();
                Mustache.parse(template);
                var processedTemplate = Mustache.render(template, {ths: colCommande, title: "Bon de commande"});
                $('#pageContentProfil').html(processedTemplate);

                var template = $('#templateLigneCommandeUser').html();
                Mustache.parse(template);
                var processedTemplate = Mustache.render(template, {commandes: result});
                $('#tbody').html(processedTemplate);

                $('.prixTotalCommande').each(function () {
                    var code = $(this).attr('id');
                    afficherTotalCommande(code, $(this));
                });
            },
            error: showError
        });
    }
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    alert("Erreur: " + status + " : " + message);
}