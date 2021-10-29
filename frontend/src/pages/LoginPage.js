import * as React from 'react'
import styled from 'styled-components'
import { Button, TextField, Typography } from '@mui/material'

export default function LoginPage() {
  const handleSubmit = e => {
    e.preventDefault()
    console.log('LOGIN!')
  }

  return (
    <LoginContainer>
      <Typography>Login</Typography>
      <StyledForm onSubmit={handleSubmit}>
        <StyledTextField
          required
          id="username"
          label="Username"
          variant="standard"
          helperText="Required"
        />
        <StyledTextField
          required
          id="password"
          label="Password"
          variant="standard"
          helperText="Required"
        />
        <StyledButton type="submit" variant="contained">
          Contained
        </StyledButton>
      </StyledForm>
    </LoginContainer>
  )
}

const LoginContainer = styled.section``

const StyledButton = styled(Button)`
  margin: 5px;
`

const StyledTextField = styled(TextField)`
  margin: 5px;
`
const StyledForm = styled.form`
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  text-align: center;
`
