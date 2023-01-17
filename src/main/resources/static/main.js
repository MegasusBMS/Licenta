function load(channels){
    var mainDiv = document.getElementById("mainDiv")
    channels = JSON.parse(channels)

    if(!channels.length > 0)
        return

    var title = document.createElement("h4")
    title.textContent="Channels:"
    mainDiv.appendChild(title)

    var div = document.createElement("div")

    div.classList.add("btn-group")

    channels.forEach(element => {
        var form = document.createElement("form");
        form.method="POST"
        
        var inputChannelId = document.createElement("input");
        inputChannelId.type="hidden"
        inputChannelId.name="id"
        inputChannelId.value=element.id
        form.appendChild(inputChannelId);

        var inputChannelName = document.createElement("input");
        inputChannelName.type="hidden"
        inputChannelName.name="channelName"
        inputChannelName.value=element.channelName
        form.appendChild(inputChannelName);

        var inputChannelName = document.createElement("input");
        inputChannelName.type="submit"
        inputChannelName.value=element.channelName
        inputChannelName.classList.add("input")
        form.appendChild(inputChannelName);


        div.appendChild(form);
    });
    
    mainDiv.appendChild(div)
}