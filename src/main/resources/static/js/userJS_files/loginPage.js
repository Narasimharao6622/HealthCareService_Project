var emailidInputHeading = document.getElementById("emailidInputHeading")
var passwordInputHeading = document.getElementById("passwordInputHeading")

var emailInput = document.getElementById("email")
var passwordInput = document.getElementById("password")

var passwordLabelBox = document.getElementById("passwordLabelBox")
var emailBox = document.getElementById("emailLableBox")

emailInput.addEventListener("click", () => {
    inputHeadingStyle(emailidInputHeading, emailBox)
})
emailInput.addEventListener("blur", () => {
    let email = emailInput.value
    if (email.length == 0) {
        inputHeadingStyleReset(emailidInputHeading, emailBox)
    }
})

passwordInput.addEventListener("click", () => {
    inputHeadingStyle(passwordInputHeading, passwordLabelBox)
})
passwordInput.addEventListener("blur", () => {
    let passwordinput = passwordInput.value
    if (passwordinput.length == 0) {
        inputHeadingStyleReset(passwordInputHeading, passwordLabelBox)
    }
})

emailBox.addEventListener("mouseover", () => {
    emailBox.classList.add("check")
})

emailBox.addEventListener("mouseout", () => {
    emailBox.classList.remove("check")
})

passwordLabelBox.addEventListener("mouseover", () => {
    passwordLabelBox.classList.add("check")
})
passwordLabelBox.addEventListener("mouseout", () => {
    passwordLabelBox.classList.remove("check")
})

function inputHeadingStyle(inputHeadingField, lableField) {
    inputHeadingField.style.transition = "all .2s ease-in-out"
    inputHeadingField.style.transform = "translate(3%,-90%)"
    inputHeadingField.style.background = "rgb(231, 229, 229)"
    inputHeadingField.style.borderRadius = "1.1rem"
    inputHeadingField.style.borderBottom = "1px solid black"
    inputHeadingField.style.fontSize = "1.4rem"
    inputHeadingField.style.zindex = "1"

    lableField.style.transition = "all .3s ease-in-out"
    lableField.style.borderRadius = ".7rem"
    lableField.style.boxShadow = "0 0 5px 1px black"
    lableField.style.border = "2px solid transparent"
}

function inputHeadingStyleReset(inputHeadingField, lableField) {
    inputHeadingField.style.transition = "all .2s ease-in-out"
    inputHeadingField.style.transform = "translate(0%,0%)"
    inputHeadingField.style.background = "white"
    inputHeadingField.style.borderBottom = "none"
    inputHeadingField.style.fontSize = "1.5rem"
    inputHeadingField.style.zindex = "0"

    lableField.style.transition = "all .3s ease-in-out"
    lableField.style.boxShadow = "none"
    lableField.style.borderRadius = ".4rem"
}

document.getElementById("eyeKey").addEventListener("click", () => {
    if (passwordInput.type === "password") {
        passwordInput.type = "text"
        document.getElementById("eyeKey").classList.replace("fa-eye-slash", "fa-eye")
    }
    else {
        passwordInput.type = "password"
        document.getElementById("eyeKey").classList.replace("fa-eye", "fa-eye-slash")
    }
})



document.getElementById("formPage").addEventListener("submit", async (e) => {
    e.preventDefault();
    const userDetails = {
        emailid: emailInput.value,
        password: passwordInput.value,
        rememberme: document.getElementById('remember').checked
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
		console.log(data)
        if (data.status == 200) {
            window.location.reload();
			window.location.replace("userHomePage.html");
        }else if (data.status === 403) {
            emailidInputHeading.style.background = "red"
        } else {
            passwordInputHeading.style.background = "red"
        }
    }).catch(err => {
        alert("403 error");
    })
})




















