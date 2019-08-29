const OBSERVER_APP = (() => {
    'use strict'

    const observeService = function () {
        const loadByObserve = (load) => {
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
                    load(page++);
                });
            });

            io.observe(end);
        };

        return {
            loadByObserve: loadByObserve
        }

    }

    return {
        observeService: observeService
    }


})();