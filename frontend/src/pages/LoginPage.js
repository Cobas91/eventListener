import * as React from 'react'
import styled from 'styled-components'
import { Button, TextField } from '@mui/material'
import logo from '../components/assets/logo_name.svg'
import officeLoginIcon from '../components/assets/office_login_icon.svg'
import { useContext, useState } from 'react'
import { API_login } from '../service/loginService'
import { useHistory } from 'react-router-dom'
import { AuthContext } from '../security/AuthProvider'

export default function LoginPage() {
  const { setJWT } = useContext(AuthContext)
  const [credentials, setCredentials] = useState()
  const history = useHistory()
  const officeUrl =
    'https://login.microsoftonline.com/a6c2d6ec-3753-4571-b3ad-88b705befd2b/oauth2/v2.0/authorize?' +
    'client_id=cc1c47e2-3a5c-4d32-945e-88f4691a84a0' +
    '&response_type=code' +
    '&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2Foauth%2Foffice' +
    '&response_mode=query' +
    '&scope=offline_access%20user.read%20mail.read' +
    '&state=12345'
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

        <OfficeLoginContainer href={officeUrl}>
          <StyledImgOfficeLoginIcon src={officeLoginIcon} alt="login" />
        </OfficeLoginContainer>
      </StyledForm>
    </LoginContainer>
  )
}

const StyledImgOfficeLoginIcon = styled.img`
  margin: 15px;
  width: 25vh;
  border-radius: 10px;
`

const OfficeLoginContainer = styled.a`
  display: flex;
  justify-content: center;
  align-items: center;
  :visited {
    color: black;
  }
  color: black;
`
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
