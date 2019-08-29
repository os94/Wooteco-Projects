const ARTICLE_APP = (() => {
    'use strict';

    const ArticleController = function () {
        const articleService = new ArticleService();
        const loadArticles = articleService.loadArticles;
        //todo 팔로우한 사람 글만 보이게 하는 jpa paging 쿼리문을 몰라서 일단 무한 스크롤은 적용 안 함
        //const observer = OBSERVER_APP.observeService();

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
            loadArticles();
        };

        return {
            init: init,
        }
    };

    const ArticleService = function () {
        const connector = FETCH_APP.FetchApi();
        const fileLoader = FILE_LOAD_APP.FileLoadService();
        const template = TEMPLATE_APP.TemplateService();
        const headerService = HEADER_APP.HeaderService();
        const cards = document.getElementById('cards');

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

        // TODO 슬로스의 유작... 여기로 옮겨지다. (사용되지 않고 있음)
        const copyUrl = articleId => {
            const copiedUrl = window.location.host + `/articles/${articleId}`;
            const copyTarget = document.createElement('textarea');

            document.body.appendChild(copyTarget);
            copyTarget.value = copiedUrl;
            copyTarget.select();
            document.execCommand('copy');
            document.body.removeChild(copyTarget);

            alert(`링크가 복사되었습니다. ${copiedUrl}`);
        };

        // TODO search-result.js와 중복!!
        const loadArticles = () => {
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

            connector.fetchTemplateWithoutBody(`/api/articles`, connector.GET, addArticle);
        };

        return {
            save: save,
            changeImageJustOnFront: changeImageJustOnFront,
            loadArticles: loadArticles
        }
    };

    const init = () => {
        const articleController = new ArticleController();
        articleController.init();
    };

    return {
        init: init
    }
})();

ARTICLE_APP.init();