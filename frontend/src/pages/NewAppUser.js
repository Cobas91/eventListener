import * as React from 'react'
import styled from 'styled-components'
import { Button, TextField, Typography } from '@mui/material'
import { useState } from 'react'
import { API_addAppUser } from '../service/appUserService'
import { showSuccess } from '../utils/notificationHandler'

export default function NewAppUser() {
  const [newUser, setNewUser] = useState()
  const handleOnChange = e => {
    setNewUser({ ...newUser, [e.target.id]: e.target.value })
  }
  const handleSubmit = e => {
    e.preventDefault()
    API_addAppUser(newUser).then(newAddedUser => {
      showSuccess(`User ${newAddedUser.username} wurde erzeugt.`)
    })
  }
  return (
    <NewAppUserContainer>
      <StyledInfoBox>
        <Typography variant="h5">Neuen App User anlegen</Typography>
        <Typography>
          Ein Lokaler Zugang zur Applikation. Mit diesem User kann sich an der
          Applikation angemeldet werden.
        </Typography>
      </StyledInfoBox>
      <StyledForm onSubmit={handleSubmit}>
        <TextField
          required
          id="username"
          label="Username"
          variant="standard"
          type="username"
          onChange={handleOnChange}
        />
        <TextField
          required
          id="password"
          label="Password"
          variant="standard"
          type="password"
          onChange={handleOnChange}
        />
        <StyledButton type="submit" variant="contained">
          Save
        </StyledButton>
      </StyledForm>
    </NewAppUserContainer>
  )
}

const StyledInfoBox = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  margin: 20px;
`
const StyledButton = styled(Button)`
  margin: 5px;
`
const NewAppUserContainer = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`
const StyledForm = styled.form`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 80%;
`
