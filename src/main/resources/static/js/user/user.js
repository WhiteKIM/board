let index = {
    init:function () {
        $("#btn-save").on("click",()=>{
            this.save();
        });
        $("#btn-update").on("click",()=>{
            this.update();
        });
    },
    save:function (){
        //alert('user call save');
        let data  = {
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val(),
        }
        //console.log(data);
        //ajax는 기본값이 비동기적으로 호출
        $.ajax({
            type:"POST",
            url: "/auth/joinProc",
            data:JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).done(function (response){
            alert("회원가입이 완료되었습니다");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 통해 3개의 값을 json으로 변경하여 insert
    },

    update:function (){
        //alert('user call save');
        let data  = {
            id: $("#id").val(),
            username: $("#username").val(),
            password:$("#password").val(),
            email:$("#email").val(),
        }
        //console.log(data);
        //ajax는 기본값이 비동기적으로 호출
        $.ajax({
            type:"PUT",
            url: "/user",
            data:JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).done(function (response){
            alert("회원수정이 완료되었습니다");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error)+"수정 실패");
        }); // ajax 통신을 통해 3개의 값을 json으로 변경하여 insert
    },
    /*
    login:function (){
        //alert('user call save');
        let data  = {
            username:$("#username").val(),
            password:$("#password").val()
        }
        //console.log(data);
        //ajax는 기본값이 비동기적으로 호출
        $.ajax({
            type:"POST",
            url: "/api/user/login",
            data:JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).done(function (response){
            alert("로그인이 완료되었습니다");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 통해 3개의 값을 json으로 변경하여 insert
    }
     */
    //	a2864b3e87b5436767d580f69ef420ab kakao restapi key
}

index.init();