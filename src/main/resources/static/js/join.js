let isCheck = false;
checkPw = () =>{
    let pw = $('#login-pw');
    let pwCf = $('#login-pw-confirm');
    if(pw.val().length > 3 && pw.val().length > 3) {
        if (pw.val() === pwCf.val() && isCheck) {
            submitHidden(false);
        } else {
            submitHidden(true);
        }
    }
}
getParameter = (name) =>{
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
submitHidden = (hidden) => {
    let submit = $('#submit');
    if(hidden)
        submit.css("display","none");
    else
        submit.css("display","");
}
idCheck=()=>{
    let id = $('#login-id');

    $.ajax({
        url:'/member/'+id.val(),
        method:'POST',
        success:(response)=>{
            if(response == 'check') {
                alert('사용 가능한 ID 입니다.');
                isCheck = true;
            }else {
                alert('이미 사용중인 ID 입니다.');
                isCheck = false;
            }
            checkPw();
        }
    })
}
$(document).ready(()=>{
    $(window).keydown((event)=>{
        if(event.keyCode == 13){
            event.preventDefault();
            return false;
        }
    });
    let msg = getParameter("msg");
    if(msg != '')
        alert(msg);
})

