import { createContext, useContext, useState } from "react"

const AuthContext = createContext()

export const useAuth = () => useContext(AuthContext)

export const AuthProvider = ({children}) => {

  const [authToken, setAuthToken] = useState(() => {
    return localStorage.getItem('authToken');
  })

  const login = (token) => {
    setAuthToken(token)
    localStorage.setItem('authToken', token)
  }

  const logout = () => {
    setAuthToken(null)
    localStorage.removeItem('authToken')
  }

  return (
    <AuthContext.Provider value={{authToken, login, logout}}>
      {children}
    </AuthContext.Provider>
  )
}