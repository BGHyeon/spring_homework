function getAllComments(){
    $.ajax({
    method: "GET",
    url: "/api/allcomments",
    success: function(response){
        $('#containers').empty();
        for(var i=0; i<response.length; i++){
           let tmp = $('#origin').clone().attr('title',response[i].id).css('display', '').appendTo('#containers');
           tmp.children(".title")[0].text(response[i].makerName);
           tmp.children(".maker")[0].text(response[i].makerName);
           tmp.children(".time")[0].text(response[i].createTime);
        }
    }
    });
}
function toNoticePage(id){
    window.location.href ='/notice/'+id;
}
