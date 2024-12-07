import { Link } from 'react-router-dom'
import sharedStyles from './css/Shared.module.css'
import accountStyles from './css/Account.module.css'
import { AccountInput, HiddenInput } from './fragments/Input'
import { useState } from 'react'
import { useAuth } from './fragments/AuthContext'

function Login() {
  const [loginData, setLoginData] = useState({
    username:'',
    password:''
  })
  const [message, setMessage] = useState('')
  const {login} = useAuth()

  const loginFetch = () => {
    if(loginData.username === '' || loginData.password === '') {
      setMessage("Error: Fill out every field")
      return
    }

    fetch('/api/login', {
      method:'POST', 
      headers:{
        'Content-Type': 'application/json'
      },
      body:JSON.stringify(loginData)
    })
      .then(response => {
        if(!response.ok){
          return response.text().then(text => { 
            setMessage(text)
            throw new Error(text || response.statusText);
          });
        }
        setMessage("Login successful")
        return response.json()
      })
      .then(json => {
        login(json.token)
      })
      .catch((error) => console.log(error))
  }

  return (
    <div className={`${accountStyles.global} ${sharedStyles.global}`}>
      <main className={accountStyles.main}>
        <h2>Login</h2>
        <AccountInput
          placeholder='Username'
          name='username'
          setData={setLoginData}
        />
        <HiddenInput
          placeholder='Password'
          name='password'
          setData={setLoginData}
        />
        <p>{message}</p>
        <button onClick={loginFetch}>Login</button>
      </main>
      <footer>
        <Link to='/account'>
          <button>Back</button>
        </Link>
      </footer>
    </div>
  )
}

export default Login