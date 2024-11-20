import { Link } from "react-router-dom"

function Footer({text, path}) {
  return (
    <footer>
      <Link to={path}>
        <button> {text} </button>
      </Link>
    </footer>
  )
}

export default Footer