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
                        return result;
                    },
            error: function(){console.log("erreur");}
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
                            return 'admin';
                        }
                    },
            error: function(){console.log("erreur");}
    });		
}
function connexionClient(mail,pw,action) {
    console.log(mail,pw,action);
    $.ajax({
            url: "../ConnexionServlet",
            xhrFields: {
                withCredentials: true
            },
            data: { "email" : mail, "pw" : pw , "action":action},
            success: afficherPage,
            error: function(){console.log("erreur");}
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
            error: function(){console.log("erreur");}
    });				
}

function afficherPage(){
    isAdmin();
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
                        var template = $('#templateWelcome').html();
                        Mustache.parse(template);
                        var processedTemplate = Mustache.render(template, {user: result });
                        $('body').html(processedTemplate);	
                    },
            error: function(){console.log("erreur");}
        });		
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
            error: function(){console.log("erreur");}
        });		
    }
}

function afficheInformationUser(){
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
                            var template = $('#templateFormInfoUser').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {user: result });
                            $('#pageContentProfil').html(processedTemplate);	
                        },
                error: function(){console.log("erreur");}
            });		
    }
}