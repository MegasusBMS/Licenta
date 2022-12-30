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
        
        var inputChannelName = document.createElement("input");
        var inputChannelId = document.createElement("input");

        inputChannelName.type="submit"
        inputChannelName.name="channelName"
        inputChannelName.value=element.channelName
        inputChannelName.classList.add("input")
        form.appendChild(inputChannelName);

        inputChannelId.type="hidden"
        inputChannelId.name="id"
        inputChannelId.value=element.id
        form.appendChild(inputChannelId);

        div.appendChild(form);
    });
    
    mainDiv.appendChild(div)
}