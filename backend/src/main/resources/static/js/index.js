//HTML elements
const outputElement = document.querySelector(".output");
const inputElement = document.querySelector(".input input");
const inputButtonElement = document.querySelector(".input button")
const shoppingListElement = document.querySelector(".shopping-list")
const createButtonElement = document.querySelector(".create-button button")


//HTML calls
function genericFetch(endpoint, method, elementToUpdate) {
  return (
    fetch(endpoint, {method:method})
      .then(response => {
        if(!response.ok){
          return response.text().then(text => { 
            throw new Error(text || response.statusText);
          });
        }
        return response.text();
      })
      .then(html => {
        elementToUpdate.innerHTML = html;
      })
      .catch((error) => console.log(error))
  )
}

function getCurrentIngredients() {
  return genericFetch("/current", "GET", outputElement) 
}

function addCurrentIngredient(name) {
  return genericFetch(`/current/add/${name}`, "POST", outputElement) 
}

function incrementCurrentIngredient(id) {
  return genericFetch(`/current/increment/${id}`, "PUT", outputElement) 
}

function decrementCurrentIngredient(id) {
  return genericFetch(`/current/decrement/${id}`, "PUT", outputElement) 
}

function getShoppingList() {
  return genericFetch("/current/processed", "GET", shoppingListElement)
}

//Listeners
inputElement.addEventListener("keydown", (event) => {
  if(event.key === "Enter") {
    addCurrentIngredient(inputElement.value);
    inputElement.value = "";
  }
})

inputButtonElement.addEventListener("click", () => {
  addCurrentIngredient(inputElement.value);
  inputElement.value = "";
})

createButtonElement.addEventListener("click", () => {
  getShoppingList()
})


//On start
getCurrentIngredients();
