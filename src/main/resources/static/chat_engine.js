//??? wtf ???
let chatForm = "#chat_form";

//message config
let sendMessageButton = "#send";
let messageDestination = "/chat/in";
let messageListenerEndpoint = "/chat/out_all";
let messageInputArea = "#message";
let messageOutputArea = "#message_log";

//stomp socket variable
let stompSocketName = "/gs-chat-websocket";
let stompClient = null;

$(document).ready(() => initializer());

function initializer() {
    $(chatForm).on('submit', e => e.preventDefault());
    $(sendMessageButton).click(() => sendJSONMessage());
    connect();
}

function connect() {
    const socket = new SockJS(stompSocketName);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => addMessageListener(frame));
    //auto disconnect when close page
    window.addEventListener('beforeunload', disconnect)
}

function addMessageListener(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe(messageListenerEndpoint, in_message => processIncomingMessage(in_message));
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

//send message
function sendJSONMessage() {
    const out_message = JSON.stringify({
        'message': $(messageInputArea).val()
    });
    $(messageInputArea).val("");
    stompClient.send(messageDestination, {}, out_message);
}

//print our incoming message
function processIncomingMessage(in_message) {
    const message = JSON.parse(in_message.body);
    $(messageOutputArea).append(message["author"] + ":" + message["message"] + "\r\n");
    //colored message not work yet
    // $(messageOutputArea).append(
    //     $("<div contenteditable><span style='color: green;'>" + message["author"] + ":</span>" + message["message"] + "\r\n</div>").html()
    // );
}
