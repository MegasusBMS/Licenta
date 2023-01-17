var listMessages = []

function loadchat(){
    addMessages(messages)
    updateChat()
}

function addMessages(messages){
    messages = JSON.parse(messages)

    var chatDiv = document.getElementById("chat");

    messages.forEach(msg => {
        
        var bubble = document.createElement("div")
        bubble.classList.add("bubbleWrapper")
        
        var divContainer = document.createElement("div");
        var divUserName = document.createElement("div");
        var divMsg = document.createElement("div");
        var span = document.createElement("span");
        var spanUserName = document.createElement("span");

        if(msg.sender.id !== userId){

            divContainer.classList.add("own")
            divMsg.classList.add("ownBubble")
            divMsg.classList.add("own")
            span.classList.add("own");
            spanUserName.classList.add("own-username");
            listMessages.push(msg.id);

        }else{
            span.classList.add("other");
            divMsg.classList.add("otherBubble")
            divMsg.classList.add("other")
            span.classList.add("other");
            spanUserName.classList.add("other-username");

        }
        divContainer.classList.add("inlineContainer")

        spanUserName.textContent=msg.sender.userName
        span.textContent=msg.dateOfCreate
        divMsg.textContent=msg.rawMessage

        divUserName.appendChild(span)
        divUserName.appendChild(divMsg)
        divUserName.appendChild(spanUserName)
        divContainer.appendChild(divUserName)
        bubble.appendChild(divContainer)

        chatDiv.appendChild(bubble);

        chatDiv.scrollTop = chatDiv.scrollHeight-chatDiv.clientHeight;
    }
    );
}

function SendMessage() {
    var message=document.getElementById("message")
    var parameters={
        message: message.value,
        channelName: channelName,
        channelId:  channelId
    };

    fetch('channel', {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(parameters)
        })
    .then(() => {
            var chatDiv = document.getElementById("chat");
            var bubble = document.createElement("div")
            bubble.classList.add("bubbleWrapper")
            
            var divContainer = document.createElement("div");
            var divUserName = document.createElement("div");
            var divMsg = document.createElement("div");
            var span = document.createElement("span");
            var spanUserName = document.createElement("span");

            span.classList.add("other");
            divMsg.classList.add("otherBubble")
            divMsg.classList.add("other")
            span.classList.add("other");
            spanUserName.classList.add("other-username");

            divContainer.classList.add("inlineContainer")

            spanUserName.textContent=userName
            span.textContent="now"
            divMsg.textContent=message.value
            message.value=""

            divUserName.appendChild(span)
            divUserName.appendChild(divMsg)
            divUserName.appendChild(spanUserName)
            divContainer.appendChild(divUserName)
            bubble.appendChild(divContainer)

            chatDiv.appendChild(bubble);
            chatDiv.scrollTop = chatDiv.scrollHeight-chatDiv.clientHeight;
    })
}

function updateChat(){

    var parameters={
        lastMessageId: listMessages[listMessages.length-1],
        userId: userId,
        channelId:  channelId
    };

    fetch('channelUpdate', {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(parameters)
        }).then(response=>response.json())
        .then(data=>{
            data=JSON.stringify(data)
            addMessages(data)
        })


    setTimeout(updateChat,5000)
}