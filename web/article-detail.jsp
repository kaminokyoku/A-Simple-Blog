<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Article</title>
    <style>
        body {
            background-color: #f5cabc !important;
        }

        #user-page-nav {
            background-color: #394740 !important;
        }

        .before-login-height {
            height: 64px;
        }

        footer {
            display: table;
            width: 100%;
            height: 50px;
            background-color: #f4f4f4;
        }

        .foot-info {
            display: table-cell;
            vertical-align: middle;
        }
    </style>
</head>
<body>

<!-- set a hidden input so later it can be used as a boolean in js -->
<c:choose>
    <c:when test="${currentUser == null}">
        <input type="hidden" id="have-a-user" value="0">
    </c:when>
    <c:otherwise>
        <input type="hidden" id="have-a-user" value="1">
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${currentUser == null}">
        <nav class="navbar navbar-expand navbar-light bg-light mb-3 before-login-height" id="user-page-nav">
            <a class="navbar-brand text-white" href="./index.jsp">HOME</a>
            <ul class="navbar-nav">
                <li>
                    <a class="nav-link text-white-50" href="./login.jsp">LOG IN</a>

                </li>
                <li>
                    <a class="nav-link text-white-50" href="./signup.jsp">SIGN UP</a>
                </li>
            </ul>
        </nav>
    </c:when>
    <c:otherwise>
        <nav class="navbar navbar-expand-md navbar-light bg-light mb-3" id="user-page-nav">
            <button class="navbar-toggler" data-toggle="collapse" data-target="#c_t">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="c_t">
                <a class="navbar-brand text-white" href="./index.jsp">HOME</a>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img src="${currentUser.avatarName}" width="32px" height="32px">
                        </a>
                        <div class="dropdown-menu dropdown-menu-md-right" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="./user-page.jsp">User page</a>
                            <a class="dropdown-item" href="./settings_profile.jsp">My account</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="./logout">Log out</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
    </c:otherwise>
</c:choose>


<div id="article-detail-panel">
    <div class="container">
        <c:if test="${article.userId == currentUser.userId}">
            <a class="btn btn-dark" href="./editArticle?articleId=${article.articleId}">Edit</a>
        </c:if>
        <h3>${article.title}</h3>
        <p class="text-muted">
            <small>posted: ${article.date}</small>
        </p>


        <hr>
        <div id="article-content">
            <c:choose>
                <c:when test="${article.isShown == true}">
                    ${article.content}
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning" role="alert">
                        This article is hidden by the administrator.
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
        <hr>
    </div>
</div>


<div class="container" id="comments">
    <h4>Comments</h4>
</div>
<c:if test="${currentUser != null}">
    <div class="container" id="comments-panel">

        <input type="hidden" name="articleId" value="${article.articleId}" id="comment-article-id">
        <input type="hidden" name="parentId" id="parentId">
        <input type="hidden" name="userId" value="${currentUser.userId}" id="comment-user-id">
        <input type="hidden" name="userName" value="${currentUser.userName}" id="comment-user-name">
        <input type="hidden" name="userAvatar" value="${currentUser.avatarName}" id="comment-user-avatar">
        <div class="mx-3">
            <textarea class="form-control description" id="newComment" rows="3" name="newComment"
                      placeholder="Say something..."></textarea>
            <div class="my-3">
                <button type="button" class="btn btn-light comment_button" id="comment_button"
                        onclick="replyCommentByParentId(0)">Comment
                </button>
            </div>
        </div>

    </div>
</c:if>

<footer class="text-center text-muted">
    <div class="foot-info">Plants For Fun 2019</div>
</footer>

