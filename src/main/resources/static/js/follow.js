const FOLLOW_APP = (() => {

    const FollowController = function () {
        const followService = new FollowService();

        const follow = () => {
            const followButton = document.getElementById('follow');
            followButton ? followButton.addEventListener('click', followService.follow) : undefined;

        };

        const unfollow = () => {
            const followButton = document.getElementById('unfollow');
            followButton ? followButton.addEventListener('click', followService.unfollow) : undefined;
        };

        const followers = () => {
            const followButton = document.getElementById('followers');
            followButton ? followButton.addEventListener('click', followService.followers) : undefined;
        };

        const init = () => {
            follow();
            unfollow();
            followers();

        };

        return {
            init: init
        }
    };

    const FollowService = function () {
        const connector = FETCH_APP.FetchApi();
        const header = {
            'Content-Type': 'application/json; charset=UTF-8',
            'Accept': 'application/json'
        };

        //todo 아직 프론트에서 바꾸는거 안
        // const followRelation = document.getElementById('follow').innerText;
        const formData = {
            fromNickName: document.getElementById('guest').value,
            toNickName: document.getElementById('feedOwner').innerText,
        };

        const follow = event => {
            event.preventDefault();
            const ifSucceed = () => window.location.href = '/user/' + document.getElementById('feedOwner').innerText;

            connector.fetchTemplate('/follow', connector.POST, header, JSON.stringify(formData), ifSucceed)
        };

        const unfollow = event => {
            event.preventDefault();
            const ifSucceed = () => window.location.href = '/user/' + document.getElementById('feedOwner').innerText;

            connector.fetchTemplate('/unfollow', connector.DELETE, header, JSON.stringify(formData), ifSucceed)
        };

        const followers = event => {
            event.preventDefault();
            const ifSucceed = response => {
                response.json().then((res) => {
                    const modalBody = document.getElementById('follower-info');
                    modalBody.innerHTML = "";

                    let followerList = '';
                    for (let i = 0; i < res.length; i++) {
                        followerList = followerList + `<div class="content">  
                                                        <div style="float: left; width: 13%;">
                                                          <img class="img-circle height-40px width-40px" src="/images/default/default_profile.png">
                                                        </div>
                                                        <div>
                                                          <div id="nickName-${i}" class="text-bold" style="font-size: medium">${res[i].nickName}</div>  
                                                          <div id="userName-${i}" style="font-size: small"> ${res[i].userName}</div>
                                                        </div>
                                                   </div>`
                    }
                    modalBody.insertAdjacentHTML('beforeend', followerList);
                })
            };
            document.getElementById('followers');
            const nickName = document.querySelector('#feedOwner').innerHTML;
            connector.fetchTemplateWithoutBody('/followers/' + nickName, connector.GET, ifSucceed);
        };

        return {
            follow: follow,
            unfollow: unfollow,
            followers: followers,
        }
    };

    const init = () => {
        const followController = new FollowController();
        followController.init();
    };

    return {
        init: init
    }
})();

FOLLOW_APP.init();
