import * as React from 'react'
import { AppBar, Toolbar, Typography } from '@mui/material'

import styled from 'styled-components'
import { Link } from 'react-router-dom'
import { useContext } from 'react'
import { AuthContext } from '../security/AuthProvider'
import icon from '../components/assets/icon.png'

import { FiHome, FiFilePlus, FiMonitor, FiLogOut } from 'react-icons/fi'

export default function Navigation() {
  const { logout, username } = useContext(AuthContext)
  const handleLogout = () => {
    logout()
  }
  return (
    <StyledAppBar position="static">
      <a href="/">
        <StyledImg src={icon} alt="logo" />
      </a>

      <Toolbar>
        <StyledLink to="/">
          <Gap>
            <FiHome />
          </Gap>
          Home
        </StyledLink>
        <StyledLink to="/administration">
          <Gap>
            <FiMonitor />
          </Gap>
          Übersicht
        </StyledLink>
        <StyledLink to="/add-user">
          <Gap>
            <FiFilePlus />
          </Gap>
          User hinzufügen
        </StyledLink>
        <StyledLink to="/add-event">
          <Gap>
            <FiFilePlus />
          </Gap>
          Event hinzufügen
        </StyledLink>
        <StyledLink to="/add-appuser">
          <Gap>
            <FiFilePlus />
          </Gap>
          App User hinzufügen
        </StyledLink>
      </Toolbar>
      <Typography>Angemeldet als: {username}</Typography>
      <StyledLink to="/login" onClick={handleLogout}>
        <Gap>
          <FiLogOut />
        </Gap>
        Logout
      </StyledLink>
    </StyledAppBar>
  )
}
const Gap = styled.div`
  margin-right: 10px;
`
const StyledImg = styled.img`
  margin-top: -12px;
  margin-bottom: -12px;
  width: 15vh;
`
const StyledAppBar = styled(AppBar)`
  display: grid;
  grid-template-columns: min-content 1fr max-content min-content;
  align-items: center;
  background-color: var(--primary-blue);
`

const StyledLink = styled(Link)`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
  margin-left: 20px;
  text-decoration: none;
  :visited {
    color: var(--secondary-font);
  }
  color: var(--secondary-font);
`
