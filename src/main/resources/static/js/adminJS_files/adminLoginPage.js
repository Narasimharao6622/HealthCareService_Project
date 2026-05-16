var inputUserID = document.getElementById("userName");
var password = document.getElementById("password");

var button = document.getElementById("button");

document.forms[0].addEventListener("submit", async (event) => {
    event.preventDefault();
    var adminRequest = {
        "adminid": inputUserID.value,
        "password": password.value
    }
    try {
        var response = await fetch(`/appController/adminLogin`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(adminRequest)
        });

        var data = await response.json();
        if (!response.ok) {
            throw new Error(data);
        }

        if (data.status === 200) {
                document.getElementById("loadingBody").style.display = "flex";
            setTimeout(() => {
				window.location.replace("adminHomePage.html")
            }, 200)
        } else {
            document.getElementById("loadingBody").style.display = "flex";
            setTimeout(() => {
                document.getElementById("loadingBody").style.display = "none";
                document.getElementById("errorMessage").innerHTML = data.message;
            }, 500)
        }
    } catch (err) {
        console.log(err)
        //window.location.replace("/adminLoginPage.html")
    }

});


function viewPass() {
    document.getElementById("password").type = "text";
    document.getElementById("eye").className = "fa-solid fa-eye";
}
function hidePass() {
    document.getElementById("password").type = "password";
    document.getElementById("eye").className = "fa-solid fa-eye-slash";
}

















