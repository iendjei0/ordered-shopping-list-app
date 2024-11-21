import './css/b-side.css'
import Header from './fragments/Header.jsx'
import Footer from './fragments/Footer.jsx'
import { useState, useEffect } from 'react'
import { API_PATH } from './api.jsx'
import IngredientInput from './fragments/IngredientInput.jsx'
import SavedIngredients from './fragments/SavedIngredients.jsx'
import IngredientOrder from './fragments/IngredientOrder.jsx'

function Settings() {
  const [savedIngredients, setSavedIngredients] = useState([])
  const [ingredientOrder, setIngredientOrder] = useState([])

  const genericFetch = async (endpoint, method) => {
    return (
      fetch(API_PATH+endpoint, {method:method})
        .then(response => {
          if(!response.ok){
            return response.text().then(text => { 
              throw new Error(text || response.statusText);
            });
          }
          return response.json();
        })
        .then(data => {
          setSavedIngredients(data['savedIngredients'])
          setIngredientOrder(data['ingredientOrder'])
        })
        .catch((error) => console.log(error))
    )
  }

  const getIngredients = async () => {
    await genericFetch("/saved", "GET")
  }

  const addSavedIngredient = async (name) => {
    await genericFetch(`/saved/add/${name}`, "POST")
  }

  const deleteSavedIngredient = async (name) => {
    await genericFetch(`/saved/delete/${name}`, "DELETE")
  }

  const swapIngredientOrder = (name1, name2) => {
    return fetch(API_PATH+"/saved/swap", { 
        method:"PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body:JSON.stringify({ name1: name1, name2: name2 })
    })
    .then(response => {
        if(!response.ok) {
            return response.text().then(text => {
                throw new Error(text || response.statusText) 
            })
        }
        return response.json()
    })
    .then(data => {
      setSavedIngredients(data['savedIngredients'])
      setIngredientOrder(data['ingredientOrder'])
    })
    .catch(error => console.log(error))
}

  useEffect(() => {
    getIngredients()
  }, [])

  return (
    <>
      <Header />
      <main>
        <div class="grid">
          <div class="left-side">
            <section class="instructions">
              <h2>Ingredients & recipes</h2>
              <p>
                Here is a list of all previously used ingredients. <br />
                You can delete them or add new ones.
              </p>
            </section>
            <div class="list-creator">
              <IngredientInput
                addFunction={addSavedIngredient}
              />
              <div class="output">
                <SavedIngredients
                  data={savedIngredients}
                  minusAction={deleteSavedIngredient}
                />
              </div>
            </div>
          </div>
          <div class="right-side">
            <h1>The order</h1>
            <div class="ingredients-order">
              <IngredientOrder
                data={ingredientOrder}
                swap={swapIngredientOrder}
              />
            </div>
          </div>
        </div>
      </main>
      <Footer text="Home" path="/"/>
    </>
  )
}

export default Settings
