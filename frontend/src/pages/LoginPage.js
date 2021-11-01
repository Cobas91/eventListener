import * as React from 'react'
import styled from 'styled-components'
import { Button, TextField } from '@mui/material'
import logo from '../components/assets/logo.svg'
import { useState } from 'react'

import { API_login } from '../service/loginService'
import { useHistory } from 'react-router-dom'

export default function LoginPage({ login }) {
  const [credentials, setCredentials] = useState()
  const history = useHistory()
  const handleOnChange = e => {
    setCredentials({ ...credentials, [e.target.id]: e.target.value })
  }

  const handleSubmit = e => {
    e.preventDefault()
    API_login(credentials)
      .then(login)
      .then(() => {
        history.push('/')
      })
  }

  return (
    <LoginContainer>
      <LogoBackground>
        <StyledImg src={logo} alt="logo" />
      </LogoBackground>
      <StyledForm onSubmit={handleSubmit}>
        <StyledTextField
          required
          id="username"
          label="Username"
          variant="standard"
          type="username"
          helperText="Required"
          onChange={handleOnChange}
        />
        <StyledTextField
          required
          id="password"
          label="Password"
          variant="standard"
          type="password"
          helperText="Required"
          onChange={handleOnChange}
        />
        <StyledButton type="submit" variant="contained">
          Contained
        </StyledButton>
      </StyledForm>
    </LoginContainer>
  )
}

const StyledImg = styled.img`
  width: 50vh;
`

const LogoBackground = styled.div`
  align-items: center;
  justify-content: center;
  display: flex;
  width: 100vw;
  height: 20vw;
  background-color: black;
  border: black solid;
  margin-bottom: 100px;
`

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
