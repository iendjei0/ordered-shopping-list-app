import { useState } from "react"

function IngredientInput({addFunction}) {
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
		<div class="input">
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

export default IngredientInput