/* global Mustache */

$(document).ready(function(){
    MonPanier = new Panier();
    remplirPanierAvecHtml(MonPanier);
    
    $('#ajoutPanier').click(function(e){
        var id = $(this).val();
        $.get("Produit",id,ajouter,'json');;//appel ajax
    });
});

// Objet LignePanier
function LignePanier (code,libelle, qte, prix)
{
    this.codeArticle = code;
    this.libelle = libelle;
    this.qteArticle = qte;
    this.prixArticle = prix;
    this.prixLigne = this.prixArticle * this.qteArticle;
}    
LignePanier.prototype.setQte = function(qte)
{
        this.qteArticle += qte;
        this.prixLigne = this.prixArticle*this.qteArticle;
};

LignePanier.prototype.getCode = function() 
{
    return this.codeArticle;
};

// Objet Panier
function Panier()
{
    this.liste = [];
}  

Panier.prototype.getPanier = function(){
    return this.liste;
};

Panier.prototype.ajouterArticle = function(code, libelle,qte, prix)
{ 
    var index = this.getArticle(code);
    if (index === -1) this.liste.push(new LignePanier(code,libelle, qte, prix));
    else this.liste[index].setQte(qte);
};

Panier.prototype.setArticleQte = function (code,qte){
    var index = this.getArticle(code);
    if (index!==-1){
        var article = this.liste[index];
        if (article.qteArticle+qte === 0){
            afficherPopupConfirmationSup("Souhaitez-vous supprimer le produit "+ article.libelle +" de votre panier ?",this,code);
        }else article.setQte(qte);
    }
};

Panier.prototype.getPrixPanier = function()
{
    var total = 0;
    for(var i = 0 ; i < this.liste.length ; i++)
        total += this.liste[i].prixLigne;
    return total;
};

Panier.prototype.getArticle = function(code)
{
    for(var i = 0 ; i <this.liste.length ; i++)
        if (code === this.liste[i].getCode()) return i;
    return -1;
};

Panier.prototype.supprimerArticle = function(code)
{
    var index = this.getArticle(code);
    if (index > -1) this.liste.splice(index, 1);
};
        
function ajouter(dataJson)
{
    var monPanier = new Panier();
    monPanier.ajouterArticle(dataJson.reference,dataJson.nom, 1, dataJson.prix_unitaire);
    remplirPanierAvecHtml(monPanier);
    displayPanier(monPanier);
}

function qteUpDate(id,val){
    var monPanier = new Panier();
    remplirPanierAvecHtml(monPanier);
    monPanier.setArticleQte(id,val);
    displayPanier(monPanier);
}

//E : Panier panier
// Parcour les élément du DOM -> panier 
// ajoute les articles à panier
// S : panier
function remplirPanierAvecHtml(panier){
    $('.list-group-item').each( function(){
            var kids = $(this).children();
            var ligne = [];
            kids.each(function (){
                if ($(this).hasClass( "info" )){
                    ligne.push( $(this).text());
                }
                if ($(this).hasClass("input-group")){
                    ligne.push($(this).children(".info").val());
                }
            });
            panier.ajouterArticle(parseInt(ligne[0]), ligne[1], parseInt(ligne[3]), parseInt(ligne[4]));
    });
    return panier;
}

function displayPanier(monPanier){
    var template = $('#templateItemPanier').html();
    Mustache.parse(template);
    var processedTemplate = Mustache.render(template, {items: monPanier.getPanier()});
    $('#displayPanier').html(processedTemplate);	 
    
    var templateP = $('#templatePrixTotalPanier').html();
    Mustache.parse(templateP);
    var processedTemplate = Mustache.render(templateP, {prix: monPanier.getPrixPanier() });
    $('#displayprixTotal').html(processedTemplate);	
}

function afficherPopupConfirmationSup(question,panier,article) {
    $('body').append('<div id="popupconfirmation" title="Confirmation"></div>');
    $("#popupconfirmation").html(question);
    var popup = $("#popupconfirmation").dialog({
        autoOpen: true,
        width: 400,
        dialogClass: 'dialogstyleperso',
        hide: "fade",
        buttons: [{
                text: "Oui",
                class: "btn btn-dark",
                click: function () {
                    $(this).dialog("close");
                    $("#popupconfirmation").remove();
                    panier.supprimerArticle(article);
                    displayPanier(panier);
                }},
            {
                text: "Non",
                class: "btn btn-dark",
                click: function () {
                    $(this).dialog("close");
                    $("#popupconfirmation").remove();
            }}]
    });
    $("#popupconfirmation").prev().addClass('ui-state-question');
    return popup;
}