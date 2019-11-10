$(document).ready(function () {
    listPersons();
    $("#create").on("click", function () {
        window.location.href = "./personCreate.html";
    });
    $("#logout").on("click", logout);
});

function logout() {
    $
        .ajax({
            url: "http://localhost:8080/Modul183V2_war_exploded/resources/person/logout",
            dataType: "text",
            type: "POST"
        })
        .done(function() {window.location.href = "./login.html";})
        .fail(function (xhr, status, errorThrown) {
            alert(xhr.status);
            window.location.href = "./login.html";
        })
}

function listPersons() {
    $
        .ajax({
            url: "http://localhost:8080/Modul183V2_war_exploded/resources/person/list",
            dataType: "json",
            type: "POST"
        })
        .done(showPersons)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status === 401) {
                window.location.href = "./login.html";
            } else {
                $("#message").text("Fehler beim Lesen der Personen");
            }
        })
}

function showPersons(personData) {
    $("#message").text("");
    $("#personTable > tbody").html("");
    function getGender(gender) {
        if (gender) {
            return "male";
        } else{
            return "female";
        }
    };
    var tableData = "";
    personData.forEach(function (person, index) {
        tableData += "<tr>";
        tableData += "<td>" + person.email + "</td>";
        tableData += "<td>" + getGender(person.sex) + "</td>";
        tableData += "<td>" + person.firstname + "</td>";
        tableData += "<td>" + person.lastname + "</td>";
        tableData += "<tr>";
    });
    $("#personTable > tbody").html(tableData);
}
