import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { HashRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import './css/general.css'
import Home from './Home.jsx'
import Settings from './Settings.jsx'
import Register from './Register.jsx'
import Login from './Login.jsx'
import Account from './Account.jsx'
import { AuthProvider, useAuth } from './fragments/AuthContext.jsx'

const ProtectedRoute = ({children}) => {
  const {authToken} = useAuth()

  if(!authToken) {
    return <Navigate to={'/account'}/>
  }
  else {
    return children
  }
}


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider>
      <Router>
        <Routes>
          <Route path='/' element={<ProtectedRoute><Home/></ProtectedRoute>}/>
          <Route path='/settings' element={<ProtectedRoute><Settings/></ProtectedRoute>}/>
          <Route path='/account' element={<Account/>}/>
          <Route path='/register' element={<Register/>}/>
          <Route path='/login' element={<Login/>}/>
        </Routes>
      </Router>
    </AuthProvider>
  </StrictMode>
)
