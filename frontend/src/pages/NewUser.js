import * as React from 'react'
import { useState } from 'react'
import { Button, Card, TextField, Typography } from '@mui/material'
import styled from 'styled-components'
import useNotificationUsers from '../components/hooks/useNotificationUsers'

export default function NewUser() {
  const { addNotificationUser } = useNotificationUsers()
  const [newUser, setNewUser] = useState({})
  const handleSubmit = e => {
    e.preventDefault()
    addNotificationUser(newUser)
  }
  const handleOnChange = e => {
    setNewUser({ ...newUser, [e.target.id]: e.target.value })
  }
  return (
    <NewUserContainer>
      <StyledInfoBox>
        <Typography>
          Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
          nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
          sed diam voluptua. At vero eos et accusam et justo duo dolores et ea
          rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem
          ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur
          sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
          dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam
          et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
          takimata sanctus est Lorem ipsum dolor sit amet.
        </Typography>
      </StyledInfoBox>
      <StyledForm onSubmit={handleSubmit}>
        <StyledTextField
          required
          id="name"
          label="Username"
          variant="outlined"
          helperText="Required"
          onChange={handleOnChange}
        />
        <StyledTextField
          required
          id="email"
          label="E-Mail"
          variant="outlined"
          helperText="Required"
          onChange={handleOnChange}
        />
        <StyledButton type="submit" variant="contained">
          Speichern
        </StyledButton>
      </StyledForm>
    </NewUserContainer>
  )
}

const StyledInfoBox = styled(Card)`
  padding: 15px;
`

const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`
const NewUserContainer = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`

const StyledButton = styled(Button)``

const StyledTextField = styled(TextField)`
  margin: 10px;
`
