<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
    <c:if test="${board.user.id == principal.user.id}">
        <a href="/board/${board.id}/updateForm" id="btn-update" class="btn btn-warning">수정</a>
        <button id="btn-delete" class="btn btn-danger">삭제</button>
    </c:if>
    <br/>
    <div>
        글 번호 : <span id="id"><i>${board.id}</i></span>
        작성자 : <span id="username"><i>${board.user.username}</i></span>
    </div>
    <br/>
    <div class="form-group">
        <h3 id="title">${board.title}</h3>
    </div>
    <hr/>
    <div class="form-group">
        <div id="content">
            ${board.content}
        </div>
    </div>
    <hr/>
    <div class="card">
        <div class="card-header">
            댓글 리스트
        </div>
        <div>
            <ul id="reply-box" class="list-group">
                <c:forEach var="reply" items="${board.replys}">
                    <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                        <div>${reply.content}</div>
                        <div class="d-flex">
                            <div class="font-italic">작성자 : ${reply.user.username} &nbsp;</div>
                            <button onClick="index.replyDelete(${board.id}, ${reply.id})" class="badge">삭제</button>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <br/>
    <div class="card">
        <form>
            <input type="hidden" id="boardId" value="${board.id}">
            <div class="card-body">
                <textarea id="reply-content" class="form-control" rows="1"></textarea>
            </div>
            <div class="card-footer">
                <button id="btn-reply-save" class="btn btn-primary">등록</button>
            </div>
        </form>
    </div>
</div>
<script src="/js/user/board.js"></script>
<%@include file="../layout/footer.jsp"%>


