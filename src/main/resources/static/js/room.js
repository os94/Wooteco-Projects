const CHAT_APP = (() => {

    let stompClient = null;

    const ChatController = function() {
        const chatService = new ChatService();

        const sendMessage = () => {
            const sendButton = document.getElementById('send');
            sendButton ? sendButton.addEventListener('click', chatService.sendMessage) : undefined;
        };

        const connect = () => {
            chatService.connect();
        };

        const showPrevMessage = () => {
            chatService.showPrevMessages();
        };

        const init = () => {
            connect();
            showPrevMessage();
            sendMessage();
        };
        return {
            init: init,
        }
    };

    const ChatService = function() {
        const connector = FETCH_APP.FetchApi();
        const template = TEMPLATE_APP.TemplateService();

        const content = document.getElementById('content');
        const roomCode = document.getElementById('room-code').value;
        const sessionUserId = parseInt(document.getElementById('session-user-id').value);
        const messageBody = document.getElementById('message-body');

        const showPrevMessages = () => {
            const insertMessages = response => {
                response.json()
                    .then(data => {
                        data.prevMessages.forEach(message => {
                            insertMessage(message, sessionUserId);
                        });
                    });
            };
            connector.fetchTemplateWithoutBody(`/api/chat/messages/${roomCode}`, connector.GET, insertMessages);
        };

        const insertMessage = message => {
            messageBody.insertAdjacentHTML('beforeend', template.chatMessage(message, sessionUserId));
            scrollDown();
        };

        const sendMessage = event => {
            event.preventDefault();
            const message = {
                from: sessionUserId,
                content: content.value,
            };

            stompClient.send(`/app/chat/${roomCode}`, {}, JSON.stringify(message));
            content.value = '';
        };

        const scrollDown = () => {
            const a = document.getElementsByTagName('html')[0];
            a.scroll({
                behavior: 'smooth',
                top: a.offsetHeight * a.offsetHeight,
            })
        };

        const connect = () => {
            const socket = new SockJS('/dm');
            stompClient = Stomp.over(socket);
            stompClient.debug = null;
            stompClient.connect({}, () => {
                stompClient.subscribe(`/topic/open/${roomCode}`, message => {
                    insertMessage(JSON.parse(message.body));
                });
            });
        };

        return {
            connect: connect,
            showPrevMessages: showPrevMessages,
            sendMessage: sendMessage,
        }

    };

    const init = () => {
        const chatController = new ChatController();
        chatController.init();
    };

    return {
        init: init,
    }
})();

CHAT_APP.init();