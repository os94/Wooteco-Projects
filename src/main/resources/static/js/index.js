const INDEX_APP = (() => {
    'use strict';

    const IndexController = function () {
        const articleService = ARTICLE_APP.ArticleService();
        const observer = OBSERVER_APP.observeService();
        const url = "/api/articles";

        const init = () => {
            observer.loadByObserve(articleService.loadArticlesFrom(url));
        };

        return {
            init: init,
        };

    };

    const init = () => {
        const indexController = new IndexController();
        indexController.init();
    };

    return {
        init: init,
    }

})();

INDEX_APP.init();