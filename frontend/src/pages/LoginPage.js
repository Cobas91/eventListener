import * as React from 'react'
import styled from 'styled-components'
import { Button, TextField } from '@mui/material'
import logo from '../components/assets/logo_name.svg'
import { useContext, useState } from 'react'

import { API_login } from '../service/loginService'
import { useHistory } from 'react-router-dom'
import { AuthContext } from '../security/AuthProvider'

export default function LoginPage() {
  const { setJWT } = useContext(AuthContext)
  const [credentials, setCredentials] = useState()
  const history = useHistory()
  const handleOnChange = e => {
    setCredentials({ ...credentials, [e.target.id]: e.target.value })
  }

  const handleSubmit = e => {
    e.preventDefault()
    API_login(credentials)
      .then(setJWT)
      .then(() => {
        history.push('/')
      })
  }

  return (
    <LoginContainer>
      <LogoBackground>
        <a href="/">
          <StyledImg src={logo} alt="logo" />
        </a>
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
          Login
        </StyledButton>
      </StyledForm>
    </LoginContainer>
  )
}

const StyledImg = styled.img`
  width: 50vh;
`

const LogoBackground = styled.div`
  position: relative;
  align-items: center;
  justify-content: center;
  display: flex;
  width: 100vw;
  height: 20vw;
  background-color: black;
  margin-bottom: 100px;
`

const LoginContainer = styled.section`
  min-height: calc(100vh - 50px);
`

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
