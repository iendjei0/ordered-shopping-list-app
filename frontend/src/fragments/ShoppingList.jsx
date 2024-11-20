function ShoppingList({data=[]}) {
  return (
    <>
      {data.map((item) => (
        <div id={`item-${item.id}`}>
          <span>{item.name}</span>
          <span>x{item.count}</span>
        </div>
      ))}
    </>
  )
}

export default ShoppingList