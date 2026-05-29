var emailidInputHeading = document.getElementById("emailidInputHeading")
var passwordInputHeading = document.getElementById("passwordInputHeading")

var rememberMeBox = document.getElementById("remember")
var emailInput = document.getElementById("email")
var passwordInput = document.getElementById("password")

document.getElementById("formPage").addEventListener("submit", async (e) => {
    e.preventDefault();
    const userDetails = {
        emailid: emailInput.value,
        password: passwordInput.value,
        rememberme: rememberMeBox.checked
    };
    await fetch("/appController/user_login", {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userDetails)
    }).then(async res => {
        var data = await res.json();
        if (!res.ok) {
            throw new Error(data);
        }
        return data;
    }).then(data => {
        if (data.status == 200) {
            window.location.reload();
			window.location.replace("userHomePage.html");
        }else if (data.status === 406) {
            emailidInputHeading.style.background = "red"
        } else {
            passwordInputHeading.style.background = "red"
        }
    }).catch(err => {
        alert("Something went to wrong! Please try again later.");
        location.reload();
    })
})