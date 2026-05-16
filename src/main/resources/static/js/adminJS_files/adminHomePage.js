function showMessage(message) {
    alert(message);
}
var profileContainer = document.getElementById("profileContainer");
var backButton = document.getElementById("profileContainerBack");
function showProfile() {
    profileContainer.style.transform = "translateY(0px)"
    profileContainer.style.height = "100vh";
    profileInputs(adminData);
}

function profileInputs(data) {
    var adminProfileImage = document.getElementById("adminProfileContainerImage")
    adminProfileImage.src = data.imageURLPath;
    document.getElementById("adminName").innerHTML = data.name;
    document.getElementById("adminID").innerHTML = `ID : ${data.adminid}`
    document.getElementById("adminID").innerHTML = `ID : ${data.adminid}`

    document.getElementById("adminGenderInput").value = data.gender
    document.getElementById("adminDateOfBirthInput").value = data.dateofbirth
    document.getElementById("adminEmailInput").value = data.email
    document.getElementById("adminPhoneInput").value = data.mobilenumber
    document.getElementById("adminJoinDateInput").value = data.joinDate
}

function backToHomePageFromProfileContainer() {
    setTimeout(() => {
        profileContainer.style.height = "0vh";
        profileContainer.style.transform = "translateY(-730px)"
    }, 200)
}

function showFullProfileImage() {
    let fullImageContainer = document.getElementById("fullProfileImageContainer");
    let fullImage = document.getElementById("fullProfileImage");
    fullImage.src = adminData.imageURLPath;
    fullImageContainer.style.display = "flex";
}

function hideFullProfileImage() {
    let fullImageContainer = document.getElementById("fullProfileImageContainer");
    fullImageContainer.style.display = "none";
}

/* Dynamic Patient Counter */
setInterval(() => {

    let patientCount = document.getElementById("patients");

    let current = parseInt(patientCount.innerHTML);

    patientCount.innerHTML = current + 1;

}, 1000);


let adminData = null;
window.onload = function() {
    fetch("/adminController/adminHomePage", {
        method: "GET",
        credentials: "include"
    }).then(async res => {
        var data = await res.json();
        if (!res.ok) {
            throw new Error(data);
        }
        return data;
    }).then(data => {
        adminData = data.data;
        console.log(adminData)
        document.getElementById("homePageAdminName").innerText = adminData.name;
        document.getElementById("adminProfileImage").src = adminData.imageURLPath;
        document.getElementById("adminRole").innerText = "Admin";
    }).catch(err => {
        console.log(err);
		window.location.replace("adminLoginPage.html");
    })
}


// Edit Admin Details
function editAdminDetails(editButton) {
    if (editButton.innerText === "Edit Profile") {
        if (!document.getElementById("adminNameInput")) {

            let adminNameInputField = document.createElement("div");
            adminNameInputField.id = "adminNameInputField";
            console.log(adminNameInputField)
            let adminNameLabel = document.createElement("span");
            adminNameLabel.innerText = "Admin Name";

            let adminInputfield = document.createElement("p");
            adminInputfield.id = "adminNameField";

            let adminNameInput = document.createElement("input");
            adminNameInput.id = "adminNameInput";
            adminNameInput.type = "text";
            adminNameInput.value = adminData.name;
            adminNameInput.style.border = "1px solid white";

            adminNameInputField.appendChild(adminNameLabel);
            adminInputfield.appendChild(adminNameInput);
            adminNameInputField.appendChild(adminInputfield);

            document.querySelector(".adminProfileBody").appendChild(adminNameInputField);
            document.querySelector(".editProfileBtn").innerHTML = "Save Changes";
            document.querySelectorAll(".input").forEach(input => {
                input.style.border = "1px solid white";
                input.removeAttribute("readonly");

            });
        }
    } else {
        var adminEmailInput = document.getElementById("adminEmailInput");
        var adminPhoneInput = document.getElementById("adminPhoneInput");
        var adminDateOfBirthInput = document.getElementById("adminDateOfBirthInput");
        var adminGenderInput = document.getElementById("adminGenderInput");
        var adminNameInput = document.getElementById("adminNameInput");

        let adminRequest = {
            name: adminNameInput.value,
            gender: adminGenderInput.value,
            dateofbirth: adminDateOfBirthInput.value,
            mobilenumber: adminPhoneInput.value,
            email: adminEmailInput.value
        }

        console.log(adminRequest)
        fetch("/adminController/updateAdminProfile", {
            method: "PUT",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(adminRequest)
        }).then(res => {
            var data = res.json();
            if (!res.ok) {
                throw new Error(data);
            }
            return data;
        }).then(data => {
            adminData = data.data;
            profileInputs(adminData);
            console.log(data)
        }).catch(err => {
            console.log(err);
        })

        document.querySelector(".adminProfileBody").removeChild(adminNameInputField);
        document.querySelectorAll(".input").forEach(input => {
            input.style.border = "none";
            input.setAttribute("readonly", true);
        });
        document.querySelector(".editProfileBtn").innerHTML = "Edit Profile";
        // var adminNameInput = document.getElementById("adminNameInput");
        // if(adminNameInput){
        //     adminNameInput.remove();
        // }
    }
}
let profileChangeChecker = true;
function changeProfile() {
    if (profileChangeChecker) {
        document.getElementById("changeProfileImageInput").click();
        document.getElementById("changeProfileImage").innerHTML = "Save Changes"
        profileChangeChecker = false;
    } else {
        document.getElementById("changeProfileImage").innerHTML = "Change Profile Image"
        hideFullProfileImage();
    }
}

