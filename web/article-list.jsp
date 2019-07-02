<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        body {
            background-color: #f5cabc !important;
        }
        #user-page-nav {
            background-color: #394740  !important;
        }
        .before-login-height {
            height: 64px;
        }

        footer {
            display: table;
            width: 100%;
            height: 50px;
            background-color: #f4f4f4;
            margin-top: 20px;
        }

        .foot-info {
            display: table-cell;
            vertical-align: middle;
        }

    </style>
    <title>Article Lists</title>
</head>
<body>



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
        <!-- a nav bar with a search bar -->
        <nav class="navbar navbar-expand-md navbar-light bg-light mb-3" id="user-page-nav">
            <button class="navbar-toggler" data-toggle="collapse" data-target="#c_t">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="c_t">
                <a class="navbar-brand text-white" href="./index.jsp">HOME</a>

                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" id="search-keyword">
                    <button class="btn btn-light my-2 my-sm-0" type="button" onclick="pushSearch()">Search</button>
                </form>


                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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

<c:if test="${currentUser != null}">
<%--if logged in, show search options --%>
    <div class="container-fluid mb-3" id="search-option-toolbar">
        <div style="display: flex">
            <div class="f-item">
                <div><small>search by:</small></div>
                <div class="btn-group btn-group-toggle" data-toggle="buttons">

                    <label class="btn btn-light active">
                        <input type="radio" name="options" id="search1" autocomplete="off" checked><i class="far fa-file"></i>
                    </label>
                    <label class="btn btn-light">
                        <input type="radio" name="options" id="search2" autocomplete="off"><i class="far fa-user"></i>
                    </label>
                    <label class="btn btn-light">
                        <input type="radio" name="options" id="search3" autocomplete="off"><i class="far fa-calendar-alt"></i>
                    </label>
                </div>
            </div>
            <div class="f-item">
                <div><small>sort by:</small></div>
                <div class="btn-group btn-group-toggle" data-toggle="buttons">

                    <label class="btn btn-light active">
                        <input type="radio" name="options" id="sort1" autocomplete="off" checked><i class="far fa-file"></i>
                    </label>
                    <label class="btn btn-light">
                        <input type="radio" name="options" id="sort2" autocomplete="off"><i class="far fa-user"></i>
                    </label>
                    <label class="btn btn-light">
                        <input type="radio" name="options" id="sort3" autocomplete="off"><i class="far fa-calendar-alt"></i>
                    </label>
                </div>
            </div>
        </div>
    </div>
</c:if>




<!-- This is the container for display the article list -->
<div class="container" id="article-list">

</div>
<div class="container text-center" id="article-container">
    <!-- Thea add more article button will be added here -->
</div>
<footer class="text-center text-muted">
    <div class="foot-info">Plants For Fun 2019</div>
</footer>

