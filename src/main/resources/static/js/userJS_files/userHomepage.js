function toggleMenu() {
    document.getElementById("navLinks").classList.toggle("active");
}
var homePageSearch = document.getElementById("homePageSearch");
var searchedResult = document.getElementById("searchedResult");
homePageSearch.addEventListener("input", (e) => {
    if (e.target.value.length === 0) {
        searchedResult.style.display = "none";
        searchedResult.style.maxHeight = "50px";
    }
    else {
        searchedResult.style.display = "grid";
        searchedResult.style.maxHeight = "200px";
    }

})
var profileContainer_PopUp = document.getElementById("profileContainer");

document.getElementById("userHomeProfilePage").addEventListener("click", function() {
    profileContainer_PopUp.style.display = "flex";
    profileContainer_PopUp.style.width = "100%";
    profileContainer_PopUp.style.height = "100%";

    document.getElementById("userProfileImage").src = user.imagefilepath;
    document.getElementById("profilePageUserName").innerHTML = user.name;
    document.getElementById("profilePageEmail").innerHTML = user.emailid;
    document.getElementById("profilePageMobilenumber").innerHTML = `+91 ${user.mobilenumber}`;

    document.getElementById("profileinputName").value = user.name;
    document.getElementById("profileinputEmail").value = user.emailid;
    document.getElementById("profileinputphone").value = user.mobilenumber
    // document.getElementById("").value = user
    // document.getElementById("").value = user
    // document.getElementById("").value = user

    history.pushState({ page: "profileOpen" }, "");

});
document.getElementById("appointmentTab").addEventListener("click", () => {
    let appointments = document.getElementById("appointments");
    appointments.innerHTML = "";
    fetch("/userController/getDoctorAppoinments", {
        method: "GET",
        credentials: "include"
    }).then(async res => {
        var data = await res.json();
        if (!res.ok) {
            throw data;
        }
        return data;
    }).then(data => {
        data.data.forEach(doctor => {
            let div = document.createElement("div")
            setTimeout(() => {
                div.classList.add("appointment")
                div.innerHTML = `					
				<div class="appointmentDoctorCard">
					<div class="image">
						<img src="${doctor.imagefilepath || 'No_doctor_image_found'}" class="doctorImage" alt="no image">
					</div>
					<div class="content">
						<h3>${doctor.name}</h3>
						<p>${doctor.specialization}</p>
						<p>
							Appointment date & time
							<span>18 May 2026 - 10:30</span>
						</p>
					</div>
				</div>`
            }, 100)
            appointments.append(div);
        })
    }).catch(async err => {
        let div = document.createElement("div")
        div.classList.add("appointment")
        div.classList.add("active")
        div.innerHTML = err.message
        appointments.append(div)
    })
})

// MANUAL BACK BUTTON
document.getElementById("userHomeProfilePageBackButton").addEventListener("click", function() {
    profileContainer_PopUp.style.width = "0%";
    profileContainer_PopUp.style.height = "0%";
    setTimeout(() => {
        profileContainer_PopUp.style.display = "none";
    }, 100);
    history.back();
});


function openTab(tabId, tabContent) {
    document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));

    document.getElementById(tabContent).classList.add('active');
    document.getElementById(tabId).classList.add('active');
}

var user = null;
window.onload = async () => {
    try {
        let response = await fetch("/userController/userHomePage", {
            method: "GET",
            credentials: "include"
        })
        var data = await response.json();
        if (!response.ok) {
            throw data
        }
        user = data.data;
        document.getElementById("userHomePagePhoto").src = user.imagefilepath;
        document.getElementById("homePageUserName").innerHTML = user.name;

        history.pushState({ page: "homePage" }, "")
    } catch (err) {
        console.log(err.data)
        window.location.replace("loginPage.html")
    }
    return;
}

