var appointment_status = document.querySelectorAll(".get_appointment_status")
for (let i = 0; i <= appointment_status.length - 1; i++) {
    let text = appointment_status[i].textContent;
    if (text === "Rejected") {
        appointment_status[i].style.background = "tomato"
    } else if (text == "Pendding") {
        appointment_status[i].style.background = "yellow"
    } else if (text === "Approved") {
        appointment_status[i].style.background = "green"
    } else {
        appointment_status[i].style.background = "red"
    }
}

window.onload = function () {
    
}

const params = new URLSearchParams(window.location.search);

const request = params.get("request");
const id = params.get("id")
// console.log(id)
console.log(request);

let back_Button = document.getElementById("back_Button")
back_Button.addEventListener("click", () => {
    if (request === "view_all_User_appointments_in_specialization_dashBoard") {
        location.href = "/userHomePage.html"
    }
})