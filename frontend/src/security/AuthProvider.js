import { createContext, useEffect, useState } from 'react'

export const AuthContext = createContext({})

export default function AuthProvider({ children }) {
  const [JWT, setJWT] = useState(localStorage.getItem('JWT') || '')
  useEffect(() => {
    localStorage.setItem('JWT', JWT)
  }, [JWT])

  const logout = () => {
    setJWT('')
  }
  return (
    <AuthContext.Provider value={{ setJWT, logout }}>
      {children}
    </AuthContext.Provider>
  )
}
