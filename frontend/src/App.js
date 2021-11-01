import Navigation from './components/Navigation'
import { Route, Switch } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import Homepage from './pages/Homepage'
import Administration from './pages/Administration'
import NewUser from './pages/NewUser'
import PrivateRoute from './components/PrivateRoute'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <Navigation />
      </header>
      <Switch>
        <Route path="/login">
          <LoginPage />
        </Route>
        <PrivateRoute exact path="/">
          <Homepage />
        </PrivateRoute>
        <PrivateRoute path="/administration">
          <Administration />
        </PrivateRoute>
        <PrivateRoute path="/add-user">
          <NewUser />
        </PrivateRoute>
      </Switch>
    </div>
  )
}

export default App
