/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

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
import Datenschutz from './pages/Datenschutz'
import NewAppUser from './pages/NewAppUser'
import useResponsive from './components/hooks/useResponsive'
import MobileNavigation from './components/MobileNavigation'
import OfficeRedirectOAuth from './pages/oauth/OfficeRedirectOAuth'
import styled from 'styled-components'

function App() {
  const { isMobile } = useResponsive()
  return (
    <Theme>
      <Switch>
        <Route exact path="/login">
          <LoginPage />
          <Footer />
        </Route>
        <Route exact path="/oauth/office">
          <OfficeRedirectOAuth />
        </Route>
        <Route exact path="/impressum">
          <Impressum />
          <Footer />
        </Route>
        <Route exact path="/datenschutz">
          <Datenschutz />
          <Footer />
        </Route>
        <PrivateRoute exact path="/">
          {isMobile ? <MobileNavigation /> : <Navigation />}
          <Homepage />
        </PrivateRoute>
        <PrivateRoute path="/administration">
          {isMobile ? <MobileNavigation /> : <Navigation />}
          <Overview />
        </PrivateRoute>
        <PrivateRoute path="/add-user">
          {isMobile ? <MobileNavigation /> : <Navigation />}
          <NewUser />
        </PrivateRoute>
        <PrivateRoute path="/edit-user">
          {isMobile ? <MobileNavigation /> : <Navigation />}
          <EditUser />
        </PrivateRoute>
        <PrivateRoute path="/add-event">
          {isMobile ? <MobileNavigation /> : <Navigation />}
          <NewEvent />
        </PrivateRoute>
        <PrivateRoute path="/edit-event">
          {isMobile ? <MobileNavigation /> : <Navigation />}
          <EditEvent />
        </PrivateRoute>
        <PrivateRoute path="/add-appuser">
          {isMobile ? <MobileNavigation /> : <Navigation />}
          <NewAppUser />
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
    </Theme>
  )
}

const Theme = styled.section`
  color: var(--primary-font);
  background-color: var(--background-primary);
  height: 100vh;
`

export default App
