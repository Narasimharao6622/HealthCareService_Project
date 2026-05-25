window.onload = () => {
    history.pushState({ page: "emailBlock" }, "", "")
    // var bobles = document.querySelectorAll(".boble");
    var bobles = [];
    for (let i = 0; i < 20; i++) {
        let boble = document.createElement("div");
        boble.classList.add("boble");
        bobles.push(boble);
        document.querySelector(".animationContaner").appendChild(boble);
    }
    setInterval(() => {
        bobles.forEach(boble => {
            let size = Math.floor(Math.random() * 100) + 50;
            let scale = (Math.random() * 2).toFixed(1);
            let color = `rgb(
                ${Math.floor(Math.random() * 255)},
                ${Math.floor(Math.random() * 255)},
                ${Math.floor(Math.random() * 255)}
            )`;
            var height = window.innerHeight;
            var width = window.innerWidth;
            if (height > 900) {
                height = height - 950;
            }
            boble.style.setProperty("--size", size + "px");
            boble.style.setProperty("--scale", scale);
            boble.style.setProperty("--bg", color);
            boble.style.setProperty("--shadow", `0 0 10px 10px ${color}`);
            let x = Math.floor(Math.random() * width);
            let y = Math.floor(Math.random() * height);
            boble.style.setProperty("--x", x + "px");
            boble.style.setProperty("--y", y + "px");
        })
    }, 2000);
}
let limitOtps = 5;
var emailInput = document.getElementById("email");
var emailerror = document.getElementById("emailerror");
var emailVerifyButton = document.getElementById("emailVerifyButton");
emailVerifyButton.addEventListener("click", (e) => {
    e.preventDefault();
    var email = emailInput.value;
    let timerMessage = document.getElementById("timerMessage");
    emailerror.textContent = "";
    otperror.textContent="";
    if (email.length === 0) {
        emailerror.textContent = "Email is required";
        return;
    }
    console.log(limitOtps)
    if (limitOtps !== 0) {
        console.log("clicked")
        fetch("/appController/email_otp_send?email=" + email, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(async (res) => {
            var data = await res.json();
            if (!res.ok) {
                throw data;;
            }
            return data;
        }).then(data => {
            console.log(data)
            if (data.condition) {
                emailVerifyButton.setAttribute("readonly", true);
                limitOtps--;
                setTimeout(() => {
                    alert("Email opt :- " + data.message)
                }, 5000)
                document.getElementById("otp").removeAttribute("readonly");
                let timer = 59;
                timerMessage.textContent = `00:${timer}`;
                timerMessage.style.color = "white"
                let interval = setInterval(() => {
                    timer--;
                    timerMessage.textContent = `00:${timer < 10 ? "0" + timer : timer}`;
                    emailInput.setAttribute("readonly", true)
                    if (timer === 0) {
                        fetch("/appController/clearOTP?email=" + email, {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            }
                        }).then(async response => {
                            if (!response.ok) {
                                throw "Something went to wrong. Please try again.";
                            }
                            var data = await response.text();
                            timerMessage.textContent = `${data}. Please try again.`;
                            timerMessage.style.color = "red"
                            emailVerifyButton.value = "Resend OTP"
                            otperror.innerHTML = "";
                        }).catch(err => {
                            timerMessage.textContent = err;
                            timerMessage.style.color = "red"
                        })
                        clickOtp = true;
                        clearInterval(interval);
                        document.getElementById("otp").setAttribute("readonly", true);
                        emailVerifyButton.removeAttribute("readonly");
                        emailInput.removeAttribute("readonly");
                    }
                }, 1000);
            } else {
                emailerror.textContent = data.message;
            }
        }).catch((err) => {
            emailerror.textContent = err.message;
        });
    } else if (limitOtps === 0) {
        timerMessage.textContent = `Your limit is over `;
        timerMessage.style.color = "red"
    }

})
var verityOtpAndOpenPasswordBlock = document.getElementById("otpVerifyButton")
var otperror = document.getElementById("otperror");
verityOtpAndOpenPasswordBlock.addEventListener("click", () => {
    var otp = document.getElementById("otp").value;
    var email = emailInput.value;
    if (otp !== 0) {
        fetch(`/appController/email_otp_verify?email=${email}&otp=${otp}`, {
            method: "POST"
        }).then(async res => {
            var data = await res.json();
            if (!res.ok) {
                throw data;
            }
            return data
        }).then(data => {
            console.log(data)
            if (data.condition) {
                history.pushState({ page: "passwordBlock" }, "", "")
                document.getElementById("emailBlock").style.display = "none"
                document.getElementById("passwordBlock").style.display = "flex"
                document.getElementById("otp").value = ""
            }
        }).catch(err => {
            console.log(err);
            otperror.innerHTML = err.message;
        })
    } else {
        otperror.innerHTML = "Please enter otp..."
    }
})

