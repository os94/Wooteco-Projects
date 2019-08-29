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
        //if span 이걸로 구분해야함
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

            connectors.fetchTemplate(`/api/comments`,
                connectors.POST,
                header,
                JSON.stringify(commentInfo),
                () => alert('댓글 작성 성공'));
        };

        const show = event => {
            let countOfComments = event.target.getAttribute('data-count-comment');
            let writtenComments = event.target.nextElementSibling.querySelectorAll('li').length.toString();
            console.log('comments',countOfComments)
            console.log('written',writtenComments)
            if(countOfComments === writtenComments) {
                alert('모든 댓글을 가져왔습니다.')
                return;
            }
            const connectors = FETCH_APP.FetchApi();
            const commentTemplate =
                `
               {{#comments}}
               <li class="comment-item no-pdd">
                   <div class="info pdd-left-15 pdd-vertical-5">
                       <a href="" class="title no-pdd-vertical text-bold inline-block font-size-15">{{user.nickName}}</a>
                       <span class="font-size-14" style="float: left; width: 7%;">{{contents}}</span>
                       <a data-comment-id="{{id}}" class="comment-delete" style="float: right; width: 7%;">
                        <i class="ti-trash pdd-right-10 text-dark"></i>
                        </a>
                       <time class="font-size-8 text-gray d-block">{{writeTime}}</time>
                   </div>
               </li>
               {{/comments}}`;

            let commentItemTemplate = Handlebars.compile(commentTemplate);
            let target = event.target;
            let targetArticleId = target.parentNode.querySelector('.add-comment').getAttribute('data-article-id');
            let commentList = target.parentNode.querySelector('ul');

            connectors.fetchTemplateWithoutBody(`/api/comments/${targetArticleId}`,
                connectors.GET,
                (response) => {
                    response.json().then(res => {
                        let data = {comments: res};
                        commentList.insertAdjacentHTML('afterbegin', commentItemTemplate(data));
                    });
                }
            )
        };

        const deleteComment = event => {
            const connector = FETCH_APP.FetchApi();
            let commentId = event.target.getAttribute('data-comment-id');
            console.log("*********",commentId)
            const ifSucceed = () => {
                event.target.parentElement.parentElement.innerHTML='';
                alert('댓글이 삭제 되었습니다.');
            }
            console.log('before')
            connector.fetchTemplateWithoutBody('/api/comments/'+commentId,connector.DELETE,ifSucceed());
            console.log('after')
        }

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


