// Image Preview
const imageInput = document.getElementById("adminImage");
const previewImage = document.getElementById("previewImage");

imageInput.addEventListener("change", function () {

    const file = this.files[0];

    if(file){

        const reader = new FileReader();

        reader.onload = function(e){
            previewImage.src = e.target.result;
            previewImage.style.display = "block";
        };

        reader.readAsDataURL(file);
    }
});
document.getElementById("previewImage").addEventListener("click", function(){
    console.log("Image Clicked");
    document.getElementById("cropImagePopUp").style.display = "flex";
    // imageInput.click();
});
// Form Submit
document.getElementById("adminForm")
.addEventListener("submit", async function(e){

    e.preventDefault();

    const formData = new FormData();

    formData.append("photo", croppedFile);
	console.log(croppedFile)
    const adminData = {

        name: document.getElementById("name").value,
        dateofbirth: document.getElementById("dob").value,
        gender: document.getElementById("gender").value,
        joinDate: document.getElementById("joinDate").value,
        mobilenumber: document.getElementById("mobile").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };
	console.log(adminData)
    formData.append(
        "adminDTO",
        new Blob(
            [JSON.stringify(adminData)],
            { type:"application/json" }
        )
    );

    try{

        const response = await fetch("/appController/register",{
            method:"POST",
            body:formData
        });
		console.log(response);
        const result = await response.json();
		if(!response.ok){
			throw result
		}
		window.location.reload();
        console.log(result);

        alert("Admin Registered Successfully");

    }catch(error){

        console.log(error);

        alert("Registration Failed");
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
cropButton.addEventListener("click", function(){

    if(!cropper) return;

    const canvas = cropper.getCroppedCanvas({

        width:500,
        height:500
    });

    // Preview
    previewImage.src = canvas.toDataURL("image/jpeg");

    // Convert canvas to file
    canvas.toBlob(function(blob){

        croppedFile = new File(
            [blob],
            "doctorProfile_image.jpeg",
            {
                type:"image/jpeg"
            }
        );

        console.log(croppedFile);

    }, "image/jpeg", 0.9);
    document.getElementById("cropImagePopUp").style.display = "none";
});


