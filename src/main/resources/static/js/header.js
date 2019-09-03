const HEADER_APP = (() => {
    const HeaderController = function() {
        const headerService = new HeaderService();

        const search = () => {
            const searchInput = document.querySelector('.search-input input');
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

    const HeaderService = function() {
        const connector = FETCH_APP.FetchApi();
        const template = TEMPLATE_APP.TemplateService();

        const cachingResult = new Map();

        const searchHashTag = event => {
            const searchResult = document.getElementById('search-result');

            const query = event.target.value
                .replace(new RegExp('#', 'gi'), ''); // user 검색과 분기 처리

            const showSuggestions = data => {
                const removeChildElements = () => {
                    searchResult.innerHTML = '';
                };

                const insertResultElements = data => {
                    data['hashTags'].forEach(hashTag => {
                        // const a = searchResultTemplate;
                        searchResult.insertAdjacentHTML('beforeend', template.searchResult(hashTag));
                    });
                };

                const toggleSearchList = () => {
                    const advancedSearch = document.querySelector('.advanced-search');
                    if (query.length > 0 && searchResult.childElementCount > 0) {
                        advancedSearch.classList.add('active');
                        return;
                    }
                    advancedSearch.classList.remove('active');
                };

                removeChildElements();
                insertResultElements(data);
                toggleSearchList();
            };

            const getSearchResult = response => {
                response.json()
                    .then(data => {
                        showSuggestions(data);
                        cachingResult.set(query, data);
                    });
            };

            if (cachingResult.has(query)) {
                showSuggestions(cachingResult.get(query));
                return;
            }
            connector.fetchTemplateWithoutBody(`/api/hashTags?query=${query}`, connector.GET, getSearchResult);
        };

        const toggleSearchInput = event => {
            event.preventDefault();
            document.querySelector('.search-box').classList.toggle('active');
            document.querySelector('.search-input').classList.toggle('active');
            document.querySelector('.search-input input').focus();
        };

        const applyHashTag = () => {
            const contents = Array.from(document.getElementsByClassName('contents'));
            const regex = new RegExp('#([0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣_]+)', 'g');

            contents.forEach(content => {
                content.innerHTML = content.innerHTML.replace(regex, '<a href="/tags/$1">#$1</a>')
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
        HeaderService: HeaderService
    }
})();

HEADER_APP.init();