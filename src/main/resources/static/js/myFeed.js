const MY_FEED_APP = (() => {
    'use strict'

    const myFeedService = function () {
        const connector = FETCH_APP.FetchApi();
        const fileLoader = FILE_LOAD_APP.FileLoadService();
        const template = TEMPLATE_APP.TemplateService();
        const headerService = HEADER_APP.HeaderService();
        const cards = document.getElementById('cards');

        // TODO search-result.js와 중복!!
        const loadArticles = (userNickname) => {
            const addArticle = response => {
                response.json()
                    .then(articleInfos => {
                        articleInfos.forEach(articleInfo => {
                            fileLoader.loadMediaFile(fileLoader, `${articleInfo.articleFileName}`, `${articleInfo.articleId}`);
                            fileLoader.loadProfileImageFile(fileLoader, `${articleInfo.userId}`, "thumb-img-user-");
                            cards.insertAdjacentHTML('beforeend', template.card(articleInfo));
                        });
                        headerService.applyHashTag();
                    });
            };

            connector.fetchTemplateWithoutBody(`/api/articles/users/` + userNickname, connector.GET, addArticle);
        };

        return {
            loadArticles: loadArticles
        }
    }

    return {
        myFeedService: myFeedService
    }


})();
