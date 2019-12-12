/* global Mustache */
$(document).ready(function () {
    $(document).on('click', '#profilInformation', function () {
        afficheInformationUser();
    });
});

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

function modifierInfoUser(){
    var champs = ['inputEmail4','inputAddress','inputCity','inputRegion','inputCp','inputPays','inputTel','inputFax','inputSociete','inputFonction'];
    var opts = ['email','adresse','ville','region','cp','pays','tel','fax','societe','fonction'];
    for (var i=0; i<champs.length; i++){
        if ($('#'+champs[i]).val()!=""){
            $.ajax({
                url: "../ModifierClientServlet",
                xhrFields: {
                    withCredentials: true
                },
                data: {  "action":opts[i], "data":$('#'+champs[i]).val()},
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
}
