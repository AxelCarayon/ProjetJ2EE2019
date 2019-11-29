/* global Mustache */

$(document).ready(function(){
    
    $('#ajoutPanier').click(function(e){
        var id = $(this).val();
        $.get("Produit",id,ajouter,'json');;//appel ajax
    });
    
    $('#qtemoins').click(function(e){
        console.log("moins");
       
        Panier.setArticleQte($(this).val(),-1);
    });
    $('#qteplus').click(function(e){
        console.log("plus");
        Panier.setArticleQte($(this).val(),1);
    });
    
});

// Objet LignePanier
function LignePanier (code,libelle, qte, prix)
{
    this.codeArticle = code;
    this.libelle = libelle;
    this.qteArticle = qte;
    this.prixArticle = prix;
}    
LignePanier.prototype.setQte = function(qte)
{
    this.qteArticle += qte;
};

LignePanier.prototype.getPrixLigne = function()
{
    var resultat = this.prixArticle * this.qteArticle;
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
    this.liste[index].setQte(qte);
};

Panier.prototype.getPrixPanier = function()
{
    var total = 0;
    for(var i = 0 ; i < this.liste.length ; i++)
        total += this.liste[i].getPrixLigne();
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
    var panier = parcourPannierHtml();
    var nbrLigne = panier.length;
    
    if (nbrLigne > 0)
    {
        for(var i = nbrLigne-1 ; i >= 0  ; i--)
        {
            monPanier.ajouterArticle(parseInt(panier[i][0]), panier[i][1], parseInt(panier[i][3]), parseInt(panier[i][4]));
        }
    }
    console.log(monPanier.getPanier());
    // Ajout html template 
    var template = $('#templateItemPanier').html();
    Mustache.parse(template);
    var processedTemplate = Mustache.render(template, {items: monPanier.getPanier() });
    $('#displayPanier').html(processedTemplate);	
                            
    var templateP = $('#templatePrixTotalPanier').html();
    Mustache.parse(templateP);
    console.log(monPanier.getPrixPanier());
    var processedTemplate = Mustache.render(templateP, {prix: monPanier.getPrixPanier() });
    $('#displayprixTotal').html(processedTemplate);	
    
   //$('#prixTotal').innerHTML = monPanier.getPrixPanier();
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