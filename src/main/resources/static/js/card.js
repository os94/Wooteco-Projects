const CARD_APP = (() => {

    const CardController = function() {
        const cardService = new CardService();

        const cards = document.getElementById('cards');

        const deleteCard = () => {
            cards ? cards.addEventListener('click', cardService.deleteCard) : undefined;
        };

        const changeEditForm = () => {
            cards ? cards.addEventListener('click', cardService.changeEditForm) : undefined;
        };

        const clickHeart = () => {
            cards ? cards.addEventListener('click', cardService.clickHeart) : undefined;
        }

        const likerList = () => {
            cards ? cards.addEventListener('click', cardService.likerList) : undefined;
        }

        const init = () => {
            deleteCard();
            changeEditForm();
            clickHeart();
            likerList();
        };

        return {
            init: init,
        }
    };

    const CardService = function () {
        const changeEditForm = event => {
            let target = event.target;
            if (target.tagName === 'I' || target.tagName === 'SPAN') {
                target = target.parentNode;
            }
            const articleId = target.getAttribute('data-id');

            if (target.classList.contains('article-edit')) {
                const cardContents = document.getElementById(`article-contents-${articleId}`);
                cardContents.children[0].style.display = 'none';
                cardContents.children[1].style.display = 'block';
                cardContents.children[1].children[1].addEventListener('click', () => editArticle(cardContents, articleId), false);
            }

            // TODO private method 를 어떻게 처리
            const editArticle = (cardContents, articleId) => {
                const header = {
                    'Content-Type': 'application/json; charset=UTF-8',
                    'Accept': 'application/json'
                };
                const articleInfo = {
                    contents: cardContents.children[1].children[0].value
                };
                const connector = FETCH_APP.FetchApi();

                connector.fetchTemplate(`/api/articles/${articleId}`,
                    connector.PUT,
                    header,
                    JSON.stringify(articleInfo),
                    () => window.location.reload());
            };

        };

        const deleteCard = event => {
            let target = event.target;
            if (target.tagName === 'I' || target.tagName === 'SPAN') {
                target = target.parentNode;
            }
            const articleId = target.getAttribute('data-id');

            if (target.classList.contains('article-delete')) {
                if (confirm("삭제하시겠습니까?")) {
                    const connector = FETCH_APP.FetchApi();
                    connector.fetchTemplateWithoutBody(`/api/articles/${articleId}`,
                        connector.DELETE,
                        () => window.location.href = '/');
                }
            }
        };

        const clickHeart = event => {
            const header = {
                'Content-Type': 'application/json; charset=UTF-8',
                'Accept': 'application/json'
            };
            const target = event.target;
            if (target.tagName === 'I' && target.classList.contains('fa') ) {
                const articleId = target.closest('.article-card').getAttribute('id');
                const connector = FETCH_APP.FetchApi();
                let likeState = false;

                if(target.classList.contains('fa-heart-o')) {
                    likeState = true;
                }

                const ifSucceed = (response) => {
                    response.json().then(data => {
                        const present = document.getElementById(`count-like-${articleId}`);
                        present.innerText = data;
                        target.setAttribute('class', `${!likeState ? 'fa fa-heart-o' : 'fa fa-heart'} activated-heart font-size-25`)
                    })
                };

                connector.fetchTemplate(`/api/articles/${articleId}/like`, connector.POST, header, {}, ifSucceed);

            }
        };

        const likerList = event => {
            let target = event.target;
            if(target.tagName === 'SPAN' && target.classList.contains('count-like')) {
                const articleId = target.closest('.article-card').getAttribute('id');
                const connector = FETCH_APP.FetchApi();

                const ifSucceed = response => {
                    response.json().then((res) => {
                        printModal(res);
                        document.getElementById('likes').innerText = '좋아요';
                    })
                };
                connector.fetchTemplateWithoutBody(`/api/articles/${articleId}/liker`, connector.GET, ifSucceed);
            }
        };

        const printModal = res => {
            const modalBody = document.getElementById('liker-info');
            modalBody.innerHTML = "";

            let likerList = '';
            for (let i = 0; i < res.length; i++) {
                likerList = likerList + `<div class="content">  
                                                        <div style="float: left; width: 13%;">
                                                          <img class="img-circle height-40px width-40px" src="/images/default/default_profile.png">
                                                        </div>
                                                        <div>
                                                          <div id="nickName-${i}" class="text-bold" style="font-size: medium">${res[i].nickName}</div>  
                                                          <div id="userName-${i}" style="font-size: small"> ${res[i].userName}</div>
                                                        </div>
                                                   </div>`
            }
            modalBody.insertAdjacentHTML('beforeend', likerList);
        };

        return {
            deleteCard: deleteCard,
            changeEditForm: changeEditForm,
            clickHeart: clickHeart,
            likerList: likerList,
        }
    };

    const init = () => {
        const cardController = new CardController();
        cardController.init();
    };

    return {
        init: init,
    }
})();

CARD_APP.init();