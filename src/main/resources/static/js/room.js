let stompClient = null;

const roomId = document.getElementById('roomId').innerText;
const sessionUserId = document.getElementById('sessionUserId').value;

connect();

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    const socket = new SockJS('/dm');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe(`/topic/open/${roomId}`, function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });

        // stompClient.subscribe(`/user/queue/chat`, function (greeting) {
        //     showGreeting(JSON.parse(greeting.body).content);
        // });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    const content = document.getElementById('content');

    const message = {
        from: sessionUserId,
        content: content.value,
        to: "dom"
    };+

    stompClient.send(`/app/dm/${roomId}`, {}, JSON.stringify(message));
    // stompClient.send(`/app/message`, {}, JSON.stringify(message));

    content.value = '';
}

function showGreeting(message) {
    let template;
    if (message.from === sessionUserId) {
        template =
            `<div class="incoming_msg">
              <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
              <div class="received_msg">
                <div class="received_withd_msg">
                  <p>${message.content}</p>
              </div>
            </div>`
    }
    if (message.to === sessionUserId) {
        template =
            `<div class="outgoing_msg">
              <div class="sent_msg">
                <p>${message.content}</p>
            </div>`
    }
    const messageBody = document.getElementById('message-body');

    messageBody.insertAdjacentHTML('beforeend', template);

    const scrollDown = () => {
        const body = document.getElementsByTagName('body')[0];
        window.scroll({
            behavior: 'smooth',
            top: body.offsetHeight,
        })
    };

    scrollDown();
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});