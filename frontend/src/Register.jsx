import { Link } from 'react-router-dom'
import sharedStyles from './css/Shared.module.css'
import accountStyles from './css/Account.module.css'

function Register() {

  return (
    <div className={`${accountStyles.global} ${sharedStyles.global}`}>
      <main className={accountStyles.main}>
        <h2>Register</h2>
        <input placeholder="Username"/>
        <input placeholder="Password"/>
        <input placeholder="Repeat password"/>
        <spacer/>
        <button>Register</button>
      </main>
      <footer>
        <Link to='/'>
          <button>Back</button>
        </Link>
      </footer>
    </div>
  )
}

export default Register