function updateProfileImage(event) {
    let file = event.target.files[0];
    console.log(file);

}

async function logoutAdmin() {
    let confirmLogout = confirm("Are you sure want to logout ?");
    if (confirmLogout) {
        try {
            let logout = await fetch("/adminController/adminLogout", {
                method: "GET",
                credentials: "include"
            })

            if (!logout.ok) {
                throw new Error("Logout failed...")
            }
            else {
                window.location.replace("adminLoginPage.html");
            }
        } catch (err) {
            console.log(err)
			window.location.replace("adminLoginPage.html");
        }
    }
}

var openDoctorManagementclicked = true;

function openDoctorManagement() {
    if (!openPatientManagementclicked) {
        openPatientManagement();
    }
    if (!openDoctorManagementclicked) {
        document.getElementById("doctorManagementBlock").style.width = "0%";
        document.getElementById("doctorManagementBlock").style.height = "0%";
        setTimeout(() => {
            document.getElementById("doctorManagementBlock").style.display = "none";
            document.getElementById("doctorManagementBlock").style.opacity = "0";
        }, 210);
        openDoctorManagementclicked = true;
        return;
    }
    document.getElementById("doctorManagementBlock").style.display = "block";
    setTimeout(() => {
        document.getElementById("doctorManagementBlock").style.width = "83%";
        document.getElementById("doctorManagementBlock").style.height = "auto";
        document.getElementById("doctorManagementBlock").style.opacity = "1";
    }, 100);
    openDoctorManagementclicked = false;
}
var openPatientManagementclicked = true;
function openPatientManagement() {
    if (!openDoctorManagementclicked) {
        openDoctorManagement();
    }
    if (!openPatientManagementclicked) {
        document.getElementById("patientManagementBlock").style.width = "0%";
        document.getElementById("patientManagementBlock").style.height = "0%";
        setTimeout(() => {
            document.getElementById("patientManagementBlock").style.display = "none";
            document.getElementById("patientManagementBlock").style.opacity = "0";
        }, 210);
        openPatientManagementclicked = true;
        return;
    }
    document.getElementById("patientManagementBlock").style.display = "block";
    setTimeout(() => {
        document.getElementById("patientManagementBlock").style.width = "83%";
        document.getElementById("patientManagementBlock").style.height = "auto";
        document.getElementById("patientManagementBlock").style.opacity = "1";
    }, 100);
    openPatientManagementclicked = false;
}

function openAddDoctorPopup() {
    document.getElementById("addDoctorPopup").style.display = "flex";
}

function closeAddDoctorPopup() {
    document.getElementById("addDoctorPopup").style.display = "none";
}

function addDoctor() {

    let name = document.getElementById("doctorName").value;
    let specialization = document.getElementById("doctorSpecialization").value;
    let email = document.getElementById("doctorEmail").value;
    let phone = document.getElementById("doctorPhone").value;
    let timing = document.getElementById("doctorTiming").value;
    let experience = document.getElementById("doctorExperience").value;

    let card = document.createElement("div");

    card.classList.add("doctorCard");

    card.innerHTML = `
    
        <div class="doctorTopSection">

            <img src="/images/doctor.png">

            <div>
                <h2>${name}</h2>
                <p>${specialization}</p>
            </div>

        </div>

        <div class="doctorDetails">

            <div class="doctorInfo">
                <span>Email</span>
                <p>${email}</p>
            </div>

            <div class="doctorInfo">
                <span>Phone</span>
                <p>${phone}</p>
            </div>

            <div class="doctorInfo">
                <span>Timing</span>
                <p>${timing}</p>
            </div>

            <div class="doctorInfo">
                <span>Experience</span>
                <p>${experience}</p>
            </div>

        </div>

        <div class="doctorButtons">

            <button class="updateBtn">
                Update
            </button>

            <button class="timingBtn">
                Update Timing
            </button>

            <button class="deleteBtn" onclick="deleteDoctor(this)">
                Delete
            </button>

        </div>
    
    `;

    document.getElementById("doctorCardsContainer").appendChild(card);

    closeAddDoctorPopup();
}

function deleteDoctor(button) {
    button.parentElement.parentElement.remove();
}