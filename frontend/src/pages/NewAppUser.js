import * as React from 'react'
import styled from 'styled-components'
import { Button, TextField } from '@mui/material'
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
      <StyledForm onSubmit={handleSubmit}>
        <StyledTextField
          required
          id="username"
          label="Username"
          variant="standard"
          type="username"
          onChange={handleOnChange}
        />
        <StyledTextField
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
const StyledButton = styled(Button)`
  margin: 5px;
`

const StyledTextField = styled(TextField)``

const NewAppUserContainer = styled.section`
  height: 80%;
`

const StyledForm = styled.form`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`
