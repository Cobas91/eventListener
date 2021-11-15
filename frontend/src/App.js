import Navigation from './components/Navigation'
import { Route, Switch } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import Homepage from './pages/Homepage'
import Overview from './pages/Overview'
import NewUser from './pages/user/NewUser'
import PrivateRoute from './components/PrivateRoute'
import EditUser from './pages/user/EditUser'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import EditEvent from './pages/event/EditEvent'
import NewEvent from './pages/event/NewEvent'
import Footer from './components/Footer'
import Impressum from './pages/Impressum'

function App() {
  return (
    <>
      <Switch>
        <Route exact path="/login">
          <LoginPage />
          <Footer />
        </Route>
        <Route exact path="/impressum">
          <Impressum />
        </Route>
        <PrivateRoute exact path="/">
          <Navigation />
          <Homepage />
        </PrivateRoute>
        <PrivateRoute path="/administration">
          <Navigation />
          <Overview />
        </PrivateRoute>
        <PrivateRoute path="/add-user">
          <Navigation />
          <NewUser />
        </PrivateRoute>
        <PrivateRoute path="/edit-user">
          <Navigation />
          <EditUser />
        </PrivateRoute>
        <PrivateRoute path="/add-event">
          <Navigation />
          <NewEvent />
        </PrivateRoute>
        <PrivateRoute path="/edit-event">
          <Navigation />
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
