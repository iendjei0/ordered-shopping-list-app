import { Link } from 'react-router-dom'
import sharedStyles from './css/Shared.module.css'
import accountStyles from './css/Account.module.css'

function Login() {

  return (
    <div className={`${accountStyles.global} ${sharedStyles.global}`}>
      <main className={accountStyles.main}>
        <h2>Login</h2>
        <input placeholder="Username"/>
        <input placeholder="Password"/>
        <spacer/>
        <button>Login</button>
      </main>
      <footer>
        <Link to='/'>
          <button>Back</button>
        </Link>
      </footer>
    </div>
  )
}

export default Login