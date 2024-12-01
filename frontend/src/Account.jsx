import { Link } from 'react-router-dom'
import sharedStyles from './css/Shared.module.css'
import accountStyles from './css/Account.module.css'
import { useAuth } from './fragments/AuthContext'

function Account() {
  const {authToken, logout} = useAuth()
  
  const Login = () => {
    if(!authToken) {
      return (
        <Link to='/login'>
          <button>Login</button>
        </Link>
      )
    }
    else {
      return (
        <button onClick={logout}>Logout</button>
      )
    }
  }

  const Home = () => {
    if(authToken) {
      return (
        <footer>
          <Link to='/'>
            <button>Home</button>
          </Link>
        </footer>
      )
    }
  }

  return (
    <div className={`${accountStyles.global} ${sharedStyles.global}`}>
      <main className={accountStyles.main}>
        <h2>Account settings</h2>
        <Login/>
        <Link to='/register'>
          <button>Register</button>
        </Link>
      </main>
      <Home/>
    </div>
  )
}

export default Account