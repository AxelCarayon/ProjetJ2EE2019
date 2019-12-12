/* global google, Mustache */

var categorie = [];
var ca;
var  pays;

$(document).ready(function () {
    $.get("../ClientPays",getPays,"json");
    $.get("../CategorieServlet",getCategorie,"json");

    $(document).on('click', '#link-ca-cat', function () {
        displayChart();
        $('.dateStat').addClass('dateCat');
        displayTitre("Chiffre d'affaires par catégorie d'article");
        $(document).on('change','.dateCat', function (){
            var dateD = $('#dateD').val();
            var dateF = $('#dateF').val();
            if (dateD != "" && dateF!= ""){
                $.get("../statServlet",{"action":"cat","dateD":dateD,"dateF":dateF}, statByCat,"json");
            }
        });
    });
    
    $(document).on('click', '#link-ca-pays', function () {
        displayChart();
        $('.dateStat').addClass('datePays');
        displayTitre("Chiffre d'affaires par pays");
        $(document).on('change','.datePays', function (){
            var dateD = $('#dateD').val();
            var dateF = $('#dateF').val();
            if (dateD != "" && dateF!= ""){
                $.get("../statServlet",{"action":"pays","dateD":dateD,"dateF":dateF}, statByPays,"json");
            }
        });
    });
    
//    $(document).on('click', '#link-ca-p', function () {
//        displayChart();
//        displayTitre("Chiffre d'affaires par client");
//        displayListeClients();
//        $('.col-stat').removeClass('col-12');
//        $('.col-stat').addClass('col-8');
//        $(document).on('click','.select', function (){
//            var dateD = $('#dateD').val();
//            var dateF = $('#dateF').val();
//            if (dateD != "" && dateF!= ""){
//                $.get("../statServlet",{"action":"pays","dateD":dateD,"dateF":dateF}, statByUser,"json");
//            }else{
//                alert("Selectionnez une date de début et de fin.");
//            }
//        });
//    });
    

});

function getCategorie(result){
    for (var i=0;i<result.length;i++){
        categorie.push([result[i].code,result[i].libelle]);
    }
}
function getPays(result){
    console.log("getPays ",result);
    pays = result;
}

function displayTitre(msg){
    var template = $('#templatetitreSection').html();
    Mustache.parse(template);
    var processedTemplate = Mustache.render(template, {titreSection:msg});
    $('h2').html(processedTemplate);	
}

function displayListeClients(){
    $.ajax({
           url: "../ClientServlet",
           data: {  "action": "commande"},
           dataType: "json",
           success: 
                   function(result) {
                       console.log(result);
                       var template = $('#templateListClient').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template,{"clients":result});
                        $('.listeClients').html(processedTemplate);
                                $('.table-clients').DataTable();

                   },
           error: showError
        });	
}

function displayChart(){
    var template = $('#templatePieChart').html();
    Mustache.parse(template);
    var processedTemplate = Mustache.render(template);
    $('#pageContentProfil').html(processedTemplate);	
}

function statByCat(result){
    ca = result;
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChartCat);
}
function statByPays(result){
    ca = result;
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChartPays);
}

function statByUser(result){
    ca = result;
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChartUser);
}

function drawChartCat() {
    var tab =[];
    tab.push(['Task', 'Hours per Day']);
    for (var i=0 ; i<categorie.length;i++){
        tab.push([categorie[i][1],ca[categorie[i][1]]]);
    }
    var data = google.visualization.arrayToDataTable(tab);
    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
    chart.draw(data);
}

function drawChartPays() {
    var tab =[];
    tab.push(['Task', 'Hours per Day']);
    for (var i=0 ; i<pays.length;i++){
        var val = ca[pays[i]];
        if (val!=null){
            tab.push([pays[i],val]);
        }
    }
    var data = google.visualization.arrayToDataTable(tab);
    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
    chart.draw(data);
}


// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    alert("Erreur: " + status + " : " + message);
}