document.getElementById("logout").addEventListener("click", () => {
    fetch("/userController/user_logout", {
        method: "GET",
        credentials: "include"
    }).then(res => {
        var data = res.json();
        if (!res.ok) {
            return Promise.reject(data)
        }
        return data;
    }).then(data => {
        window.location.replace("loginPage.html")
        console.log(data);
    }).catch(err => {
        console.log(err)
    })
})
var searchedResult = document.getElementById("searchedResult");
document.getElementById("homePageSearch").addEventListener("input", (e) => {
    searchedResult.innerHTML = ""
    fetch('/userController/user_home_Page_SearchBar_ForOnlyUse_Doctors_Name?name=' + e.target.value, {
        method: "POST",
        credentials: "include"
    }).then(async res => {
        var data = await res.json();
        if (!res.ok) {
            return Promise.reject(data)
        }
        return data;
    }).then(doctor => {
			console.log(doctor)
        doctor.data.forEach(async doctorData => {
            let card = document.createElement("div")
            setTimeout(() => {
                card.classList.add("search_Result");
                card.innerHTML = `
					<img src="${doctorData.imagefilepath || ''}" alt="No Photo" class="doctor_image"/>
					<div class="doctor_info">
						<div class="doctor_name">
							${doctorData.name}
						</div>
						<div class="category">
							${doctorData.specialization}
						</div>
						<div class="rating">
							${doctorData.totalrating}
						</div>
					</div>
							`;
            }, 300)

            card.onclick = function() {
				bookAppointment(doctorData.doctorid);
            };

            searchedResult.appendChild(card);
        })
    }).catch(err => {
        let card = document.createElement("div");
        card.classList.add("noDoctorsFound");
        card.innerHTML = err.errors[0];
        searchedResult.appendChild(card);
    })

})

var openBookAppointment = document.querySelector(".openBookAppointmentPage");

function openBookAppointmentPage() {
    //adds into history
    history.pushState({ page: "openBookAppointmentPage" }, "")
    openBookAppointment.style.display = "flex"
    let diseaseBox = document.createElement("div");
    diseaseBox.classList.add(".diseaseBox");
    diseaseBox.innerHTML = `
	
	`;
    openBookAppointment.append(diseaseBox)
}

let selectedSpecialization = "";
let specialization = document.querySelectorAll(".specialization");

specialization.forEach(span => {
    span.addEventListener('click', () => {
        specialization.forEach(item => {
            item.classList.remove("active")
        })
        span.classList.add("active")
        selectedSpecialization = span.innerHTML;
    })
})

async function searchSpecializationDoctors() {
    var noDoctorFound = document.getElementById("noDoctorFound");
    var doctors = "";
    if (selectedSpecialization.length == 0) {
        alert("Please select Specialization")
        return;
    }
    try {
        var response = await fetch("/userController/getSpecialiazation?specialization=" + selectedSpecialization, {
            method: "GET",
            credentials: "include"
        })
        var data = await response.json();

        if (!response.ok) {
            throw data;
        }
        doctors = data.data;
        noDoctorFound.innerHTML = "";
        openspecializationListBoxDashboard(doctors, selectedSpecialization);
    } catch (err) {
        noDoctorFound.innerHTML = err.errors[0];
    }

}

let specializationListBoxDashboard = document.getElementById("specializationListBoxDashboard");
function openspecializationListBoxDashboard(data, selectedSpecialization) {
    //Adds into history -- 2 --
    history.pushState({ page: "openspecializationListBoxDashboard" }, "")
    var doctors = data;
    document.getElementById("dashboardHeading").innerHTML = `${selectedSpecialization} Dashboard`
    specializationListBoxDashboard.style.display = "block";
    document.querySelector(".doctor-section").innerHTML = "";
    doctors.forEach(doctor => {
        let doctorCard = document.createElement("div");
        doctorCard.classList.add("doctor-card")
        let id = doctor.doctorid;
        doctorCard.innerHTML = `
        <img src="${doctor.photo}">
		<div class="doctor-details">
			<h3>Dr. ${doctor.name}</h3>
			<p>
				<b>Specialization:</b> ${doctor.specialization}
			</p>
			<p>
				<b>Experience:</b> ${doctor.experiance} Years
			</p>
			<p>
			    <b>Hospital:</b> City Care Hospital
			</p>
			<p>
			    <b>Consultation Fee:</b> ₹800
			</p>
			<p>
				<b>Available Time:</b> 10:00 AM - 4:00 PM
			</p>
			<button onclick="bookAppointment('${doctor.doctorid}')">Book Appointment</button>
		</div>
    `;
        document.querySelector(".doctor-section").append(doctorCard)
    });

}

function specializationListBoxDashboard_BackButton() {
    specializationListBoxDashboard.style.display = "none";
    history.back();
}

