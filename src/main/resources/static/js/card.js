const CARD_APP = (() => {

    const CardController = function () {
        const cardService = new CardService();

        const deleteCard = () => {
            const cards = document.getElementById('cards');
            cards ? cards.addEventListener('click', cardService.deleteCard) : undefined;
        };

        const changeEditForm = () => {
            const cards = document.getElementById('cards');
            cards ? cards.addEventListener('click', cardService.changeEditForm) : undefined;
        };

        const init = () => {
            deleteCard();
            changeEditForm();
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
                cardContents.children[1].children[1]
                    .addEventListener('click', () => editArticle(cardContents, articleId), false);
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

        return {
            deleteCard: deleteCard,
            changeEditForm: changeEditForm
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