//TODO: scroll

function setupDraggables() {
    const draggables = document.querySelectorAll(".ingredients-order div")
    draggables.forEach((draggable) => {
        draggable.draggable = true
        draggable.addEventListener('dragstart', () => {
            draggable.classList.add("currently-dragging")
        })
        draggable.addEventListener('dragend', () => {
            draggable.classList.remove("currently-dragging")
        }) 
    })
}

function setupDragZone() {
    const dragzone = document.querySelector(".ingredients-order")

    dragzone.addEventListener("dragover", (e) => {
        e.preventDefault()
    })

    dragzone.addEventListener("drop", async (e) => {
        const dragged = document.querySelector(".currently-dragging")
        const draggedAfter = getDragAfterElement(e.clientY)
        console.log(dragged, draggedAfter)

        const name1 = getNameFromDraggable(dragged)
        const name2 = getNameFromDraggable(draggedAfter)
        
        if(name1 !== name2) {
            await swapIngredientsOrder(name1, name2)
            setupDraggables()
        }
    })
}

function getDragAfterElement(y) {
    const draggables = [...document.querySelectorAll(".ingredients-order div")]
    let target = draggables[0]
    draggables.forEach((draggable) => {
        const box = draggable.getBoundingClientRect()
        //TODO: maybe use getComputedStyle for more accuracy
        if(y > box.top) {
            target = draggable
        }
    })
    return target
}

function getNameFromDraggable(draggable) {
    return draggable.querySelector("span").innerText
}

function setupDragNDrop() {
    setupDraggables()
    setupDragZone()
}

document.addEventListener("htmlElementsLoaded", () => {
    setupDraggables()
    setupDragZone()
})