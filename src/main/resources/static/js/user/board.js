let index = {
    init:function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
        $("#btn-delete").on("click", () => {
            this.deleteById();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
        $("#btn-reply-save").on("click", () => {
            this.replySave();
        });
    }
    ,
    save:function (){
        //alert('user call save');
        let data  = {
            title:$("#title").val(),
            content:$("#content").val()
        };
        //console.log(data);
        //ajax는 기본값이 비동기적으로 호출
        $.ajax({
            type:"POST",
            url: "/api/board",
            data:JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).done(function (response){
            alert("글 작성이 완료되었습니다");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 통해 3개의 값을 json으로 변경하여 insert
    },

    deleteById:function (){
        let id = $("#id").text();
        $.ajax({
            type:"DELETE",
            url: "/api/board/"+id,
            dataType:"json"
        }).done(function (response){
            alert("글 삭제 완료");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 통해 3개의 값을 json으로 변경하여 insert
    },

    update:function (){
        let id = $("#id").val();
        //alert('user call save');
        let data = {
            title:$("#title").val(),
            content:$("#content").val()
        };
        //console.log(data);
        //ajax는 기본값이 비동기적으로 호출
        $.ajax({
            type:"PUT",
            url: "/api/board/"+id,
            data:JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).done(function (response){
            alert("글 수정이 완료되었습니다");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 통해 3개의 값을 json으로 변경하여 insert
    },

    replySave:function (){
        //alert('user call save');
        let data  = {
            content:$("#reply-content").val()
        };
        let boardId = $("#boardId").val();
        //console.log(data);
        //ajax는 기본값이 비동기적으로 호출
        $.ajax({
            type:"POST",
            url: `/api/board/${boardId}/reply`,
            data:JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).done(function (response){
            alert("댓글 작성이 완료되었습니다");
            location.href = `/board/${boardId}`;
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 통해 3개의 값을 json으로 변경하여 insert
    },

    replyDelete:function (boardId, replyId){
        $.ajax({
            type:"DELETE",
            url: `/api/board/${boardId}/reply/${replyId}`,
            dataType:"json"
        }).done(function (response){
            alert("댓글 삭제이 완료되었습니다");
            location.href = `/board/${boardId}`;
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 통해 3개의 값을 json으로 변경하여 insert
    },
}

index.init();