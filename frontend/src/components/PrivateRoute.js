import { Redirect, Route } from 'react-router-dom'

export default function PrivateRoute(props, loggedIn) {
  //TODO Add AuthContext and Check on loggedIn
  return loggedIn ? <Route {...props} /> : <Redirect to={'/login'} />
}
