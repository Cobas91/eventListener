import { createContext, useEffect, useState } from 'react'
import { useHistory } from 'react-router-dom'

export const AuthContext = createContext({})

export default function AuthProvider({ children }) {
  const [JWT, setJWT] = useState(localStorage.getItem('JWT') || '')
  const history = useHistory()
  useEffect(() => {
    localStorage.setItem('JWT', JWT)
    history.push('/')
  }, [JWT, history])
  return (
    <AuthContext.Provider value={{ setJWT }}>{children}</AuthContext.Provider>
  )
}
