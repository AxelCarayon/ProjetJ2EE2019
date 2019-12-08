/* global Mustache */
$(document).ready(function () {
    afficherPage();
    $('#connexion').submit(function(e){
        e.preventDefault();
        connexionClient($('#inputEmail').val(),$('#inputPassword').val(),$(".action").val());
    });
    
    $('#profilInformation').click(function(){
        console.log("coucou");
    });
});
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
                        //statusClient(result);
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
                            //statusClient('admin');
                        }
                    },
            error: showError
    });		
}

//function statusClient(role){
//    return role;
//}
function connexionClient(mail,pw,action) {
    console.log(mail,pw,action);
    $.ajax({
            url: "../ConnexionServlet",
            xhrFields: {
                withCredentials: true
            },
            data: { "email" : mail, "pw" : pw , "action":action},
            success: afficherPage,
            error: showError
    });				
}

function deconnexionClient() {
    console.log("function deconnexionClient");
    $.ajax({
            url: "../ConnexionServlet",
            xhrFields: {
                withCredentials: true
            },
            data: { "action":"deconnexion"},
            success: function(){localStorage.removeItem(("MonPanier"));afficherPage();},
            error: showError
    });				
}

function afficherPage(){
    isAdmin();
    //console.log("affiche page -> satus : "+isAdmin());
    if (localStorage.getItem('acces') === 'false' || localStorage.getItem('acces') == null){
        var template = $('#templateFormConnexion').html();
        Mustache.parse(template);
        var processedTemplate = Mustache.render(template);
        $('#pageContent').html(processedTemplate);	
    }
    if (localStorage.getItem('acces') === 'admin'){
        $.ajax({
            url: "../SessionActiveServlet",
            xhrFields: {
                withCredentials: true
            },
            data: {  "action":"getuser"},
            dataType: "json",
            success: 
                    function(result) {
                        var template = $('#templateAdmin').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {user: result });
                        $('#body').html(processedTemplate);
                    },
            error: showError
        });
        displayCatForAdmin();
    }
    if (localStorage.getItem('acces') === 'true'){
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
                        var processedTemplate = Mustache.render(template, {user: result });
                        $('#body').html(processedTemplate);	
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
                            console.log('oo');	
                    },
            error: showError
    });		
}

// Fonction qui traite les erreurs de la requête
function showError(xhr, status, message) {
        alert("Erreur: " + status + " : " + message);
}