<script>

    //pagination search setting
    let offset = 0;
    let rowCount = 5;

    //If the article content length is longer than 300 characters, show only the first 300 characters
    const characterCount = 300;

    //holds article lists
    let articles;

    //holds search type and sort type
    let searchType = 1;
    let sortType = 1;

    window.addEventListener('load', function () {

        //load an article list
        loadData();
        //add a button called load more
        addLoadMoreButton();
    });
    
    function loadData() {

        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState == XMLHttpRequest.DONE && xhttp.status == 200) {
                articles = JSON.parse(xhttp.responseText);
                console.log(articles);
                displayArticles();
            }
        };

        xhttp.open("Get", './paginationArticleList?offset=' + offset + '&rowCount=' + rowCount, true);
        xhttp.send();
    }
    
    function addLoadMoreButton() {
        const parentDiv = document.querySelector('#article-container');
        const childButton = document.createElement('button');
        childButton.className = 'btn btn-dark';
        childButton.id = 'add-more-article';
        childButton.innerText = 'Add more articles...';
        childButton.addEventListener('click', loadData);
        parentDiv.appendChild(childButton);
    }

    //append articles we have retrieved from the database
    function displayArticles() {
        if (articles.length!=0) {
            //we have some articles to append
            const articleDiv = document.querySelector("#article-list");
            for (let i = 0; i < articles.length; i++) {

                const card = document.createElement('div');
                card.className = 'card mb-4';

                const cardTitle = document.createElement('div');
                cardTitle.className = 'card-header';
                cardTitle.innerHTML = '<h4>' + articles[i].title + '</h4>';
                card.appendChild(cardTitle);

                const cardBody = document.createElement('div');
                cardBody.className = 'card-body';

                const cardPostedTime = document.createElement('h6');
                cardPostedTime.className = 'card-subtitle mb-2 text-muted';
                const strDate = articles[i].date + '';
                cardPostedTime.innerText= 'posted at: ' + strDate.substr(0,strDate.length - 2);
                cardBody.appendChild(cardPostedTime);

                let headText = document.createElement('p');
                headText.innerHTML = articles[i].content;
                let description = headText.innerText.trimLeft();

                if (description.length > characterCount) {
                    description = description.substr(0, characterCount) + '......';
                }

                const cardText = document.createElement('p');
                cardText.className = 'card-text';
                if (articles[i].isShow) {
                    cardText.innerText = description;
                } else {
                cardText.innerHTML = '<div class="alert alert-warning" role="alert">This article is hidden by the administrator.</div>';
                }

                cardBody.appendChild(cardText);

                card.appendChild(cardBody);

                const cardFoot = document.createElement('div');
                cardFoot.className = 'card-footer text-right';
                cardFoot.innerHTML = '<a href="./articleDetail?articleId=' + articles[i].articleId + '">Read the full article...</a>';
                card.appendChild(cardFoot);
                articleDiv.appendChild(card);
            }
            //pagination, move the pointer to the next page
            offset += rowCount;

        } else {
            document.querySelector('#add-more-article').innerText = 'No more articles';
        }
    }
    
    function pushSearch() {


        if (document.querySelector('#search1').checked) {
            searchType = 1;
        } else if (document.querySelector('#search2').checked) {
            searchType = 2;
        } else if (document.querySelector('#search3').checked) {
            searchType = 3;
        }

        if (document.querySelector('#sort1').checked) {
            sortType = 1;
        } else if (document.querySelector('#sort2').checked) {
            sortType = 2;
        } else if (document.querySelector('#sort3').checked) {
            sortType = 3;
        }

        const keyword = document.querySelector('#search-keyword').value;

        console.log('serchtype:'+searchType);
        console.log('sorttype:' +sortType);

        if (keyword !== '') {

            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (xhttp.readyState == XMLHttpRequest.DONE && xhttp.status == 200) {
                    articles = JSON.parse(xhttp.responseText);
                    console.log(articles);

                    //clear the add more button
                    document.querySelector('#article-container').innerHTML = "";
                    if (articles.length != 0) {
                        const articleDiv = document.querySelector("#article-list");
                        articleDiv.innerHTML = "";
                        for (let i = 0; i < articles.length; i++) {
                            const card = document.createElement('div');
                            card.className = 'card mb-4';

                            const cardTitle = document.createElement('div');
                            cardTitle.className = 'card-header';
                            cardTitle.innerHTML = '<h4>' + articles[i].title + '</h4>';
                            card.appendChild(cardTitle);

                            const cardBody = document.createElement('div');
                            cardBody.className = 'card-body';

                            const cardPostedTime = document.createElement('h6');
                            cardPostedTime.className = 'card-subtitle mb-2 text-muted';
                            const strDate = articles[i].date + '';
                            cardPostedTime.innerText= 'posted at: ' + strDate.substr(0,strDate.length - 2);
                            cardBody.appendChild(cardPostedTime);

                            let headText = document.createElement('p');
                            headText.innerHTML = articles[i].content;
                            let description = headText.innerText.trimLeft();

                            if (description.length > characterCount) {
                                description = description.substr(0, characterCount) + '......';
                            }

                            const cardText = document.createElement('p');
                            cardText.className = 'card-text';
                            if (articles[i].isShow) {
                                cardText.innerText = description;
                            } else {
                                cardText.innerHTML = '<div class="alert alert-warning" role="alert">This article is hidden by the administrator.</div>';
                            }

                            cardBody.appendChild(cardText);

                            card.appendChild(cardBody);
                            const cardFoot = document.createElement('div');
                            cardFoot.className = 'card-footer text-right';
                            cardFoot.innerHTML = '<a href="./articleDetail?articleId=' + articles[i].articleId + '">Read the full article...</a>';
                            card.appendChild(cardFoot);
                            articleDiv.appendChild(card);
                        }

                    } else {
                        const articleDiv = document.querySelector("#article-list");
                        articleDiv.innerHTML = "No more matching results."
                    }

                }
            };

            xhttp.open("GET", './searchRequest?searchType=' + searchType + '&keyword=' + keyword + '&sortType=' + sortType, true);
            xhttp.send();
        }
    }


</script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<!-- TODO font awesome cause slow icon loading -->
<script src="https://kit.fontawesome.com/69e85da0a5.js"></script>
</body>
</html>