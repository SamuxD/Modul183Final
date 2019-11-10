$(document).ready(function()    {
    // $("#loginForm").serialize()
    $("#login").submit(function (form) {
        form.preventDefault();
        $
            .ajax({
                url: "http://localhost:8080/Modul183V2_war_exploded/resources/person/login",
                dataType: "text",
                type: "POST",
                data: {email: $("#email").val(), password: $("#password").val()}
            })
            .done(function () {
                window.location.href = "./personsOverview.html";
            })
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status === 401) {
                    $("#message").text("Benutzername/Passwort unbekannt");
                } else {
                    $("#message").text("Oops");
                }
            })
    });
});