<script>
    let session = '<%= session.getAttribute("currentUser") %>';
    let currentUser;
    let articleIndex = 0;
    let comments;
    let userId = document.querySelector('#comment-user-id').value;

    window.addEventListener('load', function () {
        loadData();
        currentUser = document.querySelector("#have-a-user").value;
        console.log(userId);
    });

    function loadData() {
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState == XMLHttpRequest.DONE && xhttp.status == 200) {
                //get the comments with a particular article id;
                comments = JSON.parse(xhttp.responseText);
                console.log(comments);
                nestedComments(comments);

            }
        };
        xhttp.open("GET", "./commentsOfArticle?articleId=" +  ${article.articleId}, true);
        xhttp.send();
    }

    function replyCommentByClick(parentID) {
        document.querySelector('#parentId').value = parentID;
        document.querySelector('#newComment').value = 'reply to comment id = ' + parentID;
    }

    function deleteUserComment(commentId) {
        //TODO delete user comment by comment id.

        if (confirm('Are you sure you want to delete this comment? All comments belong to this comment will be deleted too.')) {
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState == XMLHttpRequest.DONE && xhttp.status == 200) {
                    const deleteStatus = JSON.parse(xhttp.responseText);
                    console.log('I have deleted' + deleteStatus);
                    location.reload(true);
                }
            };
            xhttp.open("GET", './deleteComment?commentId=' + commentId, true);
            xhttp.send();
        }

    }

    function nestedComments(comments) {

        const commentsArea = document.querySelector('#comments');
        for (let i = 0; i < comments.length; i++) {
            //if the element is a parent comment
            if (comments[i].parentID === 0) {
                const parentStyle = document.createElement('div');
                parentStyle.className = 'card card-body mb-3 mx-3';

                const mediaParent = document.createElement('div');
                mediaParent.className = 'media';
                mediaParent.id = 'delete-' + comments[i].id;


                const parentImage = document.createElement('img');
                parentImage.src = comments[i].userAvatar;
                parentImage.setAttribute('width', '64px');
                parentImage.setAttribute('height', '64px');
                parentImage.className = 'mr-3';
                mediaParent.appendChild(parentImage);

                const mediaParentBody = document.createElement('div');
                mediaParentBody.className = 'media-body';
                mediaParentBody.id = 'comment-parent-' + comments[i].id;

                const parentUserInfo = document.createElement('div');
                parentUserInfo.innerText = 'user: ' + comments[i].userName + ' commented at: ' + comments[i].date;
                mediaParentBody.appendChild(parentUserInfo);

                const mediaParentBodyContent = document.createElement('div');

                if (comments[i].isShow) {
                    console.log('i id: ' + comments[i].id);
                    mediaParentBodyContent.innerText = comments[i].content;
                } else {
                    mediaParentBodyContent.innerHTML = '<div class="alert alert-warning" role="alert">This comment is hidden by the administrator.</div>';
                }
                mediaParentBody.appendChild(mediaParentBodyContent);

                //we should add a button so users can reply parent comment

                if (currentUser == 1) {
                    const replyComment = document.createElement('button');
                    replyComment.innerHTML = '<i class="fas fa-reply"></i>';
                    replyComment.className = 'btn btn-light';

                    replyComment.id = 'replyComment';


                    replyComment.addEventListener('click', function () {

                        replyCommentByParentId(comments[i].id);
                    });
                    mediaParentBody.appendChild(replyComment);

                    //add delete button
                    const deleteComment = document.createElement('button');
                    deleteComment.innerHTML = '<i class="fas fa-trash-alt"></i>';
                    deleteComment.className = 'btn btn-light ml-3';
                    mediaParentBody.appendChild(deleteComment);
                    console.log("userid =" + comments[i].userId);
                    if (userId != comments[i].userId) {
                       deleteComment.style.display = "none";
                    }
                    deleteComment.addEventListener('click', function(){ deleteUserComment(comments[i].id); });
                    //add delete button
                }


                //if this parent comment have some children.
                for (let j = 0; j < comments.length; j++) {
                    if (comments[j].parentID === comments[i].id) {
                        const mediaChild = document.createElement('div');
                        mediaChild.className = 'media mt-3';
                        mediaChild.id = 'delete-' + comments[j].id;


                        const childImage = document.createElement('img');
                        childImage.src = comments[j].userAvatar;
                        childImage.setAttribute('width', '64px');
                        childImage.setAttribute('height', '64px');
                        childImage.className = 'mr-3';
                        mediaChild.appendChild(childImage);

                        const mediaChildBody = document.createElement('div');
                        mediaChildBody.className = 'media-body';

                        const childUserInfo = document.createElement('div');
                        childUserInfo.innerText = 'user: ' + comments[j].userName + ' commented at: ' + comments[j].date;
                        mediaChildBody.appendChild(childUserInfo);

                        const mediaChildBodyContent = document.createElement('div');
                        if (comments[j].isShow) {
                            console.log('j id:' + comments[j].id);
                            mediaChildBodyContent.innerText = comments[j].content;
                        } else {
                            mediaChildBodyContent.innerHTML = '<div class="alert alert-warning" role="alert">This comment is hidden by the administrator.</div>';
                        }
                        mediaChildBody.appendChild(mediaChildBodyContent);

                        const deleteCommentForChild = document.createElement('button');
                        deleteCommentForChild.innerHTML = '<i class="fas fa-trash-alt"></i>';
                        deleteCommentForChild.className = 'btn btn-light ml-3';
                        mediaChildBody.appendChild(deleteCommentForChild);
                        console.log("userid =" + comments[j].userId);
                        if (userId != comments[j].userId) {
                            deleteCommentForChild.style.display = "none";
                        }
                        deleteCommentForChild.addEventListener('click', function(){ deleteUserComment(comments[j].id); });

                        //add delete button

                        mediaChild.appendChild(mediaChildBody);
                        mediaParentBody.appendChild(mediaChild);
                    }
                }

                mediaParent.appendChild(mediaParentBody);

                parentStyle.appendChild(mediaParent);
                commentsArea.appendChild(parentStyle);

            }

        }
    }

    function replyCommentByParentId(parentId) {
        const replyContent = document.querySelector('#newComment').value;

        if (replyContent !== '') {
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState == XMLHttpRequest.DONE && xhttp.status == 200) {
                    const replySuccess = JSON.parse(xhttp.responseText);
                    console.log(replySuccess);

                    //at first clear comment textarea
                    document.querySelector('#newComment').value = '';
                    //then append comment
                    if (replySuccess.comment.parentID !== 0) {
                        //find it's parent(body);

                        const parent = document.querySelector('#comment-parent-' + replySuccess.comment.parentID);
                        console.log('myParent is');
                        console.log(parent);

                        const mediaChild = document.createElement('div');
                        mediaChild.className = 'media mt-3';
                        mediaChild.id = 'delete-' + replySuccess.comment.id;


                        const childImage = document.createElement('img');
                        childImage.src = replySuccess.comment.userAvatar;
                        childImage.setAttribute('width', '64px');
                        childImage.setAttribute('height', '64px');
                        childImage.className = 'mr-3';
                        mediaChild.appendChild(childImage);

                        const mediaChildBody = document.createElement('div');
                        mediaChildBody.className = 'media-body';

                        const childUserInfo = document.createElement('div');
                        childUserInfo.innerText = 'user: ' + replySuccess.comment.userName + ' said at: ' + replySuccess.comment.date;
                        mediaChildBody.appendChild(childUserInfo);

                        const mediaChildBodyContent = document.createElement('div');
                        mediaChildBodyContent.innerText = replySuccess.comment.content;
                        mediaChildBody.appendChild(mediaChildBodyContent);

                        //add delete button
                        /*  const deleteComment = document.createElement('button');
                          deleteComment.innerHTML = '<i class="fas fa-trash-alt"></i>';
                          deleteComment.className = 'btn btn-light';
                          deleteComment.addEventListener('click', function () {
                              //TODO delete a comment
                          });
                          mediaChildBody.appendChild(deleteComment);*/
                        //add delete button

                        mediaChild.appendChild(mediaChildBody);
                        parent.appendChild(mediaChild);

                    } else {
                        //this is a comment to the article. add comment to the comment area

                        const commentsArea = document.querySelector('#comments');

                        const parentStyle = document.createElement('div');
                        parentStyle.className = 'card card-body mb-3 mx-3';

                        const mediaParent = document.createElement('div');
                        mediaParent.className = 'media';
                        mediaParent.id = 'delete-' + replySuccess.comment.id;


                        const parentImage = document.createElement('img');
                        parentImage.src = replySuccess.comment.userAvatar;
                        parentImage.setAttribute('width', '64px');
                        parentImage.setAttribute('height', '64px');
                        parentImage.className = 'mr-3';
                        mediaParent.appendChild(parentImage);

                        const mediaParentBody = document.createElement('div');
                        mediaParentBody.className = 'media-body';
                        mediaParentBody.id = 'comment-parent-' + replySuccess.comment.id;

                        const parentUserInfo = document.createElement('div');
                        parentUserInfo.innerText = 'user: ' + replySuccess.comment.userName + ' said at: ' + replySuccess.comment.date;
                        mediaParentBody.appendChild(parentUserInfo);

                        const mediaParentBodyContent = document.createElement('div');
                        mediaParentBodyContent.innerText = replySuccess.comment.content;
                        mediaParentBody.appendChild(mediaParentBodyContent);

                        //we should add a button so users can reply parent comment

                        if (currentUser == 1) {
                            const replyComment = document.createElement('button');
                            replyComment.innerHTML = '<i class="fas fa-reply"></i>';
                            replyComment.className = 'btn btn-light';

                            replyComment.id = 'replyComment';

                            replyComment.addEventListener('click', function () {

                                replyCommentByParentId(replySuccess.comment.id);
                            });
                            mediaParentBody.appendChild(replyComment);

                            //add delete button
                            /* const deleteComment = document.createElement('button');
                             deleteComment.innerHTML = '<i class="fas fa-trash-alt"></i>';
                             deleteComment.className = 'btn btn-light ml-3';
                             deleteComment.addEventListener('click', function () {
                                 //TODO delete a comment
                             });
                             mediaParentBody.appendChild(deleteComment);*/
                            //add delete button
                        }


                        mediaParent.appendChild(mediaParentBody);
                        parentStyle.appendChild(mediaParent);
                        commentsArea.appendChild(parentStyle);
                    }
                }
            };


            const articleId = document.querySelector('#comment-article-id').value;

            const userId = document.querySelector('#comment-user-id').value;
            const userName = document.querySelector('#comment-user-name').value;
            const userAvatar = document.querySelector('#comment-user-avatar').value;
            const submitUrl = './newComment?articleId=' + articleId + '&parentId=' + parentId + '&userId=' + userId + '&newComment=' + replyContent + '&userName=' + userName + '&userAvatar=' + userAvatar;
            xhttp.open("GET", submitUrl, true);
            xhttp.send();
        }
        // location.reload(true);
    }


</script>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/69e85da0a5.js"></script>
</body>
</html>