$(document).ready(function()    {
    $("#cancel").on("click", function(){ window.location.href = "./personsOverview.html"; });

    $("#person").submit(function (form) {
        form.preventDefault();
        sex = false
        if($("#man").is(':checked')){
            sex = true;
        }

        $
            .ajax({
                url: "http://localhost:8080/Modul183V2_war_exploded/resources/person/save",
                dataType: "text",
                type: "POST",
                data: {firstname: $("#firstname").val(), lastname: $("#lastname").val(), sex: sex, email: $("#email").val(), password: $("#password").val()}
            })
            .done(function () {
                window.location.href = "./personsOverview.html";
            })
            .fail(function (xhr, status, errorThrown) {
                alert(xhr.status);
                if (xhr.status === 401) {
                    window.location.href = "./login.html";
                } else {
                    $("#message1").text("Fehler beim speichern der Person");
                }
            })
    });
});
