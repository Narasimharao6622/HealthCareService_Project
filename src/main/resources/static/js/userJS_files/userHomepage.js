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
    profileContainer_PopUp.style.display = "block";
    profileContainer_PopUp.style.width = "80%";
    profileContainer_PopUp.style.height = "90%";
    document.getElementById("userProfileImage").src = user.imagefilepath;
    document.getElementById("profilePageUserName").innerHTML = user.name;
    document.getElementById("profilePageEmail").innerHTML = user.emailid;
    document.getElementById("profilePageMobilenumber").innerHTML = `+91 ${user.mobilenumber}`;
});

// MANUAL BACK BUTTON
document.getElementById("userHomeProfilePageBackButton").addEventListener("click", function() {
    profileContainer_PopUp.style.width = "0%";
    profileContainer_PopUp.style.height = "0%";
    setTimeout(() => {
        profileContainer_PopUp.style.display = "none";
    }, 100);
});

function openTab(tabId) {
    document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));

    document.getElementById(tabId).classList.add('active');
}

var user = null;

window.onload = async () => {
    try {
        let response = await fetch("/userController/userHomePage", {
            method: "GET",
            credentials: "include"
        })

        if (!response.ok) {
            throw new Error(response)
        }
        var data = await response.json();
        user = data.data;
    	//alert("Hello : "+user.name)
        document.getElementById("userHomePagePhoto").src = user.imagefilepath;
        document.getElementById("homePageUserName").innerHTML = user.name;
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
        doctor.data.forEach(async doctorData => {

            let card = document.createElement("div")
            setTimeout(() => {
                card.classList.add("search_Result");
                card.innerHTML = `
					<img src="${doctorData.imagefilepath || ''}" 
							             alt="No Photo" 
							             class="doctor_image"/>

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
                alert(doctorData.name);
            };

            if (doctorData.imagefilepath != null) {
                document.getElementById("doctor_image").src = doctorData.imagefilepath;
            } else {
                document.getElementById("doctor_image").src = doctorData.imagefilepath;
            }
            document.getElementById("doctor_name").innerHTML = doctorData.name;
            document.getElementById("categiry").innerHTML = doctorData.specialization;
            document.getElementById("rating").innerHTML = doctorData.totalrating;


            /*card.innerHTML=`
                <h3>${doctorData.name}</h3>
            `*/
            searchedResult.appendChild(card);
            console.log(doctorData.name);
        })
        console.log(doctor.data)
    }).catch(err => {
        console.log(err)
        let card = document.createElement("div");
        card.classList.add("noDoctorsFound");
        card.innerHTML = err.errors[0];
        searchedResult.appendChild(card);
    })

})













