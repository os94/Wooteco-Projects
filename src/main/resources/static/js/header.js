HEADER_APP = (() => {
    const HeaderController = function () {
        const headerService = new HeaderService();

        const search = () => {
            const searchInput = document.querySelector(".search-input input");
            searchInput ? searchInput.addEventListener('keyup', headerService.searchHashTag) : undefined;
        };

        const toggleSearchBar = () => {
            const searchBar = document.querySelector('.search-toggle');
            searchBar ? searchBar.addEventListener('click', headerService.toggleSearchInput) : undefined;
        };

        const applyHashTag = () => {
            window.addEventListener('DOMContentLoaded', headerService.applyHashTag);
        };

        const init = () => {
            search();
            toggleSearchBar();
            applyHashTag();
        };
        return {
            init: init,
        }
    };

    const HeaderService = function () {
        const connector = FETCH_APP.FetchApi();

        const searchHashTag = event => {
            const searchResult = document.querySelector("#search-result");

            const getSearchResult = response => {
                response.json()
                    .then(data => {
                        removeChildElements();
                        data["hashTags"].forEach(hashTag => {
                            const searchResultTemplate =
                                `<li class="search-result-item">
                                    <a href="" class="text-dark">${hashTag.keyword}</a>
                                </li>`;
                            searchResult.insertAdjacentHTML('afterbegin', searchResultTemplate);
                        });

                        toggleSearchList();
                    });

                const removeChildElements = () => {
                    searchResult.innerHTML = "";
                };

                const toggleSearchList = () => {
                    const advancedSearch = document.querySelector(".advanced-search");
                    if (query.length > 0 && searchResult.childElementCount > 0) {
                        advancedSearch.classList.add('active');
                        return;
                    }
                    advancedSearch.classList.remove("active")
                };
            };

            const query = event.target.value
                .replace(new RegExp('#', "gi"), ''); // TODO 사람 검색과 분기 처리!!

            connector.fetchTemplateWithoutBody('/api/hashTag?query=' + query, connector.GET, getSearchResult)
        };

        const toggleSearchInput = event => {
            event.preventDefault();
            document.querySelector('.search-box').classList.toggle('active');
            document.querySelector('.search-input').classList.toggle('active');
            document.querySelector('.search-input input').focus()
        };

        const applyHashTag = () => {
            const contents = Array.from(document.getElementsByClassName('contents'));
            const regex = new RegExp('#([0-9a-zA-Z가-힣_]{2,30})', 'g');

            contents.forEach(content => {
                const contentsHtml = content.innerHTML;

                let tag = contentsHtml.match(regex);
                tag.forEach(tag => {
                    content.innerHTML = content.innerHTML.replace(tag, `<a href="${tag}">${tag}</a>`)
                });
            });
        };

        return {
            searchHashTag: searchHashTag,
            toggleSearchInput: toggleSearchInput,
            applyHashTag: applyHashTag,
        }
    };

    const init = () => {
        const headerController = new HeaderController();
        headerController.init();
    };

    return {
        init: init,
    }
})();

HEADER_APP.init();