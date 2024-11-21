import { useState } from "react"

function IngredientOrder({data=[], swap}) {
	const [dragged, setDragged] = useState(null)

	const handleDrop = (e, draggedAfter) => {
		if(dragged && dragged != draggedAfter) {
			swap(dragged, draggedAfter)
		}
	}

	return (
		<>
			{data.map((ingredient) => (
				<div 
					id={`ordered-ingredient-${ingredient.id}`}
					draggable
					onDragStart={() => setDragged(ingredient.name)}
					onDragEnd={() => setDragged(null)}
					onDragOver={(e) => e.preventDefault()}
					onDrop={(e) => handleDrop(e, ingredient.name)}
				>
					<i className="fa-solid fa-bars"></i> 
					<span>{ingredient.name}</span>
				</div>
			))}
		</>
	)
}

export default IngredientOrder