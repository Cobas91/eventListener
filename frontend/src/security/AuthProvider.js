/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import { createContext, useEffect, useState } from 'react'
import jwt_decode from 'jwt-decode'
export const AuthContext = createContext({})

export default function AuthProvider({ children }) {
  const [JWT, setJWT] = useState(localStorage.getItem('JWT') || '')
  const [username, setUsername] = useState('')
  useEffect(() => {
    localStorage.setItem('JWT', JWT)
    if (JWT !== '') {
      setUsername(jwt_decode(JWT).sub)
    }
  }, [JWT])

  const logout = () => {
    setJWT('')
  }
  return (
    <AuthContext.Provider value={{ setJWT, logout, username }}>
      {children}
    </AuthContext.Provider>
  )
}
