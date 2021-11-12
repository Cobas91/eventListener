import * as React from 'react'
import { AppBar, Toolbar } from '@mui/material'

import styled from 'styled-components'
import { Link } from 'react-router-dom'
import { useContext } from 'react'
import { AuthContext } from '../security/AuthProvider'
import icon from '../components/assets/icon.png'

export default function Navigation() {
  const { logout } = useContext(AuthContext)
  const handleLogout = () => {
    logout()
  }
  return (
    <StyledAppBar position="static">
      <StyledImg src={icon} alt="logo" />

      <Toolbar>
        <StyledLink to="/">Home</StyledLink>
        <StyledLink to="/administration">Übersicht</StyledLink>
        <StyledLink to="/add-user">User hinzufügen</StyledLink>
        <StyledLink to="/add-event">Event hinzufügen</StyledLink>
      </Toolbar>
      <StyledLink to="/login" onClick={handleLogout}>
        Logout
      </StyledLink>
    </StyledAppBar>
  )
}
const StyledImg = styled.img`
  margin-top: -12px;
  margin-bottom: -12px;
  width: 15vh;
`
const StyledAppBar = styled(AppBar)`
  display: grid;
  grid-template-columns: min-content 1fr min-content;
  align-items: center;
`

const StyledLink = styled(Link)`
  margin-right: 10px;
  margin-left: 5px;
  text-decoration: none;
  :visited {
    color: white;
  }
  color: white;
`
