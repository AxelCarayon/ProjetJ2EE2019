/* global Mustache */
$(document).ready(function () {
    $('#profilInformation').click(function(){
        console.log("coucou");
    });
    testConnexionForAfficherPage();
});

function connect(){
    connexionClient($('#inputEmail').val(),$('#inputPassword').val(),$(".action").val());
}

function isConnected(){
    $.ajax({
            url: "../SessionActiveServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {  "action":"isconected"},
            dataType: "json",
            success: 
                    function(result) {
                        localStorage.setItem('acces', result);
                        show(result);
                    },
            error: showError
    });	
}

function isAdmin(){
    $.ajax({
            url: "../SessionActiveServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {  "action":"isadmin"},
            dataType: "json",
            success: 
                    function(result) {
                        if (!result){
                           isConnected();
                        }else{
                            localStorage.setItem('acces', 'admin');
                            show('admin');
                        }
                    },
            error: showError
    });	
}

function show(rep){
    afficherPage(rep);
}

function connexionClient(mail,pw,action) {
    console.log(mail,pw,action);
    $.ajax({
            url: "../ConnexionServlet",
            xhrFields: {
                withCredentials: true
            },
            data: { "email" : mail, "pw" : pw , "action":action},
            success: connectSuccess,
            error: showError
    });				
}

function connectSuccess(){
    testConnexionForAfficherPage();
}

function deconnexionClient() {
    $.ajax({
            url: "../ConnexionServlet",
            xhrFields: {
                withCredentials: true
            },
            data: { "action":"deconnexion"},
            success: function(){
                localStorage.removeItem(("MonPanier"));
                connectSuccess();
            },
            error: showError
    });	
}

function testConnexionForAfficherPage(){
    isAdmin();
}

function afficherPage(status){
    if (status == false){
        var template = $('#templateFormConnexion').html();
        Mustache.parse(template);
        var processedTemplate = Mustache.render(template);
        $('#pageContent').html(processedTemplate);	
    }
    if (status == 'admin'){
        $.ajax({
            url: "../SessionActiveServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {  "action":"getuser"},
            dataType: "json",
            success: 
                    function(result) {
                        var template = $('#templateWelcome').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {user: result ,titre:"Votre espace Administration"});
                        $('#body').html(processedTemplate);
                        
                        var template = $('#templateMenuforAdmin').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template);
                        $('#showTemplateMenu').html(processedTemplate);
                    },
            error: showError
        });
        displayCatForAdmin();
    }
    if (status == true){
        $.ajax({
            url: "../SessionActiveServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {  "action":"getuser"},
            dataType: "json",
            success: 
                    function(result) {
                        var template = $('#templateWelcome').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {user: result,titre:"Votre espace client" });
                        $('#body').html(processedTemplate);
                        
                        var template = $('#templateMenuforUser').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template);
                        $('#showTemplateMenu').html(processedTemplate);
                    },
            error: showError
        });		
    }
}
function displayCatForAdmin(){
    $.ajax({
            url: "../CategorieServlet",
            dataType: "json",
            success: 
                    function(result) {
                            var template = $('#templateMenuCatAdmin').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {categories: result });
                            $('#menuListeCategorie').html(processedTemplate);
                    },
            error: showError
    });		
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
        alert("Erreur: " + status + " : " + message);
}