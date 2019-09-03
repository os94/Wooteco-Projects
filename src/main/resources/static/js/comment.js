const COMMENT_APP = (() => {

    const connector = FETCH_APP.FetchApi();
    const template = TEMPLATE_APP.TemplateService();

    const CommentController = function() {
        const commentService = new CommentService();

        const cards = document.getElementById('cards');

        const saveComment = () => {
            cards ? cards.addEventListener('click', commentService.save) : undefined;
        };

        const showComment = () => {
            cards ? cards.addEventListener('click', commentService.show) : undefined;
        };

        const removeComment = () => {
            cards ? cards.addEventListener('click', commentService.remove) : undefined;
        };

        const init = () => {
            saveComment();
            showComment();
            removeComment();
        };

        return {
            init: init,
        };

    };

    const CommentService = function() {
        const save = event => {
            const target = event.target;
            if (!target.classList.contains('comment-save-button')) {
                return;
            }

            const targetArticleId = target.getAttribute("data-article-id");
            const commentContents = target.parentNode.parentNode.querySelector("textarea").value;

            const header = {
                'Content-Type': 'application/json; charset=UTF-8',
                'Accept': 'application/json'
            };

            const commentInfo = {
                articleId: targetArticleId,
                contents: commentContents,
            };

            const drawComment = (comment) => {
                const commentTemplate = template.comment(comment.contents, comment.user.nickName, comment.id);

                const commentList = target.parentNode.parentNode.parentNode.querySelector('.list-info');
                commentList.insertAdjacentHTML('afterbegin', commentTemplate);
            };

            const updateDomAfterSaveComment = (target) => {
                target.parentNode.parentNode.querySelector('textarea').value = '';
                const addedCountOfComment = Number(target.parentElement.parentElement.parentElement.querySelector('span').getAttribute('data-count-comment')) + 1;
                target.parentElement.parentElement.parentElement.querySelector('span').setAttribute('data-count-comment', addedCountOfComment)
                target.parentElement.parentElement.parentElement.querySelector('span').setAttribute('data-count-comment', addedCountOfComment);
            };

            connector.fetchTemplate(`/api/comments`,
                connector.POST,
                header,
                JSON.stringify(commentInfo),
                (response) => response.json()
                    .then(res => {
                        drawComment(res);
                        updateDomAfterSaveComment(target, res);
                    }));
        };

        const show = event => {
            const target = event.target;

            if (!target.classList.contains('show-comment')) {
                return;
            }

            const comment = template.comment('{{contents}}', '{{user.nickName}}', '{{id}}');
            const commentTemplate =
                `{{#comments}}
                    ${comment}
                 {{/comments}}`;

            const commentItemTemplate = Handlebars.compile(commentTemplate);

            const targetArticleId = target.parentElement.getAttribute('data-article-id');
            const countOfBrowserComments = target.parentNode.querySelector('ul').querySelectorAll('li').length;
            const countOfServerComments = target.getAttribute('data-count-comment');

            if (countOfBrowserComments >= countOfServerComments) {
                alert('더이상 댓글이 없습니다.');
                return;
            }

            const page = parseInt((countOfBrowserComments / 5).toString());

            connector.fetchTemplateWithoutBody(`/api/comments/${targetArticleId}?page=${page}`,
                connector.GET,
                (response) => {
                    response.json().then(res => {
                        const data = {comments: res.content};
                        target.parentNode.querySelector('ul').insertAdjacentHTML('beforeend', commentItemTemplate(data));
                        target.innerText = (countOfServerComments - target.parentNode.querySelector('ul').querySelectorAll('li').length) + '개 댓글 더보기'
                    });
                }
            )
        };

        const remove = event => {
            const target = event.target;

            if (!target.classList.contains('comment-delete')) {
                return;
            }

            const commentId = target.getAttribute('data-comment-id');

            const ifSucceed = (target) => {
                alert('댓글이 삭제 되었습니다.');
                const spanTag = target.parentNode.parentNode.parentNode.parentNode.querySelector('span');
                const commentCount = Number(spanTag.getAttribute('data-count-comment'));
                spanTag.setAttribute('data-count-comment', (commentCount - 1));
                target.parentElement.parentElement.remove();
            };

            connector.fetchTemplateWithoutBody(`/api/comments/${commentId}`,connector.DELETE,ifSucceed(target));
        };

        return {
            save: save,
            show: show,
            remove: remove,
        }

    };

    const commentsCount = function (articleId) {
        connector.fetchTemplateWithoutBody(`/api/comments/${articleId}/counts`,
            connector.GET,
            (response) => {
                response.json().then(res => {
                    document.getElementById(`article-footer-${articleId}`).querySelector('.show-comment').setAttribute('data-count-comment', res);
                    document.getElementById(`article-footer-${articleId}`).querySelector('.count-of-comments').innerHTML = res;
                });
            }
        )
    };

    const init = () => {
        const commentController = new CommentController();
        commentController.init();
        const commentsCount = new commentsCount();
    };

    return {
        init: init,
        commentsCount: commentsCount,
    };

})();

COMMENT_APP.init();