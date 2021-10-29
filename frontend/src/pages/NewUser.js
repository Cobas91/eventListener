import * as React from 'react'
import { useState } from 'react'
import { Button, Card, TextField, Typography } from '@mui/material'
import styled from 'styled-components'

export default function NewUser() {
  const [newUser, setNewUser] = useState({})
  const handleSubmit = e => {
    e.preventDefault()
    console.log('CREATE NEW USER!')
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
          id="username"
          label="Username"
          variant="outlined"
          helperText="Required"
        />
        <StyledTextField
          required
          id="email"
          label="E-Mail"
          variant="outlined"
          helperText="Required"
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
