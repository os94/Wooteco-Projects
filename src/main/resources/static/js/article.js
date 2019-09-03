const ARTICLE_APP = (() => {
    'use strict';

    const ArticleController = function() {
        const articleService = new ArticleService();


        const saveArticle = () => {
            const articleSaveButton = document.getElementById('save-button');
            articleSaveButton ? articleSaveButton.addEventListener('click', articleService.save) : undefined;
        };

        const showThumbnail = () => {
            const imageInput = document.getElementById("file");
            imageInput ? imageInput.addEventListener("change", articleService.changeImageJustOnFront) : undefined;
        };

        const init = () => {
            saveArticle();
            showThumbnail();
        };

        return {
            init: init,
        }
    };

    const ArticleService = function() {
        const connector = FETCH_APP.FetchApi();


        const save = () => {
            const file = document.getElementById('file').value;

            if (file === '') {
                alert('파일은 필수입니다');
                return;
            }

            const postForm = document.getElementById('save-form');
            const formData = new FormData(postForm);

            const redirectToArticlePage = response => {
                response.json().then(articleId => window.location.href = `/articles/${articleId}`);
            };
            connector.fetchTemplate('/api/articles', connector.POST, {}, formData, redirectToArticlePage);
        };

        // TODO User꺼랑 합치기!!
        const changeImageJustOnFront = event => {
            const file = event.target.files[0];
            const reader = new FileReader();
            reader.onload = function (readEvent) {
                const articleImage = document.getElementById('article-image');
                articleImage.src = readEvent.target.result;
            };
            reader.readAsDataURL(file);
        };

        const fileLoader = FILE_LOAD_APP.FileLoadService();
        const template = TEMPLATE_APP.TemplateService();
        const headerService = HEADER_APP.HeaderService();

        const cards = document.getElementById('cards');
        const articleCount = document.getElementById('article-count');

        const handleArticleInfo = articleInfo => {
            cards.insertAdjacentHTML('beforeend', template.card(articleInfo));
            COMMENT_APP.commentsCount(articleInfo.articleId);
            fileLoader.loadMediaFile(fileLoader, articleInfo.articleFileName, articleInfo.articleId);
            fileLoader.loadProfileImageFile(fileLoader, articleInfo.userId, "thumb-img-user-");

            const ifSucceed = (response) => {
                response.json().then(data => {
                    let target = document.getElementById(`like-state-${articleInfo.articleId}`);
                    const present = document.getElementById(`count-like-${articleInfo.articleId}`);
                    present.innerText = data.countOfLikes;
                    target.setAttribute('class', `${!data.likeState ? 'fa fa-heart-o' : 'fa fa-heart'} activated-heart font-size-25`);
                })
            };
            connector.fetchTemplateWithoutBody(`/api/articles/${articleInfo.articleId}/like/status`, connector.GET, ifSucceed);
        };

        const handleResponse = data => {
            if (articleCount != null) {
                articleCount.innerText = data.totalElements;
            }
            data.content.forEach(handleArticleInfo);
            headerService.applyHashTag();
        };

        // TODO search-result.js와 중복!!
        const loadArticlesFrom = (url) => {
            return (page) => {
                const addArticle = response => {
                    response.json()
                        .then(handleResponse);
                }
                connector.fetchTemplateWithoutBody(`${url}?page=${page}`, connector.GET, addArticle);
            };
        };

        return {
            save: save,
            changeImageJustOnFront: changeImageJustOnFront,
            loadArticlesFrom: loadArticlesFrom,
        }
    };

    const init = () => {
        const articleController = new ArticleController();
        articleController.init();
    };

    return {
        init: init,
        ArticleService: ArticleService
    }
})();

ARTICLE_APP.init();