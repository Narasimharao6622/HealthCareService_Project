var varifiedMobileNumber = null;
var varifiedEmailid = null;

var conformMobileNummberStatus = false;
var conformEmailidStatus = false;
var enteredMobileNumberStatus = false;
var enteredEmailIdStatus = false;

var conformVarifiedMobileMessage = document.getElementById("mobileVarifiedMessage");
conformVarifiedMobileMessage.style.display = "none";
var conformVarifiedEmailMessage = document.getElementById("emailVarifiedMessage");
conformVarifiedEmailMessage.style.display = "none";

var mobileNumberInput = document.getElementById("mobilenumber");
var emailInput = document.getElementById("email");

var mobileVerifyMessage = document.getElementById("mobileNumberVerifyMessage");
var emailVerifyMessage = document.getElementById("verifyEmailidMessage");


var mobileOTP = document.getElementById("mobileOTP");
var emailOTP = document.getElementById("emailOTP");



var nameinput = document.getElementById("name");
var locationInput = document.getElementById("location");
var city = document.getElementById("city");
var district = document.getElementById("district");
var state = document.getElementById("state");

[nameinput, locationInput, city, district, state].forEach(input => {
    input.addEventListener("input", function() {
        if (this.value.trim() !== "") {
            this.value = inputText(this.value);
        }
    });
});

function inputText(text) {
    var name1 = text;
    var validName = "";
    var spaceCount = 0;
    var FirstLetterUppercase = true;
    for (i = 0;i <= name1.length - 1;i++) {
        var ele = name1.charAt(i);
        if (ele >= '0' && ele <= '9' && name1.length == 1) {
            alert("Name doesn't starts with number");
            name1 = '';
        }
        else if (/[^a-zA-Z0-9]/.test(ele) && ele != ' ') {
            alert("Special characters are not allowed in name");
            name1 = validName;
        }
        else if (FirstLetterUppercase) {
            FirstLetterUppercase = false;
            if (ele >= 'a' && ele <= 'z' && ele != ' ') {
                name1 = validName + ele.toUpperCase();
            } else if (ele == ' ') {
                if (name1.length == 1) {
                    alert("name doesn't start with space");
                } else {
                    alert("double spaces not allowed in name");
                }
                name1 = validName;
            } else if (ele >= '0' && ele <= '9') {
                alert("Name doesn't starts with number");
                name1 = validName;
            }
        } else if (ele <= 'A' && ele >= 'Z') {
            name1 = validName + ele.toLowerCase();
        } else if (ele >= '0' && ele <= '9') {
            name1 = validName;
            alert("Name doesn't contains numbers....");
        } else if (ele == ' ') {
            spaceCount++;
            if (spaceCount == 1) {
                FirstLetterUppercase = true;
                spaceCount = 0;
            }
        }
        validName += ele;
    }
    return name1;
}


// document.querySelectorAll(".varify").forEach(function(element) {
//     element.innerHTML = "Verify";
// });




var imageInput = document.getElementById("photo")

const previewImage = document.getElementById("previewImage");
previewImage.addEventListener("click", () => {
    document.getElementById("cropImagePopUp").style.display = "flex";
})
imageInput.addEventListener("change", function() {

    const file = this.files[0];

    if (file) {

        const reader = new FileReader();

        reader.onload = function(e) {
            previewImage.src = e.target.result;
            previewImage.style.display = "block";
        };

        reader.readAsDataURL(file);
    }
});
const cropImage = document.getElementById("cropImage");
const cropButton = document.getElementById("cropButton");

let cropper;
let croppedFile;

// Upload Image
imageInput.addEventListener("change", function(e) {

    const file = e.target.files[0];

    if (!file) return;

    const reader = new FileReader();

    reader.onload = function(event) {

        cropImage.src = event.target.result;
        cropImage.style.display = "block";

        // Destroy previous cropper
        if (cropper) {
            cropper.destroy();
        }

        // Create Cropper
        cropper = new Cropper(cropImage, {

            aspectRatio: 1, // square crop

            viewMode: 1,

            dragMode: "move",

            autoCropArea: 1,

            responsive: true,

            background: false,

            movable: true,

            zoomable: true,

            scalable: true,

            rotatable: false
        });
    };

    reader.readAsDataURL(file);
    document.getElementById("cropImagePopUp").style.display = "flex";
});


// Crop Image
cropButton.addEventListener("click", function() {

    if (!cropper) return;

    const canvas = cropper.getCroppedCanvas({

        width: 500,
        height: 500
    });

    // Preview
    previewImage.src = canvas.toDataURL("image/jpeg");

    // Convert canvas to file
    canvas.toBlob(function(blob) {

        croppedFile = new File(
            [blob],
            "doctorProfile_image.jpeg",
            {
                type: "image/jpeg"
            }
        );

        // console.log(croppedFile);

    }, "image/jpeg", 0.9);
    document.getElementById("cropImagePopUp").style.display = "none";
});



