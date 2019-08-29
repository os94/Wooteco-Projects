const COMMENT_APP = (() => {

    const CommentController = function() {
        const commentService = new CommentService();

        const saveComment = () => {
            const commentSaveButtons = document.getElementsByClassName('comment-save-button');

            for (let i = 0; i < commentSaveButtons.length; i++) {
                commentSaveButtons.item(i).addEventListener('click', commentService.save);
            }
        };

        const showComment = () => {
            const showCommentButtons = document.getElementsByClassName('show-comment');
            for (let i = 0; i < showCommentButtons.length; i++) {
                showCommentButtons.item(i).addEventListener('click', commentService.show);
            }
        };

    //todo 데이터를 받아온다음에 이벤트 리스너를 하는게아니라 먼저 리스너를 하기때문에 리스너가 등록이 안됨
        const deleteComment = () => {
            const deleteCommentButtons =  document.getElementsByClassName('comment-delete');
            for (let i = 0; i < deleteCommentButtons.length; i++) {
                deleteCommentButtons.item(i).addEventListener('click', commentService.deleteComment);
            }
        };

        const init = () => {
            saveComment();
            showComment();
            deleteComment();
        };

        return {
            init: init,
        };

    };

    const CommentService = function() {
        const save = event => {
            const connectors = FETCH_APP.FetchApi();
            let target = event.target;
            let targetArticleId = target.parentNode.parentNode.getAttribute("data-article-id");
            let commentContents = target.parentNode.parentNode.querySelector("textarea").value;

            const header = {
                'Content-Type': 'application/json; charset=UTF-8',
                'Accept': 'application/json'
            };

            const commentInfo = {
                articleId: targetArticleId,
                contents: commentContents,
            };

            const drawComment = function(comment) {
                let commentTemplate =
                    `<li class="comment-item no-pdd">
                       <div class="info pdd-left-15 pdd-vertical-5">
                           <a href="" class="title no-pdd-vertical inline-block font-size-15">${comment.contents}</a>
                           <span class="font-size-14" text-bold style="float: left; width: 7%;">${comment.user.nickName}</span>
                           <a data-comment-id="{{id}}" class="comment-delete" style="float: right; width: 7%;">
                            <i class="ti-trash pdd-right-10 text-dark comment-delete"></i>
                            </a>
                           <time class="font-size-8 text-gray d-block"></time>
                       </div>
                   </li>`;

               let commentUl = target.parentElement.parentElement.parentElement.querySelector('.list-info');
               commentUl.insertAdjacentHTML('afterbegin', commentTemplate);
               target.parentElement.parentElement.querySelector('textarea').value = '';

               let updatedCountOfComment = Number(target.parentElement.parentElement.parentElement.querySelector('span').getAttribute('data-count-comment')) + 1;
               target.parentElement.parentElement.parentElement.querySelector('span').setAttribute('data-count-comment', updatedCountOfComment);
               target.parentElement.parentElement.parentElement.querySelector('.show-comment').innerText = updatedCountOfComment + '개 댓글 모두 보기' ;
            };

            connectors.fetchTemplate(`/api/comments`,
                connectors.POST,
                header,
                JSON.stringify(commentInfo),
                (response) => response.json()
                    .then(res => drawComment(res)));
        };

        const show = event => {
            const connectors = FETCH_APP.FetchApi();
            const commentTemplate =
                `
               {{#comments}}
               <li class="comment-item no-pdd" data-comment-id="{{id}}">
                   <div class="info pdd-left-15 pdd-vertical-5">
                       <a href="" class="title no-pdd-vertical inline-block font-size-15">{{contents}}</a>
                       <span class="font-size-14 text-bold" style="float: left; width: 7%;">{{user.nickName}}</span>
                       <a data-comment-id="{{id}}" class="comment-delete" style="float: right; width: 7%;">
                        <i class="ti-trash pdd-right-10 comment-delete text-dark"></i>
                        </a>
                       <time class="font-size-8 text-gray d-block">{{writeTime}}</time>
                   </div>
               </li>
               {{/comments}}`;

            let commentItemTemplate = Handlebars.compile(commentTemplate);
            let target = event.target;
            let targetArticleId = target.parentNode.querySelector('.add-comment').getAttribute('data-article-id');
            let commentList = target.parentNode.querySelector('ul');
            let countOfComments = target.getAttribute('data-count-comment');
            let countOfWrittenComments = commentList.querySelectorAll('li').length;

            if (countOfWrittenComments >= countOfComments) {
                alert('더이상 댓글이 없습니다.');
                return;
            }

            let pageCount = countOfWrittenComments / 5;
            let page = parseInt(pageCount);

            connectors.fetchTemplateWithoutBody(`/api/comments/${targetArticleId}?page=${page}`,
                connectors.GET,
                (response) => {
                    response.json().then(res => {

                        let data = {comments: res.content};
                        commentList.insertAdjacentHTML('beforeend', commentItemTemplate(data));
                    });
                }
            )
        };

        const deleteComment = event => {
            const connector = FETCH_APP.FetchApi();
            let commentId = event.target.getAttribute('data-comment-id');
            const ifSucceed = () => {
                event.target.parentElement.parentElement.innerHTML='';
                alert('댓글이 삭제 되었습니다.');
            };

            connector.fetchTemplateWithoutBody('/api/comments/'+commentId,connector.DELETE,ifSucceed());

        };

        return {
            save: save,
            show: show,
            deleteComment:deleteComment,
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


