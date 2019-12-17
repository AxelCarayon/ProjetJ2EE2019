/* global google, Mustache */

var categorie = [];
var ca;
var  pays;
var caUsers = [];
var users = [];

$(document).ready(function () {
    $.get("../ClientPays",getPays,"json");
    $.get("../CategorieServlet",getCategorie,"json");

    $(document).on('click', '#link-ca-cat', function () {
        $('h2').removeClass('d-none');
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
        $('h2').removeClass('d-none');
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
    
    $(document).on('click', '#link-ca-p', function () {
        displayChart();
        displayTitre("Chiffre d'affaires par client");
        displayListeClients();
        $('.col-stat').addClass('col-lg-8');
        $(document).on('click','.select', function (){
            var client = $(this).attr("id");
            var dateD = $('#dateD').val();
            var dateF = $('#dateF').val();
            if(this.checked) {
                getNameUser(client);
                if (dateD != "" && dateF!= ""){
                    statFor1Usr(dateD,dateF,client);
                }else{
                    alert("Selectionnez une date de début et de fin.");
                }
            } 
            else {
                var name = getNameUserInList(client);
                var newT = [];
                for (var i=0; i<caUsers.length; i++){
                    if (caUsers[i][0]!=name){
                        newT.push(caUsers[i]);
                    }
                }
               caUsers=newT;
                if (dateD != "" && dateF!= ""){
                    statByUser();
                }
            }
        });
        $(document).on('change','.dateStat', function (){
            var dateD = $('#dateD').val();
            var dateF = $('#dateF').val();
            if (dateD != "" && dateF!= "" && caUsers.length>0){
                while(caUsers.length>0){
                    var max = caUsers.length-1;
                    var id = getIdUserInList(caUsers[max][0]);
                    statFor1Usr(dateD,dateF,id);
                    caUsers.pop();
                }
            }
        });
    });
});

function getCategorie(result){
    for (var i=0;i<result.length;i++){
        categorie.push([result[i].code,result[i].libelle]);
    }
}
function getPays(result){
    pays = result;
}

function getNameUser(code){
    if (getNameUserInList(code)===-1){
        $.ajax({
           url: "../ClientServlet",
           data: {  "code": code},
           dataType: "json",
           success: addClientInList,
           error: showError
        });	
    }
}

function addClientInList(user){
    users.push([user.code,user.contact]);
}

function getNameUserInList(code){
    for (var i=0; i<users.length;i++){
        if (users[i][0]==code){
            return users[i][1];
        }
    }
    return -1;
}
function getIdUserInList(name){
    for (var i=0; i<users.length;i++){
        if (users[i][1]==name){
            return users[i][0];
        }
    }
    return -1;
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
           dataType: "json",
           success: 
                   function(result) {
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

function statFor1Usr(d1,d2,usr){
     $.ajax({
           url: "../statServlet",
           data: {  "action":"client","client":usr,"dateD":d1,"dateF":d2},
           dataType: "json",
           success: 
                   function(result) {
                        caUsers.push([getNameUserInList(usr),result]);
                        statByUser();
                   },
           error: showError
        });
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

function statByUser(){
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChartUsr);
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

function drawChartUsr() {
    var tab =[];
    tab.push(['Task', 'Hours per Day']);
    for (var i=0 ; i<caUsers.length;i++){
            tab.push(caUsers[i]);
    }
    var data = google.visualization.arrayToDataTable(tab);
    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
    chart.draw(data);
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
    alert("Erreur: " + status + " : " + message);
}