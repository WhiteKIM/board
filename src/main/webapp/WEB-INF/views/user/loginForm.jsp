<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="username">유저이름:</label>
            <input type="username" class="form-control" name="username" placeholder="Enter username" id="username">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" name="password" placeholder="Enter password" id="password">
        </div>
        <!--
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input" name="remember" type="checkbox"> Remember me
            </label>
        </div>
        -->
        <button id="btn-login" class="btn btn-primary">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=a2864b3e87b5436767d580f69ef420ab&redirect_uri=http://localhost:8090/auth/kakao/callback&response_type=code"><img height="38" src="/image/kakao_login_medium.png"></a>
    </form>
</div>
<script src="/js/user/user.js"></script>
<%@include file="../layout/footer.jsp"%>


