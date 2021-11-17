import * as React from 'react'
import { useHistory, useLocation } from 'react-router-dom'
import { useContext, useEffect } from 'react'
import { API_loginWithOfficeCode } from './OAuthOfficeService'
import { AuthContext } from '../../security/AuthProvider'
import loadingAnimation from '../../components/assets/loading.gif'

import styled from 'styled-components'
import { Typography } from '@mui/material'

export default function OfficeRedirectOAuth() {
  const urlQuery = useLocation().search
  const code = new URLSearchParams(urlQuery).get('code')
  const { setJWT } = useContext(AuthContext)
  const history = useHistory()

  useEffect(() => {
    API_loginWithOfficeCode(code)
      .then(jwt => {
        setJWT(jwt)
      })
      .then(() => {
        history.push('/')
      })
    // eslint-disable-next-line
  }, [code])

  return (
    <RedirectContainer>
      <Typography variant="h3">Logging in...</Typography>
      <StyledImg src={loadingAnimation} alt="logo" />
    </RedirectContainer>
  )
}

const RedirectContainer = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`

const StyledImg = styled.img`
  margin-top: 30vh;
  width: 20vh;
`