var newPasswordInput = document.getElementById("newPassword");
var newPasswordeye = document.getElementById("newPasswordeye");
newPasswordeye.addEventListener("click", (e) => {
    if (newPasswordInput.type === "password") {
        newPasswordInput.type = "text";
        e.target.classList.remove("fa-eye-slash");
        e.target.classList.add("fa-eye");
    } else {
        newPasswordInput.type = "password";
        e.target.classList.remove("fa-eye");
        e.target.classList.add("fa-eye-slash");
    }
})

var userPassword = "";
var passwordErrorMessage = document.getElementById("passwordErrorMessage");
newPasswordInput.addEventListener("input", () => {
    var password = newPasswordInput.value;
    var strength = 0;
    if (password.length >= 8) strength++;
    if (/[A-Z]/.test(password)) strength++;
    if (/[a-z]/.test(password)) strength++;
    if (/[0-9]/.test(password)) strength++;
    if (/[^A-Za-z0-9]/.test(password)) strength++;
    if (strength < 3) {
        newPasswordInput.style.borderBottom = "2px solid red";
        newPasswordeye.style.borderBottom = "2px solid red";
        passwordErrorMessage.textContent = "Password is too weak.";
    } else if (strength < 5) {
        newPasswordInput.style.borderBottom = "2px solid orange";
        newPasswordeye.style.borderBottom = "2px solid orange";
        passwordErrorMessage.textContent = "Password is moderate. ";
    } else {
        newPasswordInput.style.borderBottom = "2px solid green";
        newPasswordeye.style.borderBottom = "2px solid green";
        passwordErrorMessage.textContent = "";
    }
    userPassword = password;
})


var conformPassword = document.getElementById("conformPassword");
var conformPasswordeye = document.getElementById("conformPasswordeye")
conformPasswordeye.addEventListener("click", (e) => {
    if (conformPassword.type === "password") {
        conformPassword.type = "text";
        e.target.classList.remove("fa-eye-slash");
        e.target.classList.add("fa-eye");
    } else {
        conformPassword.type = "password";
        e.target.classList.remove("fa-eye");
        e.target.classList.add("fa-eye-slash");
    }
})

var userComformPassord = "";
conformPassword.addEventListener("input", () => {
    if (conformPassword.value !== userPassword) {
        passwordErrorMessage.textContent = "Passwords do not match.";
        conformPassword.style.borderBottom = "2px solid red";
        conformPasswordeye.style.borderBottom = "2px solid red"
    } else {
        passwordErrorMessage.textContent = "";
        conformPassword.style.borderBottom = "2px solid green";
        conformPasswordeye.style.borderBottom = "2px solid green"
    }
    userComformPassord = conformPassword.value;
})


document.getElementById("setPassword").addEventListener("click", () => {
    if (userPassword.length === 0 || userComformPassord.length === 0) {
        document.getElementById("passwordErrorMessage").textContent = "Password and Conform password is required";
        return;
    } else {
        fetch("/appController/set_new_password", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email : emailInput.value,
                password: userPassword
            })
        }).then(async res => {
            var data = await res.json();
            console.log(data)
            if (!res.ok) {
                throw data;
            }
            return data;

        }).then(data => {
            if (data.condition) {
                alert(data.message || "Password changed successfully. Please login with your new password.");
                location.href = "/loginPage.html";
            } else {
                document.getElementById("passwordErrorMessage").textContent = data.message;
            }
        }).catch(err => {
            document.getElementById("passwordErrorMessage").textContent = err.message || "An error occurred. Please try again.";
        });
    }
})




function hidePages() {
    document.getElementById("emailBlock").style.display = "none"
    document.getElementById("passwordBlock").style.display = "none"
}

window.addEventListener("popstate", (e) => {
    console.log(e)
    if (e.state === null) {
        location.href = "/loginPage.html";
    }
    hidePages();
    if (e.state && e.state.page === "passwordBlock") {
        console.log("password block")
        document.getElementById("passwordBlock").style.display = "flex"
    }

    if (e.state && e.state.page === "emailBlock") {
        console.log("email block")
        document.getElementById("emailBlock").style.display = "grid"
    }
})

























