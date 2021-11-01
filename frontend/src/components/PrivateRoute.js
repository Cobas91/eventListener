import { Redirect, Route } from 'react-router-dom'

export default function PrivateRoute(props) {
  return localStorage.getItem('JWT') ? (
    <Route {...props} />
  ) : (
    <Redirect to={'/login'} />
  )
}