var icon = document.getElementById("Icon");
var conformIcon = document.getElementById("conformIcon");


icon.addEventListener("click", function() {
    var passwordInput = document.getElementById("password");
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        icon.classList.remove("fa-eye-slash");
        icon.classList.add("fa-eye");
    } else {
        passwordInput.type = "password";
        icon.classList.remove("fa-eye");
        icon.classList.add("fa-eye-slash");
    }
});
conformIcon.addEventListener("click", function() {
    var conformPasswordInput = document.getElementById("conformpassword");
    if (conformPasswordInput.type === "password") {
        conformPasswordInput.type = "text";
        conformIcon.classList.remove("fa-eye-slash");
        conformIcon.classList.add("fa-eye");
    } else {
        conformPasswordInput.type = "password";
        conformIcon.classList.remove("fa-eye");
        conformIcon.classList.add("fa-eye-slash");
    }
});




//validate mobile number and email id using regex


mobileVerifyMessage.innerHTML = "Verify";
mobileNumberInput.addEventListener("input", async function() {
    var mobilenumber = mobileNumberInput.value;
    mobileVerifyMessage.style.display = "flex";
    mobileVerifyMessage.style.color = "black";
    mobileVerifyMessage.style.cursor = "pointer";
    conformVarifiedMobileMessage.style.display = "none";
    enteredMobileNumberStatus = false;
    var mobileNumberPattern = /^\d{10}$/;
    if (mobilenumber.length >= 10) {
        mobilenumber = mobilenumber.slice(0, 10);
        mobileNumberInput.value = mobilenumber;
        console.log(mobilenumber)
    }
    if (mobilenumber.length === 10 && mobileNumberPattern.test(mobilenumber)) {
        await fetch("/appController/check-mobile-number?mobilenumber=" + mobileNumberInput.value, {
            method: "POST",
        }).then(async res => {
            const data = await res.json();
            if (!res.ok) {
                return Promise.reject(data);
            }
            return data;
        }).then(async data => {
            if (data.condition) {
                mobileNumberInput.style.borderColor = "red";
                alert("Mobile number already exists");
            } else {
                mobileNumberInput.style.borderColor = "green";
                enteredMobileNumberStatus = true;
            }
        }).catch(err => {
            console.log(err);
        })
    } else if (mobileNumberInput.value.includes(" ") || /[a-zA-Z]/.test(mobileNumberInput.value)) {
        mobileNumberInput.value = mobileNumberInput.slice(0, mobilenumber.length - 1);
        mobileNumberInput.style.borderColor = "red";
        alert("Please enter a digits for mobile number");
    } else {
        mobileVerifyMessage.style.display = "flex";
        mobileVerifyMessage.innerHTML = "Verify";
        mobileVerifyMessage.style.color = "black";
        mobileVerifyMessage.style.cursor = "pointer";

        mobileNumberInput.style.borderColor = "red";
    }
});

