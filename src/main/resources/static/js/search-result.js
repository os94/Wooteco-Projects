const SEARCH_APP = (() => {

    const SearchController = function() {
        const observer = OBSERVER_APP.observeService();
        const articleService = ARTICLE_APP.ArticleService();
        const query = document.getElementById('query').innerText;
        const url = `/api/hashTags/${query}`;

        const init = () => {
            observer.loadByObserve(articleService.loadArticlesFrom(url));
        };

        return {
            init: init
        }
    };

    const init = () => {
        const searchController = new SearchController();
        searchController.init();
    };

    return {
        init: init
    }
})();

SEARCH_APP.init();
