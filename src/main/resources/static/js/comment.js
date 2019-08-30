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

         //todo 데이터를 받아온다음에 이벤트 리스너를 하는게아니라 먼저 리스너를 하기때문에 리스너가 등록이 안됨
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
            let target = event.target;
            if (!target.classList.contains('comment-save-button')) {
                return;
            }

            let targetArticleId = target.getAttribute("data-article-id");
            let commentContents = target.parentNode.parentNode.querySelector("textarea").value;

            const header = {
                'Content-Type': 'application/json; charset=UTF-8',
                'Accept': 'application/json'
            };

            const commentInfo = {
                articleId: targetArticleId,
                contents: commentContents,
            };

            const drawComment = function (comment) {
                let commentTemplate = template.comment(comment.contents, comment.user.nickName, comment.id);

                let commentList = target.parentNode.parentNode.parentNode.querySelector('.list-info');
                commentList.insertAdjacentHTML('afterbegin', commentTemplate);
            };

            const updateDomAfterSaveComment = function(target) {
                target.parentNode.parentNode.querySelector('textarea').value = '';
                let addedCountOfComment = Number(target.parentElement.parentElement.parentElement.querySelector('span').getAttribute('data-count-comment')) + 1;
                target.parentElement.parentElement.parentElement.querySelector('span').setAttribute('data-count-comment', addedCountOfComment);
                target.parentElement.parentElement.parentElement.querySelector('.show-comment').innerText = addedCountOfComment + '개 댓글 더보기';
            };

            connector.fetchTemplate(`/api/comments`,
                connector.POST,
                header,
                JSON.stringify(commentInfo),
                (response) => response.json()
                    .then(res => {
                        drawComment(res);
                        updateDomAfterSaveComment(target);
                    }));
        };

        const show = event => {
            let target = event.target;

            if (!target.classList.contains('show-comment')) {
                return;
            }

            const comment = template.comment('{{contents}}', '{{user.nickName}}', '{{id}}');
            const commentTemplate =
                `{{#comments}}
                    ${comment}
                 {{/comments}}`;

            let commentItemTemplate = Handlebars.compile(commentTemplate);

            let targetArticleId = target.parentElement.getAttribute('data-article-id');
            let countOfBrowserComments = target.parentNode.querySelector('ul').querySelectorAll('li').length;
            let countOfServerComments = target.getAttribute('data-count-comment');

            if (countOfBrowserComments >= countOfServerComments) {
                alert('더이상 댓글이 없습니다.');
                return;
            }

            let page = parseInt((countOfBrowserComments / 5).toString());

            connector.fetchTemplateWithoutBody(`/api/comments/${targetArticleId}?page=${page}`,
                connector.GET,
                (response) => {
                    response.json().then(res => {
                        let data = {comments: res.content};
                        target.parentNode.querySelector('ul').insertAdjacentHTML('beforeend', commentItemTemplate(data));
                        target.innerText = (countOfServerComments - target.parentNode.querySelector('ul').querySelectorAll('li').length) + '개 댓글 더보기'
                    });
                }
            )
        };

        const remove = event => {
            let target = event.target;

            if (!target.classList.contains('comment-delete')) {
                return;
            }

            let commentId = target.getAttribute('data-comment-id');

            const ifSucceed = (target) => {
                alert('댓글이 삭제 되었습니다.');
                let spanTag = target.parentNode.parentNode.parentNode.parentNode.querySelector('span');
                let commentCount = Number(spanTag.getAttribute('data-count-comment'));
                spanTag.setAttribute('data-count-comment', (commentCount - 1));
                target.parentElement.parentElement.remove();
            };

            connector.fetchTemplateWithoutBody('/api/comments/'+commentId,connector.DELETE,ifSucceed(target));
        };

        return {
            save: save,
            show: show,
            remove: remove,
        }

    };

    const init = () => {
        const commentController = new CommentController();
        commentController.init()
    };

    return {
        init: init,
    };

})();


