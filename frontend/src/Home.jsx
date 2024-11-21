import './css/index.css'
import Header from './fragments/Header.jsx'
import Footer from './fragments/Footer.jsx'
import { useEffect, useState } from 'react'
import CurrentIngredients from './fragments/CurrentIngredients.jsx'
import { API_PATH } from './api.jsx'
import ShoppingList from './fragments/ShoppingList.jsx'
import IngredientInput from './fragments/IngredientInput.jsx'

function Home() {
  const [currentIngredients, setCurrentIngredients] = useState([])
  const [shoppingList, setShoppingList] = useState([])

  const genericFetch = (endpoint, method) => {
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
        .catch((error) => console.log(error))
    )
  }

  const getCurrentIngredients = async () => {
    const data = await genericFetch("/current", "GET") 
    setCurrentIngredients(data)
  }
  
  const addCurrentIngredient = async (name) => {
    const data = await genericFetch(`/current/add/${name}`, "POST")
    setCurrentIngredients(data)
  }

  const incrementCurrentIngredient = async (id) => {
    const data = await genericFetch(`/current/increment/${id}`, "PUT")
    setCurrentIngredients(data)
  }

  const decrementCurrentIngredient = async (id) => {
    const data = await genericFetch(`/current/decrement/${id}`, "PUT")
    setCurrentIngredients(data)
  }
  
  const getShoppingList = async () => {
    const data = await genericFetch("/current/processed", "GET")
    setShoppingList(data)
  }

  useEffect(() => {
    getCurrentIngredients()
  }, [])



  return (
    <>
      <Header />
      <main>
        <div class="create-button">
          <button onClick={getShoppingList}>Create</button>
        </div>
        <div class="grid">
          <div class="left-side">
            <section class="instructions">
              <h2>Add items to the shopping list</h2>
              <p>
                You can change the quantity with the + and - buttons <br />
                Freely add them in any order along with duplicates!
              </p>
            </section>
            <div class="list-creator">
              <IngredientInput
                addFunction={addCurrentIngredient}
              />
              <div class="output">
                <CurrentIngredients 
                  data={currentIngredients}
                  plusAction={incrementCurrentIngredient}
                  minusAction={decrementCurrentIngredient}
                />
              </div>
            </div>
          </div>
          <div class="right-side">
            <h1>Shopping list <button> <i class="fa-solid fa-file-export"></i> </button> </h1>
            <div class="shopping-list">
              <ShoppingList 
                data={shoppingList}
              />
            </div>
          </div>
        </div>
      </main>
      <Footer text="Settings" path="/settings"/>
    </>
  )
}

export default Home
