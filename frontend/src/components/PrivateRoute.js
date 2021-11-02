import { Redirect, Route } from 'react-router-dom'
import { isExpired } from 'react-jwt'

export default function PrivateRoute(props) {
  const tokenIsValid = !isExpired(localStorage.getItem('JWT'))
  return tokenIsValid ? <Route {...props} /> : <Redirect to={'/login'} />
}
