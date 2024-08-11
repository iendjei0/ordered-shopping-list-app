//HTML elements
const outputElement = document.querySelector(".output");
const inputElement = document.querySelector(".input input");
const inputButtonElement = document.querySelector(".input button")


//HTML calls
function getCurrentIngredients() {
  return (
    fetch("/current", {method:"GET"})
      .then(response => {
        if(!response.ok){
          return response.text().then(text => { 
            throw new Error(text || response.statusText);
          });
        }
        return response.text();
      })
      .then(html => {
        outputElement.innerHTML = html;
      })
      .catch((error) => console.log(error))
  )
}

function addCurrentIngredient(name) {
  return (
    fetch(`/current/add/${name}`, {method:"POST"})
      .then(response => {
        if(!response.ok){
          return response.text().then(text => { 
            throw new Error(text || response.statusText);
          });
        }
        return response.text();
      })
      .then(html => {
        outputElement.innerHTML = html;
      })
      .catch((error) => console.log(error))
  )
}

function incrementCurrentIngredient(name) {
  return (
    fetch(`/current/increment/${name}`, {method:"PUT"})
      .then(response => {
        if(!response.ok){
          return response.text().then(text => { 
            throw new Error(text || response.statusText);
          });
        }
        return response.text();
      })
      .then(html => {
        outputElement.innerHTML = html;
      })
      .catch((error) => console.log(error))
  )
}

function decrementCurrentIngredient(name) {
  return (
    fetch(`/current/decrement/${name}`, {method:"PUT"})
      .then(response => {
        if(!response.ok){
          return response.text().then(text => { 
            throw new Error(text || response.statusText);
          });
        }
        return response.text();
      })
      .then(html => {
        outputElement.innerHTML = html;
      })
      .catch((error) => console.log(error))
  )
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

//On start
getCurrentIngredients();
