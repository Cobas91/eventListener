import * as React from 'react'
import { useHistory, useLocation } from 'react-router-dom'
import { useContext, useEffect, useState } from 'react'
import { API_loginWithOfficeCode } from './OAuthOfficeService'
import { AuthContext } from '../../security/AuthProvider'

export default function OfficeRedirectOAuth() {
  const url =
    'https://login.microsoftonline.com/a6c2d6ec-3753-4571-b3ad-88b705befd2b/oauth2/v2.0/authorize?' +
    'client_id=cc1c47e2-3a5c-4d32-945e-88f4691a84a0' +
    '&response_type=code' +
    '&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2Foauth%2Foffice' +
    '&response_mode=query' +
    '&scope=offline_access%20user.read%20mail.read' +
    '&state=12345'
  const urlQuery = useLocation().search
  const res = new URLSearchParams(urlQuery).get('code')
  const { setJWT } = useContext(AuthContext)
  const history = useHistory()
  const [code, setCode] = useState()

  useEffect(() => {
    setCode(res)
  }, [res])
  if (res === null) {
    window.location.href = url
  }

  useEffect(() => {
    API_loginWithOfficeCode(code).then(jwt => {
      setJWT(jwt)
      history.push('/home')
    })
    // eslint-disable-next-line
  }, [code])

  return (
    <div>
      <h1>{code}</h1>
    </div>
  )
}
