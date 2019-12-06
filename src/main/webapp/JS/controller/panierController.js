/* global Mustache, Storage */

$(document).ready(function(){
    if (localStorage.getItem('acces')=== 'succes'|| localStorage.getItem('acces')=== 'admin'){
        $('#displayProd').parent().removeClass('col-lg-12');

        // Affichage block panier 
        var templateP = $('#templateBlockPanier').html();
        Mustache.parse(templateP);
        var processedTemplate = Mustache.render(templateP);
        $('#displayBlockPanier').html(processedTemplate);
        
        var panier = new Panier();
        remplirPanierAvecStorage(panier);
        displayPanier(panier);
        console.log("coucou");

        $(document).on('click', '.ajoutPanier', function () {
            var id = $(this).val();
            $.get("ProduitServlet",{reference:id},ajouter,'json');;//appel ajax
        });
    }
    else{
        $('#displayProd').parent().addClass('col-lg-12');
         $(document).on('click', '.ajoutPanier', function () {
             alert("Vous devez être connecté.");
         });
    }
    
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
    remplirPanierAvecStorage(monPanier);
    displayPanier(monPanier);
    localStorage.setItem("MonPanier",JSON.stringify(monPanier.liste));
}

function qteUpDate(id,val){
    var monPanier = new Panier();
    remplirPanierAvecStorage(monPanier);
    monPanier.setArticleQte(id,val);
    displayPanier(monPanier);
    localStorage.setItem("MonPanier",JSON.stringify(monPanier.liste));
}

//E : Panier panier
// Parcour les élément du DOM -> panier 
// ajoute les articles à panier
// S : panier
function remplirPanierAvecStorage(panier){
    if(localStorage.getItem('MonPanier')!==null){
        var listeJSON=localStorage.getItem('MonPanier');
        var obj = JSON.parse(listeJSON);
        for (var i = 0; i<obj.length;i++){
            panier.ajouterArticle(obj[i].codeArticle,obj[i].libelle,obj[i].qteArticle,obj[i].prixArticle);
        }
    }
    return panier;
}

function displayPanier(monPanier){
    var templateItem = $('#templateItemPanier').html();
    Mustache.parse(templateItem);
    var processedTemplate = Mustache.render(templateItem, {items: monPanier.getPanier()});
    $('#displayPanier').html(processedTemplate);	 

    var templateP = $('#templatePrixTotalPanier').html();
    Mustache.parse(templateP);
    var processedTemplate = Mustache.render(templateP, {prix: monPanier.getPrixPanier() });
    $('#displayprixTotal').html(processedTemplate);
    
//    var templateMob = $('#templatePanierMob').html();
//    Mustache.parse(templateMob);
//    var processedTemplate = Mustache.render(templateMob, {
//    liste: templateItem,items: monPanier.getPanier()
//    });
//    $('#displayShopMob').html(processedTemplate);	 

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
                    localStorage.setItem("MonPanier",JSON.stringify(panier.liste));
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