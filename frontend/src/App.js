import Navigation from './components/Navigation'
import { Route, Switch } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import Homepage from './pages/Homepage'
import Administration from './pages/Administration'
import NewUser from './pages/NewUser'
import PrivateRoute from './components/PrivateRoute'
import { useContext } from 'react'
import { AuthContext } from './security/AuthProvider'
import EditUser from './pages/EditUser'

function App() {
  const { setJWT, logout } = useContext(AuthContext)
  return (
    <div className="App">
      <Switch>
        <Route exact path="/login">
          <LoginPage login={setJWT} />
        </Route>
        <PrivateRoute exact path="/">
          <Navigation logout={logout} />
          <Homepage />
        </PrivateRoute>
        <PrivateRoute path="/administration">
          <Navigation logout={logout} />
          <Administration />
        </PrivateRoute>
        <PrivateRoute path="/add-user">
          <Navigation logout={logout} />
          <NewUser />
        </PrivateRoute>
        <PrivateRoute path="/edit-user">
          <Navigation logout={logout} />
          <EditUser />
        </PrivateRoute>
      </Switch>
    </div>
  )
}

export default App
