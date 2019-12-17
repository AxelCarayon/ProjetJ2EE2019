/* global Mustache */
$(document).ready(function () {
    isAdmin();
    $(document).on('click', '#deconnexion',deconnexionClient); 
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
                        afficherPage(result);
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
                            afficherPage('admin');
                        }
                    },
            error: showError
    });	
}

function connexionClient(mail,pw,action) {
    $.ajax({
            url: "../ConnexionServlet",
            xhrFields: {
                withCredentials: true
            },
            data: { "email" : mail, "pw" : pw , "action":action},
            success:  isAdmin,
            error: showError
    });				
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
                isAdmin();
            },
            error: showError
    });	
}

function afficherPage(status){
    if (status == false){
        var template = $('#templateFormConnexion').html();
        Mustache.parse(template);
        var processedTemplate = Mustache.render(template);
        $('#body').html(processedTemplate);	
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
