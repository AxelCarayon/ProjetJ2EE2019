/* global Mustache */

$(document).ready(function(){
    MonPannier = new Panier();
    remplirPanier(MonPannier,parcourPannierHtml());
    
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
    if (this.qteArticle + qte > 0){
        this.qteArticle += qte;
        this.prixLigne = this.prixArticle*this.qteArticle;
    }
};

LignePanier.prototype.getPrixLigne = function()
{
    this.pr = this.prixArticle * this.qteArticle;
    return resultat;
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
        this.liste[index].setQte(qte);
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
// Panier panier, liste d'article [[id,nom,categorie,qte,prix]]
function remplirPanier(panier,liste)
{
    for(var i = 0 ; i < liste.length  ; i++)
        {
            panier.ajouterArticle(parseInt(liste[i][0]), liste[i][1], parseInt(liste[i][3]), parseInt(liste[i][4]));
        }
} 
        
function ajouter(dataJson)
{
    var monPanier = new Panier();
    monPanier.ajouterArticle(dataJson.reference,dataJson.nom, 1, dataJson.prix_unitaire);
    var panier = parcourPannierHtml();
    var nbrLigne = panier.length;
    
    if (nbrLigne > 0)
    {
        remplirPanier(monPanier,panier);
    }
    displayPanier(monPanier);
}

function qteUpDate(id,val){
    var monPanier = new Panier();
    var panier = parcourPannierHtml();
    remplirPanier(monPanier,panier);
    monPanier.setArticleQte(id,val);
    displayPanier(monPanier);
}

// Parcour les élément du DOM -> pannier 
// Return : Panier[[id,nom,cate,qte,prix]], nombre de ligne
function parcourPannierHtml(){
    var panier = [];
    $('.list-group-item').each( function(){
            var kids = $(this).children();
            cpt =0;
            var ligne = [];
            kids.each(function (){
                cpt ++;
                if (cpt === 4){
                    ligne.push(($(this).children("input").val()));
                }else{
                    ligne.push( $(this).text());
                }
            });
            panier.push(ligne);
    } );
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
