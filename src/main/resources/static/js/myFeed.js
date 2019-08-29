const MY_FEED_APP = (() => {
    'use strict';

    const MyFeedController = function () {
        const myFeedService = new MyFeedService();
        const observer = OBSERVER_APP.observeService();

        const init = () => {
            observer.loadByObserve(myFeedService.loadArticles);
            myFeedService.loadBigProfileImage();
        };

        return {
            init: init,
        };

    };

    const MyFeedService = function () {
        const connector = FETCH_APP.FetchApi();
        const fileLoader = FILE_LOAD_APP.FileLoadService();
        const template = TEMPLATE_APP.TemplateService();
        const headerService = HEADER_APP.HeaderService();

        const cards = document.getElementById('cards');
        const articleCount = document.getElementById('article-count');
        const userInfo = document.getElementById('feedOwner');

        const loadBigProfileImage = () => {
            fileLoader.loadProfileImageFile(fileLoader, userInfo.dataset.id, 'big-thumb-img-user-');
        };

        // TODO search-result.js와 중복!!
        const loadArticles = page => {
            const userNickName = userInfo.innerText;
            const addArticle = response => {
                response.json()
                    .then(data => {
                        articleCount.innerText = data.totalElements;

                        data.content.forEach(articleInfo => {
                            fileLoader.loadMediaFile(fileLoader, `${articleInfo.articleFileName}`, `${articleInfo.articleId}`);
                            fileLoader.loadProfileImageFile(fileLoader, `${articleInfo.userId}`, "thumb-img-user-");
                            cards.insertAdjacentHTML('beforeend', template.card(articleInfo));
                        });
                        headerService.applyHashTag();
                    });
            };

            connector.fetchTemplateWithoutBody(`/api/articles/users/${userNickName}?page=${page}`, connector.GET, addArticle);
        };

        return {
            loadArticles: loadArticles,
            loadBigProfileImage: loadBigProfileImage
        };
    };

    const init = () => {
        const myFeedController = new MyFeedController();
        myFeedController.init();
    };

    return {
        init: init,
    }

})();

MY_FEED_APP.init();