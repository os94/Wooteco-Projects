const OBSERVER_APP = (() => {
    'use strict';

    const observeService = function() {
        const loadByObserve = (callBack, url) => {
            const end = document.getElementById('end');
            if (!end) {
                return;
            }
            let page = 0;
            const io = new IntersectionObserver(entries => {
                entries.forEach(entry => {
                    if (!entry.isIntersecting) {
                        return;
                    }
                    callBack(page++, url);
                });
            });

            io.observe(end);
        };

        return {
            loadByObserve: loadByObserve
        }

    };

    return {
        observeService: observeService
    }


})();