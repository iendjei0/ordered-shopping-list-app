
function CurrentIngredients({data=[], plusAction, minusAction}) {
	
	return (
		<>
			{data.map((ingredient) => (
				<div id={`ingredient-${ingredient.id}`}>
					<div class="text">
						<span>{ingredient.name}</span>
						<span>x{ingredient.count}</span>
					</div>
					<div class="buttons">
						<button onClick={() => plusAction(ingredient.id)}>+</button>
						<button onClick={() => minusAction(ingredient.id)}>-</button>
					</div>
				</div>
			))}
		</>
	)
}

export default CurrentIngredients