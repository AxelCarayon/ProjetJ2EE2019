function displayCat(){
    $.ajax({
            url: "CategorieServlet",
            dataType: "json",
            success: 
                    function(result) {
                            var template = $('#').html();
                            Mustache.parse(template);
                            var processedTemplate = Mustache.render(template, {categories: result });
                            $('#').html(processedTemplate);	
                    },
            error: showError
    });		
}