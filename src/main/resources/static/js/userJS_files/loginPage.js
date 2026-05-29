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

function inputHeadingStyle(inputHeadingField, lableField) {
    inputHeadingField.style.transition = "all .1s ease-in-out"
    inputHeadingField.style.transform = "translate(3%,-90%)"
    inputHeadingField.style.background = "rgb(231, 229, 229)"
    inputHeadingField.style.borderRadius = "1.1rem"
    inputHeadingField.style.zindex = "1"
    // inputHeadingField.style.padding = "0px 10px"
    inputHeadingField.style.borderBottom = ".1rem solid black"

    lableField.style.transition = "all .3s ease-in-out"
    lableField.style.borderRadius = ".7rem"
    lableField.style.boxShadow = "0 0 5px 1px black"
}

function inputHeadingStyleReset(inputHeadingField, lableField) {
    inputHeadingField.style.transition = "all .2s ease-in-out"
    inputHeadingField.style.transform = "translate(0%,0%)"
    inputHeadingField.style.background = "white"
    inputHeadingField.style.zindex = "0"
    inputHeadingField.style.borderBottom = "none"

    lableField.style.transition = "all .3s ease-in-out"
    lableField.style.boxShadow = "none"
    lableField.style.borderRadius = ".4rem"
}

var eyekey = document.getElementById("eyeKey")
eyekey.addEventListener("click", () => {
    if (passwordInput.type === "password") {
        passwordInput.type = "text"
        eyekey.classList.replace("fa-eye-slash", "fa-eye")
    }
    else {
        passwordInput.type = "password"
        eyekey.classList.replace("fa-eye", "fa-eye-slash")
    }
})
