var bookDoctorAppointmentContainer = document.querySelector(".bookDoctorAppointmentContainer")
async function bookAppointment(doctorid) {
    let doctor = null;
    console.log(doctorid)
    await fetch("/userController/getDoctorByDoctorid?doctorid=" + doctorid, {
        method: "GET",
        credentials: "include"
    }).then(async res => {
        var data = await res.json();
        if (!res.ok) {
            throw data;
        }
        return data.data;
    }).then(data => {
        if (data != null) {
            history.pushState({ page: "bookDoctorAppointmentContainer" }, "")
            bookDoctorAppointmentContainer.style.display = "flex"
            doctor = data;
            return;
        }
    })
        .catch(err => {
            console.log(err)
            return;
        })

    bookDoctorAppointmentContainer.innerHTML = "";
    var div = document.createElement("div")
    div.classList.add(".appointment-container")
    div.innerHTML = `
        <div class="appointment-container">

			<h1>Book Appointment</h1>

			<!-- Doctor Details -->

			<div class="doctor-info">

				<h2 id="doctorName">Dr. ${doctor.name}</h2>

				<p><b>Specialization: </b><span id="Specialization"> ${doctor.specialization}</span></p>

				<p><b>Experience:</b> <span id="Experience">${doctor.experiance} years</span></p>

				<p><b>Consultation Fee:</b> <span id="Fee">800 /-</span></p>

				<p><b>Available Time:</b> <span id="AvailableTime">10:00AM - 1:00PA</span></p>

			</div>

			<!-- Appointment Form -->

			<div class="form-group">

				<label>Appointment Date</label>

				<input type="date" id="appointment_container_appointmentDate">

			</div>

			<div class="form-group">

				<label>Select Time</label>

				<select id="appointment_container_appointmentTime">

					<option>Select Time</option>
					<option value="10:00">10:00 AM</option>
					<option value="10:30">10:30 AM</option>
					<option value="11:00">11:00 AM</option>
					<option value="11:30">11:30 AM</option>
					<option value="12:00">12:00 AM</option>
					<option value="12:30">12:20 AM</option>
					<option value="01:00">01:00 PM</option>

				</select>

			</div>

			<div class="form-group">

				<label>Symptoms</label>

				<textarea id="appointment_container_symptoms" placeholder="Enter symptoms"></textarea>

			</div>

			<button class="book-btn" onclick="conformbookAppointment('${doctor.doctorid}')">
				Confirm Appointment
			</button>

			<div class="success-message" id="successMessage">
				
			</div>

		</div>
    `
    bookDoctorAppointmentContainer.append(div);

}



async function conformbookAppointment(doctorid) {
    let appointmentDate = document.getElementById("appointment_container_appointmentDate").value;
    let appointmentTime = document.getElementById("appointment_container_appointmentTime").value;
    let symptoms = document.getElementById("appointment_container_symptoms").value;
    if (
        appointmentDate === "" ||
        appointmentTime === "Select Time" ||
        symptoms === ""
    ) {

        alert("Please fill all details");
        return;

    }
    console.log("Appointment Date :", appointmentDate);
    console.log("Appointment Time :", appointmentTime);
    console.log("Symptoms :", symptoms);

    var bookAppointment = {
        "doctorid": doctorid,
        "appointmentdate": appointmentDate,
        "appointmenttime": appointmentTime,
        "symptoms": symptoms
    }
	
    let successMessage = document.getElementById("successMessage");
    await fetch("/userController/conformbookAppointment", {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(bookAppointment)
    }).then(async res => {
        var data = await res.json();
        if (!res.ok) {
            throw data;
        }
        return data;
    }).then(data => {
        history.back();
        history.back();
        history.back();
        history.replaceState({ page: "homePage" }, "")
        successMessage.style.display = "block";
        successMessage.innerHTML = data.message;

    }).catch(err => {
        successMessage.style.display = "block";
        successMessage.innerHTML = err.message;
        successMessage.style.color = "red"
    })
}

function hideAllPages() {
    openBookAppointment.style.display = "none";
    specializationListBoxDashboard.style.display = "none";
    profileContainer_PopUp.style.width = "0%";
    profileContainer_PopUp.style.height = "0%";
    profileContainer_PopUp.style.display = "none";

    bookDoctorAppointmentContainer.style.display = "none"
}

window.addEventListener("popstate", function(event) {
    if (!event.state) {
        return;
    }
    let currentPage = event.state.page;

    hideAllPages();

    if (currentPage === "openspecializationListBoxDashboard") {
        openBookAppointment.style.display = "flex"
        specializationListBoxDashboard.style.display = "block";
    }

    if (currentPage === "openBookAppointmentPage") {
        openBookAppointment.style.display = "flex"
    }

    if (currentPage === "profileOpen") {
        profileContainer_PopUp.style.width = "100%";
        profileContainer_PopUp.style.height = "100%";
        setTimeout(() => {
            profileContainer_PopUp.style.display = "flex";
        }, 100);
    }

    if (currentPage === "bookDoctorAppointmentContainer") {
        bookDoctorAppointmentContainer.style.display = "flex"
    }

});













