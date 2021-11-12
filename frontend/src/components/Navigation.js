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
      </Toolbar>
      <StyledLink to="/login" onClick={handleLogout}>
        Logout
      </StyledLink>
    </StyledAppBar>
  )
}

const StyledAppBar = styled(AppBar)`
  display: grid;
  grid-template-columns: min-content 1fr min-content;
  align-items: center;
`

const StyledLink = styled(Link)`
  margin-right: 15px;
  text-decoration: none;
  :visited {
    color: white;
  }
  color: white;
`
