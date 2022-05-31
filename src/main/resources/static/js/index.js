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
$(document).ready(()=>{
    let isError = getParameter("msg");
    if(isError != '')
        alert(isError);
});