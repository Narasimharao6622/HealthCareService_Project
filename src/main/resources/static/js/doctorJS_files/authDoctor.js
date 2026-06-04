let submit = document.getElementById("submit");
var userid = document.getElementById("userid")
var passwordid = document.getElementById("passwordid")
submit.addEventListener("click",(e)=>{
    e.preventDefault();
    let user = userid.value;
    let password = passwordid.value;

    let userdata = {
        "doctorid" : user,
        "password" : password
    }
    fetch("/doctorController/doctorLogin",{
        method : "POST",
        headers : {
            "Content-type":"Application/json"
        },
        body : JSON.stringify(userdata)
    }).then(async res=>{
        let data = await res.json();
        if(!res.ok){
            throw data;
        }
        return data;
    }).then(data=>{
        location.reload();
        console.log(data)
    }).catch(err=>{
        console.log(err)
    })
})