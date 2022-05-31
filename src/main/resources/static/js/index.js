let ACCESS_TOKEN = "myJwtToken";
let REFRESH_TOKEN = "refreshToken";
getParameter = (name) =>{
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
login=()=>{
    $.ajax({
        url:'/member/login',
        method:'post',
        data:{
            'username' : $('#login-id').val(),
            'password' : $('#login-pw').val()
        },
        success:(response)=>{
            console.log(response)
            if(response == 'success'){
                window.location.href="main";
            }else{
                alert("ID와 Password를 확인해 주세요");
                $('#login-id').val('');
                $('#login-pw').val('');
            }
        }
    });
}
setCookie=(name,value,options={})=>{
    options = {
        path:'/',
        ...options
    };
    if(options.expires instanceof Date){
        options.expires = options.expires.toUTCString();
    }
    let updatedCookie = encodeURIComponent(name)+"="+encodeURIComponent(value);
    for(let optionKey in options){
        updatedCookie +="; "+optionKey;
        let optionValue = options[optionKey];
        if(optionValue !== true){
            updatedCookie += "="+optionValue;
        }
    }
    document.cookie= updatedCookie;
}
deleteCookie=(name)=>{
    setCookie(name,"",{
        'max-age':-1
    });
}
$(document).ready(()=>{
    let isError = getParameter("msg");
    if(isError != '')
        alert(isError);
    deleteCookie(ACCESS_TOKEN);
    deleteCookie(REFRESH_TOKEN);
    // console.log($.removeCookie(ACCESS_TOKEN,{path:'/'}));
    // console.log($.removeCookie(REFRESH_TOKEN,{path:'/'}));
});
