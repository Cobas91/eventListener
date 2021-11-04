import * as React from 'react'
import { AppBar, Toolbar, Typography } from '@mui/material'

import styled from 'styled-components'
import { Link } from 'react-router-dom'

export default function Navigation({ logout }) {
  const handleLogout = () => {
    logout()
  }
  return (
    <StyledAppBar position="static">
      <Typography variant="h6" component="div">
        EventListener
      </Typography>

      <Toolbar>
        <StyledLink to="/">Home</StyledLink>
        <StyledLink to="/administration">Übersicht</StyledLink>
        <StyledLink to="/add-user">User hinzufügen</StyledLink>
        <StyledLink to="/login" onClick={handleLogout}>
          Logout
        </StyledLink>
      </Toolbar>
    </StyledAppBar>
  )
}

const StyledAppBar = styled(AppBar)`
  display: flex;
  flex-direction: row;
  align-items: center;
`

const StyledLink = styled(Link)`
  margin-right: 15px;
  text-decoration: none;
  :visited {
    color: black;
  }
`
