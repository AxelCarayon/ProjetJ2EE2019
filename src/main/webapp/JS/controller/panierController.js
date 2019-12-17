/* global Mustache, Storage */

$(document).ready(function(){
    isConnected();
     $(document).on('change', '.quantity', function () {
        qteUpDate($(this).attr('id'),$(this).val());
    });
    $(document).on('click', '.close', function () {
        supprimer($(this).attr('id'));
    });
});

// Objet LignePanier
function LignePanier (code,libelle, qte, prix,quantite_par_unite,unites_en_stock)
{
    this.codeArticle = code;
    this.libelle = libelle;
    this.qteArticle = qte;
    this.prixArticle = prix;
    this.quantite_par_unite = quantite_par_unite;
    this.unites_en_stock = unites_en_stock;
    this.prixLigne = this.prixArticle * this.qteArticle;
}    
LignePanier.prototype.setQte = function(qte)
{
        this.qteArticle = qte;
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

Panier.prototype.ajouterArticle = function(code, libelle,qte, prix,quantite_par_unite,stock)
{ 
    var index = this.getArticle(code);
    if (index === -1) this.liste.push(new LignePanier(code,libelle, qte, prix,quantite_par_unite,stock));
    else this.liste[index].setQte(qte);
};

Panier.prototype.setArticleQte = function (code,qte){
    var index = this.getArticle(code);
    if (index!==-1){
        var article = this.liste[index];
        if (qte == 0){
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
        if (code == this.liste[i].getCode()) return i;
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
    monPanier.ajouterArticle(dataJson.reference,dataJson.nom, 1, dataJson.prix_unitaire,dataJson.quantite_par_unite,dataJson.unites_en_stock);
    remplirPanierAvecStorage(monPanier);
    displayPanier(monPanier);
    localStorage.setItem("MonPanier",JSON.stringify(monPanier.liste));
}

function supprimer(code){
    var panier = new Panier();
    remplirPanierAvecStorage(panier);
    panier.supprimerArticle(code);
    displayPanier(panier);
    localStorage.setItem("MonPanier",JSON.stringify(panier.liste));
}

function qteUpDate(id,val){
    var monPanier = new Panier();
    remplirPanierAvecStorage(monPanier);
    monPanier.setArticleQte(id,val);
    displayPanier(monPanier);
    localStorage.setItem("MonPanier",JSON.stringify(monPanier.liste));
}

//E : Panier panier
// Parcour les élément localStorage 'MonPanier' 
// ajoute les articles à panier
// S : panier
function remplirPanierAvecStorage(panier){
    if(localStorage.getItem('MonPanier')!==null){
        var listeJSON=localStorage.getItem('MonPanier');
        var obj = JSON.parse(listeJSON);
        for (var i = 0; i<obj.length;i++){
            var index = panier.getArticle(obj[i].codeArticle);
            if (index != -1){
                var qte = parseInt(obj[i].qteArticle)+parseInt(panier.liste[index].qteArticle);
                panier.ajouterArticle(obj[i].codeArticle,obj[i].libelle,qte,obj[i].prixArticle,obj[i].quantite_par_unite,obj[i].unites_en_stock);
            }
            else
            panier.ajouterArticle(obj[i].codeArticle,obj[i].libelle,obj[i].qteArticle,obj[i].prixArticle,obj[i].quantite_par_unite,obj[i].unites_en_stock);
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
    $('#prixTotalMobile').html(processedTemplate);
    
    $('#panierMob').removeClass('d-none');
    var templateMob = $('#templateItemPanierMobile').html();
    Mustache.parse(templateMob);
    var processedTemplate = Mustache.render(templateMob, {items: monPanier.getPanier()});
    $('.panierForMobil').html(processedTemplate);	 

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

function isConnected(){
    $.ajax({
            url: "SessionActiveServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {  "action":"isconected"},
            dataType: "json",
            success: show,
            error: showError
    });	
}

function show(rep){
    localStorage.setItem('acces',rep);
    if (rep===true){
        afficherBlockPanier();
    }else{
        $('#displayProd').parent().addClass('col-lg-12');
        $(document).on('click', '.ajoutPanier', function () {
            alert("Vous devez être connecté.");
        });
    }
}

function afficherBlockPanier(){
    $('#displayProd').parent().removeClass('col-lg-12');
    // Affichage block panier 
        var templateP = $('#templateBlockPanier').html();
        Mustache.parse(templateP);
        var processedTemplate = Mustache.render(templateP);
        $('#displayBlockPanier').html(processedTemplate);
        
        var panier = new Panier();
        remplirPanierAvecStorage(panier);
        displayPanier(panier);
        
        $(document).on('click', '.ajoutPanier', function () {
            $.get("ProduitServlet",{reference:$(this).val()},ajouter,'json');;//appel ajax
        });
}

function commander(){
    panier = new Panier();
    remplirPanierAvecStorage(panier);
    if (panier.getPanier().length>0){
        $.ajax({
           url: "CommanderServlet",
           xhrFields: {
               withCredentials: true
           },
           data: {  "action": "commande"},
           dataType: "json",
           success: 
                   function(result) {
                       ajoutLigneCommande(result,panier);
                   },
           error: showError
        });	
    }else alert("Ajoutez des articles à votre panier.");
}

function ajoutLigneCommande(code,panier){
    for (var i=0;i<panier.getPanier().length;i++){
        var article = panier.getPanier()[i];
        $.get("CommanderServlet",{code:code,prod:article.codeArticle,qte:article.qteArticle},'json');;//appel ajax
    }
    localStorage.removeItem('MonPanier');
    displayPanier(new Panier());
    alert("Commande enregistré !");
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
        alert("Erreur: " + status + " : " + message);
}
