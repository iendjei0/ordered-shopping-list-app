//HTML calls
function getCurrentIngredients() {
  return (
    fetch("/current", {method:"GET"})
      .then(response => response.text())
  )
}

//Rendering
async function renderCurrentIngredients() {
  let currentIngredientsHTML = await getCurrentIngredients();

  let div = document.querySelector(".output");
  div.innerHTML = currentIngredientsHTML;
}

function fullRender() {
  renderCurrentIngredients();
}

//Listeners


//On start
fullRender()
