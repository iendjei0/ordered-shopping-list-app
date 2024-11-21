
function SavedIngredients({data=[], minusAction}) {

	return (
		<>
			{data.map((ingredient) => (
				<div id={`ingredient-${ingredient.id}`}>
					<div class="text">
						<span>{ingredient.name}</span>
					</div>
					<div class="buttons">
						<button onClick={() => minusAction(ingredient.name)}>-</button>
					</div>
				</div>
			))}
		</>
	)
}

export default SavedIngredients