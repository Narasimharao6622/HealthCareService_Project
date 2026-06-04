function showMessage(message) {
    alert(message);
}
var profileContainer = document.getElementById("profileContainer");
var backButton = document.getElementById("profileContainerBack");
function showProfile() {
    history.pushState({ page: "profilePage" }, "")
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
    history.back();
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
window.onload =async function() {
    history.pushState({ page: "homepage" }, "")

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
		
		
		fetch("/adminController/getTodayScheduleDoctorsList",{
			method : "GET",
			credentials : "include"
		}).then(async res=>{
			let data =await res.json();
			if(!res.ok){
				throw data;
			}
			return data;
		}).then(data=>{
			console.log(data);
		}).catch(err=>{
			console.log(err);
		})
		
		
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
        history.back();
        return;
    }
    history.pushState({ page: "openDoctorManagement" }, "")
    document.getElementById("doctorManagementBlock").style.width = "83%";
    document.getElementById("doctorManagementBlock").style.height = "auto";
    setTimeout(() => {
        document.getElementById("doctorManagementBlock").style.display = "block";
        document.getElementById("doctorManagementBlock").style.opacity = "1";
    }, 210);
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
        history.back();
        return;
    }
    history.pushState({ page: "openPatientManagement" }, "")
    document.getElementById("patientManagementBlock").style.width = "83%";
    document.getElementById("patientManagementBlock").style.height = "auto";
    setTimeout(() => {
        document.getElementById("patientManagementBlock").style.display = "block";
        document.getElementById("patientManagementBlock").style.opacity = "1";
    }, 210);
    openPatientManagementclicked = false;
}

var addDoctorDashboard = document.getElementById("addDoctorPopup");
function openAddDoctorPopup() {
    addDoctorDashboard.style.display = "flex";
    history.pushState({ page: "addDoctorDashboard" }, "")
}

function closeAddDoctorPopup() {
    document.getElementById("addDoctorPopup").style.display = "none";
}

/* Upload doctors image and adject the size*/

var imageInput = document.getElementById("doctorImageInput")

const previewImage = document.getElementById("previewDoctorImage");

function previewDoctorImage() {
    document.getElementById("cropImagePopUp").style.display = "flex";
}

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
            "doctorupload_image.jpeg",
            {
                type: "image/jpeg"
            }
        );
    }, "image/jpeg", 0.9);
    document.getElementById("cropImagePopUp").style.display = "none";
});


async function addDoctor() {

    history.pushState({ page: "addDoctorPage" }, "")

    let name = document.getElementById("doctorName").value;
    let dateofbirth = document.getElementById("doctorDateOfBirth").value;
    let gender = document.getElementById("gender").value;
    let specialization = document.getElementById("doctorSpecialization").value;
    let email = document.getElementById("doctorEmail").value;
    let phone = document.getElementById("doctorPhone").value;
    let joindate = document.getElementById("doctorJoinDate").value;
    let experience = document.getElementById("doctorExperience").value;
    let password = document.getElementById("doctorPassword").value;

    const doctor = {
        "name": name,
        "dateofbirth": dateofbirth,
        "gender": gender,
        "specialization": specialization,
        "email": email,
        "password": password,
        "number": phone,
        "joindate": joindate,
        "experiance": experience
    }

    var formData = new FormData();

    formData.append("doctor",
        new Blob([JSON.stringify(doctor)],
            { type: "application/json" }
        )
    );
    formData.append("doctorImage", croppedFile);

    await fetch("/adminController/addDoctor", {
        method: "POST",
        credentials: "include",
        body: formData,
        credentials: "include"

    }).then(async res => {
        var data = await res.json();
        if (!res.ok) {
            throw data;
        }
        return data;
    }).then(data => {
        console.log(data)
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
        history.pushState({ page: "doctorCardsContainer" }, "")
        closeAddDoctorPopup();
    }).catch(err => {
        console.log(err)
    })

}

function deleteDoctor(button) {
    button.parentElement.parentElement.remove();
    history.back();
}

function hidePages() {
    addDoctorDashboard.display = "none";

    /*profile Page*/
    profileContainer.style.transform = "translateY(-730px)"
    profileContainer.style.height = "0vh";

    document.getElementById("doctorManagementBlock").style.width = "0%";
    document.getElementById("doctorManagementBlock").style.height = "0%";
    setTimeout(() => {
        document.getElementById("doctorManagementBlock").style.display = "none";
        document.getElementById("doctorManagementBlock").style.opacity = "0";
    }, 210);
    openDoctorManagementclicked = true;

    document.getElementById("patientManagementBlock").style.width = "0%";
    document.getElementById("patientManagementBlock").style.height = "0%";
    setTimeout(() => {
        document.getElementById("patientManagementBlock").style.display = "none";
        document.getElementById("patientManagementBlock").style.opacity = "0";
    }, 210);
    openPatientManagementclicked = true;

}

window.addEventListener("popstate", (e) => {
    if (!e.state) {
        return;
    }

    hidePages();
    var currentPage = e.state.page;

    if (currentPage === "addDoctorDashboard") {
        addDoctorDashboard.style.display = "flex";
    }
    if (currentPage === "doctorCardsContainer") {
        document.getElementById("doctorCardsContainer").style.display = "flex";
    }

    if (currentPage === "openDoctorManagement") {
        console.log("openDoctorManagement")
        document.getElementById("doctorManagementBlock").style.width = "83%";
        document.getElementById("doctorManagementBlock").style.height = "auto";
        setTimeout(() => {
            document.getElementById("doctorManagementBlock").style.display = "block";
            document.getElementById("doctorManagementBlock").style.opacity = "1";
        }, 210);
        openDoctorManagementclicked = false;
    }

    if (currentPage === "openPatientManagement") {
        console.log("openPatientManagement")
            document.getElementById("patientManagementBlock").style.width = "83%";
            document.getElementById("patientManagementBlock").style.height = "auto";
        setTimeout(() => {
            document.getElementById("patientManagementBlock").style.display = "block";
            document.getElementById("patientManagementBlock").style.opacity = "1";
        }, 210);
        openPatientManagementclicked = false;

    }


    if (currentPage === "profilePage") {
        console.log("profilePage");
        profileContainer.style.transform = "translateY(0px)"
        profileContainer.style.height = "100vh";
    }

    if (currentPage === "homepage") {
        console.log("homepage")
    }

})
























