// HTML elements
const htmlElements = {
    "saved-ingredients":document.querySelector(".output"),
    "ingredient-order":document.querySelector(".ingredients-order"),
    input:document.querySelector(".input input"),
    inputButton:document.querySelector(".input button")
}

// HTML calls
function genericHTMLJsonFetch(endpoint, method, elementsToUpdate) {
    fetch(endpoint, { method:method })
    .then(response => {
        if(!response.ok) {
            return response.text().then(text => {
                throw new Error(text || response.statusText) 
            })
        }
        return response.json()
    })
    .then(json => {
        for(key in json) {
            elementsToUpdate[key].innerHTML = json[key]
        }
    })
    .catch(error => console.log(error))
}

function getHtmlElements() {
    return genericHTMLJsonFetch("/saved", "GET", htmlElements)
}

function addSavedIngredient(name) {
    return genericHTMLJsonFetch(`/saved/add/${name}`, "POST", htmlElements)
}

function deleteSavedIngredient(name) {
    return genericHTMLJsonFetch(`/saved/delete/${name}`, "DELETE", htmlElements)
}

function swapIngredientsOrder(name1, name2) {
    fetch("/saved/swap", { 
        method:"PUT",
        body:{name1:name1, name2:name2} 
    })
    .then(response => {
        if(!response.ok) {
            return response.text().then(text => {
                throw new Error(text || response.statusText) 
            })
        }
        return response.text()
    })
    .then(text => {
        elementToUpdate.innerHTML = text;
    })
    .catch(error => console.log(error))
}

// Listeners
htmlElements.input.addEventListener("keydown", (event) => {
    if(event.key === "Enter") {
        addSavedIngredient(htmlElements.input.value)
        htmlElements.input.value = ""
    }
})

htmlElements.inputButton.addEventListener("click", () => {
    addSavedIngredient(htmlElements.input.value)
    htmlElements.input.value = ""
})

// On start
getHtmlElements()