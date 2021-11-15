import * as React from 'react'
import { AppBar, Button, Menu, MenuItem } from '@mui/material'
import styled from 'styled-components'
import { FiAlignJustify } from 'react-icons/fi'
import { useHistory } from 'react-router-dom'
import icon from './assets/icon.png'

export default function MobileNavigation() {
  const history = useHistory()
  const [anchorEl, setAnchorEl] = React.useState(null)
  const open = Boolean(anchorEl)
  const handleClick = event => {
    setAnchorEl(event.currentTarget)
  }
  const handleClose = e => {
    history.push(e.target.id)
    setAnchorEl(null)
  }
  return (
    <StyledAppBar>
      <a href="/">
        <StyledImg src={icon} alt="logo" />
      </a>
      <StyledMenu>
        <StyledButton
          id="basic-button"
          aria-controls="basic-menu"
          aria-haspopup="true"
          aria-expanded={open ? 'true' : undefined}
          onClick={handleClick}
        >
          <StyledIcon />
          Menü
        </StyledButton>
        <Menu
          id="basic-menu"
          anchorEl={anchorEl}
          open={open}
          onClose={handleClose}
          MenuListProps={{
            'aria-labelledby': 'basic-button',
          }}
        >
          <MenuItem onClick={handleClose} id="/administration">
            Übersicht
          </MenuItem>
          <MenuItem onClick={handleClose} id="/add-user">
            User hinzufügen
          </MenuItem>
          <MenuItem onClick={handleClose} id="/add-event">
            Event hinzufügen
          </MenuItem>
          <MenuItem onClick={handleClose} id="/add-appuser">
            App User hinzufügen
          </MenuItem>
        </Menu>
      </StyledMenu>
    </StyledAppBar>
  )
}
const StyledMenu = styled.section`
  display: flex;
  align-items: center;
  justify-content: center;
`
const StyledImg = styled.img`
  margin-top: -12px;
  margin-bottom: -12px;
  width: 10vh;
`

const StyledButton = styled(Button)`
  font-size: 20px;
  color: white;
`

const StyledIcon = styled(FiAlignJustify)`
  font-size: 30px;
  padding: 5px;
`
const StyledAppBar = styled(AppBar)`
  display: grid;
  grid-template-columns: min-content 1fr;
`
