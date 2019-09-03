const TEMPLATE_APP = (() => {
    const TemplateService = function() {
        const searchResult = hashTag => {
            const template =
                `<li class="search-result-item">
                    <div class="row align-items-center margin-10">
                        <div class="col-2 text-center">
                            <p class="text-gray font-size-25 mrg-btm-0">#</p>
                        </div>
                        <div class="col-8">
                            <a href="/tags/${hashTag.keyword.substr(1)}" class="text-dark">
                                <div class="row">
                                    ${hashTag.keyword}
                                </div>
                                <div class="row text-gray">
                                    게시물 ${Intl.NumberFormat.call().format(hashTag.count)}
                                </div>
                            </a>
                        </div>
                    </div>
                </li>`;
            return template;
        };

        const comment = (contents, nickName, commentId) => {
            const template =
                `<li class="comment-item no-pdd" data-comment-id="${commentId}" style="border-bottom:none;">
                       <div class="info pdd-left-15 pdd-vertical-5">
                           <span class="font-size-14 text-bold"">${nickName}</span>
                           <span class="no-pdd-vertical inline-block font-size-15" style="margin-left:3%">${contents}</span>
                            <i class="ti-trash pdd-right-10 comment-delete text-dark" style="float: right; width: 6%; cursor:pointer;" data-comment-id="${commentId}"></i>
                        </div>
                   </li>`;
            return template;
        };

        const card = articleInfo => {
            const template =
                `<div id="${articleInfo.articleId}" class="article-card card widget-feed no-pdd mrg-btm-70 shadow-sm">
                                    <div class="feed-header padding-15">
                                        <ul class="list-unstyled list-info">
                                            <li>
                                                <img class="thumb-img img-circle thumb-img-user-${articleInfo.userId}" src="/images/default/default_profile.png"
                                                      alt="">
                                                <div class="info">
                                                    <a href="/users/${articleInfo.nickName}" class="title no-pdd-vertical text-bold inline-block font-size-15">
                                                        ${articleInfo.nickName}</a>
                                                </div>
                                            </li>
                                            <a class="pointer absolute top-10 right-0" data-toggle="dropdown"
                                               aria-expanded="false">
                                        <span class="btn-icon text-dark">
                                            <i class="ti-more font-size-16"></i>
                                        </span>
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <a id="article-edit-${articleInfo.articleId}" data-id="${articleInfo.articleId}" class="pointer article-edit">
                                                        <i class="ti-pencil pdd-right-10 text-dark"></i>
                                                        <span class="">게시글 수정</span>
                                                    </a>
                                                    <a id="article-delete-${articleInfo.articleId}" data-id="${articleInfo.articleId}" class="pointer article-delete">
                                                        <i class="ti-trash pdd-right-10 text-dark"></i>
                                                        <span class="">게시글 삭제</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </ul>
                                    </div>
                                    <div class="feed-body no-pdd">
                                        <img id="article-image-${articleInfo.articleId}" class="img-fluid" src="" alt=""
                                             style="display: none; width: 100%;">
                                        <video id="article-video-${articleInfo.articleId}" controls autoplay loop src=""
                                               style="display: none; width: 100%;">
                                        </video>
                                    </div>
                                    <ul class="feed-action pdd-horizon-15 pdd-top-5">
                                        <li>
                                            <a> 
                                                <input id="like-state-${articleInfo.articleId}" type="hidden" value="${articleInfo.like}">
                                                <i class="fa ${articleInfo.likeState ? 'fa-heart' : 'fa-heart-o'} activated-heart font-size-25" style="display: block"></i>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="/articles/${articleInfo.articleId}">
                                                <i class="ti-comment font-size-22"></i>
                                            </a>
                                        </li>
                                        <li>
                                            <i class="ti-export font-size-22 copy-url" data-article-id="${articleInfo.articleId}" style="cursor:pointer;"
                                            data-container="body" data-toggle="popover" data-placement="top" data-content="Link Copied!"></i>
                                        </li>
                                    </ul>
                                    <div class="feedback-status-container pdd-horizon-15">
                                        <img class="mini-profile-img" src="/images/default/eastjun_profile.jpg">
                                        <p class="no-mrg pdd-left-5 d-inline-block">
                                            <span id="count-like-${articleInfo.articleId}" class="count-like text-bold" data-target="#liker-list" data-toggle="modal">${articleInfo.countOfLikes}</span>명이
                                            좋아합니다.
                                        </p>
                                    </div>
                                    <div id="article-contents-${articleInfo.articleId}" class="feed-contents pdd-left-15">
                                        <p class="contents">${articleInfo.contents}</p>
                                        <form style="display: none;">
                                            <input type="text" value="${articleInfo.contents}">
                                            <button type="button">수정</button>
                                        </form>
                                    </div>
                                    <div class="feed-footer">
                                        <div class="comment" data-article-id="${articleInfo.articleId}">
                                            <span class="show-comment mrg-left-10" data-count-comment="${articleInfo.countOfComments}"><span class="count-of-comments">${articleInfo.countOfComments}</span>개 댓글 더보기</span>
                                            <ul class="list-unstyled list-info pdd-horizon-5"></ul>
                                            <div class="add-comment relative">
                                                <textarea rows="1" class="form-control text-dark padding-15"
                                                  placeholder="댓글 달기..."></textarea>
                                                <div class="absolute top-5 right-0">
                                                    <button class="btn btn-default no-border text-gray comment-save-button" data-article-id="${articleInfo.articleId}">게시</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;
            return template;
        };

        const chatMessage = (message, sessionUserId) => {
            let template;
            if (message.from.id !== sessionUserId) {
                template =
                    `<div class="incoming_msg">
                        <div class="incoming_msg_img">
                            <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil">
                        </div>
                        <div class="received_msg">
                            <div>${message.from.nickName}</div>
                            <div class="received_withd_msg">
                              <p>${message.content}</p>
                            </div>
                        </div>
                    </div>`;
                return template;
            }
            template =
                `<div class="outgoing_msg">
            <div class="sent_msg">
                <p>${message.content}</p>
            </div>
        </div>`;
            return template;
        };

        return {
            searchResult: searchResult,
            card: card,
            chatMessage: chatMessage,
            comment: comment,
        }
    };

    return {
        TemplateService: TemplateService,
    }
})();