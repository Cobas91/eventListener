import * as React from 'react'
import { AppBar, Toolbar, Typography } from '@mui/material'

import styled from 'styled-components'
import { Link } from 'react-router-dom'

export default function Navigation() {
  return (
    <StyledAppBar position="static">
      <Typography variant="h6" component="div">
        EventListener
      </Typography>

      <Toolbar>
        <StyledLink to="/login">Login</StyledLink>
      </Toolbar>
    </StyledAppBar>
  )
}

const StyledAppBar = styled(AppBar)`
  display: flex;
  flex-direction: row;
  align-items: center;
`

const StyledLink = styled(Link)``
