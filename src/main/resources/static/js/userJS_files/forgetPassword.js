window.onload = () => {
    // var bobles = document.querySelectorAll(".boble");
    var bobles = [];
    for (let i = 0;i < 20;i++) {
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
var limitOtps = 5;
var clickOtp = true;
var emailInput = document.getElementById("email");
document.getElementById("emailVerifyButton").addEventListener("click", (e) => {
    e.preventDefault();
	var email = emailInput.value; 
	console.log(email)
    let timerMessage = document.getElementById("timerMessage");
    document.getElementById("emailerror").textContent = "";
    if (email.length === 0) {
        document.getElementById("emailerror").textContent = "Email is required";
        return;
    }
    if (clickOtp && limitOtps !== 0) {
        clickOtp = false;
        fetch("/appController/email_otp_send?email=" + email, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
        })
            .then(async (res) => {
                var data = await res.json();
                if (!res.ok) {
                    throw new Error(data.message || "Failed to send OTP");
                }
                return data;
            })
            .then(data => {
                if (data.condition) {
                    limitOtps--;
                    console.log(data)
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
                        if (timer === 0) {
                            fetch("/appController/clearOTP?email=" + email, {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json"
                                }
                            }).then(async response => {
                                if (!response.ok) {
                                    throw new ("Something went to worng")
                                }
                                var data = await response.text();
                                timerMessage.textContent = `${data}. Please try again.`;
                                timerMessage.style.color = "red"
                                document.getElementById("emailVerifyButton").value = "Resend OTP"
                            })
                            clickOtp = true;
                            clearInterval(interval);
                            document.getElementById("otp").setAttribute("readonly", true);
                        }
                    }, 1000);
                } else {
                    document.getElementById("emailerror").textContent = data.message;
                }
            }).catch(() => {
                document.getElementById("emailerror").textContent = "An error occurred. Please try again.";
            });
    } else if (limitOtps === 0) {
        timerMessage.textContent = `Your limit is over `;
        timerMessage.style.color = "red"
    }

})
var verityOtpAndOpenPasswordBlock = document.getElementById("otpVerifyButton")
verityOtpAndOpenPasswordBlock.addEventListener("click", () => {
    var otp = document.getElementById("otp").value;
	var email = emailInput.value;
    if (otp !== 0) {
        fetch(`/appController/email_otp_verify?email=${email}&otp=${otp}`, {
            method: "POST"
        }).then(async res => {
            var data = await res.json();
            if (!res.ok) {
                throw new Error(data);
            }
            return data
        }).then(data => {
			console.log(data)
            if(data.condition){
				document.getElementById("emailBlock").style.display = "none"
				document.getElementById("passwordBlock").style.display = "flex"
			}
        }).catch(err => {
            console.log(err)
        })
    } else {
        document.getElementById("otperror").innerHTML = "Please enter otp..."
    }
})

























