
function Skeleton() {
	return (
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
					<div class="input">
						<input placeholder="Insert text here"/>
						<button>+</button>
					</div>
					<div class="output">

					</div>
				</div>
			</div>
			<div class="right-side">
				<h1>The order</h1>
				<div class="ingredients-order">

				</div>
			</div>
		</div>
	)
}

export default Skeleton