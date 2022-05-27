function toEditPage(id) {
	window.location.href = '/editpage/' + id;
}
function deletePage(id){
	$.ajax({
		url:'/notice/'+id,
		method:'DELETE',
		success:(response)=>{
			if(response == 'save'){
				alert('삭제되었습니다.');
				window.location.href='/main';
			}else
				alert('삭제 실패');
		}
	})
}
function editCommentModal(id,comment){
	$('#comment-id').val(id);
	$('#comment-edit').val(comment);
	$('#modal').modal('show');
}
function editComment(){
	$.ajax({
		url:'/comment/'+$('#comment-id').val(),
		method:'PATCH',
		data:{
			'comment':$('#comment-edit').val()
		},
		success:(response)=>{
			if(response == 'save'){
				alert('저장되었습니다.');
				window.location.reload();
			}else
				alert('수정 실패');
		}
	})
}
function deleteComment(id){
	$.ajax({
		url:'/comment/'+id,
		method:'DELETE',
		success:(response)=>{
			if(response == 'save'){
				alert('삭제되었습니다.');
				window.location.reload();
			}else
				alert('삭제 실패');
		}
	})
}
// function deletePage() {
// 	let id = $('#data-id').attr('title');
// 	$.ajax({
// 		method: "DELETE",
// 		url: "/api/deletecomment",
// 		data: {
// 			'pw': $('#input-password').val(),
// 			'id': id
// 		},
// 		success: function(response) {
// 			if (response == 'save') {
// 				alert('저장되었습니다.');
// 				window.location.href = '/';
// 			} else {
// 				alert('비밀번호가 틀렸습니다.');
// 				$('#input-password').val('');
// 			}
// 		},
// 		onerror: function() {
// 			alert('처리 실패');
// 			$('#input-password').val('');
//
// 		}
// 	});
// }
// function isEditPage() {
// 	let id = $('#data-id').attr('title');
// 	$.ajax({
// 		method: "GET",
// 		url: "/api/comment/" + id,
// 		success: function(response) {
// 			if (response.password == $('#input-password').val()) {
// 				toEditPage(id);
// 			} else {
// 				alert('비밀번호가 다릅니다.')
// 				$('#input-password').val('');
// 			}
// 		},
// 	});
// }
// function openModal(isEdit) {
// 	$('#modal').modal('show');
// 	if (isEdit) {
// 		$('#btn-submit').attr('onclick', 'isEditPage()');
// 	} else {
// 		$('#btn-submit').attr('onclick', 'deletePage()');
// 	}
// }