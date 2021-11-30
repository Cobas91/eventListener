/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import * as React from 'react'
import styled from 'styled-components'
import { Link } from 'react-router-dom'

export default function Footer() {
  return (
    <StyledFooter>
      <StyledLink to="/impressum">Impressum</StyledLink>
      <StyledLink to="/datenschutz">Datenschutz</StyledLink>
    </StyledFooter>
  )
}

const StyledFooter = styled.footer`
  bottom: 0;
  width: 100%;
  height: 50px;
  background-color: black;
  display: flex;
  justify-content: center;
  align-items: center;
`

const StyledLink = styled(Link)`
  margin-right: 10px;
  margin-left: 5px;
  text-decoration: none;
  :visited {
    color: white;
  }
  color: white;
`
