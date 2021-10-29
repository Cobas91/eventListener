import { Redirect, Route } from 'react-router-dom'

export default function PrivateRoute(props, loggedIn) {
  return loggedIn ? <Route {...props} /> : <Redirect to={'/login'} />
}
