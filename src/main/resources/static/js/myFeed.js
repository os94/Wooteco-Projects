const MY_FEED_APP = (() => {
    'use strict';

    const MyFeedController = function () {
        const myFeedService = new MyFeedService();
        const articleService = ARTICLE_APP.ArticleService();
        const observer = OBSERVER_APP.observeService();
        const userNickName = document.getElementById('feedOwner').innerText;
        const url = `/api/articles/users/${userNickName}`;

        const init = () => {
            observer.loadByObserve(articleService.loadArticlesFrom(url));
            myFeedService.loadBigProfileImage();
        };

        return {
            init: init,
        };

    };

    const MyFeedService = function () {
        const fileLoader = FILE_LOAD_APP.FileLoadService();
        const userInfo = document.getElementById('feedOwner');

        const loadBigProfileImage = () => {
            fileLoader.loadProfileImageFile(fileLoader, userInfo.dataset.id, 'big-thumb-img-user-');
        };

        return {
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