emailVerifyMessage.innerHTML = "Verify";
emailInput.addEventListener("input", function() {
    let emailidGivenByUser = emailInput.value;
    emailVerifyMessage.style.display = "flex";
    emailVerifyMessage.classList.remove("loading");
    emailVerifyMessage.classList.add("varify");
    emailVerifyMessage.style.cursor = "pointer";
    //emailOTP.style.display = "none";
    enteredEmailIdStatus = false;
    conformVarifiedEmailMessage.style.display = "none";
    document.getElementById("emailErrorMessage").style.opacity = "0";
    /*var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;*/
    var domains = ["@gmail.com", "@yahoo.com", "@outlook.com"]

    var userDomain = "";
    for (let i = 0;i <= emailidGivenByUser.length - 1;i++) {
        var ch = emailidGivenByUser.charAt(i);
        try {
            if (!((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch === '@')) {
                throw new Error("Doesn't contains special charecters")
            }
        } catch (err) {
            document.getElementById("emailErrorMessage").style.opacity = "1";
            document.getElementById("emailErrorMessage").innerText = err.message;
            console.log(err.message)
            return;
        }
        if (ch === '@') {
            //userDomain += ch;
            for (let j = i;j <= emailidGivenByUser.length - 1;j++) {
                userDomain += emailidGivenByUser.charAt(j);
            }
            break;
        }
    }
    if (domains.includes(userDomain)) {
        enteredEmailIdStatus = true;
    }
});
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

/*verify mobile number and email id using OTP*/


//send request to the backend for mobile OTP

var mobilealertOTP;
let genaretedMobileOTP = null;
mobileVerifyMessage.addEventListener("click", async function() {
    if (enteredMobileNumberStatus) {
        mobileNumberInput.disabled = true;
        /*mobilealertOTP = getOTP();*/
        /*alert(`Mobile OTP is  : ${mobilealertOTP} sending otp is expensive so i am showing otp in alert`);*/
        await fetch("/appController/send_mobile_otp?number=" + mobileNumberInput.value, {
            method: "POST"
        }).then(res => {
            const otp = res.json();
            if (!res.ok) {
                throw new Error("OTP not send");
            } return otp;
        }).then(otp => {
            genaretedMobileOTP = otp.message;
            mobileVerifyMessage.classList.add("loading");
            mobileVerifyMessage.classList.remove("verify");
            mobileVerifyMessage.innerText = "Sending OTP...";
            mobileVerifyMessage.style.cursor = "default";
            alert("Mobile Number OTP :- " + otp.message)
            mobileVerifyMessage.style.display = "none";
            mobileOTP.style.display = "flex";
        }).catch(err => {
            console.log(err)
        })
    } else {
        alert("Please enter a valid mobile number");
    }
});

//send request to the backend for emailid OTP

var genaretedEmailOTP = null;
emailVerifyMessage.addEventListener("click", function() {
    if (enteredEmailIdStatus) {
        emailInput.disabled = true;
        fetch(`/appController/check_User_email_status?email=${emailInput.value}`, {
            method: "POST",
        }).then(async res => {
            const data = await res.json();
            if (!res.ok) {
                return Promise.reject(data);
            }
            return data;
        }).then(data => {
                console.log(data);
            if (data.status === 200) {
                emailVerifyMessage.classList.remove("varify");
                emailVerifyMessage.classList.add("loading");
                emailVerifyMessage.innerHTML = "Sending OTP...";
                emailVerifyMessage.style.cursor = "default";
                conformEmailidStatus = true;
                if (enteredEmailIdStatus) {
                    fetch(`appController/email_otp_send?email=${emailInput.value}`, {
                        method: "POST",
                    }).then(async res => {
                        //response is not a json object...
                        const data = res.json();
                        if (!res.ok) {
                            throw new Promise.reject(data);
                        } return data;
                    }).then(otp => {
                        console.log(otp);
                        genaretedEmailOTP = otp.message;
                        alert("Email id OTP :- " + otp.message)
                        emailVerifyMessage.style.display = "none";
                        emailOTP.style.display = "flex";
                        /*if (!otp.bodyUsed) {
                            document.getElementById("emailErrorMessage").style.opacity = "1";
                            document.getElementById("emailErrorMessage").innerText = "Email id is not valid, Please enter valid email id.";
                            emailOTP.style.display = "none";
                            emailVerifyMessage.style.display = "flex";
                            emailVerifyMessage.classList.add("varify");
                            emailVerifyMessage.classList.remove("loading");
                            emailVerifyMessage.innerHTML = "Verify";
                            emailVerifyMessage.style.cursor = "default";
                            conformEmailidStatus = false;
                            return;
                        }*/
                    }).catch(err => {
                        emailInput.disabled = false;
                        console.log(err)
                    })
                }else{
					alert("Enter valid email id")
				}
            } else {
                    emailInput.disabled = false;
                    document.getElementById("emailErrorMessage").style.opacity = "1";
                    document.getElementById("emailErrorMessage").innerText = data.message;
					//console.log("Hai")
                //alert("Please enter a valid email id");
            }
        }).catch(err => {
            console.log(err);
        });
    } else {
        document.getElementById("emailErrorMessage").style.opacity = "1";
        document.getElementById("emailErrorMessage").innerText = "Please enter valid email";
    }

});

//verify mobile OTP

mobileOTP.style.fontSize = "clamp(.3rem , 4vw , 1.1rem)";
mobileOTP.addEventListener("input", async function() {
    conformVarifiedMobileMessage.style.display = "none";
    if (mobileOTP.value.length === 6) {
        mobileOTP.placeholder = "Enter OTP";
        await fetch(`/appController/verify_mobile_otp?mobilenumber=${mobileNumberInput.value}&otp=${mobileOTP.value}`, {
            method: "POST",
        }).then(async res => {
            var data = await res.json();
            if (!res.ok) {
                return Promise.reject(data);
            }
            return data;
        }).then(data => {
            if ("Verfied" === data.message) {
                mobileNumberInput.disabled = false;
                conformVarifiedMobileMessage.style.display = "flex";
                conformVarifiedMobileMessage.innerHTML = "verified";
                conformVarifiedMobileMessage.style.color = "green";
                conformVarifiedMobileMessage.style.cursor = "default";
                mobileOTP.style.display = "none";
                mobileOTP.value = "";
                conformMobileNummberStatus = true;
            } else {
                mobileOTP.style.borderColor = "red";
                mobileOTP.value = "";
                mobileOTP.placeholder = "Invalid OTP, try again";
                mobileOTP.style.display = "flex";
            }
        }).catch(err => {
            mobileOTP.style.borderColor = "red";
            mobileOTP.value = "";
            mobileOTP.placeholder = "Invalid OTP, try again";
            mobileOTP.style.display = "flex";
            console.log(err);
        })
    }
    else if (mobileOTP.value.length > 6) {
        mobileOTP.value = mobileOTP.value.slice(0, 6);
    }
});

//verify email OTP

emailOTP.addEventListener("input", async function() {
    if (emailOTP.value.length === 6) {
        emailOTP.placeholder = "Enter OTP";
        try {
            var response = await fetch(`/appController/email_otp_verify?email=${emailInput.value}&otp=${emailOTP.value}`, {
                method: "POST"
            })
            var data = await response.json();
            console.log(data)
            if (!response.ok) {
                throw new Error(data?.message || "something went to worng");
            }
            if (data.status === 200) {
                emailInput.disabled = false;
                conformVarifiedEmailMessage.style.display = "flex";
                conformVarifiedEmailMessage.innerHTML = "verified";
                conformVarifiedEmailMessage.style.color = "green";
                conformVarifiedEmailMessage.style.cursor = "default";
                emailOTP.style.display = "none";
                conformEmailidStatus = true;

            }
            console.log(data);
        } catch (err) {
            console.log(err.message);
            emailVerifyMessage.style.display = "flex";
            emailVerifyMessage.innerHTML = err.message;
            emailVerifyMessage.style.fontSize = "16px"
            emailVerifyMessage.style.color = "red";
            emailOTP.style.display = "none";
        }
        if (emailOTP.value === genaretedEmailOTP) {
            //-----------------------------
        } else {
            //-----------------------------
        }
    } else if (emailOTP.value.length > 6) {
        emailOTP.value = emailOTP.value.slice(0, 6);
    }
});





var conformAggrement = document.getElementById("conformAggrement");
conformAggrement.addEventListener("change", function() {
    if (conformAggrement.checked) {
        submit.disabled = false;
    } else {
        submit.disabled = true;
    }
});




document.forms[0].addEventListener('submit', function(event) {
    event.preventDefault();

    var date = new Date(document.getElementById("dob").value);
    var today = new Date();
    var age = today.getFullYear() - date.getFullYear() - (today.getMonth() < date.getMonth() || (today.getMonth() === date.getMonth() && today.getDate() < date.getDate()) ? 1 : 0);

    var password = document.getElementById("password").value;
    var conformPassword = document.getElementById("conformpassword").value;


    var patient = {
        "name": document.getElementById("name").value,
        "age": age,
        "gender": document.getElementById("gender").value,
        "bloodgroup": document.getElementById("bloodgroup").value,
        "mobilenumber": document.getElementById("mobilenumber").value,
        "secondmobilenumber": document.getElementById("mobilenumber").value,
        "emailid": document.getElementById("email").value,
        "secondemailid": document.getElementById("email").value,
        "password": password,
    }
    var address = {
        "house_no": document.getElementById("house_no").value,
        "street": document.getElementById("street").value,
        "location": document.getElementById("location").value,
        "city": document.getElementById("city").value,
        "district": document.getElementById("district").value,
        "state": document.getElementById("state").value,
        "pincode": document.getElementById("pincode").value
    }


    var formData = new FormData();
    formData.append("patient",
        new Blob([JSON.stringify(patient)],
            { type: "application/json" }
        ));
    formData.append("address",
        new Blob([JSON.stringify(address)],
            { type: "application/json" }
        ));
    formData.append("photo", croppedFile);

    if (password === conformPassword && password.length >= 8) {
        if (conformMobileNummberStatus && conformEmailidStatus) {
            fetch("/appController/patientDetails", {
                method: "POST",
                body: formData,
                credentials: "include"
            }).then(async function(response) {
                var data = await response.json();
                console.log(data);
                if (response.ok) {
                    return data;
                } else {
                    return Promise.reject(data);
                }
            }).then(data => {

                if (data.status == 201) {
                    alert("Registration successfully!");
                    document.forms[0].reset;
                    window.location.replace("loginPage.html");
                } else {
                    console.log("some thing went to worng..")
                }

            }).catch(error => {
                console.error("Error:", error);
            });

        } else {
            alert("Please verify your mobile number and email id");
        }
    } else {
        alert("Password is incorrect");
    }
});







