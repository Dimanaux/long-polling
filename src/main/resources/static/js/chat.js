function sendMessage(inputId) {
    let data = {text: textFrom(inputId)};

    fetch('/messages', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(() => {
        console.log('sent!');
    });
}

function textFrom(elementId) {
    return document.getElementById(elementId).value;
}

function fetchNewMessages() {
    fetch('/messages/notification', {
        credentials: 'include'
    }).then(response => response.json())
        .then(prependMessage)
        .then(() => fetchNewMessages());
}

function fetchHistory() {
    fetch('/messages', {
        credentials: 'include'
    }).then(response => response.json())
        .then(messages => messages.forEach(prependMessage));
}

function prependMessage(message) {
    let listId = 'messages-list';
    let list = document.getElementById(listId);

    let messageView = document.createElement('li');

    messageView.innerText = `${message.author.username} said: ${message.text}`;

    list.prepend(messageView);
}
