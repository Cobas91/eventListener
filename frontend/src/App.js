import Navigation from './components/Navigation'
import { Route, Switch } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import Homepage from './pages/Homepage'
import Overview from './pages/Overview'
import NewUser from './pages/NewUser'
import PrivateRoute from './components/PrivateRoute'
import { useContext } from 'react'
import { AuthContext } from './security/AuthProvider'
import EditUser from './pages/EditUser'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import EditEvent from './pages/EditEvent'

function App() {
  const { setJWT, logout } = useContext(AuthContext)
  return (
    <>
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
          <Overview />
        </PrivateRoute>
        <PrivateRoute path="/add-user">
          <Navigation logout={logout} />
          <NewUser />
        </PrivateRoute>
        <PrivateRoute path="/edit-user">
          <Navigation logout={logout} />
          <EditUser />
        </PrivateRoute>
        <PrivateRoute path="/edit-event">
          <Navigation logout={logout} />
          <EditEvent />
        </PrivateRoute>
      </Switch>
      <ToastContainer
        position="top-center"
        hideProgressBar={true}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
    </>
  )
}

export default App
