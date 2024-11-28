import { useState } from "react"
import sharedStyles from '../css/Shared.module.css'

export function IngredientInput({addFunction}) {
	const [ingredient, setIngredient] = useState('')
	
	const handleInputChange = (event) => {
		setIngredient(event.target.value)
		console.log(ingredient)
	}

	const handleAdd = () => {
		if(ingredient != '') {
			addFunction(ingredient)
			setIngredient('')
		}
	}

	const handleKeyDown = (event) => {
		if(event.key === "Enter") {
			handleAdd()
		}
	}
	
	return (
		<div className={sharedStyles.input}>
			<input 
				placeholder="Insert text here"
				value={ingredient}
				onChange={handleInputChange}
				onKeyDown={handleKeyDown}
			/>
			<button onClick={handleAdd}>+</button>
		</div>
	)
}

export function AccountInput({setData, placeholder, name}) {
  const handleChange = (e) => {
    setData((data) => ({
      ...data,
      [e.target.name]: e.target.value
    }))
  }

  return (
    <input
      placeholder={placeholder}
      name={name}
      onInput={handleChange}
    />
  )
}

export function HiddenInput({ setData, placeholder, name }) {
  const handleChange = (e) => {
    setData((data) => ({
      ...data,
      [e.target.name]: e.target.value
    }));
  };

  return (
    <input
      type="password"
      placeholder={placeholder}
      name={name}
      onInput={handleChange}
      onCopy={(e)=>e.preventDefault()}
      onPaste={(e)=>e.preventDefault()}
      onCut={(e)=>e.preventDefault()}
    />
  );
}