let usernameHeading = document.getElementById("usernameHeading");
var userid = document.getElementById("userid");
let userInputContainer = document.querySelector(".userInputContainer");

let passwordHeading = document.getElementById("passwordHeading");
var passwordid = document.getElementById("passwordid");
let passwordInputContainer = document.querySelector(".passwordInputContainer");
let passwordInputBlock = document.querySelector(".passwordInputBlock");

usernameHeading.addEventListener("click", function(e) {
    if(!usernameHeading.classList.contains("active")){
        usernameHeading.classList.add("active");
    }
    userInputContainer.style.boxShadow = "0 0 0 1px black";
    userid.style.transform = "translateY(.5rem)"
    userid.style.border="none"

});

userid.addEventListener("blur",()=>{
    let userInputSize = userid.value.length;
    if(userInputSize==0){
        resetInputField(usernameHeading,userid,userInputContainer);
    }
})

passwordHeading.addEventListener("click", function(e) {
    if(!passwordHeading.classList.contains("active")){
        passwordHeading.classList.add("active");
    }
    passwordInputContainer.style.boxShadow = "0 0 0 1px black"
    passwordInputBlock.style.border = "none"
    passwordInputBlock.style.paddingTop = "20px"
});
passwordid.addEventListener("blur",()=>{
    let passwordLength = passwordid.value.length;
    if(passwordLength==0){
        resetInputField(passwordHeading,passwordInputBlock,passwordInputContainer);
    }
})

function resetInputField(heading,inputfield,inputContainer){
    heading.classList.remove("active");
    inputContainer.style.boxShadow = "none";
    inputfield.style.borderBottom="1px solid black"
    inputfield.style.paddingTop = "0px"
    inputfield.style.transform = "translateY(0rem)"
}

let iconkey = document.getElementById("eyeKey");
iconkey.addEventListener("click",()=>{
    if(passwordid.type=="password"){
        passwordid.type = "text";
        iconkey.classList.replace("fa-eye-slash","fa-eye")
    }
    else{
        passwordid.type = "password";
        iconkey.classList.replace("fa-eye","fa-eye-slash")
    }
})
