import { Link } from 'react-router-dom'
import sharedStyles from './css/Shared.module.css'
import accountStyles from './css/Account.module.css'
import { useState } from 'react'
import { AccountInput, HiddenInput } from './fragments/Input'
import { API_PATH } from './api'

function Register() {
  const [registerData, setRegisterData] = useState({
    username:'',
    password:'',
    repeatPassword:''
  })
  const [message, setMessage] = useState('')

  const registerFetch = () => {
    if(registerData.repeatPassword != registerData.password) {
      setMessage("Error: Passwords do not match")
      return
    }
    if(registerData.username === '' || registerData.password === '') {
      setMessage("Error: Fill out every field")
      return
    }

    fetch(API_PATH+'/register', {
      method:'POST', 
      headers:{
        'Content-Type': 'application/json'
      },
      body:JSON.stringify(registerData)
    })
      .then(response => {
        if(!response.CREATED){
          return response.text().then(text => { 
            setMessage(text)
            throw new Error(text || response.statusText);
          });
        }
        setMessage("Account created. You can now login.")
      })
      .catch((error) => console.log(error))
  }

  return (
    <div className={`${accountStyles.global} ${sharedStyles.global}`}>
      <main className={accountStyles.main}>
        <h2>Register</h2>
        <AccountInput
          placeholder='Username'
          name='username'
          setData={setRegisterData}
        />
        <HiddenInput
          placeholder='Password'
          name='password'
          setData={setRegisterData}
        />
        <HiddenInput
          placeholder='Repeat password'
          name='repeatPassword'
          setData={setRegisterData}
        />
        <p>{message}</p>
        <button onClick={registerFetch}>Register</button>
      </main>
      <footer>
        <Link to='/account'>
          <button>Back</button>
        </Link>
      </footer>
    </div>
  )
}

export default Register